package br.com.fiap.qhealth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para retorno de Endereço")
public class EnderecoBodyResponse {

    private UUID id;

    private String rua;

    private Integer numero;

    private String cep;

    private String complemento;

    private String bairro;

    private String cidade;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataUltimaAlteracao;
}
