package ShiJiServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Blog;
import entity.Detective;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class blog
 */
@WebServlet("/blog")
public class blog extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String driver = "com.mysql.jdbc.Driver"; //���ݿ�����  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //�������ݿ��URL��ַ  
    private static final String username="root";//���ݿ���û���  
    private static final String password="root";//���ݿ������</span>   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public blog() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    //��all_blog������������Լ򻯲�ѯ��
    public void setAllBlog()
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
              sql =  "insert into allblog (select blog_id, blog_name, blog_content from single_blog)"; //ȫ��ѡ����
              System.out.println(sql);
              //ִ�в�ѯ�������
              int count = stmt.executeUpdate(sql);
              System.out.println(count);
              
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
    
    //��ȡ���ݿ��е�blog
    public void getBlog(Blog arr1[], int index)
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
              sql =  "select * from  allblog"; //ȫ��ѡ����
              System.out.println(sql);
              
              ResultSet rs = stmt.executeQuery(sql);//��ѯ  
              List<String> namelist=new ArrayList<String>();//����ȡ������б�֮����ʹ���б��������飬��Ϊ���ڻ���֪������ж��٣�����ȷ�����鳤�ȣ���������list���գ�Ȼ��תΪ����  
              List<String> contentlist=new ArrayList<String>();
              while (rs.next()) 
              {		//��������ݣ� ����list  
            	  	namelist.add(rs.getString(2)); 
            	  	contentlist.add(rs.getString(3)); 
              }  
              if(namelist != null && namelist.size()>0)
              {		//���list�д��������ݣ�ת��Ϊ����  
            	  	Blog arr[]=new Blog[namelist.size()];//����һ����list����һ��������  
            	  	
					for(int i=0;i<namelist.size();i++)  
					{ 
						arr[i] = new Blog();
						arr[i].setBlogname(namelist.get(i));//���ָ�ֵ��
						arr[i].setBlogcontent(contentlist.get(i));//���ܸ�ֵ
						arr1[i]=arr[i];////
					}   

					for(int i=0;i<arr.length;i++)  
						System.out.println(arr[i].getBlogname()+" "+arr[i].getBlogcontent()); 
					index = arr.length;
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
    	  
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub 
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");  
        System.out.println("\nBLOG");  
        PrintWriter out=response.getWriter();   
        
        setAllBlog();
        Map m = new HashMap();//����map
        int index = 0;
        Blog  arr[]=new Blog[100];//��֪����С����һ����һ���  
        getBlog(arr, index);  //�Ѹ�ֵ��ע������
        for(int i=0; i<index; i++)
        	m.put("d"+i, arr[i]);
         
        JSONArray jsonArray2 = JSONArray.fromObject(arr);
        //��java����ת����ת����json����   
        out.print(jsonArray2);//return to ajax
		
      //  out.write(result);
        out.flush(); 
        out.close();
        System.out.println("blog finish��\n");
	}

}
