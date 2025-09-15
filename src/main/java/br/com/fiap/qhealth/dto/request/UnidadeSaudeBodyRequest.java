package br.com.fiap.qhealth.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para criar ubs")
public class UnidadeSaudeBodyRequest {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Valid
    @NotNull
    private EnderecoRequest endereco;

}
