package com.Exercice1.TD1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Exercice1.TD1.model.Server;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

}
