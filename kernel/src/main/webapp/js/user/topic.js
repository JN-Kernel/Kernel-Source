// #替换
$(function(){
	var param = GetQueryString("topicId");
	if(param == null){
		var url = document.referrer;
		url = url == ""?"index.html":url;
		alert("错误请求！");
		$(window).attr('location',url);					
	}else{
		//获取数据
		alert($("#comments ol").attr("class"));
		getTopic(param);
		
		
	}
	
	Date.prototype.toLocaleString = function() {
		return this.getFullYear() + "年" + (this.getMonth() + 1) + "月"
				+ this.getDate() + "日 ";
	};
});

//topic
function getTopic(param){
	alert("ss");
	$.ajax({
		type : "post",
		url : "index/getTopic.do",
		data : "topicId="+param,
		dataType : "json",
		success : function(data){
			if(data.stauts == "success"){
				var topic = data.data;
				var catoreyname = topic.catoreyname;
				var content = topic.topicContent.content;
				//导航栏
				$(".page-content > ul").children().each(function(index){
					if(index == 1){
						var title = "查看所有"+catoreyname+"分类";
						var url = "topic.js;gettopic";
						var str = '<a href="'+url+'" title="'+title+'">'+catoreyname+'</a>';
						$(this).prepend(str);
					}else if(index == 2){
						$(this).text(topic.title);
					}
				});
				
				//title
				$("#topic_title").text(topic.title);
				$("#topic_title").attr("href","topic.html?topicId="+topic.topicId);
				
				//topic info
				$("#topic_info").children("span.date").text(new Date(topic.publishtime).toLocaleString());
				
				$("#topic_info").children("span.category").html('<a href="#替换"'
								+'title="查看所有'+catoreyname+'分类">'+catoreyname+'</a>');
				$("#topic_info").children("span.comments").html('<a href="#" '
								+'title="有'+topic.replycount+'条评论">'+topic.replycount
								+'条评论</a>');
				$("#topic_info").children("span.like-count").text(topic.likecount);

				//topic content
				$("#topic_content").html(content);
				
				//like-btn.
				isLiked(param);
				$("#likeBtn").text(topic.likecount);
				
				
				//Comments
				$("#comments-title").text(topic.replycount+"条评论");
				
			}else{				
				$(window).attr('location','index.html');
				alert(data.data);
			}
		},
		error : function(xhr){
			alert("获取数据失败，请稍候再试！！！\n错误提示： " + xhr.status + " " + xhr.statusText);
		}
	});
}

//reply
function getReplys(topicId,pageNum){
	var postData = "topicId="+topicId+"&pageNum="+pageNum;
	$.post("index/getTopicReplys.do",postData,function(data,stauts){
		if(stauts == "success"){
			alert(data);
		}else{
			$("#comments-title").text("暂时无法获取到评论信息！请稍后再试！");
		}
	});
}



//get url param
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);	//取得param对应值
     if(r!=null)return  unescape(r[2]); return null;
}


function isLiked(topicId){
	$.post("index/getLikeStauts.do","topicId="+topicId,function(data,stauts){
		
		if(stauts == "success"){
			if(data.stauts == "info"){
				$("#likeBtn").attr("class","like-it");
				//绑定点赞
				$("#likeBtn").on("click",{topicId},chickLike);
			}else {
				$("#likeBtn").attr("class","like-no");
			}
			$("#likeBtn").attr("data-original-title",data.data);
		}else{
			$("#likeBtn").attr("class","like-no");
			$("#likeBtn").attr("data-original-title","获取点赞数据失败！展示无法进行点赞操作，您可以尝试刷新本页面！");
		}
		
		
		//绑定提示工具
		$("#likeBtn").hover(function(){
			$(this).tooltip("show");
		},function(){
			$(this).tooltip("hide");
		});
		

	});
}

//点赞
function chickLike(event){
	var topicId = event.data.topicId;
	$.post("index/likeTopic.do","topicId="+topicId,function(data,stauts){
		if(stauts == "success"){
			if(data.stauts == "success"){
				$("#likeBtn").attr("class","like-it");
			}else{
				$("#likeBtn").attr("class","like-no");
			}
			
			$("#likeBtn").attr("data-original-title",data.data);
		}else{
			$("#likeBtn").attr("data-original-title","点赞时发生异常！请稍后再试！！");
		}
		$("#likeBtn").toolip("show");
	});
}
 