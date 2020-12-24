package com.vue.vuebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="user")
public class User {
    private String username;
    private String password;
    Date birthday;
    private String address;
    private String sex;

    public User(String username, String password, Date birthday, String address,String sex) {
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.address = address;
        this.sex=sex;
    }

}
