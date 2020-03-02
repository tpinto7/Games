//package com.boylegu.springboot_vue.entities;
//
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString
//@Entity
//@Table(name = "pokemon")
//public class PokemonMove {
//    private static final String COLUMN_NAME_POKEMON = "pokemon";
//    private static final String COLUMN_NAME_MOVE = "move";
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @EqualsAndHashCode.Include
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name= COLUMN_NAME_POKEMON, referencedColumnName = "id", nullable=false)
//    private Pokemon pokemon;
//
//    @EqualsAndHashCode.Include
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name= COLUMN_NAME_MOVE, referencedColumnName = "id", nullable=false)
//    private Move move;
//}
