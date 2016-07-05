package br.com.cep.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import br.com.cep.api.AppRunner;
import br.com.cep.api.aspect.Message;
import br.com.cep.api.aspect.MessageType;
import junit.framework.TestCase;

/**
 * The Cep controller test.
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppRunner.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=8000", "management.port=0" })
@DirtiesContext
public class CepControllerTest extends TestCase {

	private static final String CEP_RESOURCE = "http://localhost:8000/cep-api/cep/";

    @Test
    public void testFindAddressWithWrongCep() throws Exception {
        Message expectedMessage = new Message(MessageType.Business_Logic_Error, "Address not found!");
        ResponseEntity<Message> response = callAPItoGetError("12345678");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    @Test
    public void testFindPaulistaAddress() throws Exception {
        AddressRequest siqueiraCampos = new AddressRequest("Av Paulista", "Consolação", "São Paulo", "SP");

        ResponseEntity<AddressRequest> response = callAPItoGetAddress("01311300");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(siqueiraCampos, response.getBody());
    }

    @Test
    public void testFindPresidentePrudenteAddress() throws Exception {
        AddressRequest sebastiaoSoares = new AddressRequest("Rua Tenente Nicolau Maffei", "Centro", "Presidente Prudente", "SP");

        ResponseEntity<AddressRequest> response = callAPItoGetAddress("19010010");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sebastiaoSoares, response.getBody());
    }
    
    @Test
    public void testFindSantoAndreAddress() throws Exception {
        AddressRequest sebastiaoSoares = new AddressRequest("Rua Do Centro", "Centro", "Santo André", "SP");

        ResponseEntity<AddressRequest> response = callAPItoGetAddress("09230590");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sebastiaoSoares, response.getBody());
    }

    @Test
    public void testFindPaulistaWithWrongCepOneZero() throws Exception {
        AddressRequest santaRita = new AddressRequest("Av Paulista", "Consolação", "São Paulo", "SP");

        ResponseEntity<AddressRequest> response = callAPItoGetAddress("01311302");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(santaRita, response.getBody());
    }
    
    @Test
    public void testFindPaulistaWithWrongCepTwoZero() throws Exception {
        AddressRequest santaRita = new AddressRequest("Av Paulista", "Consolação", "São Paulo", "SP");

        ResponseEntity<AddressRequest> response = callAPItoGetAddress("01311312");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(santaRita, response.getBody());
    }

    @Test
    public void testGetAddressByCepWihtInvalidCep() throws Exception {
        Message expectedMessage = new Message(MessageType.Parameter_Error, "Validation error!");
        expectedMessage.addNotification("invalid CEP");

        ResponseEntity<Message> response = callAPItoGetError("abcd");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
    }

    private ResponseEntity<Message> callAPItoGetError(final String cep) {
        return new TestRestTemplate().getForEntity(CEP_RESOURCE + cep, Message.class);
    }

    private ResponseEntity<AddressRequest> callAPItoGetAddress(final String cep) {
        return new TestRestTemplate().getForEntity(CEP_RESOURCE + cep, AddressRequest.class);
    }
}