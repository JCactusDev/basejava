package com.github.jcactusdev.storage.sample;

import com.github.jcactusdev.Config;
import com.github.jcactusdev.storage.ListStorage;
import com.github.jcactusdev.storage.SqlStorage;

import java.io.File;

public class SqlStorageTest extends AbstractStorageTest {

    private final static String DATABASE_URL = Config.getInstance().getDatabaseUrl();
    private final static String DATABASE_USER = Config.getInstance().getDatabaseUser();
    private final static String DATABASE_PASSWORD = Config.getInstance().getDatabasePassword();

    public SqlStorageTest() {
        super(new SqlStorage(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD));
    }

}
