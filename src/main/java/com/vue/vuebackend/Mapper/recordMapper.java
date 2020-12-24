package com.vue.vuebackend.Mapper;

import com.vue.vuebackend.domain.record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface recordMapper {
    @Insert("insert into record (bookname,price,username,time) values(#" +
            "{bookname},#{price},#{username},#{time})")
    void InsertRecord(record record);
    @Select("select * from record where username=#{username} order by time desc limit 10")
    List<record> selectFromRecord(@Param("username") String username);
    @Select("select classfication from record inner join book where record.bookname=book.bookname")
    List<String> selectFromJoin();
    @Select("select sum(price) from record")
    Double selectSumPrice();
    @Select("select  bookname,count(bookname) as cnt from record group by bookname order by cnt desc limit 3")
    List<Map<String,Integer>> selectTop3Book();
    @Select("select count(*) from record")
    Integer selectTotal();
}
