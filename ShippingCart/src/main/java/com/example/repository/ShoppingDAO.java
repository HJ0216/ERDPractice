package com.example.repository;

import com.example.entity.Cart;
import com.example.entity.Customer;
import com.example.entity.Order;
import com.example.entity.Product;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class ShoppingDAO { // MyBatis API
    // DB연결 -> config.xml(환경설정파일) -> API read 연결작업을 대신 해주면 된다.
   private static SqlSessionFactory sqlSessionFactory; // Connection(SqlSession) Pool
    static{  // 초기화 블럭
         try{
             String resource = "mybatis-config/config.xml";
             InputStream inputStream = Resources.getResourceAsStream(resource);
             sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
         }catch(Exception e){
             e.printStackTrace();
         }
    }

    public List<Product> products(){
      SqlSession session = sqlSessionFactory.openSession();
      List<Product> products = session.selectList("products");

      session.close();

      return products;
    }

    public Customer customerLogin(Customer customer){
      SqlSession session = sqlSessionFactory.openSession();
      Customer findCustomer = session.selectOne("customerLogin", customer);

      session.close();

      return findCustomer;
    }

    public int order(Order order){
      SqlSession session = sqlSessionFactory.openSession();

      Order findProduct = session.selectOne("existProduct", order);

      int result = 0;

      if(findProduct !=null) {
        result = session.update("updateOrder", order);
      } else {
        result = session.insert("order", order);
      }

      session.commit();

      session.close();

      return result;
    }

    public List<Cart> getCart(String customerId){
      SqlSession session = sqlSessionFactory.openSession();
      List<Cart> cart = session.selectList("getCart", customerId);

      session.close();

      return cart;
    }

    public int getTotalAmount(String customerId){
      SqlSession session = sqlSessionFactory.openSession();
      int totalAmount = session.selectOne("getTotalAmount", customerId);

      session.close();

      return totalAmount;
    }

  public int removeProductFromCart(int orderId) {
    SqlSession session = sqlSessionFactory.openSession();
    int result = session.delete("removeProductFromCart", orderId);

    session.commit();
    session.close();

    return result;
  }

  public int orderCart(String customerId) {
    SqlSession session = sqlSessionFactory.openSession();
    int result = session.delete("orderCart", customerId);

    session.commit();
    session.close();

    return result;
  }

}
