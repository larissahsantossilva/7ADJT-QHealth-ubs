package br.com.fiap.qhealth.dto.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeAtualizarBodyRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UnidadeSaudeAtualizarBodyRequest criaRequestValido() {
        EnderecoAtualizarRequest endereco = new EnderecoAtualizarRequest();
        endereco.setRua("Rua Teste");
        endereco.setNumero(100);
        endereco.setCep("12345678");
        endereco.setBairro("Centro");
        endereco.setCidade("São Paulo");

        return new UnidadeSaudeAtualizarBodyRequest(
                "UBS Central",
                "ubs@exemplo.com",
                "ubslogin",
                "senha123",
                "12345678901",
                "F",
                "11999999999",
                LocalDate.of(1990, 1, 1),
                endereco
        );
    }

    @Test
    void deveCriarRequestValido() {
        UnidadeSaudeAtualizarBodyRequest req = criaRequestValido();

        assertEquals("UBS Central", req.getNome());
        assertEquals("ubs@exemplo.com", req.getEmail());
        assertEquals("ubslogin", req.getLogin());
        assertEquals("senha123", req.getSenha());
        assertEquals("12345678901", req.getCpf());
        assertEquals("F", req.getGenero());
        assertEquals("11999999999", req.getTelefone());
        assertEquals(LocalDate.of(1990, 1, 1), req.getDataNascimento());
        assertNotNull(req.getEndereco());

        Set<ConstraintViolation<UnidadeSaudeAtualizarBodyRequest>> violacoes = validator.validate(req);
        assertTrue(violacoes.isEmpty(), "Não deveria haver violações");
    }

    @Test
    void deveDetectarEmailInvalido() {
        UnidadeSaudeAtualizarBodyRequest req = criaRequestValido();
        req.setEmail("email_invalido");

        Set<ConstraintViolation<UnidadeSaudeAtualizarBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("Email inválido")));
    }

    @Test
    void deveDetectarSenhaCurta() {
        UnidadeSaudeAtualizarBodyRequest req = criaRequestValido();
        req.setSenha("123"); // menor que 6

        Set<ConstraintViolation<UnidadeSaudeAtualizarBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("senha")
                        && v.getMessage().contains("mínimo 6")));
    }

    @Test
    void deveDetectarCpfInvalido() {
        UnidadeSaudeAtualizarBodyRequest req = criaRequestValido();
        req.setCpf("1234"); // não tem 11 dígitos

        Set<ConstraintViolation<UnidadeSaudeAtualizarBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("cpf")
                        && v.getMessage().contains("11 dígitos")));
    }

    @Test
    void deveDetectarGeneroInvalido() {
        UnidadeSaudeAtualizarBodyRequest req = criaRequestValido();
        req.setGenero("X"); // não é F, M, O ou U

        Set<ConstraintViolation<UnidadeSaudeAtualizarBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("genero")
                        && v.getMessage().contains("F, M, O ou U")));
    }

    @Test
    void deveValidarEnderecoInterno() {
        UnidadeSaudeAtualizarBodyRequest req = criaRequestValido();
        // Deixar o CEP inválido no endereço para testar @Valid
        req.getEndereco().setCep("abc");

        Set<ConstraintViolation<UnidadeSaudeAtualizarBodyRequest>> violacoes = validator.validate(req);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream()
                .anyMatch(v -> v.getPropertyPath().toString().contains("endereco.cep")));
    }

    @Test
    void deveAtribuirECompararTodosOsCampos() {
        EnderecoAtualizarRequest endereco = new EnderecoAtualizarRequest();
        endereco.setRua("Rua Teste");

        UnidadeSaudeAtualizarBodyRequest dto1 = new UnidadeSaudeAtualizarBodyRequest();
        dto1.setNome("UBS Centro");
        dto1.setEmail("ubs@teste.com");
        dto1.setLogin("ubslogin");
        dto1.setSenha("123456");
        dto1.setCpf("12345678901");
        dto1.setGenero("M");
        dto1.setTelefone("11999999999");
        dto1.setDataNascimento(LocalDate.of(1990, 5, 20));
        dto1.setEndereco(endereco);

        // getters e toString
        assertEquals("UBS Centro", dto1.getNome());
        assertEquals("ubs@teste.com", dto1.getEmail());
        assertEquals("ubslogin", dto1.getLogin());
        assertEquals("123456", dto1.getSenha());
        assertEquals("12345678901", dto1.getCpf());
        assertEquals("M", dto1.getGenero());
        assertEquals("11999999999", dto1.getTelefone());
        assertEquals(LocalDate.of(1990, 5, 20), dto1.getDataNascimento());
        assertEquals(endereco, dto1.getEndereco());
        assertTrue(dto1.toString().contains("UBS Centro"));

        // equals, hashCode, canEqual
        UnidadeSaudeAtualizarBodyRequest dto2 = new UnidadeSaudeAtualizarBodyRequest(
                "UBS Centro", "ubs@teste.com", "ubslogin", "123456",
                "12345678901", "M", "11999999999", LocalDate.of(1990, 5, 20), endereco
        );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertTrue(dto1.canEqual(dto2));

        // equals negativo
        UnidadeSaudeAtualizarBodyRequest dto3 = new UnidadeSaudeAtualizarBodyRequest();
        dto3.setNome("Outro Nome");
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, "string");
    }
}
