package ru.job4j.Persistence;

import java.util.List;

public interface Store {

    List<Place> getAllPlaces();

    void createTable();

    void createHall(int rows, int columns);

    void connectDb();

    void disconnectedDb();

    void changeStatusPlace(int row, int column, boolean isActiveNew, int idAccount);

    void addNewAccount(String name, int phone);

    int getIdAccount(String name);
}