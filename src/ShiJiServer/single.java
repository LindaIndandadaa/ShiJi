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
	
	private static final String driver = "com.mysql.jdbc.Driver"; //���ݿ�����  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //�������ݿ��URL��ַ  
    private static final String username="root";//���ݿ���û���  
    private static final String password="root";//���ݿ������</span>   
       
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
              /// ע�� JDBC ������ 
              Class.forName(driver);
              
              /// ��һ������ 
              conn = DriverManager.getConnection(url,username,password);
               
              /// ִ�� SQL ���
              stmt = conn.createStatement();//�������ݿ�ʱ�����һ����ִ�е�SQL���ִ�ж���
              String sql;
              //new Date().getTime();ʱ�������Ϊid
              sql =  "select * from single_blog where blog_name = "+"\'"+name+" \'"; //ѡ����ƥ���blog
              System.out.println(sql);
              //ִ�в�ѯ�������
              ResultSet rs = stmt.executeQuery(sql);//��ѯ  
              while(rs.next())
              {
            	  sBlog.setBlogname(rs.getString("blog_name"));
            	  sBlog.setBlogtime(rs.getString("blog_time"));
            	  sBlog.setBlogperson(rs.getString("blog_person"));
            	  sBlog.setBlogcontent(rs.getString("blog_content"));
              }
             // sBlog.printBlog();
              
              // ��ɺ�ر� 
              stmt.close();
              conn.close();
          } catch(SQLException se) {
              // ���� JDBC ����
              se.printStackTrace();
          } catch(Exception e) {
              // ���� Class.forName ����
              e.printStackTrace();
          }finally{
              // ��������ڹر���Դ�Ŀ�
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
	    String name = request.getParameter("blogName");//��ȡ��������
	    System.out.println(name);
	    get_singleBlog(sBlog, name); 
	    
	    
	    Gson gson=new Gson();
	    String sb = gson.toJson(sBlog);
	    
	    
        //JSONArray jsonArray2 = JSONArray.fromObject(arr);
        //��java����ת����ת����json����   
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
