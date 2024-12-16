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
    function removeProductFromCart(orderId, customerId){
      location.href="/shopping/cancel?orderId=" + orderId + "&customerId=" + customerId;
    }

    function orderCart(customerId){
      if(${!empty totalAmount}){
        $.ajax({
          url: "/shopping/order-cart",
          type: "GET",
          data: {"customerId": customerId, "totalAmount": ${totalAmount}},
          success: function(data){
            alert("🎉");
            location.href="/shopping/cart?customer_id="+customerId;
          },
          error: function(){
            alert("주문에 실패하였습니다.");
          }
        });
      } else {
        alert("주문할 제품이 없습니다.");
        return false;
      }
    }

    function updateOrderQuantity(orderId){
      var updatedQuantity = $("#quantity_" + orderId).val();

      $.ajax({
        url: "/shopping/order-quantity",
        type: "POST",
        data: {"orderId": orderId, "quantity": updatedQuantity},
        success: function(){
          location.href="/shopping/cart?customer_id=${findCustomer.customer_id}"
        },
        error: function(){
          alert("수량 수정에 실패하였습니다.");
        }
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
              <button class="btn btn-sm btn-warning" type="button" onclick="orderCart('${findCustomer.customer_id}')">Buy</button>
            </div>
          </div>
          <h3>Cart List</h3>
          <table class="table table-bordered table-hover">
            <thead>
              <tr>
                <th>제품번호</th>
                <th>제품명</th>
                <th>수량</th>
                <th>가격</th>
                <th>금액</th>
                <th class="text-center">취소</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="orderProduct" items="${cart}">
                <tr>
                  <td>${orderProduct.product_id}</td>
                  <td>${orderProduct.name}</td>
                  <td>
                    <input class="form-control" type="number" name="quantity" id="quantity_${orderProduct.order_id}" min="1" max="5" value="${orderProduct.quantity}" onchange="updateOrderQuantity(${orderProduct.order_id})"/>
                  </td>
                  <td>${orderProduct.price}</td>
                  <td>${orderProduct.amount}</td>
                  <td class="text-center">
                    <button class="btn btn-sm btn-primary" type="button" onclick="removeProductFromCart(${orderProduct.order_id}, '${findCustomer.customer_id}')">Cancel</button>
                  </td>
                </tr>
              </c:forEach>

              <tr>
                <td colspan="4" class="text-right">Total Amount: </td>
                <td colspan="2">${totalAmount}</td>
              </tr>
            </tbody>
          </table>

          <div class="row">
            <div class="col text-right">
              <button class="btn btn-sm btn-primary" type="button" onclick="location.href='/shopping/products'">Continue Shopping</button>
            </div>
          </div>
        </div>
        <div class="card-footer">[7일 완성]생각하는 데이터베이스 모델링</div>
      </div>
  </div>
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>