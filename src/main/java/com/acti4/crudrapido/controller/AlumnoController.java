
package com.acti4.crudrapido.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.acti4.crudrapido.dto.AlumnoRequestDTO;
import com.acti4.crudrapido.dto.AlumnoResponseDTO;
import com.acti4.crudrapido.service.AlumnoService;

@RestController
@RequestMapping(path = "api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<AlumnoResponseDTO> getAlumnos() {
        return alumnoService.getAlumnoss();
    }

    @GetMapping("{id}")
    public ResponseEntity<AlumnoResponseDTO> getAlumnoById(
            @PathVariable("id") Long id) {

        Optional<AlumnoResponseDTO> alumno =
                alumnoService.getAlumnoById(id);

        return alumno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public void deleteAlumno(@PathVariable("id") Long id) {
        alumnoService.deleteAlumno(id);
    }

    @PostMapping
    public AlumnoResponseDTO saveAlumno(
            @RequestBody AlumnoRequestDTO dto) {

        return alumnoService.saveOrUpdate(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<AlumnoResponseDTO> updateAlumno(
            @PathVariable("id") Long id,
            @RequestBody AlumnoRequestDTO dto) {

        Optional<AlumnoResponseDTO> alumno =
                alumnoService.updateAlumno(id, dto);

        return alumno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
