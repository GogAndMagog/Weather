package org.fizz_buzz.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
@Table(name = "`Users`", schema = "`Main`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`ID`", unique = true, nullable = false)
    private Long id;

    @Column(name = "`Login`")
    @NonNull
    private String login;

    @Column(name = "`Password`")
    @NonNull
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "UserId")
    private List<Location> locations = new ArrayList<>();
    ;

    public void addLocation(@NonNull Location location) {
        location.setUser(this);
        locations.add(location);
    }

    public boolean removeLocation(@NonNull String locationName) {
        return locations.stream()
                .filter(location -> location.getName().equals(locationName))
                .findFirst()
                .map(locations::remove)
                .orElse(false);
    }

    public boolean removeLocation(@NonNull Location location) {
        return locations.remove(location);
    }
}
