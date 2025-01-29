package com.pragma.powerup.infrastructure.output.jpa.entity;

import com.pragma.powerup.domain.enums.RolesEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;
import javax.persistence.Column;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RolesEnum name;

    private String description;
}
