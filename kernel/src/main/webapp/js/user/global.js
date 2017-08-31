$(document).ready(function(){

	$.post("user/isLogined.do",function(data,stauts){
		if(stauts == "success"){	
			if(data == "true"){
				//topic.html
				topic(true);
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
						$("#userinfo").attr("userid",data.userId);
					}
					
				});
						
			}else{
				topic(false);
			}
		}else{
			$("#userinfo").empty();
		}
	});
});


//topic页面处理,flag为true时代表可以使用评论发表
function topic(flag){
	var url = window.location.pathname;
	var reg = /\/topic.html/;
	//判断是否为topic.html
	if(reg.exec(url) != null){
		if(!flag){	//未登录
			$("textarea").attr("disabled","disabled");
			$("textarea").text("请登陆后再发表您的评论!");
			$("#submit").attr("disabled","disabled");
		}else{
			$("textarea").removeAttr("disabled");
			$("textarea").text("");
			$("#submit").removeAttr("disabled");
		}
	}
}