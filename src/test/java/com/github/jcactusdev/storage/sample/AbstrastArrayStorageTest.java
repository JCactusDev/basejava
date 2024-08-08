package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.model.Resume;
import com.github.jcactusdev.storage.AbstractArrayStorage;
import com.github.jcactusdev.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstrastArrayStorageTest extends AbstractStorageTest {

    public AbstrastArrayStorageTest(Storage storage){
        super(storage);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void save_Stack() throws Exception {
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.format("uuid_%d", i)));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Assert.fail();
        }
        storage.save(new Resume(String.format("uuid_%d", AbstractArrayStorage.STORAGE_LIMIT + 1)));
    }

}
