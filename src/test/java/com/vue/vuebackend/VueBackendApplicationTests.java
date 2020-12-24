package com.vue.vuebackend;

import com.vue.vuebackend.Mapper.BookMapper;
import com.vue.vuebackend.Mapper.CartMapper;
import com.vue.vuebackend.Mapper.UserMapper;
import com.vue.vuebackend.Mapper.recordMapper;
import com.vue.vuebackend.domain.Book;
import com.vue.vuebackend.domain.Cart;
import com.vue.vuebackend.domain.User;
import com.vue.vuebackend.domain.record;
import io.swagger.models.auth.In;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class VueBackendApplicationTests {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private BookMapper bookMapper;
    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private recordMapper recordMapper;
    @Test
    void contextLoads() {
        String username="xujiale";
        User user=userMapper.selectAllByName(username);
        System.out.println(user);
    }
    @Test
    void TestLoad()
    {
        User user=userMapper.selectAllByName("nihao");
        System.out.println(user);
    }
    @Test
    void TestInsert() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse("2012-12-08");
            User user = new User("xujiale", "1234567", date, "湖北省", "男");
            userMapper.insertUser(user);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Test
    void findAllbooks(){
        List<Book> bookList=bookMapper.SelectAll();
        System.out.println(bookList);
    }
    @Test
    void Insertbooks(){
        Cart cart=new Cart("xujiale","完美世界");
        cartMapper.InsertIntoCart(cart);
    }
    @Test
    public void InsertRecord(){
        String bookname="三体";
        double price=123.4;
        String username="xjl";
        Date date=new Date();
        record r=new record(bookname,username,price,date);
        recordMapper.InsertRecord(r);
    }
   @Test
    public void TestJoin(){
        List<String> res=recordMapper.selectFromJoin();
       System.out.println(res);
   }
   @Test
    public void Select3books()
   {
       Map<String,List> map=new HashMap<String, List>();
       Double sum=recordMapper.selectSumPrice();
       List<Map<String,Integer>> d=recordMapper.selectTop3Book();
       List<String> list=new ArrayList<String>();
       for(Map map1:d){
           for(Object o:map1.keySet()){
               String s=(String) o;
               if(s.equals("bookname")){
                   list.add((String)map1.get(s));
               }
           }
       }
       map.put("booklist",list);
       map.put("price",new ArrayList<String>());
       map.get("price").add(sum);
       map.put("total",new ArrayList<String>());
       Integer ans=recordMapper.selectTotal();
       map.get("total").add(ans);
       System.out.println(map);
   }
}
