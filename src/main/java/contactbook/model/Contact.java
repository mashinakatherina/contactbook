package contactbook.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

public class Contact {

    private String id;
    @NotNull(message = "Name can not be null")
    @NotEmpty(message = "Name can not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-]*$", message = "Name can not contains numbers or special symbols")
    private String name;

    @NotNull(message = "Surname can not be null")
    @NotEmpty(message = "Surname can not be empty")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9-]*$", message = "Surname can not contains numbers or special symbols")
    private final String surname;

    @NotNull(message = "Phone number can not be null")
    @NotEmpty(message = "Phone number can not be empty")
    List<String> numbers;

    public Contact(String name, String surname, List<String> numbers) {
        this.name = name;
        this.surname = surname;
        this.numbers = numbers;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumbers() {
        return numbers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) &&
                Objects.equals(surname, contact.surname) &&
                numbers.containsAll(contact.getNumbers())
                && contact.getNumbers().containsAll(numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, numbers);
    }
}

