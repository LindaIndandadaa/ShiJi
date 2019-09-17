 //在多个JS之间利用cookie传值
 /***
* @param {string} cookieName Cookie名称
* @param {string} cookieValue Cookie值
* @param {number} nDays Cookie过期天数
*/
function SetCookie(cookieName,cookieValue,nDays) {
    /*当前日期*/
    var today = new Date();
    /*Cookie过期时间*/
    var expire = new Date();
    /*如果未设置nDays参数或者nDays为0，取默认值1*/
    if(nDays == null || nDays == 0) nDays = 1;
    /*计算Cookie过期时间*/
    expire.setTime(today.getTime() + 3600000 * 24 * nDays);
    /*设置Cookie值*/
    document.cookie = cookieName + "=" + escape(cookieValue)
        + ";expires=" + expire.toGMTString();
}

function $(id) {
    return document.getElementById(id);
}

function checkLogin(name, pwd) {
	//AJAX交互
	/******检查是否在数据库中某项里存在name*******/
	 //1/得到xhr对象   
	var xmlhttp=new XMLHttpRequest();
	
	 //2.注册状态变化监听器 
	xmlhttp.onreadystatechange=function()
	{ 
		
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{  
			var pw = xmlhttp.responseText; //接收servlet返回的密码，如果没有密码会返回null
			var save = $("pw").checked;
			//alert(pw);
		    if(pw===pwd)//如果密码相等
		    {   
				   alert("登录成功！");
				   /**设置cookie，传值 */
				   if(save) 
					   SetCookie("username",name,7);
				   else 
					   SetCookie("username",name,1);
		           location.href = "index.html";  //跳转到主页 
		    } else  
		    	if(pw==="NULL") //即不存在用户名
			        alert("用户名不存在!"); 
		    	else
		    		alert("密码错误!");//密码错误
		    	
		}
	}
	
	//3.建立与服务器的连接
	xmlhttp.open("POST","login"+"?time="+new Date().getTime(),true); 
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	//alert(name+pwd);
	//4.向服务器发出请求 "
	data = "name="+name+"&"+"password="+pwd;
	xmlhttp.send(data); 
}  

function is_null() {
    var name = document.forms["login-form"]["username"].value;
    var pawd = document.forms["login-form"]["password"].value;
    
    if(name == null || name == "")
    {
        alert("请输入用户名！")
        return false
    }
    if(pawd == null || pawd == "")
    {
        alert("请输入密码！")
        return false
    }
    if(name != null && pawd != null)
    {
        checkLogin(name, pawd);
    }        
}







