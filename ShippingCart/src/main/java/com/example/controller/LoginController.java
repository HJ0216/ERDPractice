package com.example.controller;
// Servlet의 기본 골격

import com.example.entity.Customer;
import com.example.repository.ShoppingDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

// http://localhost:8081/shopping/main -----> /WEB-INF/views/template.jsp
@WebServlet("/login")
public class LoginController extends HttpServlet {
    // HttpServlet: HTTP 요청/응답을 처리하는 기본 클래스

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 모든 HTTP 요청(GET, POST 등)을 처리하는 메서드

        String customerId = req.getParameter("customer_id"); // input tag에서 name으로 넘어옴
        String password = req.getParameter("password");
        Customer customer = new Customer();
        customer.setCustomer_id(customerId);
        customer.setPassword(password);

        ShoppingDAO shoppingDAO = new ShoppingDAO();
        Customer findCustomer = shoppingDAO.customerLogin(customer);

        if(findCustomer != null){
            HttpSession session = req.getSession();
            session.setAttribute("findCustomer", findCustomer);
        }

        // Redirect
        resp.sendRedirect("/shopping/products");
    }
}
