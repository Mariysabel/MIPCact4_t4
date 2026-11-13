package com.acti4.crudrapido.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.acti4.crudrapido.dto.AlumnoRequestDTO;
import com.acti4.crudrapido.dto.AlumnoResponseDTO;
import com.acti4.crudrapido.entity.Alumno;
import com.acti4.crudrapido.exception.ResourceNotFoundException;
import com.acti4.crudrapido.repository.AlumnoRepository;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    public List<AlumnoResponseDTO> getAlumnoss() {
        return alumnoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public AlumnoResponseDTO getAlumnoById(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el alumno con ID: " + id));

        return convertirDTO(alumno);
    }

    public AlumnoResponseDTO saveOrUpdate(AlumnoRequestDTO dto) {
        Alumno alumno = convertirEntidad(dto);
        Alumno guardado = alumnoRepository.save(alumno);

        return convertirDTO(guardado);
    }

    public void deleteAlumno(Long id) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el alumno con ID: " + id));

        alumnoRepository.delete(alumno);
    }

    public AlumnoResponseDTO updateAlumno(Long id, AlumnoRequestDTO dto) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró el alumno con ID: " + id));

        alumno.setNombre(dto.getNombre());
        alumno.setEdad(dto.getEdad());
        alumno.setEmail(dto.getEmail());

        Alumno actualizado = alumnoRepository.save(alumno);

        return convertirDTO(actualizado);
    }

    private Alumno convertirEntidad(AlumnoRequestDTO dto) {
        Alumno alumno = new Alumno();

        alumno.setNombre(dto.getNombre());
        alumno.setEdad(dto.getEdad());
        alumno.setEmail(dto.getEmail());

        return alumno;
    }

    private AlumnoResponseDTO convertirDTO(Alumno alumno) {
        AlumnoResponseDTO dto = new AlumnoResponseDTO();

        dto.setId(alumno.getId());
        dto.setNombre(alumno.getNombre());
        dto.setEdad(alumno.getEdad());

        return dto;
    }
}