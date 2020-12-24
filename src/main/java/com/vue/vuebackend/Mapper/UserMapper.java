package com.vue.vuebackend.Mapper;

import com.vue.vuebackend.domain.User;
import com.vue.vuebackend.utils.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

@Mapper
public interface UserMapper extends MyMapper<User> {
    @Select("select * from user where binary username=#{username}")
    User selectAllByName(@Param("username") String username);
    @Select("select password from user where binary username=#{username}")
    String selectPasswordByName(@Param("username") String username);
    @Insert("Insert ignore into user " +
            "(username,password,birthday,address,sex) " +
            "values(#{username},#{password},#{birthday},#{address},#{sex})")
    void insertUser(User user);
}
