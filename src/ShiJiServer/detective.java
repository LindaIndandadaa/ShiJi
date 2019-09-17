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

import com.google.gson.Gson;

import entity.Detective;
import net.sf.json.JSONArray; 
 

/**
 * Servlet implementation class detective
 * �����ݿ�����detective�����ݲ�ͨ��jsonArray���ݸ�AJAX��
 */
@WebServlet("/detective")
public class detective extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String driver = "com.mysql.jdbc.Driver"; //���ݿ�����  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //�������ݿ��URL��ַ  
    private static final String username="root";//���ݿ���û���  
    private static final String password="root";//���ݿ������</span>   
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public detective() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void getDetective(Detective arr1[], int index)
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
              sql =  "select * from detective"; //ȫ��ѡ����
              System.out.println(sql);
              
              ResultSet rs = stmt.executeQuery(sql);//��ѯ  
              List<String> namelist=new ArrayList<String>();//����ȡ������б�֮����ʹ���б��������飬��Ϊ���ڻ���֪������ж��٣�����ȷ�����鳤�ȣ���������list���գ�Ȼ��תΪ����  
              List<String> introlist=new ArrayList<String>();
              while (rs.next()) 
              {		//��������ݣ� ����list  
            	  	namelist.add(rs.getString(1)); 
            	  	introlist.add(rs.getString(2)); 
              }  
              if(namelist != null && namelist.size()>0)
              {		//���list�д��������ݣ�ת��Ϊ����  
            	  	Detective arr[]=new Detective[namelist.size()];//����һ����list����һ��������  
            	  	
					for(int i=0;i<namelist.size();i++)  
					{ 
						arr[i] = new Detective();
						arr[i].setDetectivename(namelist.get(i));//���ָ�ֵ��
						arr[i].setDetectiveintro(introlist.get(i));//���ܸ�ֵ
						arr1[i]=arr[i];////
					}   

					//for(int i=0;i<arr.length;i++)  
						//System.out.println(arr[i].getDetectivename()+" "+arr[i].getDetectiveintro()); 
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");  
        System.out.println("detective");  
        PrintWriter out=response.getWriter();   
        
       
        Map m = new HashMap();//����map
        int index = 0;
        Detective  arr[]=new Detective[10];//��֪����С����һ����һ���  
        getDetective(arr, index);  //�Ѹ�ֵ��ע������
        for(int i=0; i<index; i++)
        	m.put("d"+i, arr[i]);
         
        JSONArray jsonArray2 = JSONArray.fromObject(arr);
        //��java����ת����ת����json����   
        out.print(jsonArray2);//return to ajax
		
      //  out.write(result);
        out.flush(); 
        out.close();
        System.out.println("detective finish��");
	}

}
