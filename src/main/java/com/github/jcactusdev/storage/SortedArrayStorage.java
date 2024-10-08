package com.github.jcactusdev.storage;

import com.github.jcactusdev.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());


    @Override
    protected void insertElement(Resume object, Integer key) {
        int idx = -key - 1;
        System.arraycopy(storage, idx, storage, idx + 1, size - idx);
        storage[idx] = object;
    }

    @Override
    protected void removeElement(Integer key) {
        int length = size - key - 1;
        if (length > 0) {
            System.arraycopy(storage, key + 1, storage, key, length);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stringBuilder.append(String.format("id: %d, Resume: %s", i, storage[i].toString()));
            if (i < size - 1) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    protected Integer getKey(String uuid) {
        Resume object = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, object, RESUME_COMPARATOR);
    }
}
