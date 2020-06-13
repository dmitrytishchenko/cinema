package ru.job4j.Persistence;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBStore implements Store {
    private static Connection con;

    private DBStore() {
        connectDb();
        createTable();
        createHall(3, 3);
        disconnectedDb();
    }

    private static final class Lazy {
        private static final Store INST = new DBStore();
    }

    public static Store INST() {
        return Lazy.INST;
    }

    public void connectDb() {
        try {
            Properties properties = new Properties();
            try (BufferedReader io = new BufferedReader(new FileReader("db.pr"))) {
                properties.load(io);
            } catch (Exception e) {
            }
            Class.forName(properties.getProperty("jdbc.driver"));
            con = DriverManager.getConnection(properties.getProperty("jdbc.url"),
                    properties.getProperty("jdbc.username"),
                    properties.getProperty("jdbc.password"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectedDb() {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        String sql = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\projects\\cinema\\db\\Schema.sql"));
            int c;
            while ((c = reader.read()) != -1) {
                sql += (char) c;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPlaceToHall(Place place) {
        try (PreparedStatement ps = con.prepareStatement("insert into hall(rows, columns, is_active) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, place.getRow());
            ps.setInt(2, place.getColumn());
            ps.setBoolean(3, place.isActive());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createHall(int rows, int columns) {
        try (PreparedStatement ps = con.prepareStatement("select * from hall")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return;
            } else {
                for (int i = 1; i <= columns; i++) {
                    for (int j = 1; j <= rows; j++) {
                        addPlaceToHall(new Place(j, i, false));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Place> getAllPlaces() {
        connectDb();
        List<Place> places = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement("select * from hall")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                places.add(new Place(
                        rs.getInt("rows"),
                        rs.getInt("columns"),
                        rs.getBoolean("is_active")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectedDb();
        }
        return places;
    }

    @Override
    public void changeStatusPlace(int row, int column, boolean is_activeNew) {
        connectDb();
        try (PreparedStatement ps = con.prepareStatement("update hall set is_active = ? where rows =" + row + " and columns =" + column + "")) {
            ps.setBoolean(1, is_activeNew);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectedDb();
        }
    }

    @Override
    public void addNewAccount(String name, int phone) {
        connectDb();
        try (PreparedStatement ps = con.prepareStatement("insert into account(fio, phonenumber) values (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, phone);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnectedDb();
        }
    }
}
