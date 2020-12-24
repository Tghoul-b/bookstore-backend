package com.vue.vuebackend.Controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.vue.vuebackend.Mapper.BookMapper;
import com.vue.vuebackend.Mapper.CartMapper;
import com.vue.vuebackend.Mapper.UserMapper;
import com.vue.vuebackend.Mapper.recordMapper;
import com.vue.vuebackend.domain.Book;
import com.vue.vuebackend.domain.Cart;
import com.vue.vuebackend.domain.User;
import com.vue.vuebackend.domain.record;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class proJectController {
    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private BookMapper bookMapper;
    @Autowired(required = false)
    private CartMapper cartMapper;
    @Autowired(required = false)
    private recordMapper recordMapper;
    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> Login(HttpServletRequest request, HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String true_password=userMapper.selectPasswordByName(username);
        int  flag=1;//flag为1是代表登录成功，-1代表密码错误，0代表未注册
        if(true_password==null)
                flag=0;
        else if(!true_password.equals(password))
              flag=-1;
        Map<String,Object> result=new HashMap<String, Object>();
        result.put("flag",flag);
        return result;
    }
    @RequestMapping("register")
    public void Register(HttpServletRequest request, HttpServletResponse response) throws  Exception
    {
           response.setHeader("Access-Control-Allow-Origin", "*");
           String username=request.getParameter("username");
            String password=request.getParameter("password");
            String date=request.getParameter("date");
            String address=request.getParameter("address");
            String sex=request.getParameter("sex");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date f_date=sdf.parse(date);
            User user=new User(username,password,f_date,address,sex);
            userMapper.insertUser(user);
    }
    @RequestMapping("getUserInfo")
    @ResponseBody
    public Map<String,Object> GetUserInfo(HttpServletRequest request,HttpServletResponse response)  throws  Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        User user=userMapper.selectAllByName(username);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("Info",user);
        return  map;
    }
    @RequestMapping("getAllBooks")
    @ResponseBody
    public  Map<String,Object>GetAllBooks(HttpServletRequest request,HttpServletResponse response) throws  Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<Book> bookList=bookMapper.SelectAll();
        Map<String,Object>  map=new HashMap<String, Object>();
        map.put("bookInfos",bookList);
        return map;
    }
    @RequestMapping("InsertIntoCart")
    @ResponseBody
    public  Map<String,Object> InsertIntoCart(HttpServletRequest request,HttpServletResponse response) throws  Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        String bookname=request.getParameter("bookname");
        Cart exist=cartMapper.selectSpecifyBook(new Cart(username,bookname));
        int flag=0;
        if(exist!=null)
            flag=1;
        if(flag==0)
            cartMapper.InsertIntoCart(new Cart(username,bookname));
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("flag",flag);
        return  map;
    }
    @RequestMapping("getAllCartBooks")
    @ResponseBody
    public Map<String,Object> getAllCartBook(HttpServletRequest request,HttpServletResponse response) throws  Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        List<String> booklists=cartMapper.selectAllbooksByname(username);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("booklists",booklists);
        return map;
    }
    @RequestMapping("DelFromCart")
    public void DelFromCart(HttpServletRequest request,HttpServletResponse response) throws  Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        String booklist=request.getParameter("booklist");
        List<String> booklists= JSON.parseArray(booklist,String.class);
        for(String s:booklists){
             s=s.substring(1,s.length()-1);
             cartMapper.DelFromCart(new Cart(username,s));
        }
    }
    @RequestMapping("InsertIntoRecord")
    public void InsertIntoRecord(HttpServletRequest request,HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        String bookname=request.getParameter("bookname");
        String price=request.getParameter("price");
        double p=Double.parseDouble(price);
        Date date=new Date();
        recordMapper.InsertRecord(new record(bookname,username,p,date));
    }
    @RequestMapping("boughtRecord")
    @ResponseBody
    public  Map<String,List>  boughtRecord(HttpServletRequest request,HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String username=request.getParameter("username");
        List<record> list=recordMapper.selectFromRecord(username);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Map<String,List> map=new HashMap<String, List>();
        map.put("res",new ArrayList<String>());
        for(record record:list){
            String bookname=record.getBookname();
            Double price=record.getPrice();
            Date date=record.getTime();
            String s_date=simpleDateFormat.format(date);
            map.get("res").add(bookname+";"+price+";"+s_date);
        }
        return  map;
    }
    @RequestMapping("UpdateBookInfo")
    public void UpdateBookInfo(HttpServletRequest request,HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String bookname=request.getParameter("bookname");
        String author=request.getParameter("author");
        double price=Double.parseDouble(request.getParameter("price"));
        String classfication=request.getParameter("classfication");
        Book book=new Book();
        book.setBookname(bookname);
        book.setAuthor(author);
        book.setPrice(price);
        book.setClassfication(classfication);
        bookMapper.UpdateBookInfo(book);
    }
    @RequestMapping("deleteOneBook")
    public void deleteOneBook(HttpServletRequest request,HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String bookname=request.getParameter("bookname");
        bookMapper.deleteOneBook(bookname);
        cartMapper.DelBookFromCart(bookname);
    }
    @RequestMapping("checkExistBook")
    @ResponseBody
    public Map<String,Boolean> checkExistBook(HttpServletRequest request,HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String bookname=request.getParameter("bookname");
        Book book=bookMapper.findSpecialBook(bookname);
        Boolean res=false;
        if(book!=null)
             res=true;
        Map<String,Boolean> map=new HashMap<String, Boolean>();
        map.put("res",res);
        return map;

    }
    @RequestMapping("UploadFile")
    public  void UploadFile(HttpServletRequest request, HttpServletResponse response) throws  Exception
    {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String bookname=request.getParameter("bookname");
        String author=request.getParameter("author");
        double price=Double.parseDouble(request.getParameter("price"));
        String classfication=request.getParameter("classfication");
        String description=request.getParameter("description");
        MultipartHttpServletRequest param=(MultipartHttpServletRequest) request;
        Map<String,MultipartFile> map=param.getFileMap();
        MultipartFile ImgFile=map.get("ImgFile");
        MultipartFile PdfFile=map.get("PdfFile");
        Book book=new Book();
        book.setBookname(bookname);
        book.setPrice(price);
        book.setAuthor(author);
        book.setClassfication(classfication);
        book.setDescription(description);
        bookMapper.InsertIntoBook(book);
        String path="/root/Tomcat/webapps/bookstore/"+bookname+'/';
        boolean isWin = System.getProperty("os.name").toLowerCase().contains("win");
        if(isWin)
            path="D:/glue/"+bookname;
        File uploadFile = new File(path);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }

        String ImgPath=path+"封面.jpg";
        String pdfPath=path+bookname+".pdf";
        String prepdfPath=path+bookname+"pre.pdf";
        File filepre=new File(prepdfPath);
        File fileImg=new File(ImgPath);
        File filepdf=new File(pdfPath);
        byte[] target=PdfFile.getBytes();

        FileCopyUtils.copy(ImgFile.getBytes(), fileImg);
        FileCopyUtils.copy(PdfFile.getBytes(), filepdf);
        splitPDFFile(pdfPath,prepdfPath,1,5);
    }
    public static void splitPDFFile(String respdfFile,
                                    String savepath, int from, int end) {
        Document document = null;
        PdfCopy copy = null;
        try {
            PdfReader reader = new PdfReader(respdfFile);
            int n = reader.getNumberOfPages();
            if(end==0){
                end = n;
            }
            ArrayList<String> savepaths = new ArrayList<String>();
            String staticpath = respdfFile.substring(0, respdfFile.lastIndexOf("\\")+1);
            //String savepath = staticpath+ newFile;
            savepaths.add(savepath);
            document = new Document(reader.getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));
            document.open();
            for(int j=from; j<=end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch(DocumentException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("getAllUsersInfo")
    @ResponseBody
    public Map<String,Integer[]> getAllUsersInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
        Integer[] res=new Integer[7];
        for(int i=0;i<7;i++)
             res[i]=0;
        List<String>  strList=recordMapper.selectFromJoin();
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("玄幻",0);
        map.put("武侠",1);
        map.put("都市",2);
        map.put("历史",3);
        map.put("科幻",4);
        map.put("网游",5);
        map.put("同人",6);
        for(String s:strList){
            int index=map.get(s);
            res[index]++;
        }
        Map<String,Integer[]> ans=new HashMap<String, Integer[]>();
        ans.put("recordInfo",res);
        Integer [] fmale=new Integer[2];
        for(int i=0;i<2;i++)
             fmale[i]=0;
        List<User> list=userMapper.selectAll();
        for(User user:list){
            if(user.getSex().equals("男")){
                fmale[0]++;
            }
            else{
                fmale[1]++;
            }
        }
        ans.put("fmale",fmale);
        return ans;
    }
    @RequestMapping("getAllBooksFirst")
    @ResponseBody
    public Map<String,List> getAllBooksFirst(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.setHeader("Access-Control-Allow-Origin", "*");
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
        return map;
    }

}

