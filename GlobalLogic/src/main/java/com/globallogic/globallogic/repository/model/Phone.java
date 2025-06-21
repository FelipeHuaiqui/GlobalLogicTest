package com.globallogic.globallogic.repository.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long number;
    private int citycode;
    private String countrycode;

    public Phone(long number, int citycode, String countrycode) {
        this.number = number;
        this.citycode = citycode;
        this.countrycode = countrycode;
    }

    public Phone() {
    }
}
