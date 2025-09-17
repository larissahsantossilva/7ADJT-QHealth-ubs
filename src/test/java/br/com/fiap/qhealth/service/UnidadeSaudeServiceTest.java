package br.com.fiap.qhealth.service;

import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.Endereco;
import br.com.fiap.qhealth.model.UnidadeSaude;
import br.com.fiap.qhealth.repository.EnderecoRepository;
import br.com.fiap.qhealth.repository.UnidadeSaudeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.dao.DataAccessException;

import java.util.*;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeSaudeServiceTest {

    @Mock
    private UnidadeSaudeRepository unidadeSaudeRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private UnidadeSaudeService unidadeSaudeService;

    private UUID ubsId;
    private UnidadeSaude unidadeSaude;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ubsId = randomUUID();
        endereco = new Endereco();
        endereco.setId(randomUUID());
        endereco.setRua("Rua A");
        unidadeSaude = UnidadeSaude.builder()
                .id(ubsId)
                .nome("UBS Teste")
                .endereco(endereco)
                .build();
    }

    @Test
    void deveBuscarUbsPorIdComSucesso() {
        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.of(unidadeSaude));

        UnidadeSaude result = unidadeSaudeService.buscarUBSPorId(ubsId);

        assertNotNull(result);
        assertEquals(ubsId, result.getId());
        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
    }

    @Test
    void deveLancarExcecaoQuandoUbsNaoEncontrada() {
        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> unidadeSaudeService.buscarUBSPorId(ubsId));

        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
    }

    @Test
    void deveCriarUbsComEndereco() {
        when(enderecoRepository.save(any())).thenReturn(endereco);
        when(unidadeSaudeRepository.save(any())).thenReturn(unidadeSaude);

        UnidadeSaude result = unidadeSaudeService.criarUBS(unidadeSaude);

        assertNotNull(result);
        assertEquals(ubsId, result.getId());
        verify(enderecoRepository, times(1)).save(any());
        verify(unidadeSaudeRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarUbs() {
        when(unidadeSaudeRepository.save(any()))
                .thenThrow(new DataAccessException("Erro ao salvar") {});

        assertThrows(UnprocessableEntityException.class,
                () -> unidadeSaudeService.criarUBS(unidadeSaude));

        verify(unidadeSaudeRepository, times(1)).save(any());
    }

    @Test
    void deveAtualizarUbsComSucesso() {
        UnidadeSaude novaUbs = UnidadeSaude.builder().nome("UBS Atualizada").build();

        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.of(unidadeSaude));
        when(unidadeSaudeRepository.save(any())).thenReturn(unidadeSaude);

        unidadeSaudeService.atualizarUBSExistente(novaUbs, ubsId);

        assertEquals("UBS Atualizada", unidadeSaude.getNome());
        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
        verify(unidadeSaudeRepository, times(1)).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarUbsNaoEncontrada() {
        UnidadeSaude novaUbs = UnidadeSaude.builder().nome("UBS Atualizada").build();
        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> unidadeSaudeService.atualizarUBSExistente(novaUbs, ubsId));

        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
        verify(unidadeSaudeRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarQuandoErroNoBanco() {
        UnidadeSaude novaUbs = UnidadeSaude.builder().nome("UBS Atualizada").build();
        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.of(unidadeSaude));
        when(unidadeSaudeRepository.save(any()))
                .thenThrow(new DataAccessException("Erro ao salvar") {});

        assertThrows(UnprocessableEntityException.class,
                () -> unidadeSaudeService.atualizarUBSExistente(novaUbs, ubsId));

        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
        verify(unidadeSaudeRepository, times(1)).save(any());
    }

    @Test
    void deveExcluirUbsComSucesso() {
        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.of(unidadeSaude));
        doNothing().when(unidadeSaudeRepository).deleteById(ubsId);

        unidadeSaudeService.excluirUBSPorId(ubsId);

        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
        verify(unidadeSaudeRepository, times(1)).deleteById(ubsId);
    }

    @Test
    void deveLancarExcecaoAoExcluirQuandoErroNoBanco() {
        when(unidadeSaudeRepository.findById(ubsId)).thenReturn(Optional.of(unidadeSaude));
        doThrow(new DataAccessException("Erro ao deletar") {})
                .when(unidadeSaudeRepository).deleteById(ubsId);

        assertThrows(UnprocessableEntityException.class,
                () -> unidadeSaudeService.excluirUBSPorId(ubsId));

        verify(unidadeSaudeRepository, times(1)).findById(ubsId);
        verify(unidadeSaudeRepository, times(1)).deleteById(ubsId);
    }
}
