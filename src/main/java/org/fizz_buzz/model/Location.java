package org.fizz_buzz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "`Locations`", schema = "`Main`")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`")
    private long id;

    @Column(name = "`Name`")
    private String name;

    @JoinColumn(name = "`UserId`")
    @ManyToOne(optional = false)
    private User userId;

    @Column(name = "`Latitude`")
    private double latitude;

    @Column(name = "`Longitude`")
    private double longitude;
}
