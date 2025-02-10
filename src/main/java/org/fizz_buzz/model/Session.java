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

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "`Sessions`", schema = "`Main`")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "`ID`", unique = true, nullable = false)
    private UUID id;

    @JoinColumn(name = "`UserId`")
    @ManyToOne(optional = false)
    @NonNull
    private User user;

    @Column(name = "`ExpiresAt`")
    @NonNull
    private Date expiresAt;
}
