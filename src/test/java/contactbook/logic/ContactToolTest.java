package contactbook.logic;

import contactbook.model.Contact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class ContactToolTest {

    private static final String TEST_CONTACT_BOOK_1 = "ContactBook1";
    private static final String TEST_CONTACT_BOOK_2 = "ContactBook2";

    private ContactTool contactService;

    @Before
    public void init() {
        contactService = new ContactTool();
    }

    @Test
    public void addContact() {
        Contact addedContact = contactService.addContact(TEST_CONTACT_BOOK_1, testContact());
        Contact contact = testContact();
        Assert.assertEquals(contact, addedContact);
    }

    @Test
    public void removeContact() {
        Contact addedContact = contactService.addContact(TEST_CONTACT_BOOK_1, testContact());
        contactService.removeContact(TEST_CONTACT_BOOK_1, addedContact.getId());
        try {
            contactService.removeContact(TEST_CONTACT_BOOK_1, addedContact.getId());
        } catch (Exception e) {
            Assert.assertEquals("Contact does not exist", e.getMessage());
        }
    }

    @Test
    public void getContactFromContactBook() {
        Contact addedContact1 = contactService.addContact(TEST_CONTACT_BOOK_1, testContact());
        Contact addedContact2 = contactService.addContact(TEST_CONTACT_BOOK_1, testContact());
        Contact contact3 = testContact();
        contact3.setName("Kate");
        Contact addedContact3 = contactService.addContact(TEST_CONTACT_BOOK_1, contact3);

        List<Contact> retrievedContacts = contactService.findContacts(TEST_CONTACT_BOOK_1);
        List<Contact> addedContacts = Arrays.asList(addedContact1, addedContact2, addedContact3);

        Assert.assertTrue(addedContacts.containsAll(retrievedContacts));
        Assert.assertTrue(retrievedContacts.containsAll(addedContacts));
    }

    @Test
    public void addContactToUnknownContactBook() {
        contactService.addContact(TEST_CONTACT_BOOK_1, testContact());
        try {
            contactService.findContacts(TEST_CONTACT_BOOK_2);
            Assert.fail("Given contact book does not exist.");
        } catch (Exception e) {
            Assert.assertEquals("Contact book with the given Id does not exist", e.getMessage());
        }
    }
    
    @Test
    public void getAllContacts() {

        List<Contact> retrievedContactsBeforeAddingDuplicates = contactService.findAllUniqueContacts(false);

        contactService.addContact(TEST_CONTACT_BOOK_1, testContact());
        contactService.addContact(TEST_CONTACT_BOOK_1, testContact());

        Contact contact2 = testContact();
        contact2.setName("Kate");

        contactService.addContact(TEST_CONTACT_BOOK_2, contact2);
        contactService.addContact(TEST_CONTACT_BOOK_2, testContact());

        List<Contact> retrievedContactsAfterAddingDuplicates = contactService.findAllUniqueContacts(false);

        Assert.assertEquals(4, retrievedContactsAfterAddingDuplicates.size() - retrievedContactsBeforeAddingDuplicates.size());
    }

    private Contact testContact() {
        return new Contact("Test", "TTest", Arrays.asList("1000000000", "9999999999"));
    }

}
