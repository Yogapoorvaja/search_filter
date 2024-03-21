package com.company.project.dao.model.hackathon;

import com.company.project.dao.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "hackathon", schema = "company")
public class Hackathon extends AbstractEntity {

    private String name;
    private String startDate;
    private String mode;
    private String registrationFee;
    private String registerLink;
    private String imageLink;
    private String source;
}
