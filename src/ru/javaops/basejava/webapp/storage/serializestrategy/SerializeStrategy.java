package ru.javaops.basejava.webapp.storage.serializestrategy;

import ru.javaops.basejava.webapp.model.Resume;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public interface SerializeStrategy {
    void doWrite(BufferedOutputStream bos, Resume r) throws IOException;

    Resume doRead(BufferedInputStream bis) throws IOException;
}
