package com.boylegu.springboot_vue.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "move")
public class Move {
    private static final String COLUMN_NAME_NAME = "name";
    private static final String COLUMN_NAME_TYPE = "type";
    private static final String COLUMN_NAME_CATEGORY = "category";
    private static final String COLUMN_NAME_POWER = "power";
    private static final String COLUMN_NAME_ACCURACY = "accuracy";
    private static final String COLUMN_NAME__PP = "pp";
    private static final String COLUMN_NAME_EFFECT = "effect";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // TODO: make category, type an enum
    @Column(name = COLUMN_NAME_NAME)
    private String name;

    @Column(name = COLUMN_NAME_TYPE)
    private String type;

    @Column(name = COLUMN_NAME_CATEGORY)
    private String category;

    @Column(name = COLUMN_NAME_POWER)
    private Integer power;

    @Column(name = COLUMN_NAME_ACCURACY)
    private Integer accuracy;

    @Column(name = COLUMN_NAME__PP)
    private Integer pp;

    @Column(name = COLUMN_NAME_EFFECT)
    private String effect;

}
