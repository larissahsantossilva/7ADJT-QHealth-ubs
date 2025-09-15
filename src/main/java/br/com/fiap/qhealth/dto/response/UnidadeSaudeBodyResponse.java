package br.com.fiap.qhealth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para retorno de ubs")
public class UnidadeSaudeBodyResponse {

    private UUID id;

    private String nome;

    private EnderecoBodyResponse endereco;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimaAlteracao;

}

