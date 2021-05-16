package contactbook.controller;

import contactbook.AddressBookApplication;
import contactbook.model.Contact;
import contactbook.logic.ContactTool;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerValidation {
    private static final String CONTACTS_END_POINT = "/api/v1/address-book/contact-book1/contacts";
    @LocalServerPort
    private int port;

    @Autowired
    private ContactTool contactService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void addInvalidName() {
        Contact contact = new Contact("123", "Surname", Arrays.asList("1412123345", "1412123346"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(),
                HttpMethod.POST, entity, String.class);

        Assert.assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void addInvalidSurname() {
        Contact contact = new Contact("Name", "456", Arrays.asList("1412123345", "1412123346"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(),
                HttpMethod.POST, entity, String.class);

        Assert.assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void addInvalidPhone() {
        Contact contact = new Contact("Name", "Surname", Collections.singletonList("aaaaa"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(),
                HttpMethod.POST, entity, String.class);

        Assert.assertEquals(400, response.getStatusCode().value());
    }

    private String createURLWithPort() {
        return "http://localhost:" + port + ContactControllerValidation.CONTACTS_END_POINT;
    }
}
