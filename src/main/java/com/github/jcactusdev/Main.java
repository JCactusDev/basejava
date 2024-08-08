package com.github.jcactusdev;

import com.github.jcactusdev.model.Resume;
import com.github.jcactusdev.storage.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Storage storage = new ArrayStorage();
    private static final Storage sort_storage = new SortedArrayStorage();
    private static final Storage list_storage = new ListStorage();
    private static final Storage map_storage = new MapStorage();
    private static final Storage mapWithUUID_storage = new MapWithKeyUUIDStorage();
    private static final Storage mapWithResume_storage = new MapWithKeyResumeStorage();

    public static void main(String[] args) {

        Resume resume1 = new Resume("UUID_1", "NAME1");
        Resume resume2 = new Resume("UUID_2", "NAME2");
        Resume resume3 = new Resume("UUID_3", "NAME3");

        System.out.println("============================================");

        System.out.println("ArrayStorage:");
        storage.save(resume3);
        storage.save(resume2);
        storage.save(resume1);
        System.out.println(storage.toString());
        System.out.println("Total size: " + storage.size() + ", size() == getAll(): " + (storage.size() == storage.getAllSorted().size()));

        System.out.println("delete \"UUID_2\":");
        storage.delete("UUID_2");
        System.out.println(storage.toString());
        System.out.println("Total size: " + storage.size() + ", size() == getAll(): " + (storage.size() == storage.getAllSorted().size()));

        System.out.println("clear:");
        storage.clear();
        System.out.println("Total size: " + storage.size() + ", size() == getAll(): " + (storage.size() == storage.getAllSorted().size()));

        System.out.println("============================================");

        System.out.println("SortedArrayStorage:");
        sort_storage.save(resume3);
        sort_storage.save(resume2);
        sort_storage.save(resume1);
        System.out.println(sort_storage.toString());
        System.out.println("Total size: " + sort_storage.size() + ", size() == getAll(): " + (sort_storage.size() == sort_storage.getAllSorted().size()));

        System.out.println("delete \"UUID_2\":");
        sort_storage.delete("UUID_2");
        System.out.println(sort_storage.toString());
        System.out.println("Total size: " + sort_storage.size() + ", size() == getAll(): " + (sort_storage.size() == sort_storage.getAllSorted().size()));

        System.out.println("clear:");
        sort_storage.clear();
        System.out.println("Total size: " + sort_storage.size() + ", size() == getAll(): " + (sort_storage.size() == sort_storage.getAllSorted().size()));

        System.out.println("============================================");

        System.out.println("ListStorage:");
        list_storage.save(resume3);
        list_storage.save(resume2);
        list_storage.save(resume1);
        System.out.println(list_storage.toString());
        System.out.println("Total size: " + list_storage.size() + ", size() == getAll(): " + (list_storage.size() == list_storage.getAllSorted().size()));

        System.out.println("delete \"UUID_2\":");
        list_storage.delete("UUID_2");
        System.out.println(list_storage.toString());
        System.out.println("Total size: " + list_storage.size() + ", size() == getAll(): " + (list_storage.size() == list_storage.getAllSorted().size()));

        System.out.println("clear:");
        list_storage.clear();
        System.out.println("Total size: " + list_storage.size() + ", size() == getAll(): " + (list_storage.size() == list_storage.getAllSorted().size()));

        System.out.println("============================================");

        System.out.println("MapStorage:");
        map_storage.save(resume3);
        map_storage.save(resume2);
        map_storage.save(resume1);
        System.out.println(map_storage.toString());
        System.out.println("Total size: " + map_storage.size() + ", size() == getAll(): " + (map_storage.size() == map_storage.getAllSorted().size()));

        System.out.println("delete \"UUID_2\":");
        map_storage.delete("UUID_2");
        System.out.println(map_storage.toString());
        System.out.println("Total size: " + map_storage.size() + ", size() == getAll(): " + (map_storage.size() == map_storage.getAllSorted().size()));

        System.out.println("clear:");
        map_storage.clear();
        System.out.println("Total size: " + map_storage.size() + ", size() == getAll(): " + (map_storage.size() == map_storage.getAllSorted().size()));

        System.out.println("============================================");

        System.out.println("MapWithKeyUUIDStorage:");
        mapWithUUID_storage.save(resume3);
        mapWithUUID_storage.save(resume2);
        mapWithUUID_storage.save(resume1);
        System.out.println(mapWithUUID_storage.toString());
        System.out.println("Total size: " + mapWithUUID_storage.size() + ", size() == getAll(): " + (mapWithUUID_storage.size() == mapWithUUID_storage.getAllSorted().size()));

        System.out.println("delete \"UUID_2\":");
        mapWithUUID_storage.delete("UUID_2");
        System.out.println(mapWithUUID_storage.toString());
        System.out.println("Total size: " + mapWithUUID_storage.size() + ", size() == getAll(): " + (mapWithUUID_storage.size() == mapWithUUID_storage.getAllSorted().size()));

        System.out.println("clear:");
        mapWithUUID_storage.clear();
        System.out.println("Total size: " + mapWithUUID_storage.size() + ", size() == getAll(): " + (mapWithUUID_storage.size() == mapWithUUID_storage.getAllSorted().size()));

        System.out.println("============================================");

        System.out.println("MapWithKeyResumeStorage:");
        mapWithResume_storage.save(resume3);
        mapWithResume_storage.save(resume2);
        mapWithResume_storage.save(resume1);
        System.out.println(mapWithResume_storage.toString());
        System.out.println("Total size: " + mapWithResume_storage.size() + ", size() == getAll(): " + (mapWithResume_storage.size() == mapWithResume_storage.getAllSorted().size()));

        System.out.println("delete \"UUID_2\":");
        mapWithResume_storage.delete("UUID_2");
        System.out.println(mapWithResume_storage.toString());
        System.out.println("Total size: " + mapWithResume_storage.size() + ", size() == getAll(): " + (mapWithResume_storage.size() == mapWithResume_storage.getAllSorted().size()));

        System.out.println("clear:");
        mapWithResume_storage.clear();
        System.out.println("Total size: " + mapWithResume_storage.size() + ", size() == getAll(): " + (mapWithResume_storage.size() == mapWithResume_storage.getAllSorted().size()));

        System.out.println("============================================");
    }

}