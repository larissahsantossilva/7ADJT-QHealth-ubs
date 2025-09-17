package br.com.fiap.qhealth.controller;

import br.com.fiap.qhealth.dto.request.UnidadeSaudeAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.request.UnidadeSaudeBodyRequest;
import br.com.fiap.qhealth.dto.response.UnidadeSaudeBodyResponse;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.UnidadeSaude;
import br.com.fiap.qhealth.service.UnidadeSaudeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeSaudeControllerTest {

    @Mock
    private UnidadeSaudeService ubsService;

    @InjectMocks
    private UnidadeSaudeController controller;

    private UUID ubsId;
    private UnidadeSaude unidadeSaude;
    private UnidadeSaudeBodyRequest bodyRequest;
    private UnidadeSaudeAtualizarBodyRequest atualizarBodyRequest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        ubsId = UUID.randomUUID();
        unidadeSaude = UnidadeSaude.builder()
                .id(ubsId)
                .nome("UBS Teste")
                .build();

        bodyRequest = new UnidadeSaudeBodyRequest();
        bodyRequest.setNome("UBS Teste");

        atualizarBodyRequest = new UnidadeSaudeAtualizarBodyRequest();
        atualizarBodyRequest.setNome("UBS Atualizada");
    }

    @Test
    void deveListarUBS() {
        Page<UnidadeSaude> page = new PageImpl<>(List.of(unidadeSaude));
        when(ubsService.listarUnidadesSaude(0, 10)).thenReturn(page);

        ResponseEntity<List<UnidadeSaudeBodyResponse>> response = controller.listarasUBS(0, 10);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(ubsService, times(1)).listarUnidadesSaude(0, 10);
    }

    @Test
    void deveBuscarUBSPorId() {
        when(ubsService.buscarUBSPorId(ubsId)).thenReturn(unidadeSaude);

        ResponseEntity<UnidadeSaudeBodyResponse> response = controller.buscarUBSPorId(ubsId);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("UBS Teste", response.getBody().getNome());
        verify(ubsService, times(1)).buscarUBSPorId(ubsId);
    }

    @Test
    void deveCriarUBS() {
        when(ubsService.criarUBS(any(UnidadeSaude.class))).thenReturn(unidadeSaude);

        ResponseEntity<UUID> response = controller.criarUbs(bodyRequest);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(ubsId, response.getBody());
        verify(ubsService, times(1)).criarUBS(any(UnidadeSaude.class));
    }

    @Test
    void deveAtualizarUBS() {
        doNothing().when(ubsService).atualizarUBSExistente(any(UnidadeSaude.class), eq(ubsId));

        ResponseEntity<String> response = controller.atualizarUBSExistente(ubsId, atualizarBodyRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UBS atualizado com sucesso", response.getBody());
        verify(ubsService, times(1)).atualizarUBSExistente(any(UnidadeSaude.class), eq(ubsId));
    }

    @Test
    void deveExcluirUBS() {
        doNothing().when(ubsService).excluirUBSPorId(ubsId);

        ResponseEntity<String> response = controller.excluirUBSPorId(ubsId);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(ubsService, times(1)).excluirUBSPorId(ubsId);
    }

}
