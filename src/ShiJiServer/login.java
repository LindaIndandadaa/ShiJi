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
    
    private static final String driver = "com.mysql.jdbc.Driver"; //���ݿ�����  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //�������ݿ��URL��ַ  
    private static final String username="root";//���ݿ���û���  
    private static final String password="root";//���ݿ������</span>   
    
    String getSqlPw(String name)
    {
    	String pw = null;
    	java.sql.Connection conn = null;
        Statement stmt = null;
    	
    	  try{
              // ע�� JDBC ������
    		  //System.out.println("ע��jdbc");
              Class.forName(driver);
              
              // ��һ������
              //System.out.println("������");
              conn = DriverManager.getConnection(url,username,password);

              // ִ�� SQL ��ѯ
              stmt = conn.createStatement();//�������ݿ�ʱ�����һ����ִ�е�SQL���ִ�ж���
              String sql;
              sql = "SELECT passwd FROM  user WHERE name=\'"+name+"\'";
              System.out.println(sql);
              ResultSet rs = stmt.executeQuery(sql);
 
              //��ȡ���ݿ⴫���������� 
              while (rs.next())
              {
            	  pw = rs.getString("passwd"); 
              }
              
              System.out.println("���룺"+pw);
              
              // ��ɺ�ر�
              rs.close();
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

         //String title = "�û���¼";
         // ����HTMLҳ���ϵı����ݡ�
         String name =request.getParameter("name");
         String passwd =  request.getParameter("password");
         
         System.out.println("UserName:"+name+";"+"PassWord:"+passwd); 
         
         //��ȡ���ݿ����Ӧ���ֵ����룬getSqlPw(String); �����ݿ�Ľ�����ȡ����
         String sqlPw = getSqlPw(name);
         
         //������д��
         PrintWriter out = response.getWriter();
         if(sqlPw==null)
        	 sqlPw = "NULL";
         out.write(sqlPw); //sqlPwΪxml����
         out.close();
    }
    
    // ���� POST ��������ķ���
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html; charset=UTF-8");
    	doGet(request, response);
    	// ������Ӧ��������
        /*
        response.setContentType("text/html;charset=UTF-8");

        //String title = "�û���¼";
        // ����HTMLҳ���ϵı����ݡ�
        String name =new String(request.getParameter("username"));
        String passwd =  request.getParameter("password");
        
        System.out.println("UserName:"+name+";"+"PassWord:"+passwd); 
        
        //��ȡ���ݿ����Ӧ���ֵ����룬getSqlPw(String); �����ݿ�Ľ�����ȡ����
        String sqlPw = getSqlPw(name);
        
        //������д��
        PrintWriter out = response.getWriter();
        
        System.out.println(sqlPw);
        //out.write(sqlPw); //sqlPwΪxml����
        out.close();
        */
    }
}