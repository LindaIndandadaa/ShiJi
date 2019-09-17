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
 * 从数据库里找detective的内容并通过jsonArray传递给AJAX，
 */
@WebServlet("/detective")
public class detective extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String driver = "com.mysql.jdbc.Driver"; //数据库驱动  
    private static final String url="jdbc:mysql://localhost:3307/shiji?serverTimezone=UTC"; //连接数据库的URL地址  
    private static final String username="root";//数据库的用户名  
    private static final String password="root";//数据库的密码</span>   
    
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
              /// 注册 JDBC 驱动器 
              Class.forName(driver);
              
              /// 打开一个连接 
              conn = DriverManager.getConnection(url,username,password);
               
              /// 执行 SQL 语句
              stmt = conn.createStatement();//连接数据库时，获得一个可执行的SQL语句执行对象；
              String sql;
              //new Date().getTime();时间戳，作为id
              sql =  "select * from detective"; //全部选出来
              System.out.println(sql);
              
              ResultSet rs = stmt.executeQuery(sql);//查询  
              List<String> namelist=new ArrayList<String>();//创建取结果的列表，之所以使用列表，不用数组，因为现在还不知道结果有多少，不能确定数组长度，所有先用list接收，然后转为数组  
              List<String> introlist=new ArrayList<String>();
              while (rs.next()) 
              {		//如果有数据， 加如list  
            	  	namelist.add(rs.getString(1)); 
            	  	introlist.add(rs.getString(2)); 
              }  
              if(namelist != null && namelist.size()>0)
              {		//如果list中存入了数据，转化为数组  
            	  	Detective arr[]=new Detective[namelist.size()];//创建一个和list长度一样的数组  
            	  	
					for(int i=0;i<namelist.size();i++)  
					{ 
						arr[i] = new Detective();
						arr[i].setDetectivename(namelist.get(i));//名字赋值。
						arr[i].setDetectiveintro(introlist.get(i));//介绍赋值
						arr1[i]=arr[i];////
					}   

					//for(int i=0;i<arr.length;i++)  
						//System.out.println(arr[i].getDetectivename()+" "+arr[i].getDetectiveintro()); 
					index = arr.length;
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
        
       
        Map m = new HashMap();//传递map
        int index = 0;
        Detective  arr[]=new Detective[10];//不知道大小，做一个大一点的  
        getDetective(arr, index);  //已赋值。注意容量
        for(int i=0; i<index; i++)
        	m.put("d"+i, arr[i]);
         
        JSONArray jsonArray2 = JSONArray.fromObject(arr);
        //把java数组转化成转化成json对象   
        out.print(jsonArray2);//return to ajax
		
      //  out.write(result);
        out.flush(); 
        out.close();
        System.out.println("detective finish。");
	}

}
