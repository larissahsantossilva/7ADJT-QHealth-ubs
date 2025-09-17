package br.com.fiap.qhealth.dto.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoBodyResponseTest {

    @Test
    void deveAtribuirERecuperarTodosOsCampos() {
        // given
        UUID id = UUID.randomUUID();
        String rua = "Rua das Flores";
        Integer numero = 123;
        String cep = "12345678";
        String complemento = "Apto 45";
        String bairro = "Centro";
        String cidade = "São Paulo";
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(1);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now();

        // when
        EnderecoBodyResponse response = new EnderecoBodyResponse();
        response.setId(id);
        response.setRua(rua);
        response.setNumero(numero);
        response.setCep(cep);
        response.setComplemento(complemento);
        response.setBairro(bairro);
        response.setCidade(cidade);
        response.setDataCriacao(dataCriacao);
        response.setDataUltimaAlteracao(dataUltimaAlteracao);

        // then
        assertEquals(id, response.getId());
        assertEquals(rua, response.getRua());
        assertEquals(numero, response.getNumero());
        assertEquals(cep, response.getCep());
        assertEquals(complemento, response.getComplemento());
        assertEquals(bairro, response.getBairro());
        assertEquals(cidade, response.getCidade());
        assertEquals(dataCriacao, response.getDataCriacao());
        assertEquals(dataUltimaAlteracao, response.getDataUltimaAlteracao());
    }

    @Test
    void deveInstanciarComTodosArgumentos() {
        UUID id = UUID.randomUUID();
        String rua = "Av. Paulista";
        Integer numero = 1000;
        String cep = "87654321";
        String complemento = "Bloco B";
        String bairro = "Bela Vista";
        String cidade = "São Paulo";
        LocalDateTime dataCriacao = LocalDateTime.now().minusDays(2);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.now().minusHours(1);

        EnderecoBodyResponse response = new EnderecoBodyResponse(
                id, rua, numero, cep, complemento, bairro, cidade, dataCriacao, dataUltimaAlteracao
        );

        assertEquals(id, response.getId());
        assertEquals(rua, response.getRua());
        assertEquals(numero, response.getNumero());
        assertEquals(cep, response.getCep());
        assertEquals(complemento, response.getComplemento());
        assertEquals(bairro, response.getBairro());
        assertEquals(cidade, response.getCidade());
        assertEquals(dataCriacao, response.getDataCriacao());
        assertEquals(dataUltimaAlteracao, response.getDataUltimaAlteracao());
    }

    @Test
    void devePermitirConstrutorPadrao() {
        EnderecoBodyResponse response = new EnderecoBodyResponse();
        assertNotNull(response);
    }
}
