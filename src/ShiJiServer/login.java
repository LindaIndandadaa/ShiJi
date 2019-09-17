package ShiJiServer;
 

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * Servlet implementation class HelloForm
 */
@WebServlet("/login")
public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String driver = "com.mysql.jdbc.Driver"; //数据库驱动  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //连接数据库的URL地址  
    private static final String username="root";//数据库的用户名  
    private static final String password="root";//数据库的密码</span>   
    
    String getSqlPw(String name)
    {
    	String pw = null;
    	java.sql.Connection conn = null;
        Statement stmt = null;
    	
    	  try{
              // 注册 JDBC 驱动器
    		  //System.out.println("注册jdbc");
              Class.forName(driver);
              
              // 打开一个连接
              //System.out.println("打开连接");
              conn = DriverManager.getConnection(url,username,password);

              // 执行 SQL 查询
              stmt = conn.createStatement();//连接数据库时，获得一个可执行的SQL语句执行对象；
              String sql;
              sql = "SELECT passwd FROM  user WHERE name=\'"+name+"\'";
              System.out.println(sql);
              ResultSet rs = stmt.executeQuery(sql);
 
              //获取数据库传回来的数据 
              while (rs.next())
              {
            	  pw = rs.getString("passwd"); 
              }
              
              System.out.println("密码："+pw);
              
              // 完成后关闭
              rs.close();
              stmt.close();
              conn.close();
          } catch(SQLException se) {
              // 处理 JDBC 错误
              se.printStackTrace();
          } catch(Exception e) {
              // 处理 Class.forName 错误
              e.printStackTrace();
          }finally{
              // 最后是用于关闭资源的块
              try{
                  if(stmt!=null)
                  stmt.close();
              }catch(SQLException se2){
              }
              try{
                  if(conn!=null)
                  conn.close();
              }catch(SQLException se){
                  se.printStackTrace();
              }
          }
    	  
    	return pw;
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // doPost(request, response);
    	 response.setContentType("text/html;charset=UTF-8");

         //String title = "用户登录";
         // 接收HTML页面上的表单内容。
         String name =request.getParameter("name");
         String passwd =  request.getParameter("password");
         
         System.out.println("UserName:"+name+";"+"PassWord:"+passwd); 
         
         //获取数据库里对应名字的密码，getSqlPw(String); 与数据库的交互获取密码
         String sqlPw = getSqlPw(name);
         
         //将数据写回
         PrintWriter out = response.getWriter();
         if(sqlPw==null)
        	 sqlPw = "NULL";
         out.write(sqlPw); //sqlPw为xml数据
         out.close();
    }
    
    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	doGet(request, response);
    	// 设置响应内容类型
        /*
        response.setContentType("text/html;charset=UTF-8");

        //String title = "用户登录";
        // 接收HTML页面上的表单内容。
        String name =new String(request.getParameter("username"));
        String passwd =  request.getParameter("password");
        
        System.out.println("UserName:"+name+";"+"PassWord:"+passwd); 
        
        //获取数据库里对应名字的密码，getSqlPw(String); 与数据库的交互获取密码
        String sqlPw = getSqlPw(name);
        
        //将数据写回
        PrintWriter out = response.getWriter();
        
        System.out.println(sqlPw);
        //out.write(sqlPw); //sqlPw为xml数据
        out.close();
        */
    }
}