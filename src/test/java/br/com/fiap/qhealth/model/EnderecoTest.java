package br.com.fiap.qhealth.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EnderecoTest {

    @Test
    void deveSetarETodosOsCamposCorretamente() {
        // given
        UUID id = UUID.randomUUID();
        String rua = "Av. Paulista";
        Integer numero = 1000;
        String cep = "01310200";
        String complemento = "Apto 101";
        String bairro = "Bela Vista";
        String cidade = "São Paulo";
        LocalDateTime dataCriacao = LocalDateTime.of(2024, 1, 1, 12, 0);
        LocalDateTime dataUltimaAlteracao = LocalDateTime.of(2024, 1, 2, 12, 0);

        // when
        Endereco endereco = new Endereco();
        endereco.setId(id);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setDataCriacao(dataCriacao);
        endereco.setDataUltimaAlteracao(dataUltimaAlteracao);

        // then
        assertThat(endereco.getId()).isEqualTo(id);
        assertThat(endereco.getRua()).isEqualTo(rua);
        assertThat(endereco.getNumero()).isEqualTo(numero);
        assertThat(endereco.getCep()).isEqualTo(cep);
        assertThat(endereco.getComplemento()).isEqualTo(complemento);
        assertThat(endereco.getBairro()).isEqualTo(bairro);
        assertThat(endereco.getCidade()).isEqualTo(cidade);
        assertThat(endereco.getDataCriacao()).isEqualTo(dataCriacao);
        assertThat(endereco.getDataUltimaAlteracao()).isEqualTo(dataUltimaAlteracao);
    }

    @Test
    void devePreencherDatasAoCriar() {
        // given
        Endereco endereco = new Endereco();

        // when
        endereco.onCreate();

        // then
        assertThat(endereco.getDataCriacao()).isNotNull();
        assertThat(endereco.getDataUltimaAlteracao()).isNotNull();
        assertThat(endereco.getDataCriacao()).isEqualTo(endereco.getDataUltimaAlteracao());
    }

    @Test
    void deveAtualizarDataUltimaAlteracaoAoAtualizar() throws InterruptedException {
        // given
        Endereco endereco = new Endereco();
        endereco.onCreate();
        LocalDateTime dataCriacao = endereco.getDataCriacao();

        // espera pra garantir que o valor de update será diferente
        Thread.sleep(100);

        // when
        endereco.onUpdate();

        // then
        assertThat(endereco.getDataCriacao()).isEqualTo(dataCriacao);
        assertThat(endereco.getDataUltimaAlteracao()).isAfter(dataCriacao);
    }
}
