package br.com.fiap.projetos_api.controller;

import br.com.fiap.projetos_api.dto.ProjetoDTO;
import br.com.fiap.projetos_api.service.ProjetoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/projetos")
@AllArgsConstructor
public class ProjetoController {

    private final ProjetoService projetoService;

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> getAllProjects() {
        return ResponseEntity.ok(projetoService.getAllProjects());
    }

    @GetMapping("/paged")
    public Page<ProjetoDTO> getAllProjectsPaged(Pageable pageable) {
        return projetoService.getAllProjectsPaged(pageable);
    }



    // Endpoint para buscar projeto por UUID
    @GetMapping("/{uuid}")
    public ResponseEntity<ProjetoDTO> buscarPorUUID(@PathVariable UUID uuid) {
        Optional<ProjetoDTO> projetoDTO = projetoService.buscarPorUUID(uuid);
        return projetoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjetoDTO projetoDTO) {
        try {
            ProjetoDTO novoProjeto = projetoService.saveProject(projetoDTO);
            return new ResponseEntity<>(novoProjeto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Se o projeto já existe, retorna 409 Conflict
            return new ResponseEntity<>("Projeto com UUID já existe.", HttpStatus.CONFLICT);
        }

    }

}
