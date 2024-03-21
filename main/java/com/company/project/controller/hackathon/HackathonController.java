package com.company.project.controller.hackathon;

import com.company.project.controller.hackathon.dto.HackathonDto;
import com.company.project.service.hackathon.HackathonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hackathon")
@RequiredArgsConstructor
@Slf4j
public class HackathonController {

    private final HackathonService hackathonService;

    @PostMapping
    public ResponseEntity<HackathonDto> create(@RequestBody @Validated HackathonDto dto) {
        log.info("Creating new hackathon, name: {}", dto.getName());
        return ResponseEntity.ok(hackathonService.create(dto));
    }

    @GetMapping("/{hackathonId}")
    public ResponseEntity<HackathonDto> findHackathonById(@PathVariable String hackathonId) {
        log.info("Fetching hackathon with id: {}", hackathonId);
        return ResponseEntity.ok(hackathonService.getHackathonById(hackathonId));
    }

    @GetMapping
    public ResponseEntity<PageImpl<HackathonDto>> fetchAllHackathons(@RequestParam(name = "name",required = false) String name,
                                                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                                                  @RequestParam(name = "size", defaultValue = "20") int size){
        log.info("Fetching all hackathons with, name: {}", name);
        return ResponseEntity.ok(hackathonService.findByHiddenFalse(name, page, size));
    }

    @PutMapping("/{hackathonId}")
    public ResponseEntity<HackathonDto> update(@PathVariable String hackathonId, @RequestBody HackathonDto dto) {
        log.info("Updating hackathon with id: {}", hackathonId);
        return ResponseEntity.ok(hackathonService.update(hackathonId, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        log.info("Deleting hackathon with id: {}", id);
        hackathonService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
