package com.company.project.service.hackathon;

import com.company.project.controller.hackathon.dto.HackathonDto;
import com.company.project.dao.model.hackathon.Hackathon;
import com.company.project.dao.repository.hackathon.HackathonRepository;
import com.company.project.service.AbstractService;
import com.company.project.service.common.searchable.SearchCriteria;
import com.company.project.service.common.searchable.SearchField;
import com.company.project.service.hackathon.mapper.HackathonDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Service
public class HackathonService extends AbstractService<Hackathon> {

    private final HackathonRepository hackathonRepository;

    @Transactional
    public HackathonDto create(HackathonDto dto) {
        Hackathon hackathon = new Hackathon();

        update(hackathon, dto);

        return HackathonDtoMapper.map(hackathon);
    }

    @Transactional(readOnly = true)
    public HackathonDto getHackathonById(String id) {
        return hackathonRepository
                .findByIdAndAppCustomerId(id, getCurrentUserAppCustomerIdThrowable())
                .map(HackathonDtoMapper::map)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon with given id " + id + " not found"));
    }

    public PageImpl<HackathonDto> findByHiddenFalse(String name, int page, int size) {

        List<SearchField> searchFields = new ArrayList<>();
        searchFields.add(SearchField.eqBoolean("hidden",false));
        searchFields.add(SearchField.eqString("appCustomer.id", getCurrentUserAppCustomerIdThrowable()));

        if (name != null) {
            searchFields.add(SearchField.like("name", name));
        }

        SearchCriteria searchCriteria = new SearchCriteria(searchFields, page, size, "creationTimestamp", SearchCriteria.Direction.findByValue("desc"));
        Page<Hackathon> hackathons = findPageBy(searchCriteria);
        List<HackathonDto> dtos = hackathons
                .stream()
                .map(HackathonDtoMapper::map)
                .toList();
        return new PageImpl<>(dtos, hackathons.getPageable(), hackathons.getTotalElements());
    }

    @Transactional
    public HackathonDto update(String id, HackathonDto dto) {
        Hackathon hackathon = hackathonRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hackathon with given id " + id + " not found"));

        update(hackathon, dto);

        return HackathonDtoMapper.map(hackathon);
    }

    private void update(Hackathon entity, HackathonDto dto) {
        hackathonRepository.findById(dto.getName())
                .ifPresent(hackathon -> {
                    if(!hackathon.equals(entity)){
                        throw new ResourcesExistsException("Hackathon with given name already exists");
                    }
                });

        entity.setName(dto.getName());
        entity.setDefaultCampaignPhoneNumber(dto.getDefaultCampaignPhoneNumber());
        entity.setAppCustomer(appCustomerService.findCurrent());
        repository.save(entity);
    }

    @Transactional
    public void deleteById(String id) {
        Hackathon hackathon = hackathonRepository
                .findByIdAndAppCustomerId(id, getCurrentUserAppCustomerIdThrowable())
                .orElseThrow(() -> new EntityNotFoundException("Hackathon with given id " + id + " not found"));

        if (isAnyCampaignActive(hackathon)) {
            throw new ActiveCampaignException("Cannot delete Hackathon with running campaigns");

        }
        hackathon.setHidden(true);
        hackathonSubscriptionService.hideHackathonSubscriptions(hackathon.getHackathonSubscriptions());
        customerService.hideCustomersFromHackathon(id);
        repository.save(hackathon);
    }

    private boolean isAnyCampaignActive(Hackathon hackathon) {
        return campaignRepository.countByHackathonIdAndStatusAndHackathon_AppCustomerId(hackathon.getId(), ACTIVE, getCurrentUserAppCustomerIdThrowable()) > 0;
    }
}
