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
    
    private static final String driver = "com.mysql.jdbc.Driver"; //���ݿ�����  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //�������ݿ��URL��ַ  
    private static final String username="root";//���ݿ���û���  
    private static final String password="root";//���ݿ������</span>   
    
    boolean insertUser(String name, String paw, String phone)
    {
    	java.sql.Connection conn = null;
        Statement stmt = null;
    	boolean result=false;
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
              sql = "INSERT INTO user" +" VALUES "+ "("+"\'"+ name+"\',\'"+paw+"\',\'"+phone+"\')";
              System.out.println(sql);
              
              int count = stmt.executeUpdate(sql);
              
 /*
              //��ȡ���ݿ⴫���������� 
              if(ret)
              {
            	  count = stmt.getUpdateCount();
              }
              */
              System.out.println("count:"+count);
              //��ȡ���ݿ⴫���������� 
              if(count!=0)//ִ�в���ɹ� 
              {
            	  System.out.println("����ɹ���");  
                  result= true;
              }
              else
            	  System.out.println("����ʧ��"); 
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
		return result; 
    }
       
    boolean check_exit(String name)
    { 
    	java.sql.Connection conn = null;
        Statement stmt = null; 
        boolean result=false;
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
            sql = "SELECT name FROM  user WHERE name=\'"+name+"\'";
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            
            String pw = null;
            //��ȡ���ݿ⴫���������� 
            while (rs.next())
            {
          	  pw = rs.getString("name"); 
            }
            if(pw!=null)
            	result=true;
            
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
       System.out.println("�����ڣ�"+result);
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
        // ����HTMLҳ���ϵı����ݡ�
        String name =request.getParameter("name");
        String passwd =  request.getParameter("password");
        String phone =  request.getParameter("phone");
        
        System.out.println("UserName:"+name+"; PassWord:"+passwd+"; phone:"+phone); 
        
        //����û����Ƿ��Ѿ�ע���������ע��Ϊtrue��
        if(check_exit(name))
        {
        	result="have";
        }else    
        { 
        	//ִ�в��룬�ɹ�����yes��ʧ��Ϊno
            if(insertUser(name, passwd, phone))
            {
            	result="yes";
            }else {
            	result="no";
    		}
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
