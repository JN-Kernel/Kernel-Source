// #替换
$(function(){
	var param = GetQueryString("topicId");
	if(param == null){
		var url = document.referrer;
		url = url == ""?"index.html":url;
		alert("错误请求！");
		$(window).attr('location',url);					
	}else{
		// 获取评论数据
		getTopic(param);	
				
	}
	
});

// topic
function getTopic(param){
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
				// 导航栏
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
				
				// title
				$("#topic_title").text(topic.title);
				$("#topic_title").attr("href","topic.html?topicId="+topic.topicId);
				
				// topic info
				$("#topic_info").children("span.date").text(new Date(topic.publishtime).toLocaleString());
				
				$("#topic_info").children("span.category").html('<a href="#替换"'
								+'title="查看所有'+catoreyname+'分类">'+catoreyname+'</a>');
				$("#topic_info").children("span.comments").html('<a href="#commentlist-plane" '
								+'title="有'+topic.replycount+'条评论">'+topic.replycount
								+'条评论</a>');
				$("#topic_info").children("span.like-count").text(topic.likecount);

				// topic content
				$("#topic_content").html(content);
				
				// like-btn.
				isLiked(param);
				$("#likeBtn").text(topic.likecount);
				
				// Comments
				$("#comments-title").text(topic.replycount+"条评论");
				var it = 1;
				getReplys(param,it);
				
				//comment topic
				$("#submit").on("click",{param},comment);
				
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

// reply
function getReplys(topicId,pageNum){
	var postData = "topicId="+topicId+"&pageNum="+pageNum;
	$.post("index/getTopicReplys.do",postData,function(data,stauts){
		if(stauts == "success"){
			if(data.stauts == "success"){
				replysHandle(data.data.list);	//内容处理
				pagination(data.data);	//分页
			}else{
				$("#comments-title").text(data.data);
			}
		}else{
			$("#comments-title").text("暂时无法获取到评论信息！请稍后再试！");
		}
	});
}

// replys处理,传入list
function replysHandle(data){
	if(data == null || data == ""){
		$("#comments-title").text("获取评论时发生错误！请刷新重试！");
	}else{
		//初始化评论区
		$("#commentlist-plane").remove();
		//创建评论区
		$("#comments-title").after('<ol class="commentlist" id="commentlist-plane">');
		var $plane = $("#commentlist-plane");
		$.each(data,function(index,value){
			//评论渲染
			replyRender(value,$plane);
			
		});
	}
}

//渲染评论内容
function replyRender(value,$plane){
	var id = value.topicReplyId;
	
	var imgAlt = value.reviewer;
	// #替换，后期修改头像地址
	var imgSrc = "http://1.gravatar.com/avatar/50a7625001317a58444a20ece817aeca?s=60&amp;d=http%3A%2F%2F1.gravatar.com%2Favatar%2Fad516503a11cd5ca435acc9bb6523536%3Fs%3D60&amp;r=G"
	var imgUrl = "#替换";
	var homeUrl = "#替换";
		
	var commentId = '#comment-'+id;
	
	// 评论处理
	$plane.append('<li class="comment" id="li-comment-'+id+'"></li>');
	// 添加评论内容
	$('#li-comment-'+id).append('<article id="comment-'+id+'"></article>');
	// 评论人头像
	$(commentId).append('<a href="'+imgUrl+'">'
				+ '<img alt="'+imgAlt+'" src="'+imgSrc+'" class="avatar avatar-60 photo" height="60" width="60">'
				+ '</a>');
	// 评论信息
	$(commentId).append('<div class="comment-meta"></div>');
	$(commentId).children("div.comment-meta")
				.append('<h5 class="author">'
							+'<cite class="fn"> <a href="'+homeUrl+'" rel="external nofollow" class="url">'
							+value.reviewer
							+'</a></cite>'
						+'</h5>');
	$(commentId).children("div.comment-meta")
				.append('<p class="date"><a href="#">'
							+'<time>'+new Date(value.replytime).toLocaleString()+'</time>'
						+'</a></p>');
	
	//评论内容
	$(commentId).append('<div class="comment-body"><div>');
	$(commentId).children("div.comment-body").append(value.content);
	
	if(value.childReplys != null){
		//创建子评论面板
		$('#li-comment-'+id).append('<ul class="children"></ul>');
		$.each(value.childReplys,function(index,value){
			var $childrenPlane = $('#li-comment-'+id).children("ul.children");
			replyRender(value,$childrenPlane);
		});
	}
}

// get url param
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);	// 取得param对应值
     if(r!=null)return  unescape(r[2]); return null;
}


function isLiked(topicId){
	$.post("index/getLikeStauts.do","topicId="+topicId,function(data,stauts){
		
		if(stauts == "success"){
			if(data.stauts == "success"){
				$("#likeBtn").attr("class","like-it");
				// 绑定点赞
				$("#likeBtn").on("click",{topicId},chickLike);
			}else {
				$("#likeBtn").attr("class","like-no");
			}
			$("#likeBtn").attr("data-original-title",data.data);
		}else{
			$("#likeBtn").attr("class","like-no");
			$("#likeBtn").attr("data-original-title","获取点赞数据失败！展示无法进行点赞操作，您可以尝试刷新本页面！");
		}
		
		
		// 绑定提示工具
		$("#likeBtn").hover(function(){
			$(this).tooltip("show");
		},function(){
			$(this).tooltip("hide");
		});
		

	});
}

// 点赞
function chickLike(event){
	var topicId = event.data.topicId;
	$.post("index/likeTopic.do","topicId="+topicId,function(data,stauts){
		if(stauts == "success"){
			if(data.stauts == "success"){
				$("#likeBtn").attr("class","like-it");
				isLiked(topicId);
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


function comment(event){
	var content = $("#comment").val();
	var param = event.data.param;
//	alert(param);
	if(content == null || content == ""){
		$("#comment-lable").text("请输入您的评论再发表！");
	}else{
		var postData = {"topicId":param,"content":content};
		$.post("user/commentTopic.do",postData,function(data,stauts){
			if(stauts == "success"){
				if(data.stauts == "success"){
					$("#comment").text("");
					getReplys(param,"1");
				}
				$("#comment-lable").text(data.data);
			}else{
				$("#comment-lable").text("发表失败！请重新发表");
			}
		});
	}
}
