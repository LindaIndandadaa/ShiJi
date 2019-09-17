package ShiJiServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class register
 */
@WebServlet("/register")
public class register extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private static final String driver = "com.mysql.jdbc.Driver"; //数据库驱动  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //连接数据库的URL地址  
    private static final String username="root";//数据库的用户名  
    private static final String password="root";//数据库的密码</span>   
    
    boolean insertUser(String name, String paw, String phone)
    {
    	java.sql.Connection conn = null;
        Statement stmt = null;
    	boolean result=false;
    	  try{
              // 注册 JDBC 驱动器
    		  //System.out.println("注册jdbc");
              Class.forName(driver);
              
              // 打开一个连接
              //System.out.println("打开连接");
              conn = DriverManager.getConnection(url,username,password);

              // 执行 SQL 插入
              stmt = conn.createStatement();//连接数据库时，获得一个可执行的SQL语句执行对象；
              String sql;
              sql = "INSERT INTO user" +" VALUES "+ "("+"\'"+ name+"\',\'"+paw+"\',\'"+phone+"\')";
              System.out.println(sql);
              
              int count = stmt.executeUpdate(sql);
              
 /*
              //获取数据库传回来的数据 
              if(ret)
              {
            	  count = stmt.getUpdateCount();
              }
              */
              System.out.println("count:"+count);
              //获取数据库传回来的数据 
              if(count!=0)//执行插入成功 
              {
            	  System.out.println("插入成功！");  
                  result= true;
              }
              else
            	  System.out.println("插入失败"); 
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
		return result; 
    }
       
    boolean check_exit(String name)
    { 
    	java.sql.Connection conn = null;
        Statement stmt = null; 
        boolean result=false;
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
            sql = "SELECT name FROM  user WHERE name=\'"+name+"\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            
            String pw = null;
            //获取数据库传回来的数据 
            while (rs.next())
            {
          	  pw = rs.getString("name"); 
            }
            if(pw!=null)
            	result=true;
            
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
       System.out.println("检查存在？"+result);
        return result;
      
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		 PrintWriter out = response.getWriter();
		String result = "no";
        // 接收HTML页面上的表单内容。
        String name =request.getParameter("name");
        String passwd =  request.getParameter("password");
        String phone =  request.getParameter("phone");
        
        System.out.println("UserName:"+name+"; PassWord:"+passwd+"; phone:"+phone); 
        
        //检查用户名是否已经注册过，存在注册为true，
        if(check_exit(name))
        {
        	result="have";
        }else    
        { 
        	//执行插入，成功返回yes，失败为no
            if(insertUser(name, passwd, phone))
            {
            	result="yes";
            }else {
            	result="no";
    		}
        }
        
        System.out.println(result);
        //将数据写回 
    	out.write(result); 
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	doGet(request, response); 
	}

}
