package ru.javaops.basejava.webapp.exception;

public class IncorrectDateFormat extends RuntimeException {
    public final String companyName;

    public IncorrectDateFormat(RuntimeException e, String companyName) {
        super("Wrong Date format used in forms", e);
        this.companyName = companyName;
    }
}
