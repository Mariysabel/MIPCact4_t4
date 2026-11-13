
package com.acti4.crudrapido.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acti4.crudrapido.dto.AlumnoRequestDTO;
import com.acti4.crudrapido.dto.AlumnoResponseDTO;
import com.acti4.crudrapido.entity.Alumno;
import com.acti4.crudrapido.repository.AlumnoRepository;

@Service
public class AlumnoService {

    @Autowired
    AlumnoRepository alumnoRepository;

    public List<AlumnoResponseDTO> getAlumnoss() {
        return alumnoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public Optional<AlumnoResponseDTO> getAlumnoById(Long id) {
        return alumnoRepository.findById(id)
                .map(this::convertirDTO);
    }

    public AlumnoResponseDTO saveOrUpdate(AlumnoRequestDTO dto) {
        Alumno alumno = convertirEntidad(dto);
        Alumno guardado = alumnoRepository.save(alumno);

        return convertirDTO(guardado);
    }

    public void deleteAlumno(Long id) {
        alumnoRepository.deleteById(id);
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

    public Optional<AlumnoResponseDTO> updateAlumno(Long id, AlumnoRequestDTO dto) {
        return alumnoRepository.findById(id).map(alumno -> {
            alumno.setNombre(dto.getNombre());
            alumno.setEdad(dto.getEdad());
            alumno.setEmail(dto.getEmail());

            Alumno actualizado = alumnoRepository.save(alumno);

            return convertirDTO(actualizado);
        });
    }

    
}
