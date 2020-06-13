package ru.job4j.controller;

import ru.job4j.Persistence.DBStore;
import ru.job4j.Persistence.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/cinema/index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("username");
        String phone = req.getParameter("phone");
        Store st = DBStore.INST();
        st.addNewAccount(name, Integer.valueOf(phone));
        doGet(req, resp);
    }
}
