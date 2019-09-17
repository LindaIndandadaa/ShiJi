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
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class comment
 */
@WebServlet("/comment")
public class comment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String driver = "com.mysql.jdbc.Driver"; //数据库驱动  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //连接数据库的URL地址  
    private static final String username="root";//数据库的用户名  
    private static final String password="root";//数据库的密码</span>   
    
    public boolean insert_comment(String name, String mail, String comment)
    { 
    	java.sql.Connection conn = null;
        Statement stmt = null;
    	
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
              //new Date().getTime();时间戳，作为id
              sql = "INSERT INTO comment" +" VALUES "+ "("+"\'"+new Date().getTime() +"\',\'"+name+"\',\'"+mail+"\',\'"+comment+"\')";
              System.out.println(sql);
              
              int count = stmt.executeUpdate(sql);
 
              //获取数据库传回来的数据 
              System.out.println("count:"+count);
              //获取数据库传回来的数据 
              if(count!=0)//执行插入成功 
              {
            	  System.out.println("插入成功！");  
            	  return true;
              }
              
              // 完成后关闭 
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
    	  System.out.println("插入失败！"); 
    	return false;
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		 response.setContentType("text/html;charset=UTF-8");
		 
		 String name=request.getParameter("name");
		 String mail=request.getParameter("mail");
		 String comment=request.getParameter("comment");
		 PrintWriter out = response.getWriter();

		 String result="no";
		 
		 if(name==null||mail==null||comment==null)
			 result="null";
		 else
		 {
			 boolean ins = insert_comment(name, mail, comment); 
			 if(ins)
				 result="yes";//插入成功
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
