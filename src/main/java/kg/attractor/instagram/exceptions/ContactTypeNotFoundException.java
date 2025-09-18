package kg.attractor.instagram.exceptions;

import java.util.NoSuchElementException;

public class ContactTypeNotFoundException extends NoSuchElementException {
    public ContactTypeNotFoundException() {
        super("Contact type not found");
    }
}
