package com.boylegu.springboot_vue.repository;

import com.boylegu.springboot_vue.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoveRepo extends JpaRepository<Move, UUID> {

}
