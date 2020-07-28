package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @since 2020-07-29
 */
@Entity
@Data
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

}
