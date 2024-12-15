package com.example.controller;
// Servlet의 기본 골격

import com.example.entity.Cart;
import com.example.repository.ShoppingDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// http://localhost:8081/shopping/main -----> /WEB-INF/views/template.jsp
@WebServlet("/cart")
public class CartController extends HttpServlet {
    // HttpServlet: HTTP 요청/응답을 처리하는 기본 클래스

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 모든 HTTP 요청(GET, POST 등)을 처리하는 메서드

        String customerId = req.getParameter("customer_id");

        ShoppingDAO shoppingDAO = new ShoppingDAO();
        List<Cart> cart = shoppingDAO.getCart(customerId);
        req.setAttribute("cart", cart);

        if(cart != null && cart.size() != 0){
            int totalAmount = shoppingDAO.getTotalAmount(customerId);
            req.setAttribute("totalAmount", totalAmount);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/cart.jsp");
        dispatcher.forward(req, resp);
    }
}
