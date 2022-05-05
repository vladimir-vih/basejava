package ru.javaops.basejava.webapp.storage;

import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {
    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }
}