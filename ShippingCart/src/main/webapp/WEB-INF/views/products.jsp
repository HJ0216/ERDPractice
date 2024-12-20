<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script>
      function goShoppingCart(){
        if(${empty findCustomer}){
          alert("로그인이 필요합니다.");
          return false;
        }

        location.href="/shopping/cart?customer_id=${findCustomer.customer_id}";
      }

      function order(productId){
        if(${empty findCustomer}){
          alert("로그인이 필요합니다.");
          return false;
        }

        // location.href="/shopping/order"
        $.ajax({
          url: "/shopping/order",
          type: "POST",
          data: {
            "customer_id": "${findCustomer.customer_id}",
            "product_id": productId
          },
          success: function(result) {
            if(result > 0){
              console.log(result);
              alert("주문에 성공하였습니다.");
            }
          },
          error: function(){ alert("주문에 실패하였습니다."); }
        });
      }
    </script>
</head>
<body>
  <div class="container pt-5">
    <h2>생각하는 데이터베이스 모델링</h2>
      <div class="card">
        <div class="card-header">
          <div class="row">
            <div class="col-5">
              <c:if test="${!empty findCustomer}">
                <h4>Welcome, ${findCustomer.name} Rewards:
                  <span class="badge badge-warning">${findCustomer.point}</span>
                </h4>
              </c:if>
              <c:if test="${empty findCustomer}">
                <h4>Welcome, Guest Rewards:
                  <span class="badge badge-warning">0</span>
                </h4>
              </c:if>
            </div>
            <div class="col-7">
              <c:if test="${!empty findCustomer}">
                <form class="form-inline" action="/shopping/logout" method="post">
                  <button type="submit" class="btn btn-primary btn-sm">로그아웃</button>
                </form>
              </c:if>
              <c:if test="${empty findCustomer}">
                <form class="form-inline" action="/shopping/login" method="post">
                  <label for="customer_id">ID: </label>
                  <input type="text" class="form-control" placeholder="Enter ID" id="customer_id" name="customer_id">
                  <label for="password">Password: </label>
                  <input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
                  &nbsp;
                  <button type="submit" class="btn btn-primary btn-sm">Login</button>
                </form>
              </c:if>
          </div>
        </div>

        </div>
        <div class="card-body">
          <div class="row">
            <div class="col text-right">
              <button class="btn btn-sm btn-warning" type="button" onclick="goShoppingCart()">Shopping Cart</button>
            </div>
          </div>
          <h3>Product List</h3>
          <table class="table table-bordered table-hover">
            <thead>
              <tr>
                <th>제품번호</th>
                <th>제품명</th>
                <th>재고량</th>
                <th>가격</th>
                <th>제조업체</th>
                <th class="text-center">주문</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="product" items="${products}">
                <tr>
                  <td>${product.productId}</td>
                  <td>${product.name}</td>
                  <td>${product.stock}</td>
                  <td>${product.price}</td>
                  <td>${product.manufacturer}</td>
                  <td class="text-center">
                    <button class="btn btn-sm btn-primary" type="button" onclick="order(${product.productId})">Add to Cart</button>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
        <div class="card-footer">[7일 완성]생각하는 데이터베이스 모델링</div>
      </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>