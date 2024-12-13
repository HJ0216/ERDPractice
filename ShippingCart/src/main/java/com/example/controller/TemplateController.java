package com.example.controller;
// Servlet의 기본 골격
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
// http://localhost:8081/shopping/main -----> /WEB-INF/views/template.jsp
// @WebServlet("/")
public class TemplateController extends HttpServlet {
    // HttpServlet: HTTP 요청/응답을 처리하는 기본 클래스

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 모든 HTTP 요청(GET, POST 등)을 처리하는 메서드

        // Forward: 브라우저 URL을 변경하지 않고 서버 내에서만 JSP로 이동
        RequestDispatcher dispatcher = req.getRequestDispatcher(
            "/WEB-INF/views/template.jsp");
        // 요청을 특정 JSP 파일로 전달하는 객체 생성

        dispatcher.forward(req, resp);
        // template.jsp로 요청과 응답을 전달하여 페이지 이동
    }
}
