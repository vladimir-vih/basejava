package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public interface SerializeStrategy {
    void serializeOutputStream(BufferedOutputStream bos, Resume r) throws IOException;

    Resume deserializeInputStream(BufferedInputStream bis) throws IOException;
}
