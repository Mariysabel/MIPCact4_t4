package com.acti4.crudrapido.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acti4.crudrapido.entity.Alumno;
import com.acti4.crudrapido.service.AlumnoService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping(path = "api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> getAlumnos() {
        return alumnoService.getAlumnoss();
    }

    @GetMapping("{id}")
    public Optional<Alumno> getAlumnoById(@PathVariable("id") Long id) {
        return alumnoService.getAlumnoById(id);
    }

    @DeleteMapping("{id}")
    public void deleteAlumno(@PathVariable("id") Long id) {
        alumnoService.deleteAlumno(id);
    }

    @PostMapping
    public void saveOrUpdateAlumno(@RequestBody Alumno alumno) {
        alumnoService.saveOrUpdate(alumno);
    }

    @PutMapping("{id}")
    public void updateAlumno(@PathVariable("id") Long id, @RequestBody Alumno alumno) {
        alumno.setId(id);
        alumnoService.saveOrUpdate(alumno);
    }
}
