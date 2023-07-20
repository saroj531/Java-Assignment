package com.personal.JavaAssignment.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Roles() {
    }

    public Roles(String name) {
        this.id = id;
        this.name = name;
    }
}
