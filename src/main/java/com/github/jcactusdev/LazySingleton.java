package com.github.jcactusdev;

public class LazySingleton {
    volatile private static LazySingleton INSTANCE;

    private LazySingleton(){}

    public LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;
    }

    private static class LazySingletonHolder{
        private static final LazySingleton INSTANCE = new LazySingleton();
    }
}
