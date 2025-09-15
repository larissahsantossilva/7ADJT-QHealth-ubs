package br.com.fiap.qhealth.service;

import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.exception.UnprocessableEntityException;
import br.com.fiap.qhealth.model.Endereco;
import br.com.fiap.qhealth.model.UnidadeSaude;
import br.com.fiap.qhealth.repository.EnderecoRepository;
import br.com.fiap.qhealth.repository.UnidadeSaudeRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static br.com.fiap.qhealth.utils.QHealthConstants.*;
import static br.com.fiap.qhealth.utils.QHealthUtils.uuidValidator;
import static java.time.LocalDateTime.now;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.data.domain.PageRequest.of;

@Service
@AllArgsConstructor
public class UnidadeSaudeService {

    private static final Logger logger = getLogger(UnidadeSaudeService.class);

    private final UnidadeSaudeRepository unidadeSaudeRepository;

    private final EnderecoRepository enderecoRepository;

    public Page<UnidadeSaude> listarUnidadesSaude(int page, int size) {
        return unidadeSaudeRepository.findAll(of(page, size));
    }

    public UnidadeSaude buscarUBSPorId(UUID id) {
        uuidValidator(id);
        return unidadeSaudeRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(ID_NAO_ENCONTRADO));
    }

    public UnidadeSaude criarUBS(UnidadeSaude unidadeSaude) {
        try {
            if (unidadeSaude.getEndereco() != null) {
                unidadeSaude.getEndereco().setDataCriacao(now());
                enderecoRepository.save(unidadeSaude.getEndereco());
            }
            return unidadeSaudeRepository.save(unidadeSaude);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_CRIAR_UBS, e);
            throw new UnprocessableEntityException(ERRO_AO_CRIAR_UBS);
        }
    }

    public void atualizarUBSExistente(UnidadeSaude unidadeSaude, UUID id) {
        UnidadeSaude ubsExistente = unidadeSaudeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UBS_NAO_ENCONTRADO));
        atualizarUBSExistente(unidadeSaude, ubsExistente);
        try {
            unidadeSaudeRepository.save(ubsExistente);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_ALTERAR_UBS, e);
            throw new UnprocessableEntityException(ERRO_AO_ALTERAR_UBS);
        }
    }

    public void excluirUBSPorId(UUID id) {
        uuidValidator(id);
        UnidadeSaude unidadeSaude = unidadeSaudeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UBS_NAO_ENCONTRADO));
        UUID ubsId = unidadeSaude.getId();
        try {
            unidadeSaudeRepository.deleteById(ubsId);
        } catch (DataAccessException e) {
            logger.error(ERRO_AO_DELETAR_UBS, e);
            throw new UnprocessableEntityException(ERRO_AO_DELETAR_UBS);
        }
    }

    private static void atualizarUBSExistente(UnidadeSaude unidadeSaude, UnidadeSaude unidadeExistente) {
        if (unidadeSaude != null) {
            if (unidadeSaude.getId() != null) unidadeExistente.setId(unidadeSaude.getId());
            if (unidadeSaude.getNome() != null) unidadeExistente.setNome(unidadeSaude.getNome());
            if (unidadeSaude.getDataCriacao() != null) unidadeExistente.setDataCriacao(unidadeSaude.getDataCriacao());
            atualizarEnderecoUBS(unidadeSaude, unidadeExistente);
            unidadeExistente.setDataUltimaAlteracao(now());
        }
    }

    private static void atualizarEnderecoUBS(UnidadeSaude unidadeSaude, UnidadeSaude ubsExistente) {
        if (unidadeSaude.getEndereco() != null) {
            Endereco enderecoExistente = ubsExistente.getEndereco();
            Endereco enderecoNovo = unidadeSaude.getEndereco();

            if (enderecoExistente == null) {
                ubsExistente.setEndereco(enderecoNovo);
            } else {
                if (enderecoNovo.getRua() != null) enderecoExistente.setRua(enderecoNovo.getRua());
                if (enderecoNovo.getNumero() != null) enderecoExistente.setNumero(enderecoNovo.getNumero());
                if (enderecoNovo.getBairro() != null) enderecoExistente.setBairro(enderecoNovo.getBairro());
                if (enderecoNovo.getCidade() != null) enderecoExistente.setCidade(enderecoNovo.getCidade());
                if (enderecoNovo.getCep() != null) enderecoExistente.setCep(enderecoNovo.getCep());
                if (enderecoNovo.getComplemento() != null) enderecoExistente.setComplemento(enderecoNovo.getComplemento());
                enderecoExistente.setDataUltimaAlteracao(now());
            }
        }
    }
}
