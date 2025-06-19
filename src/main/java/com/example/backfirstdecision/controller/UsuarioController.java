package com.example.backfirstdecision.controller;

import com.example.backfirstdecision.dto.UsuarioDTO;
import com.example.backfirstdecision.entity.Usuario;
import com.example.backfirstdecision.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        Usuario user = new Usuario();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());

        usuarioRepository.save(user);

        return ResponseEntity.ok(Map.of("mensagem", "Usuário registrado com sucesso."));
    }

    @GetMapping
    public List<Usuario> listar(){
        return usuarioRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) return ResponseEntity.notFound().build();
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("mensagem", "Usuário deletado com sucesso."));
    }
}
