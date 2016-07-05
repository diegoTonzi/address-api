package br.com.cep.api.controller;

import java.util.Collections;

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
 * The Address controller test.
 * @author Diego Costa (diegotonzi@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AppRunner.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=8000", "management.port=0" })
@DirtiesContext
public class AddressControllerTest extends TestCase {

    private static final String ADDRESS_RESOURCE = "http://localhost:8000/address-api/address/";

    @Test
    public void testGetAddressById() throws Exception {
        AddressRequest expected = createAddress();
        ResponseEntity<AddressRequest> responseSave = postAddressAPI(expected);

        //set generate id in expected object
        expected.setId(responseSave.getBody().getId());

        ResponseEntity<AddressRequest> response = callAddressAPI(expected.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void testDeleteAddressById() throws Exception {
        AddressRequest expected = createAddress();
        ResponseEntity<AddressRequest> responseSave = postAddressAPI(expected);

        //set generate id in expected object
        expected.setId(responseSave.getBody().getId());

        new TestRestTemplate().delete(ADDRESS_RESOURCE+expected.getId());

        Message message = new Message(MessageType.Business_Logic_Error, "Address not found!");

        ResponseEntity<Message> response = callAPItoGetError(expected.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testSaveAddressWithValidCEP() throws Exception {
        AddressRequest expected = createAddress();
        ResponseEntity<AddressRequest> response = postAddressAPI(expected);

        //set generate id in expected object
        expected.setId(response.getBody().getId());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }

    @Test
    public void testSaveAddressWithInvalidCEP() throws Exception {
        Message message = new Message(MessageType.Business_Logic_Error, "The CEP is invalid");
        AddressRequest expected = createAddress();
        expected.setCep("12345678");

        ResponseEntity<Message> response = postAddressAPIError(expected);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testSaveAddressWithInvalidFields() throws Exception {
        Message message = new Message(MessageType.Parameter_Error, "Validation error!");
        message.addNotification("Inform the address number!");
        message.addNotification("Inform the state!");
        message.addNotification("inform the CEP number!");
        message.addNotification("Inform the city!");
        message.addNotification("Inform the street!");
        Collections.sort(message.getNotifications());
        
        AddressRequest expected = createAddress();
        expected.setStreet(null);
        expected.setCep(null);
        expected.setNumber(null);
        expected.setCity(null);
        expected.setState(null);


        ResponseEntity<Message> response = postAddressAPIError(expected);
        Collections.sort(response.getBody().getNotifications());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(message, response.getBody());
    }

    @Test
    public void testEditAddress() throws Exception {
        AddressRequest expected = createAddress();
        ResponseEntity<AddressRequest> response = postAddressAPI(expected);

        //set generate id in expected object
        expected.setId(response.getBody().getId());
        expected.setCity("Santa Rita do Sapucai");

        new TestRestTemplate().put(ADDRESS_RESOURCE, expected);

        ResponseEntity<AddressRequest> responseEdited = callAddressAPI(expected.getId());
        assertEquals(HttpStatus.OK, responseEdited.getStatusCode());
        assertEquals(expected, responseEdited.getBody());


    }

    private AddressRequest createAddress() {
        AddressRequest expectedAddressRequest = new AddressRequest();
        expectedAddressRequest.setStreet("Av Paulista");
        expectedAddressRequest.setState("SP");
        expectedAddressRequest.setDistrict("Consolação");
        expectedAddressRequest.setCep("01311300");
        expectedAddressRequest.setCity("São Paulo");
        expectedAddressRequest.setComplement("Sem complemento");
        expectedAddressRequest.setNumber(28L);
        return expectedAddressRequest;
    }

    private ResponseEntity<Message> callAPItoGetError(final String id) {
        return new TestRestTemplate().getForEntity(ADDRESS_RESOURCE + id, Message.class);
    }

    private ResponseEntity<AddressRequest> callAddressAPI(final String id) {
        return new TestRestTemplate().getForEntity(ADDRESS_RESOURCE + id, AddressRequest.class);
    }

    private ResponseEntity<Message> postAddressAPIError(final AddressRequest addressRequest) {
        return new TestRestTemplate().postForEntity(ADDRESS_RESOURCE, addressRequest, Message.class);
    }

    private ResponseEntity<AddressRequest> postAddressAPI(final AddressRequest addressRequest) {
        return new TestRestTemplate().postForEntity(ADDRESS_RESOURCE, addressRequest, AddressRequest.class);
    }
}