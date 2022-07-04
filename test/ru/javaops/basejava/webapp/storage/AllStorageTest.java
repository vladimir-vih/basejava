package ru.javaops.basejava.webapp.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapStorageSearchResumeTest.class,
        FileStorageObjectStreamStrategyTest.class,
        PathStorageObjectStreamStrategyTest.class,
        FileStorageXmlSerializerTest.class,
        PathStorageXmlSerializerTest.class,
        FileStorageJsonSerializerTest.class,
        PathStorageJsonSerializerTest.class,
        FileStorageDataStreamSerializerTest.class,
        PathStorageDataStreamSerializerTest.class,
        SqlStorageTest.class
})
public class AllStorageTest {
}
