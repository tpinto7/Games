package com.boylegu.springboot_vue.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "battle")
public class PokemonBattle {
    private static final String COLUMN_NAME_USER = "user";
    private static final String COLUMN_NAME_USER_2 = "user_2";

    // TODO: Add Datetime to keep track of when game was played ... could have effective / ineffective ? or sm like that ,,, keep track too of in case game was not finished

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= COLUMN_NAME_USER, referencedColumnName = "id", nullable=false)
    private User user;

    @EqualsAndHashCode.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= COLUMN_NAME_USER_2, referencedColumnName = "id", nullable=false)
    private User user2;

    @ElementCollection
    private List<Pokemon> pokemon;

    @ElementCollection
    private List<Pokemon> pokemon2;

    private Integer currIndex = 0;
    private Integer currIndex2 = 0;
}
