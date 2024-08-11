package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.storage.PathStorage;
import com.github.jcactusdev.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializer()));
    }

}
