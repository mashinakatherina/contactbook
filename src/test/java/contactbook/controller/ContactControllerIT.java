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
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressBookApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactControllerIT {

    private static final String TEST_ADDRESS_BOOK = "contact-book1";
    private static final String CONTACTS_END_POINT = "/api/v1/address-book/contact-book1/contacts";


    @LocalServerPort
    private int port;

    @Autowired
    private ContactTool contactService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testAddContact() {
        Contact contact = new Contact("Name", "Surname", Arrays.asList("1412123345", "1412123346"));
        HttpEntity<Contact> entity = new HttpEntity<>(contact);

        ResponseEntity<Contact> response = restTemplate.exchange(
                createURLWithPort(CONTACTS_END_POINT),
                HttpMethod.POST, entity, Contact.class);

        Assert.assertEquals(200, response.getStatusCode().value());

        contactService.removeContact(TEST_ADDRESS_BOOK, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void testRemoveContact() {
        Contact contact = new Contact("Name", "Surname", Arrays.asList("1412123345", "1412123346"));
        Contact addedContact = contactService.addContact(TEST_ADDRESS_BOOK, contact);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort(TEST_ADDRESS_BOOK + addedContact.getId()),
                HttpMethod.DELETE, new HttpEntity<String>(null, null), String.class);

        Assert.assertEquals(response.getStatusCode().value(),
                200);

        Assert.assertFalse(contactService.findContacts(TEST_ADDRESS_BOOK).stream()
                .anyMatch(contact1 -> contact1.getId().equals(addedContact.getId())));
    }

    @Test
    public void testRetrieveAllContactsFromAddressBook() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/address-book/contactBookId1/contacts"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
    }

    @Test
    public void testRetrieveAllUniqueContacts() {
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/v1/contacts"),
                HttpMethod.GET, new HttpEntity<String>(null, null), String.class);
        Assert.assertEquals(response.getStatusCode().value(), 200);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
