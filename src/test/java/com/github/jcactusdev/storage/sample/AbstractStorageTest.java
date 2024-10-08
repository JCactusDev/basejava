package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.Config;
import com.github.jcactusdev.model.*;
import com.github.jcactusdev.storage.Storage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class AbstractStorageTest {

    protected final static File STORAGE_DIR = Config.getInstance().getStorageDir();
    protected Storage storage;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();
    private static final String UUID_5 = UUID.randomUUID().toString();

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;
    private static final Resume R5;

    static {
        R1 = new Resume(UUID_1, "Name1");
        R1.addContact(ContactType.MAIL, "ru-j-cactus@gmail.com");
        R1.addContact(ContactType.MOBILE, "+7(932) 319-78-60");
        R1.addContact(ContactType.GITHUB, "JCactusDev");

        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achiv11", "Achiv12", "Achiv13"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "Sql", "html"));
        R1.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("OOO Accent", "HTTP page",
                        new Organization.Position(2023, Month.JANUARY,  "1C engineer", "content 1")
                ),
                new Organization("OOO IT-Link", "HTTP page",
                        new Organization.Position(2022, Month.AUGUST, 2022, Month.DECEMBER, "1C consultant/QA", "content 2")
                )
        ));
        R1.addSection(SectionType.EDUCATION, new OrganizationSection(
                new Organization("Institute", null,
                        new Organization.Position(2015, Month.AUGUST, 2020, Month.AUGUST, "Economist", "content 3")
                )
        ));

        R2 = new Resume(UUID_2, "Name2");
        R2.addContact(ContactType.MAIL, "qwe@gmail.ru");
        R2.addContact(ContactType.MOBILE, "+8(800) 555-35-35");
        R2.addSection(SectionType.EXPERIENCE, new OrganizationSection(
                new Organization("OOO Two", null,
                        new Organization.Position(2022, Month.JANUARY, "Engineer", "content 1"),
                        new Organization.Position(2015, Month.AUGUST, 2022, Month.DECEMBER, "QA", null)
                )
        ));

        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");
        R5 = new Resume(UUID_5, "Name5");
    }

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
        Assert.assertEquals(R4, storage.get(R4.getUuid()));
    }

    @Test(expected = RuntimeException.class)
    public void saveElementExistsStorage() {
        storage.save(R5);
        storage.save(R5);
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(R1, storage.get(R1.getUuid()));
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> list = storage.getAllSorted();
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(list, Arrays.asList(R1, R2, R3));
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "Name1");
        newResume.addContact(ContactType.MAIL, "ru-j-cactus@gmail.RU");
        newResume.addContact(ContactType.MOBILE, "+7(932) 319-78-10");
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(R1.getUuid()));
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

//    @Test
//    public void clear() throws Exception {
//        storage.clear();
//        Assert.assertEquals(0, storage.size());
//    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }
}