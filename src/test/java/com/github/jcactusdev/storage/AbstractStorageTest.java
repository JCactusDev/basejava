package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String UUID_5 = "uuid5";

    private static final Resume R1 = new Resume(UUID_1);
    private static final Resume R2 = new Resume(UUID_2);
    private static final Resume R3 = new Resume(UUID_3);
    private static final Resume R4 = new Resume(UUID_4);
    private static final Resume R5 = new Resume(UUID_5);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(R4, storage.get(UUID_4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveElementExistsStorage() {
        storage.save(R5);
        storage.save(R5);
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

    public void get() throws Exception {
        Assert.assertEquals(R1, storage.get(UUID_1));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] array = (Resume[]) storage.getAll();

        Assert.assertEquals(3, array.length);
    }

    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
    }

    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}