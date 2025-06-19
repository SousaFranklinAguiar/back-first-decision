package com.example.backfirstdecision.repository;

import com.example.backfirstdecision.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
}
