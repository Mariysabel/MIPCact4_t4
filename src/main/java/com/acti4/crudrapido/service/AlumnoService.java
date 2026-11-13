package com.acti4.crudrapido.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acti4.crudrapido.entity.Alumno;
import com.acti4.crudrapido.repository.AlumnoRepository;

@Service 
public class AlumnoService {
    
    @Autowired 
    AlumnoRepository alumnoRepository;

    public List<Alumno> getAlumnoss() {
        return alumnoRepository.findAll();
    }

    public Optional<Alumno> getAlumnoById(Long id) {
        return alumnoRepository.findById(id);
    }

    public void saveOrUpdate(Alumno alumno) {
        alumnoRepository.save(alumno);
    }

    public void deleteAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }
}
