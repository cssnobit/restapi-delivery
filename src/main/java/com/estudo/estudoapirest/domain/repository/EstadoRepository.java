package com.estudo.estudoapirest.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.estudoapirest.domain.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
