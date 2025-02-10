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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "`Locations`", schema = "`Main`")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`", nullable = false, unique = true)
    private Long id;

    @Column(name = "`Name`")
    @NonNull
    private String name;

    @JoinColumn(name = "`UserId`")
    @ManyToOne(optional = false)
    @NonNull
    private User user;

    @Column(name = "`Latitude`")
    @NonNull
    private Double latitude;

    @Column(name = "`Longitude`")
    @NonNull
    private Double longitude;
}
