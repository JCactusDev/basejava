package com.github.jcactusdev;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected final Properties prop = new Properties();

    private File storageDir;

    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;

    private Config() {
        try (InputStream stream = new FileInputStream("./config/resumes.properties")) {
            prop.load(stream);
            storageDir = new File(prop.getProperty("storage.dir"));
            databaseUrl = prop.getProperty("database.url");
            databaseUser = prop.getProperty("database.user");
            databasePassword = prop.getProperty("database.password");
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not load config.properties");
        }

    }

    public static Config getInstance() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }
}
