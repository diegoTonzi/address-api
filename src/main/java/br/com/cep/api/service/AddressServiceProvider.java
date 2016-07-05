package br.com.cep.api.service;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.cep.api.controller.AddressRequest;
import br.com.cep.api.exception.AddressNotFoundException;

/**
 * The type Address service.
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@Service
public class AddressServiceProvider implements AddressService {

    //MOCK CEP VALUES TO TEST
    private static final String PAULISTA = "01311300";
    private static final String PRESIDENTE_PRUDENTE = "19010010";
    private static final String SANTO_ANDRE = "09230590";

    private static final Integer MAX_REPLACE_WITH_ZERO = 3;

    private Map<String, AddressRequest> addressResponses = new HashMap<>();

    @Override
    public void saveAddress(final AddressRequest addressRequest) {
        if(findCep(addressRequest.getCep()) != null) {
            addressResponses.put(addressRequest.getId(), addressRequest);
        } else {
            throw new AddressNotFoundException("The CEP is invalid");
        }
    }

    @Override
    public void updateAddress(final AddressRequest addressRequest) {
        if(addressResponses.containsKey(addressRequest.getId())) {
            saveAddress(addressRequest);
        } else {
            throw new AddressNotFoundException("Address not found!");
        }
    }

    @Override
    public AddressRequest findAddressById(final String id) {
        AddressRequest addressRequest = addressResponses.get(id);
        if(addressRequest == null) {
            throw new AddressNotFoundException("Address not found!");
        }
        return addressRequest;
    }

    @Override
    public void removeAddressById(final String id) {
        if(addressResponses.containsKey(id)) {
            addressResponses.remove(id);
        } else {
            throw new AddressNotFoundException("Address not found!");
        }
    }

    @Override
    public AddressRequest findAddressByCep(String cep) {
        Integer countCepFind = 0;
        AddressRequest addressRequest = findCep(cep);

        while (addressRequest == null && countCepFind <= MAX_REPLACE_WITH_ZERO) {
            countCepFind++;
            cep = replaceWithZero(cep, countCepFind);
            addressRequest = findCep(cep);
        }

        if(addressRequest == null) {
            throw new AddressNotFoundException("Address not found!");
        }

        return addressRequest;
    }

    private String replaceWithZero(String cep, Integer countCepFind) {
        StringBuffer stringBuffer = new StringBuffer(cep);
        stringBuffer.setCharAt(cep.length()-countCepFind, '0');
        return stringBuffer.toString();
    }

    private AddressRequest findCep(final String cep) {

        //MOCK CEP TO TEST
        if(cep.equals(PAULISTA)) {
            return new AddressRequest("Av Paulista", "Consolação", "São Paulo", "SP");
        } else if(cep.equals(PRESIDENTE_PRUDENTE)) {
            return new AddressRequest("Rua Tenente Nicolau Maffei", "Centro", "Presidente Prudente", "SP");
        } else if(cep.equals(SANTO_ANDRE)) {
            return new AddressRequest("Rua Do Centro", "Centro", "Santo André", "SP");
        }

        return null;
    }
}
