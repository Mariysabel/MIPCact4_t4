package com.acti4.crudrapido.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acti4.crudrapido.dto.AlumnoRequestDTO;
import com.acti4.crudrapido.dto.AlumnoResponseDTO;
import com.acti4.crudrapido.service.AlumnoService;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseEntity<List<AlumnoResponseDTO>> getAlumnos() {
        return ResponseEntity.ok(alumnoService.getAlumnoss());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoResponseDTO> getAlumnoById(
            @PathVariable Long id) {

        return ResponseEntity.ok(alumnoService.getAlumnoById(id));
    }

    @PostMapping
    public ResponseEntity<AlumnoResponseDTO> saveAlumno(
            @RequestBody AlumnoRequestDTO dto) {

        AlumnoResponseDTO alumno = alumnoService.saveOrUpdate(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(alumno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoResponseDTO> updateAlumno(
            @PathVariable Long id,
            @RequestBody AlumnoRequestDTO dto) {

        return ResponseEntity.ok(alumnoService.updateAlumno(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {

        alumnoService.deleteAlumno(id);

        return ResponseEntity.noContent().build();
    }
}