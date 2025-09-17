package br.com.fiap.qhealth.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnamneseResponseTest {

    @Test
    void deveAtribuirERecuperarTodosOsCampos() {
        // given
        UUID id = UUID.randomUUID();
        boolean fumante = true;
        boolean gravida = false;
        boolean diabetico = true;
        boolean hipertenso = false;
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        // when
        AnamneseResponse response = new AnamneseResponse();
        response.setId(id);
        response.setFumante(fumante);
        response.setGravida(gravida);
        response.setDiabetico(diabetico);
        response.setHipertenso(hipertenso);
        response.setDataCriacao(dataCriacao);
        response.setDataUltimaAlteracao(dataUltimaAlteracao);

        // then
        assertEquals(id, response.getId());
        assertTrue(response.isFumante());
        assertFalse(response.isGravida());
        assertTrue(response.isDiabetico());
        assertFalse(response.isHipertenso());
        assertEquals(dataCriacao, response.getDataCriacao());
        assertEquals(dataUltimaAlteracao, response.getDataUltimaAlteracao());
    }

    @Test
    void devePermitirInstanciacaoComConstrutorPadrao() {
        AnamneseResponse response = new AnamneseResponse();
        assertNotNull(response);
    }
}
