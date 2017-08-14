$(document).ready(function(){
	$.post("user/isLogined.do",function(data,stauts){
		if(stauts == "success"){
			if(data == "true"){
				$.post("user/getUsername.do",function(data,stauts){
					if(stauts == "success"){
						$(".guest").remove();
						$("#userinfo").html('<a href="#">'
								+data.username
								+'</a>'
								+'<ul class="sub-menu">'
								+'<li><a href="#"><i class="glyphicon glyphicon-user"></i> 我的信息</a></li>'
								+'<li><a href="#"><i class="glyphicon glyphicon-pencil"></i> 发表主题</a></li>'
								+'<li><a href="#"><i class="glyphicon glyphicon-cloud"></i> 已发表文章</a></li>'
								+'<li class="divider"></li>'
								+'<li><a href="user/logout.do"><i class="glyphicon glyphicon-log-out"></i>退出</a></li>'
								+'</ul>'
						);
						$("#userinfo").attr("userid",data.userId)
					}
					
				});
						
			}
		}else{
			$("#userinfo").empty();
		}
	});
	
});