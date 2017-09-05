$(function(){
	var url = "user/getUsername.do";
	
	$.ajax({
		  url: url,
		  type: "POST",
		  async: false,	//同步
		  success:function(data){
			  if(data.stauts == "success"){
					$("#username").text(data.username);
					$("#username").attr("userid",data.userId);	
					
				}else{
					alert("获取用户信息失败！请重新登录！");
					$(window).attr('location',"login.html");
				}
		  },
		  error:function(){
			  $("#username").text("获取失败！");
		  }
		}); 
	
});