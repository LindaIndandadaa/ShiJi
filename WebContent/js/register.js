function is_null(x) { //判断是否为空
    if(x == null || x == "")
        return true;
    else
        return false;
}
function $(id) {
    return document.getElementById(id);
}

function check_exist(name, pwd, phone) {//检查数据库中是否已存在该注册名
	//AJAX交互
	/******检查是否在数据库中某项里存在name*******/
	 //1/得到xhr对象   
	var xmlhttp=new XMLHttpRequest();
	
	 //2.注册状态变化监听器 
	xmlhttp.onreadystatechange=function()
	{  
		//alert(xmlhttp.readyState+" "+xmlhttp.status); 
		//alert(result);
		if (xmlhttp.readyState==4 )
		{ 
			if(xmlhttp.status== 200 || xmlhttp.status==0)
			{
				
				var result = xmlhttp.responseText; //接收servlet返回的信息
				console.log(result.length); 
				if(result=="have")
					alert("该用户名已存在");
				else 
				if(result=="no")
					alert("注册失败");
				else
					{
						alert("注册成功！请往登录");
						location.href = "login.html";  //跳转登录 
					}  
			}
			
		}
	}
	
	//3.建立与服务器的连接
	xmlhttp.open("POST","register"+"?time="+new Date().getTime(),true); 
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");

	//4.向服务器发出请求 
	data = "name="+name+"&password="+pwd+"&phone="+phone;
	xmlhttp.send(data); 
} 

function register() {
    var name = document.forms["register-form"]["username"].value;
    var pwd = document.forms["register-form"]["password"].value;
    var pwd1 = document.forms["register-form"]["re-password"].value;
    var phone = document.forms["register-form"]["phone"].value;

    if(is_null(name) || is_null(pwd) || is_null(pwd1) || is_null(phone))
        alert("该页内容均为必填!");
    else {
    	if(pwd!=pwd1)//检查两次密码一样吗
    		alert("两次输入密码不一致！");
    	else
    		{
    			alert(name+" "+pwd+" "+ phone);
    			check_exist(name, pwd, phone);//插入数据
    		} 
    }

}