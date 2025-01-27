package org.fizz_buzz.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "testdata")
@Entity
public class TestData {
    @Id
    private int id;

    private String data;
}
