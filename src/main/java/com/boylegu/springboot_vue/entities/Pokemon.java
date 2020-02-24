package com.boylegu.springboot_vue.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "Pokemon")
public class Pokemon {
    private static final String COLUMN_NAME_INDEX = "number";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_HP = "hp";
    private static final String COLUMN_NAME_ATTACK = "attack";
    private static final String COLUMN_NAME_DEFENSE = "defense";
    private static final String COLUMN_NAME_SPEED = "speed";
    private static final String COLUMN_NAME_SP_ATTACK = "sp_attack";
    private static final String COLUMN_NAME_SP_DEFENSE = "sp_defense";
    private static final String COLUMN_NAME_IMAGE = "image";
    private static final String COLUMN_NAME_TYPE = "type";
    private static final String COLUMN_NAME_TYPE2 = "type2";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = COLUMN_NAME_INDEX)
    private Integer index;

    @Column(name = COLUMN_NAME_NAME)
    private String name;

    @Column(name = COLUMN_NAME_HP)
    private Integer hp;

    @Column(name = COLUMN_NAME_ATTACK)
    private Integer attack;

    @Column(name = COLUMN_NAME_DEFENSE)
    private Integer defense;

    @Column(name = COLUMN_NAME_SPEED)
    private Integer speed;

    @Column(name = COLUMN_NAME_SP_ATTACK)
    private Integer spAttack;

    @Column(name = COLUMN_NAME_SP_DEFENSE)
    private Integer spDefense;

    @Column(name = COLUMN_NAME_IMAGE)
    private String image;

    @Column(name = COLUMN_NAME_TYPE)
    private String type;

    @Column(name = COLUMN_NAME_TYPE2)
    private String type2;
}
