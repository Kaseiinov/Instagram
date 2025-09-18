package kg.attractor.instagram.exceptions;

import java.util.NoSuchElementException;

public class ResumeNotFoundException extends NoSuchElementException {
    public ResumeNotFoundException() {
        super("Resume not found");
    }
}
