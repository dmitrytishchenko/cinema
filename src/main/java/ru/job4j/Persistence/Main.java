package ru.job4j.Persistence;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Store store = DBStore.INST();
        store.changeStatusPlace(2, 2, true);
store.disconnectedDb();
    }
}
