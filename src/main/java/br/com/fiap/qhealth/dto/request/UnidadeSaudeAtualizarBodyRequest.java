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
@Schema(description = "DTO para atualizar ubs")
public class UnidadeSaudeAtualizarBodyRequest {

    private String nome;

    @Email(message = "Email inválido")
    private String email;

    private String login;

    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    @Pattern(regexp = "\\d{11}", message = "CPF deve conter exatamente 11 dígitos")
    private String cpf;

    @Pattern(regexp = "[FMOU]", message = "Gênero deve ser F, M, O ou U") // ou customize como quiser
    private String genero;

    private String telefone;

    private LocalDate dataNascimento;

    @Valid
    private EnderecoAtualizarRequest endereco;
}
