/***
*读取指定的Cookie值
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

function detective()
{
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
                var json=JSON.parse(xhr.responseText); 
                var str, ida, idv;
                //写入到界面上
                for(var i=0; i < 3; i++) { 
                	ida="heada-"+(i+1);
                	idv="intro-"+(i+1);
                	///alert(ida+" "+idv);
                	$(ida).innerHTML=json[i].detectivename;
                	$(idv).innerHTML=json[i].detectiveintro;
                }  
            }         
        } 
    } 
	 //3.建立与服务器的连接  
    xhr.open("GET","detective"+"?time="+new Date().getTime());  
    //4.向服务器发出请求  
    xhr.send(); 
}

function init() {
   var username = ReadCookie("username");  
    if(username.length>0) { 
        $("login-name").innerHTML = "欢迎："+username;
        $("login-name").style.color = "#E0BD62";
    } else {
        alert("请登陆！");
    } 
    detective();
   // alert("detective了");
}
 