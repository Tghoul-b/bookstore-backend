package com.vue.vuebackend.Mapper;

import com.vue.vuebackend.domain.Book;
import com.vue.vuebackend.domain.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CartMapper {
    @Insert("insert ignore into cart (username,bookname) values(#{username},#{bookname})")
    void InsertIntoCart(Cart cart);
    @Select("select bookname from cart where username=#{username}")
    List<String>  selectAllbooksByname(String username);
    @Select("select * from cart where username=#{username} and bookname=#{bookname}")
    Cart selectSpecifyBook(Cart cart);
    @Select("delete from cart where username=#{username} and bookname=#{bookname}")
    void DelFromCart(Cart cart);
    @Delete("delete from cart where bookname=#{bookname}")
    void DelBookFromCart(@Param("bookname") String bookname);
}
