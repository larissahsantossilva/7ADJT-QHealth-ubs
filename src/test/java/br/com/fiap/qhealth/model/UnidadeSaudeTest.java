package br.com.fiap.qhealth.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UnidadeSaudeTest {

    @Test
    void deveSetarTodosOsCamposCorretamente() {
        // given
        UUID id = UUID.randomUUID();
        String nome = "UBS Vila Madalena";
        Endereco endereco = new Endereco();
        LocalDateTime criacao = LocalDateTime.of(2023, 10, 1, 10, 0);
        LocalDateTime ultimaAlteracao = LocalDateTime.of(2023, 10, 2, 12, 0);

        // when
        UnidadeSaude unidade = UnidadeSaude.builder()
                .id(id)
                .nome(nome)
                .endereco(endereco)
                .dataCriacao(criacao)
                .dataUltimaAlteracao(ultimaAlteracao)
                .build();

        // then
        assertThat(unidade.getId()).isEqualTo(id);
        assertThat(unidade.getNome()).isEqualTo(nome);
        assertThat(unidade.getEndereco()).isEqualTo(endereco);
        assertThat(unidade.getDataCriacao()).isEqualTo(criacao);
        assertThat(unidade.getDataUltimaAlteracao()).isEqualTo(ultimaAlteracao);
    }

    @Test
    void devePreencherDatasAoCriar() {
        // given
        UnidadeSaude unidade = new UnidadeSaude();

        // when
        unidade.onCreate();

        // then
        assertThat(unidade.getDataCriacao()).isNotNull();
        assertThat(unidade.getDataUltimaAlteracao()).isNotNull();
        assertThat(unidade.getDataCriacao()).isEqualTo(unidade.getDataUltimaAlteracao());
    }

    @Test
    void deveAtualizarDataUltimaAlteracaoAoAtualizar() throws InterruptedException {
        // given
        UnidadeSaude unidade = new UnidadeSaude();
        unidade.onCreate(); // simula o create
        LocalDateTime criacaoOriginal = unidade.getDataCriacao();

        // espera um pouco pra garantir diferença
        Thread.sleep(100);

        // when
        unidade.onUpdate();

        // then
        assertThat(unidade.getDataCriacao()).isEqualTo(criacaoOriginal);
        assertThat(unidade.getDataUltimaAlteracao()).isAfter(criacaoOriginal);
    }
}
