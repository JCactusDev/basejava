package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.storage.FileStorage;
import com.github.jcactusdev.storage.serializer.ObjectStreamSerializer;
import com.github.jcactusdev.storage.serializer.StreamSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }

}
