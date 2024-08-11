package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.storage.PathStorage;
import com.github.jcactusdev.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializer()));
    }

}
