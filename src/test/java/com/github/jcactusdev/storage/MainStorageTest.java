package com.github.jcactusdev.storage;


import com.github.jcactusdev.storage.sample.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapWithKeyUUIDStorageTest.class,
        MapWithKeyResumeStorageTest.class
})
public class MainStorageTest {}
