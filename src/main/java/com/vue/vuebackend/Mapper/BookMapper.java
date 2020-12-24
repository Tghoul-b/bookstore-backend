package com.vue.vuebackend.Mapper;

import com.vue.vuebackend.domain.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {
    @Select("select * from book")
    List<Book> SelectAll();
    @Update("update book set bookname=#{bookname},author=#{author},price=#{price},classfication=#{classfication}" +
            "where bookname=#" +
            "{bookname}")
    public void UpdateBookInfo( Book book);
    @Delete("delete from book where bookname=#{bookname}")
    public void deleteOneBook(@Param("bookname") String bookname);
    @Select("select * from book where bookname=#{bookname}")
    public Book findSpecialBook(@Param("bookname") String bookname);
    @Insert("insert into book (bookname,author,price,description,classfication) values(#{bookname}," +
            "#{author},#{price},#{description},#{classfication})")
    void InsertIntoBook(Book book);
}
