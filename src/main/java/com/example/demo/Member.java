package com.example.demo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @since 2020-07-28
 */
@Entity
@Data
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    private String email;
}
