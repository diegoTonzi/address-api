package br.com.cep.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.cep.api.service.AddressService;
import br.com.cep.api.validation.Cep;

/**
 * The Cep controller type
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@RestController
@RequestMapping(value = "/cep")
@Validated
public class CepController {

	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "/{cep}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public HttpEntity<AddressRequest> getAddressByCep(@Valid @Cep @PathVariable String cep) {
		AddressRequest addressResponse = addressService.findAddressByCep(cep);
		return new ResponseEntity<AddressRequest>(addressResponse, HttpStatus.OK);
	}

}
