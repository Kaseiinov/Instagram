package kg.attractor.instagram.exceptions;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {
    public NotFoundException() {
        super("Not found");
    }
}
