package com.vue.vuebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="cart")
public class Cart {
    private String username;
    private  String bookname;

    public Cart(String username, String bookname) {
        this.username = username;
        this.bookname = bookname;
    }
}
