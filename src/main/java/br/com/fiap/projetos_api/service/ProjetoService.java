package br.com.fiap.projetos_api.service;

import br.com.fiap.projetos_api.dto.ProjetoDTO;
import br.com.fiap.projetos_api.model.Projeto;
import br.com.fiap.projetos_api.repository.ProjetoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    @Transactional
    public List<ProjetoDTO> getAllProjects() {
        return projetoRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Page<ProjetoDTO> getAllProjectsPaged(Pageable pageable) {
        return projetoRepository.findAll(pageable)
                .map(this::toDto);
    }

    @Transactional
    public ProjetoDTO saveProject(ProjetoDTO projetoDTO) {
        // Verificar se o projeto já existe com base no UUID
        Optional<Projeto> projetoExistente = projetoRepository.findByUuid(projetoDTO.getUuid());
        if (projetoExistente.isPresent()) {
            throw new IllegalArgumentException("Projeto com UUID já existente.");
        }
        Projeto project = this.toEntity(projetoDTO);
        Projeto savedProject = projetoRepository.save(project);
        return this.toDto(savedProject);
    }

    private Projeto toEntity(ProjetoDTO projetoDTO) {
        Projeto projeto = new Projeto();
        projeto.setNome(projetoDTO.getNome());
        projeto.setDataInicio(projetoDTO.getDataInicio());
        projeto.setDataFim(projetoDTO.getDataFim());
        return projeto;
    }

    private ProjetoDTO toDto(Projeto project) {
        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setUuid(project.getUuid());
        projetoDTO.setNome(project.getNome());
        projetoDTO.setDataInicio(project.getDataInicio());
        projetoDTO.setDataFim(project.getDataFim());
        return projetoDTO;
    }

    @Transactional(readOnly = true)
    public Optional<ProjetoDTO> buscarPorUUID(UUID uuid) {
        return projetoRepository.findByUuid(uuid).map(this::toDto);
    }



}
