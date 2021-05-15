package contactbook.controller;

import java.util.List;

import contactbook.model.Contact;
import contactbook.logic.ContactTool;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This class contains endpoints for future developing
 * */

@RestController
public class ContactController {

    private final ContactTool contactService;

    public ContactController(ContactTool contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/api/v1/address-book/{contactBookId}/contacts")
    public Contact addContactToAddressBook(
            @PathVariable String contactBookId, @Valid @RequestBody Contact newContact) {

        return contactService.addContact(contactBookId, newContact);
    }

    @DeleteMapping("/api/v1/address-book/{contactBookId}/contacts/{contactId}")
    public Contact removeContactFromAddressBook(@PathVariable String contactBookId,
                                                @PathVariable String contactId) {
        return contactService.removeContact(contactBookId, contactId);
    }

    @GetMapping("/api/v1/address-book/{contactBookId}/contacts")
    public List<Contact> findContactsFromAddressBook(@PathVariable String contactBookId) {
        return contactService.findContacts(contactBookId);
    }

    @GetMapping("/api/v1/contacts")
    public List<Contact> findUniqueContactsFromAllAddressBooks(
            @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        return contactService.findAllUniqueContacts(unique);
    }

}
