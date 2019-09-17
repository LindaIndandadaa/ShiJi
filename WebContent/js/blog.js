
/***
* @param {string} cookieName Cookie名称
* @param {string} cookieValue Cookie值
* @param {number} nDays Cookie过期天数
*/
/**
 * 将所有的博客从数据库里呈现出来
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

var blog_name=new Array();
var blog_content=new Array();
window.onload=function blog()
{
	//alert("blog");
	//alert("detective!");
	//1/得到xhr对象   
	var xhr=new XMLHttpRequest();
	 //2.注册状态变化监听器 
	xhr.onreadystatechange=function()
	{   
		
        if(xhr.readyState==4)  
        {  
            if(xhr.status==200)  
            {    
            	//alert(xhr.readyState+"blog"+xhr.status);
                var json=JSON.parse(xhr.responseText); 
                var str, ida, idv;
                //写入到界面上
                for(var i=0; i < 3; i++) {  
                	ida="blog"+(i+1);
                	idv="blogC"+(i+1);
                	///alert(ida+" "+idv);
                	$(ida).innerHTML=json[i].blogname;
                	$(idv).innerHTML=json[i].blogcontent;
                    blog_name[i]=json[i].blogname; 
                    blog_content[i]=json[i].blogcontent;
                }  
            }         
        } 
    } 
	 //3.建立与服务器的连接  
    xhr.open("GET","blog"+"?time="+new Date().getTime());  
    //4.向服务器发出请求  
    xhr.send(); 
}

function blog(obj)
{  
	var  value = parseInt(obj.id.charAt(obj.id.length-1))-1; 
    SetCookie("blogName", blog_name[value],7);//设置cookie传值
    SetCookie("blogContent", blog_content[value],7);//设置cookie传值
}




