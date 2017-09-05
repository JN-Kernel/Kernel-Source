$(function(){
	getList(1);
	
	
});

function getList(pageNum){
	var list_url = "index/getUserTopicList.do";
	var userId = $("#username").attr("userid");
	var data = {"userId":userId,"pageNum":pageNum}
//	alert(JSON.stringify(data));
	$.post(list_url,data,function(data,stauts){
		if(stauts == "success"){
			if( data.stauts == "success"){
				var list = data.data.list;
				$.each(list,function(index,value){
					var str = '<tr class="tbl-item">'
							+'<td class="td-block"><p class="date">'+new Date(value.publishtime).toLocaleString()+'</p>'
							+'<p class="title">'+value.title+'</p>'
							+'<p class="desc">'
							+'<a href="topic.html?topicId='+value.topicId+'">查看 </a>'
							+'<a class="delete" topicid="'+value.topicId+'"> | 删除</a>'
							+'</p>'
							+'<p class="like">'+value.likecount+'赞</p>'
							+'<p class="like">'+value.replycount+'评论</p>'
							+'</td>'
							+'</tr>';
					$("table.demo-tbl").append(str);
				});
				
				$(".delete").on("click",function(){
					alert("ss");
					var data = $(this).attr("topicid");
					var postData = {"topicId":data};
					$.post("user/delete.do",function(data,stauts){
						if(stauts == "success"){
							alert(data.data);
						}else{
							alert("发送失败");
						}
					});
				});
			}else{
				$("table.demo-tbl").append('<tr  class="tbl-item"><td class="td-block">您还未发表文章</td></tr>');
			}
		}else{
			alert("不能获取");
		}
	});
}