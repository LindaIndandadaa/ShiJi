 
//# sourceMappingURL=comment.js.map
function is_null(x)//判断x是否为空或者“”
{
    if(x == null || x == "")
        return true;
    else
        return false;
}
function $(id) {
	    return document.getElementById(id);
}

function ismail(x){ 
    var atpos=x.indexOf("@");
    var dotpos=x.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=x.length){ 
      return false;
    }else
      return true;
}

function insert_comment(name, mail, com)
{
	//1/得到xhr对象   
	var xmlhttp=new XMLHttpRequest();
	 //2.注册状态变化监听器 
	alert(xmlhttp.readyState+"---"+xmlhttp.status);
	xmlhttp.onreadystatechange=function()
	{ 
		alert(xmlhttp.readyState+"- "+xmlhttp.status);
		if (xmlhttp.readyState==4)
		{  
			if(xmlHttp.status== 200 || xmlHttp.status==0)
			{
				alert("插入成功");
				var result = xmlhttp.responseText; //接收servlet返回的执行结果 
				alert(result.length);
			    if(result==="yes")
			    	alert("感谢您的评论！");
			    else
			    	alert("出错，请稍后再评"+result);
			}
			
		} 
	}
	//3.建立与服务器的连接	
	xmlhttp.open("POST","comment"+"?time="+new Date().getTime(),true);  
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	//4.向服务器发出请求 "
	data = "name="+name+"&mail="+mail+"&comment="+com;
	alert(data);
	xmlhttp.send(data); 
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
        	insert_comment(name, mail, comm);
            //alert("感谢您的评论！")；
            return true;
        }
        else
            alert("邮箱格式错误，请正确填写！");
            return false;
    }
}

   