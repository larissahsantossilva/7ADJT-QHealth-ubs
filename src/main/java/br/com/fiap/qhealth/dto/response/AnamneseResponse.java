package br.com.fiap.qhealth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO para retorno de Anamnese")
public class AnamneseResponse {

        private UUID id;

        private boolean fumante;

        private boolean gravida;

        private boolean diabetico;

        private boolean hipertenso;

        private LocalDateTime dataCriacao;

        private LocalDateTime dataUltimaAlteracao;

}