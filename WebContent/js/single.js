/*** 
*@param {string} cookieName Cookie名称
*/

function ReadCookie(cookieName) {
    var theCookie = "" + document.cookie;
    var ind = theCookie.indexOf(cookieName);
    if(ind==-1 || cookieName=="") return "";
    var ind1 = theCookie.indexOf(';',ind);

    if(ind1==-1) ind1 = theCookie.length;
    /*读取Cookie值*/
    return unescape(theCookie.substring(ind+cookieName.length+1,ind1));
}

function $(id) {
    return document.getElementById(id);
} 

function is_null(x)//判断x是否为空或者“”
{
    if(x == null || x == "")
        return true;
    else
        return false;
}

function ismail(x){ 
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){
      alert("不是一个有效的 e-mail 地址");
      return false;
    }else
      return true;
}

// 检查输入是否合法，一切合法范湖true提交，否则返回false，不提交
function checkComment()
{
    var name = document.forms["submit_comment"]["用户名"].value;
    var mail = document.forms["submit_comment"]["邮件"].value;
    var comm = document.forms["submit_comment"]["评论"].value; 
    if(is_null(name) || is_null(mail) || is_null(comm))
    {
        alert("以上均为必填内容，请全部填写！")
        return false;
    }else
    {
        //判断是否是正确email格式
        if(ismail(mail)) //是正确的mail
        {
            /************* 填写的内容传递进数据库 ****************** */
            //alert("感谢您的评论！")；
            return true;
        }
        else
            alert("邮箱格式错误，请正确填写！")
            return false;
    } 

}

window.onload=function()
{
    var blogName=ReadCookie("blogName");//
    var blogContent=ReadCookie("blogContent");// 
	//1/得到xhr对象   
	var xhr=new XMLHttpRequest();
	 //2.注册状态变化监听器 
	xhr.onreadystatechange=function()
	{   
		
        if(xhr.readyState==4)  
        {  
            if(xhr.status==200)  
            {    
            	//alert(xhr.readyState+"single-blog"+xhr.status); 
            	var json=JSON.parse(xhr.responseText); 
            	var name, time, person, content;    
                name=json.name;
                time=json.time;
                person=json.person;
                content=json.content;
                $("blogname").innerHTML=name;
                $("blogtime").innerHTML=time;
                $("blogperson").innerHTML="探员："+person;
                $("blogcontent").innerHTML=content;
            }         
        } 
    } 
	 //3.建立与服务器的连接  
    xhr.open("POST","single"+"?time="+new Date().getTime());  
    xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    data = "blogName="+blogName; 
   // alert("data"+data);
    //4.向服务器发出请求  
    xhr.send(data); 
}