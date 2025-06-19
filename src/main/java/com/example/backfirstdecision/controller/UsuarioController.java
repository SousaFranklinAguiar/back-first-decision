package com.example.backfirstdecision.controller;

import com.example.backfirstdecision.dto.UsuarioDTO;
import com.example.backfirstdecision.entity.Usuario;
import com.example.backfirstdecision.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO dto) {
        try {
            if (dto.getNome() == null || dto.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "O nome é obrigatório."));
            }

            if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "O email é obrigatório."));
            }

            if (dto.getSenha() == null || dto.getSenha().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "A senha é obrigatória."));
            }

            Usuario user = new Usuario();
            user.setNome(dto.getNome());
            user.setEmail(dto.getEmail());
            user.setSenha(dto.getSenha());

            usuarioRepository.save(user);

            return ResponseEntity.ok(Map.of("mensagem", "Usuário registrado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao registrar usuário: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            List<Usuario> usuarios = usuarioRepository.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao listar usuários: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("erro", "Usuário não encontrado."));
            }

            usuarioRepository.deleteById(id);
            return ResponseEntity.ok(Map.of("mensagem", "Usuário deletado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao deletar usuário: " + e.getMessage()));
        }
    }
}
