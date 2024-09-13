package br.com.fiap.projetos_api.dto;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class ProjetoDTO {
  
    private UUID uuid;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
