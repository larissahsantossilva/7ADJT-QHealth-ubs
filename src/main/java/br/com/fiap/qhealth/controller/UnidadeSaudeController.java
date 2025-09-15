package br.com.fiap.qhealth.controller;

import br.com.fiap.qhealth.dto.request.UnidadeSaudeAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.request.UnidadeSaudeBodyRequest;
import br.com.fiap.qhealth.dto.response.UnidadeSaudeBodyResponse;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.UnidadeSaude;
import br.com.fiap.qhealth.service.UnidadeSaudeService;
import br.com.fiap.qhealth.utils.QHealthUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

import static br.com.fiap.qhealth.utils.QHealthConstants.*;
import static br.com.fiap.qhealth.utils.QHealthUtils.convertToUBS;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
@RequestMapping(V1_UBS)
public class UnidadeSaudeController {

    private static final Logger logger = getLogger(UnidadeSaudeController.class);

    private final UnidadeSaudeService ubsService;

    @Operation(
            description = "Busca todos as UBS's de forma paginada.",
            summary = "Busca todos as UBS's de forma paginada.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnidadeSaude.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UnidadeSaudeBodyResponse>> listarasUBS(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        logger.info("GET | {} | Iniciado listarasUBS", V1_UBS);
        Page<UnidadeSaude> ubs = this.ubsService.listarUnidadesSaude(page, size);
        List<UnidadeSaudeBodyResponse> listaUbs = ubs.stream()
                .map(QHealthUtils::convertToUBS)
                .toList();
        logger.info("GET | {} | Finalizado listarasUBS", V1_UBS);
        return ok(listaUbs);
    }

    @Operation(
            description = "Busca UBS por id.",
            summary = "Busca UBS por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnidadeSaude.class))
                    ),
                    @ApiResponse(
                            description = NOT_FOUND,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeSaudeBodyResponse> buscarUBSPorId(@PathVariable("id") UUID id) {
        logger.info("GET | {} | Iniciado buscarUBSPorId | id: {}", V1_UBS, id);
        var ubs = ubsService.buscarUBSPorId(id);
        if (ubs != null) {
            logger.info("GET | {} | Finalizado buscarUBSPorId | id: {}", V1_UBS, id);
            return ok(convertToUBS(ubs));
        }
        logger.info("GET | {} | Finalizado √ No Content | id: {}", V1_UBS, id);
        return status(404).build();
    }

    @Operation(
            description = "Cria ubs.",
            summary = "Cria ubs.",
            responses = {
                    @ApiResponse(
                            description = UBS_CRIADO_COM_SUCESSO,
                            responseCode = HTTP_STATUS_CODE_201,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_CRIAR_UBS,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
            }
    )
    @PostMapping
    public ResponseEntity<UUID> criarUbs(@Valid @RequestBody UnidadeSaudeBodyRequest unidadeSaudeBodyRequest) {
        logger.info("POST | {} | Iniciado criarUbs | UBS: {}", V1_UBS, unidadeSaudeBodyRequest.getNome());
        UnidadeSaude ubs = ubsService.criarUBS(convertToUBS(unidadeSaudeBodyRequest));
        logger.info("POST | {} | Finalizado criarUbs", V1_UBS);
        return status(201).body(ubs.getId());
    }

    @Operation(
            description = "Atualiza ubs por id.",
            summary = "Atualiza ubs por id.",
            responses = {
                    @ApiResponse(
                            description = OK,
                            responseCode = HTTP_STATUS_CODE_200,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = UBS_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    ),
                    @ApiResponse(
                            description = ERRO_AO_ALTERAR_UBS,
                            responseCode = HTTP_STATUS_CODE_422,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarUBSExistente(@PathVariable("id") UUID id, @Valid @RequestBody UnidadeSaudeAtualizarBodyRequest unidadeSaudeAtualizarBodyRequest) {
        logger.info("PUT | {} | Iniciado atualizarUBSExistente | id: {}", V1_UBS, id);
        ubsService.atualizarUBSExistente(convertToUBS(unidadeSaudeAtualizarBodyRequest), id);
        logger.info("PUT | {} | Finalizado atualizarUBSExistente", V1_UBS);
        return ok("UBS atualizado com sucesso");
    }

    @Operation(
            description = "Exclui UBS por id.",
            summary = "Exclui UBS por id.",
            responses = {
                    @ApiResponse(
                            description = NO_CONTENT,
                            responseCode = HTTP_STATUS_CODE_204,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    ),
                    @ApiResponse(
                            description = UBS_NAO_ENCONTRADO,
                            responseCode = HTTP_STATUS_CODE_404,
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirUBSPorId(@PathVariable("id") UUID id) {
        logger.info("DELETE | {} | Iniciado excluirUBSPorId | id: {}", V1_UBS, id);
        try {
            ubsService.excluirUBSPorId(id);
            logger.info("DELETE | {} | UBS excluído com sucesso | Id: {}", V1_UBS, id);
            return noContent().build();
        } catch (UnprocessableEntityException e) {
            logger.error("DELETE | {} | Erro ao excluir UBS | Id: {} | Erro: {}", V1_UBS, id, e.getMessage());
            return status(404).body(UBS_NAO_ENCONTRADO);
        }
    }
}