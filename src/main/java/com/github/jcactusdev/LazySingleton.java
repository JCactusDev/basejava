package com.github.jcactusdev;

public class LazySingleton {
    private static volatile LazySingleton instance;

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }
}
