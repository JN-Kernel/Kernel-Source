$(document).ready(function(){
	$.post("user/isLogined.do",function(data,stauts){
		var url = document.referrer;
		url = url == ""?"index.html":url;
		if(stauts == "success"){
			if(data == "true"){
				if(window.location == url){
					$(window).attr('location',"index.html");
				}else{
					alert("您已经登陆，不能访问该页面！"); 
					$(window).attr('location',url);					
				}
				
			}
		}
	});
	
});