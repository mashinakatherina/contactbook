package contactbook.logic;

import contactbook.exceptions.ValidationException;
import contactbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactTool {

    private static final String FIRST_CONTACT_BOOK = "first-contact-book";
    private static final String SECOND_CONTACT_BOOK = "second-contact-book";
    private final Map<String, List<Contact>> contactBook = new HashMap<>();

    public Contact addContact(String contactBookId, Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        if (!contactBook.containsKey(contactBookId)) {
            contactBook.put(contactBookId, new ArrayList<>());
        }
        contactBook.get(contactBookId).add(contact);
        return contact;
    }

    public Contact removeContact(String contactBookId, final String contactId) {
        if (!contactBook.containsKey(contactBookId)) {
            throw new ValidationException("Contact does not exist");
        }
        Optional<Contact> contactToBeRemoved = contactBook.get(contactBookId).stream()
                .filter(contact -> contact.getId().equals(contactId)).findFirst();

        if (contactToBeRemoved.isPresent()) {
            contactBook.get(contactBookId).remove(contactToBeRemoved.get());
            return contactToBeRemoved.get();
        } else {
            throw new ValidationException("Contact does not exist");
        }
    }

    public List<Contact> findContacts(String contactBookId) {

        if (!contactBook.containsKey(contactBookId)) {
            throw new ValidationException("Contact book with the given Id does not exist");
        }

        return contactBook.get(contactBookId);
    }

    public List<Contact> findAllUniqueContacts(boolean unique) {
        List<Contact> contacts = new ArrayList<>();
        contactBook.values().forEach(contacts::addAll);
        if (unique) {
            return contacts.stream().distinct().collect(Collectors.toList());
        }
        return contacts;
    }

    public ContactTool() {
        contactBook.put(FIRST_CONTACT_BOOK, new ArrayList<>());
        contactBook.put(SECOND_CONTACT_BOOK, new ArrayList<>());

        this.addContact(FIRST_CONTACT_BOOK, new Contact("Ekaterina", "Mashina", Collections.singletonList("1234567890")));
        this.addContact(FIRST_CONTACT_BOOK, new Contact("Maria", "Ekaterinina", Collections.singletonList("0987654321")));

        this.addContact(SECOND_CONTACT_BOOK, new Contact("Ekaterina", "Mashina", Collections.singletonList("1234567890")));
        this.addContact(SECOND_CONTACT_BOOK, new Contact("Maria", "Ekaterinina", Collections.singletonList("0987654321")));
    }

}
