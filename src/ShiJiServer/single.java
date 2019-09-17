package ShiJiServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Blog;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class single
 */
@WebServlet("/single")
public class single extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String driver = "com.mysql.jdbc.Driver"; //数据库驱动  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //连接数据库的URL地址  
    private static final String username="root";//数据库的用户名  
    private static final String password="root";//数据库的密码</span>   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public single() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void get_singleBlog(Blog sBlog, String name)
    {
    	java.sql.Connection conn = null;
        Statement stmt = null;  	
        
    	  try{
              /// 注册 JDBC 驱动器 
              Class.forName(driver);
              
              /// 打开一个连接 
              conn = DriverManager.getConnection(url,username,password);
               
              /// 执行 SQL 语句
              stmt = conn.createStatement();//连接数据库时，获得一个可执行的SQL语句执行对象；
              String sql;
              //new Date().getTime();时间戳，作为id
              sql =  "select * from single_blog where blog_name = "+"\'"+name+" \'"; //选出来匹配的blog
              System.out.println(sql);
              //执行查询插入更新
              ResultSet rs = stmt.executeQuery(sql);//查询  
              while(rs.next())
              {
            	  sBlog.setBlogname(rs.getString("blog_name"));
            	  sBlog.setBlogtime(rs.getString("blog_time"));
            	  sBlog.setBlogperson(rs.getString("blog_person"));
            	  sBlog.setBlogcontent(rs.getString("blog_content"));
              }
             // sBlog.printBlog();
              
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
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");  
		
		System.out.println("\nSINGLE-BLOG");  
	    PrintWriter out=response.getWriter();  
	    
	    Blog sBlog = new Blog();
	    String name = request.getParameter("blogName");//获取博客名字
	    System.out.println(name);
	    get_singleBlog(sBlog, name); 
	    
	    
	    Gson gson=new Gson();
	    String sb = gson.toJson(sBlog);
	    
	    
        //JSONArray jsonArray2 = JSONArray.fromObject(arr);
        //把java数组转化成转化成json对象   
        out.print(sb);//return to ajax
	    System.out.println(sb); 
	    out.flush();
	    out.close();
		 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
		 
	}

}
