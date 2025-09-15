package br.com.fiap.qhealth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para atualizar Endereço")
public class EnderecoAtualizarRequest {

    private String rua;

    private Integer numero;

    @Pattern(regexp = "\\d{8}", message = "CEP deve contar 8 caracteres.")
    private String cep;

    private String complemento;

    private String bairro;

    private String cidade;
}
