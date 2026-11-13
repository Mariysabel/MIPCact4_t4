package com.acti4.crudrapido.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acti4.crudrapido.entity.Alumno;

@Repository 
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    
}
