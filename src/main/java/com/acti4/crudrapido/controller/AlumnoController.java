package com.acti4.crudrapido.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acti4.crudrapido.dto.AlumnoRequestDTO;
import com.acti4.crudrapido.dto.AlumnoResponseDTO;
import com.acti4.crudrapido.service.AlumnoService;
import jakarta.validation.Valid;

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
    public ResponseEntity<AlumnoResponseDTO> createAlumno(
            @Valid @RequestBody AlumnoRequestDTO alumnoDTO) {

        AlumnoResponseDTO response = alumnoService.saveOrUpdate(alumnoDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoResponseDTO> updateAlumno(
            @PathVariable Long id,
            @Valid @RequestBody AlumnoRequestDTO alumnoDTO) {

        AlumnoResponseDTO response = alumnoService.updateAlumno(id, alumnoDTO);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable Long id) {

        alumnoService.deleteAlumno(id);

        return ResponseEntity.noContent().build();
    }
}