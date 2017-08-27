// #替换
$(function(){
	var param = GetQueryString("topicId");
	if(param == null){
		var url = document.referrer;
		url = url == ""?"index.html":url;
		alert("错误请求！");
		$(window).attr('location',url);					
	}else{
		$("#likeBtn").click(function(){
			alert("aa");
		});
		//获取数据
//		getTopic(param);

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
				//导航栏
				$(".page-content > ul").children().each(function(index){
					if(index == 1){
						var title = "查看所有"+catoreyname+"分类";
						var url = "topic.js;gettopic";
						var str = '<a href="'+url+'" title="'+title+'">'+catoreyname+'</a>';
						$(this).html(str);
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
				alert("ss");
				//topic content
				$("#topic_content").html(topic.content);
				
				//like-btn
				$("#like-it-form").children("span.like-it").text(topic.likecount);
				$("#like-it-form").click(function(){
					alert("aa");
				});
				
				//Comments
				$("#comments-title").text(topic.replycount+"条评论");
				
			}else{
				alert(data.data);
			}
		},
		error : function(xhr){
			alert("获取数据失败，请稍候再试！！！\n错误提示： " + xhr.status + " " + xhr.statusText);
		}
	});
}

//reply
function getReplys(){
	
}

//replyHandle
function replysHandle(){
	
}

//get url param
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);	//取得param对应值
     if(r!=null)return  unescape(r[2]); return null;
}
 