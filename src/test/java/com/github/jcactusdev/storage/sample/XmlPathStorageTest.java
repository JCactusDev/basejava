package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.storage.PathStorage;
import com.github.jcactusdev.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new XmlStreamSerializer()));
    }

}
