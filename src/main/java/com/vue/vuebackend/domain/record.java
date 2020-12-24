package com.vue.vuebackend.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Table(name="record")
public class record {
    private String bookname;
    private String username;
    private Double price;
    private Date time;

    public record(String bookname, String username, Double price, Date time) {
        this.bookname = bookname;
        this.username = username;
        this.price = price;
        this.time = time;
    }
}
