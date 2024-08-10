package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.storage.PathStorage;
import com.github.jcactusdev.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }

}
