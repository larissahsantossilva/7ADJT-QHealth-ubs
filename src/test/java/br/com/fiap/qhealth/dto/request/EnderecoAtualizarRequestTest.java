package br.com.fiap.qhealth.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoAtualizarRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarEnderecoAtualizarRequestComSucesso() {
        EnderecoAtualizarRequest dto = new EnderecoAtualizarRequest();
        dto.setRua("Rua Teste");
        dto.setNumero(123);
        dto.setCep("12345678");
        dto.setComplemento("Apto 10");
        dto.setBairro("Centro");
        dto.setCidade("São Paulo");

        assertEquals("Rua Teste", dto.getRua());
        assertEquals(123, dto.getNumero());
        assertEquals("12345678", dto.getCep());
        assertEquals("Apto 10", dto.getComplemento());
        assertEquals("Centro", dto.getBairro());
        assertEquals("São Paulo", dto.getCidade());

        // deve passar sem violações
        Set<ConstraintViolation<EnderecoAtualizarRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deveria haver violações de validação");
    }

    @Test
    void deveFalharQuandoCepNaoTemOitoDigitos() {
        EnderecoAtualizarRequest dto = new EnderecoAtualizarRequest();
        dto.setCep("123"); // CEP inválido

        Set<ConstraintViolation<EnderecoAtualizarRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(), "Deveria haver violação de validação");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cep")
                        && v.getMessage().contains("8 caracteres")));
    }

    @Test
    void deveAceitarCepComExatamenteOitoDigitos() {
        EnderecoAtualizarRequest dto = new EnderecoAtualizarRequest();
        dto.setCep("87654321"); // CEP válido

        Set<ConstraintViolation<EnderecoAtualizarRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deveria haver violação de validação");
    }
}
