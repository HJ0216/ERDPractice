package com.example.controller;
// Servlet의 기본 골격

import com.example.entity.Product;
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
@WebServlet("/products")
public class ProductListController extends HttpServlet {
    // HttpServlet: HTTP 요청/응답을 처리하는 기본 클래스

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 모든 HTTP 요청(GET, POST 등)을 처리하는 메서드
        // Forward: 브라우저 URL을 변경하지 않고 서버 내에서만 JSP로 이동

        ShoppingDAO dao = new ShoppingDAO();
        List<Product> products = dao.products();

        req.setAttribute("products", products);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/products.jsp");
        dispatcher.forward(req, resp);

    }
}
