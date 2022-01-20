package com.ni.tiste.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="phones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String number;

    private String cityCode;

    private String countryCode;

}
