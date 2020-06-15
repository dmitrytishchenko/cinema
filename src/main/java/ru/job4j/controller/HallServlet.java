package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.persistence.DBStore;
import ru.job4j.persistence.Place;
import ru.job4j.persistence.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HallServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        Store st = DBStore.inst();
        List<Place> places = st.getAllPlaces();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(places);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store st = DBStore.inst();
        String name = req.getParameter("username");
        String phone = req.getParameter("phone");
        st.addNewAccount(name, Integer.valueOf(phone));
        String place = req.getParameter("place");
        String[] mas = place.split("");
        int idAccount = st.getIdAccount(phone);
        st.changeStatusPlace(Integer.valueOf(mas[0]), Integer.valueOf(mas[1]), true, idAccount);
        resp.sendRedirect("/cinema/index.html");
    }
}
