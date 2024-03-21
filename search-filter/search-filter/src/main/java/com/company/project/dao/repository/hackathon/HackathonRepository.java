package com.company.project.dao.repository.hackathon;

import com.company.project.dao.model.hackathon.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HackathonRepository extends JpaRepository<Hackathon, String> {

}
