package br.com.fiap.qhealth.utils;

import br.com.fiap.qhealth.dto.request.UnidadeSaudeAtualizarBodyRequest;
import br.com.fiap.qhealth.dto.request.UnidadeSaudeBodyRequest;
import br.com.fiap.qhealth.dto.response.UnidadeSaudeBodyResponse;
import br.com.fiap.qhealth.exception.ResourceNotFoundException;
import br.com.fiap.qhealth.model.UnidadeSaude;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static br.com.fiap.qhealth.utils.QHealthConstants.ID_INVALIDO;
import static java.util.regex.Pattern.matches;

public class QHealthUtils {

    private static final String REGEX_UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[4][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    public static void uuidValidator(UUID id) {
        if (!matches(REGEX_UUID, id.toString())) {
            throw new ResourceNotFoundException(ID_INVALIDO);
        }
    }

    public static UnidadeSaude convertToUBS(UnidadeSaudeBodyRequest unidadeSaudeBodyRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(unidadeSaudeBodyRequest, UnidadeSaude.class);
    }

    public static UnidadeSaude convertToUBS(UnidadeSaudeAtualizarBodyRequest unidadeSaudeAtualizarBodyRequest) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(unidadeSaudeAtualizarBodyRequest, UnidadeSaude.class);
    }

    public static UnidadeSaudeBodyResponse convertToUBS(UnidadeSaude entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, UnidadeSaudeBodyResponse.class);
    }
}
