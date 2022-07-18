package ru.javaops.basejava.webapp.storage.serializestrategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.model.Section;

import java.io.*;

public class JsonSerializer implements SerializeStrategy{
    @Override
    public void doWrite(BufferedOutputStream bos, Resume r) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Section.class, new JsonSectionAdapter())
                .create();
        try (Writer writer = new OutputStreamWriter(bos)) {
            gson.toJson(r, writer);
        }
    }

    @Override
    public Resume doRead(BufferedInputStream bis) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Section.class, new JsonSectionAdapter());
        Gson gson = gsonBuilder.create();
        try (Reader reader = new InputStreamReader(bis)) {
            return gson.fromJson(reader, Resume.class);
        }
    }

    public String WriteSection(Section obect) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Section.class, new JsonSectionAdapter())
                .create();
        return gson.toJson(obect, Section.class);
    }

    public Section ReadSection(String value) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        gsonBuilder.registerTypeAdapter(Section.class, new JsonSectionAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(value, Section.class);
    }
}
