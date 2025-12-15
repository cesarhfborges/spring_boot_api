package br.com.chfb.api.repository;

import br.com.chfb.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("""
                select distinct u
                from Usuario u
                join fetch u.roles
                join fetch u.perfil p
                where u.username = :username
            """)
    Optional<Usuario> findByUsernameWithProfile(@Param("username") String username);
}
