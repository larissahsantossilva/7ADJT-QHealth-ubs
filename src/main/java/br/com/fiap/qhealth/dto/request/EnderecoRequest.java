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
@Schema(description = "DTO para criar Endereço")
public class EnderecoRequest {

    @NotBlank(message = "Rua é de preenchimento obrigatório.")
    private String rua;

    @NotNull(message = "número é de preenchimento obrigatório.")
    private Integer numero;

    @NotBlank(message = " cep é de preenchimento obrigatório.")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 caracteres.")
    private String cep;

    private String complemento;

    @NotBlank(message = "bairro é de preenchimento obrigatório.")
    private String bairro;

    @NotBlank(message = "cidade é de preenchimento obrigatório.")
    private String cidade;
}
