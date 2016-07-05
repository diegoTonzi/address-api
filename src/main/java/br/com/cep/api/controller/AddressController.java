package br.com.cep.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cep.api.service.AddressService;

/**
 * The type Address resource.
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@RestController
@RequestMapping(value = "/address")
@Validated
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AddressRequest> getAddressById(@PathVariable String id) {
        AddressRequest addressRequest = addressService.findAddressById(id);
        return new ResponseEntity<>(addressRequest, HttpStatus.OK);
    }

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity deleteAddressById(@PathVariable String id) {
        addressService.removeAddressById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AddressRequest> saveAddress(@Valid @RequestBody AddressRequest addressRequest) {
        addressRequest.setId(UUID.randomUUID().toString());
        addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(addressRequest, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<AddressRequest> editAddress(@Valid @RequestBody AddressRequest addressRequest) {
        addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(addressRequest, HttpStatus.OK);
    }

}
