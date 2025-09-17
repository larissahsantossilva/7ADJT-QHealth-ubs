package br.com.fiap.qhealth.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarEnderecoRequestComSucesso() {
        EnderecoRequest dto = new EnderecoRequest();
        dto.setRua("Rua Teste");
        dto.setNumero(123);
        dto.setCep("12345678");
        dto.setComplemento("Apto 101");
        dto.setBairro("Centro");
        dto.setCidade("São Paulo");

        assertEquals("Rua Teste", dto.getRua());
        assertEquals(123, dto.getNumero());
        assertEquals("12345678", dto.getCep());
        assertEquals("Apto 101", dto.getComplemento());
        assertEquals("Centro", dto.getBairro());
        assertEquals("São Paulo", dto.getCidade());

        Set<ConstraintViolation<EnderecoRequest>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Não deveria haver violações de validação");
    }

    @Test
    void deveFalharQuandoCamposObrigatoriosForemNulosOuVazios() {
        EnderecoRequest dto = new EnderecoRequest(); // todos os campos nulos

        Set<ConstraintViolation<EnderecoRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rua")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("numero")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cep")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("bairro")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("cidade")));
    }

    @Test
    void deveFalharQuandoCepNaoTemOitoDigitos() {
        EnderecoRequest dto = new EnderecoRequest();
        dto.setRua("Rua X");
        dto.setNumero(10);
        dto.setCep("123"); // inválido
        dto.setBairro("Centro");
        dto.setCidade("São Paulo");

        Set<ConstraintViolation<EnderecoRequest>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v ->
                v.getPropertyPath().toString().equals("cep")
                        && v.getMessage().contains("8 caracteres")));
    }

    @Test
    void deveAceitarCepComExatamenteOitoDigitos() {
        EnderecoRequest dto = new EnderecoRequest();
        dto.setRua("Rua Y");
        dto.setNumero(42);
        dto.setCep("87654321"); // válido
        dto.setBairro("Centro");
        dto.setCidade("Rio de Janeiro");

        Set<ConstraintViolation<EnderecoRequest>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(), "Não deveria haver violação de validação");
    }
}
