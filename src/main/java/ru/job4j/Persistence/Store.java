package ru.job4j.Persistence;

import java.util.List;

public interface Store {
    List<Place> getAllPlaces();
    void createTable();
    void createHall(int rows, int columns);
    void connectDb();
    void disconnectedDb();
    void changeStatusPlace(int row, int column, boolean is_activeNew);
    void addNewAccount(String name, int phone);
}
