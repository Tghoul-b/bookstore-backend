package com.vue.vuebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="book")
public class Book {
    private  String bookname;
    private  String author;
    private  double price;
    private  String description;
    private  String classfication;

    public Book(String bookname, String author, double price, String description, String classfication) {
        this.bookname = bookname;
        this.author = author;
        this.price = price;
        this.description = description;
        this.classfication = classfication;
    }
}
