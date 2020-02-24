package com.boylegu.springboot_vue.dto;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PokeBattleDto {
    private UUID id;
    private UUID p1Id;
//    private UUID p2Id;
}
