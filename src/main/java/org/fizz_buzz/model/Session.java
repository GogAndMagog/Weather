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

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "`Sessions`", schema = "`Main`")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "`ID`")
    private UUID id;

    @JoinColumn(name = "`UserId`", nullable = false)
    @ManyToOne(optional = false)
    private User user;

    @Column(name = "`ExpiresAt`", nullable = false)
    private Date expiresAt;
}
