package com.github.jcactusdev;

import com.github.jcactusdev.storage.SqlStorage;
import com.github.jcactusdev.storage.Storage;

import java.io.*;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected final Properties prop = new Properties();

    private File storageDir;
    private Storage storage;

    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;

    private Config() {
        String homeDirS = "";
        File homeDir = getHomeDir();
        if (homeDir != null) {
            homeDirS = homeDir.toString() + "\\";
        }
        try (InputStream stream = new FileInputStream(homeDirS+ "config\\resumes.properties")) {
            prop.load(stream);
            storageDir = new File(prop.getProperty("storage.dir"));
            databaseUrl = prop.getProperty("database.url");
            databaseUser = prop.getProperty("database.user");
            databasePassword = prop.getProperty("database.password");

            storage = new SqlStorage(databaseUrl, databaseUser, databasePassword);

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

    public Storage getStorage() {
        return storage;
    }

    private static File getHomeDir(){
        String property = System.getProperty("homeDir");
        if (property == null) {
            return null;
        }
        File homeDir = new File(property);
        if(!homeDir.isDirectory()){
            throw new IllegalArgumentException("HomeDir is not a directory");
        }
        return homeDir;
    }
}
