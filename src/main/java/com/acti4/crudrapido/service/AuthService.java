package com.acti4.crudrapido.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acti4.crudrapido.dto.AuthResponseDTO;
import com.acti4.crudrapido.dto.LoginRequestDTO;
import com.acti4.crudrapido.dto.RegisterRequestDTO;
import com.acti4.crudrapido.entity.Usuario;
import com.acti4.crudrapido.exception.EmailAlreadyExistsException;
import com.acti4.crudrapido.exception.InvalidCredentialsException;
import com.acti4.crudrapido.repository.UsuarioRepository;
import com.acti4.crudrapido.security.JwtService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "El correo ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        usuarioRepository.save(usuario);

        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponseDTO(token, "Bearer");
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Credenciales incorrectas"));

        if (!passwordEncoder.matches(
                request.getPassword(), usuario.getPassword())) {

            throw new InvalidCredentialsException(
                    "Credenciales incorrectas");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        return new AuthResponseDTO(token, "Bearer");
    }
}