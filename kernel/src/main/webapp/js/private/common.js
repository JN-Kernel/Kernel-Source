$(function(){
	var url = "user/getUsername.do";
	$.post(url,function(data,stauts){
		if(stauts == "success"){
			if(data.stauts == "success"){
				$("#username").text(data.username);
				$("#username").attr("userId",data.userId);				
			}else{
				alert("获取用户信息失败！请重新登录！");
				$(window).attr('location',"login.html");
			}
			
		}else{
			$("#username").text("获取失败！");
		}
	});           
	
});