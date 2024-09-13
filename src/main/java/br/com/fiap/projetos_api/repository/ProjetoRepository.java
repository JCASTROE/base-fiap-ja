package br.com.fiap.projetos_api.repository;

import br.com.fiap.projetos_api.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjetoRepository extends JpaRepository<Projeto, UUID> {
    Optional<Projeto> findByUuid(UUID uuid);

}