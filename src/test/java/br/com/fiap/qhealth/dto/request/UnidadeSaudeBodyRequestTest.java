package br.com.fiap.qhealth.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeBodyRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UnidadeSaudeBodyRequest criaRequestValido() {
        EnderecoRequest endereco = new EnderecoRequest();
        endereco.setRua("Rua Teste");
        endereco.setNumero(100);
        endereco.setCep("12345678");
        endereco.setComplemento("Ap 12");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");

        UnidadeSaudeBodyRequest request = new UnidadeSaudeBodyRequest();
        request.setNome("UBS Central");
        request.setEndereco(endereco);
        return request;
    }

    @Test
    void deveCriarRequestValido() {
        UnidadeSaudeBodyRequest req = criaRequestValido();

        assertEquals("UBS Central", req.getNome());
        assertNotNull(req.getEndereco());
        assertEquals("Rua Teste", req.getEndereco().getRua());

        Set<ConstraintViolation<UnidadeSaudeBodyRequest>> violacoes = validator.validate(req);
        assertTrue(violacoes.isEmpty(), "Não deveria haver violações");
    }

    @Test
    void deveDetectarNomeVazio() {
        UnidadeSaudeBodyRequest req = criaRequestValido();
        req.setNome("   "); // vazio/blank

        Set<ConstraintViolation<UnidadeSaudeBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("nome")
                        && v.getMessage().contains("Nome é obrigatório")));
    }

    @Test
    void deveDetectarEnderecoNulo() {
        UnidadeSaudeBodyRequest req = criaRequestValido();
        req.setEndereco(null);

        Set<ConstraintViolation<UnidadeSaudeBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("endereco")
                        && v.getMessage().contains("não deve ser nulo")));
    }

    @Test
    void deveValidarEnderecoInterno() {
        UnidadeSaudeBodyRequest req = criaRequestValido();
        req.getEndereco().setCep("abc"); // CEP inválido

        Set<ConstraintViolation<UnidadeSaudeBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("endereco.cep")
                        && v.getMessage().contains("8 caracteres")));
    }

    @Test
    void deveAtribuirECompararTodosOsCampos() {
        EnderecoRequest endereco = new EnderecoRequest();
        endereco.setRua("Rua das Flores");
        endereco.setNumero(123);
        endereco.setCep("12345678");
        endereco.setComplemento("Apto 1");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");

        UnidadeSaudeBodyRequest dto1 = new UnidadeSaudeBodyRequest();
        dto1.setNome("UBS Vila");
        dto1.setEndereco(endereco);

        // getters e toString
        assertEquals("UBS Vila", dto1.getNome());
        assertEquals(endereco, dto1.getEndereco());
        assertTrue(dto1.toString().contains("UBS Vila"));

        // equals, hashCode, canEqual
        UnidadeSaudeBodyRequest dto2 = new UnidadeSaudeBodyRequest("UBS Vila", endereco);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.canEqual(dto2));

        // equals negativo
        UnidadeSaudeBodyRequest dto3 = new UnidadeSaudeBodyRequest("Outro Nome", endereco);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "string");
    }
}
