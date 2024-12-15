package com.example.controller;
// Servlet의 기본 골격

import com.example.repository.ShoppingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// http://localhost:8081/shopping/main -----> /WEB-INF/views/template.jsp
@WebServlet("/order-cart")
public class OrderCartController extends HttpServlet {
    // HttpServlet: HTTP 요청/응답을 처리하는 기본 클래스

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 모든 HTTP 요청(GET, POST 등)을 처리하는 메서드

        String customerId = req.getParameter("customerId");

        ShoppingDAO shoppingDAO = new ShoppingDAO();
        int result = shoppingDAO.orderCart(customerId);

        PrintWriter writer = resp.getWriter();
        writer.println(result);
    }
}
