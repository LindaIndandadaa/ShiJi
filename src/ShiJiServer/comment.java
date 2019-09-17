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
	
	private static final String driver = "com.mysql.jdbc.Driver"; //���ݿ�����  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //�������ݿ��URL��ַ  
    private static final String username="root";//���ݿ���û���  
    private static final String password="root";//���ݿ������</span>   
    
    public boolean insert_comment(String name, String mail, String comment)
    { 
    	java.sql.Connection conn = null;
        Statement stmt = null;
    	
    	  try{
              // ע�� JDBC ������
    		  //System.out.println("ע��jdbc");
              Class.forName(driver);
              
              // ��һ������
              //System.out.println("������");
              conn = DriverManager.getConnection(url,username,password);
               
              // ִ�� SQL ����
              stmt = conn.createStatement();//�������ݿ�ʱ�����һ����ִ�е�SQL���ִ�ж���
              String sql;
              //new Date().getTime();ʱ�������Ϊid
              sql = "INSERT INTO comment" +" VALUES "+ "("+"\'"+new Date().getTime() +"\',\'"+name+"\',\'"+mail+"\',\'"+comment+"\')";
              System.out.println(sql);
              
              int count = stmt.executeUpdate(sql);
 
              //��ȡ���ݿ⴫���������� 
              System.out.println("count:"+count);
              //��ȡ���ݿ⴫���������� 
              if(count!=0)//ִ�в���ɹ� 
              {
            	  System.out.println("����ɹ���");  
            	  return true;
              }
              
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
    	  System.out.println("����ʧ�ܣ�"); 
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
				 result="yes";//����ɹ�
		 }
		 
		 System.out.println(result);
		 //������д�� 
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
