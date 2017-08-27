$(function(){
	//搜索
	$("#searchSumbit").click(function() {
		$("#searchAlert").text("");
		var str = $("#search").val().trim();
		if (str == null || str == '') {
			$("#searchAlert").text("请输入搜索的话题");
		} else {
			searchTopic("1");
		}
	});
	
	//自定义回车提交
	$('#search').keypress(function(e){
        if(e.keyCode==13){
            e.preventDefault();
            $("#searchSumbit").click();
        }
	});
	
	//分页
	$(document).on("click",".navigatePage",function(){
		var pageNum = $(this).attr("pagenum");
		searchTopic(pageNum);
	});
	
	
	
	
	
});

function searchTopic(pageNum){
	$.ajax({
		type : "post",
		url : "index/search.do",
		data : $("#searchForm").serialize()+"&pageNum="+pageNum,
		dataType : "json",
		success : function(data, stauts) {
			if (stauts == "success") {
				if (data == null || data == "") {
					$("#searchAlert").text("暂时获取不到数据，请稍后再试！");
				} else {
					$("#showPlane").empty(); // 清空原有内容
					dataPadding(data.list);
					pagination(data);
				}
			} else {
				$("#searchAlert").text("暂时获取不到数据，请稍后再试！");
			}
		},
		error : function() {
			$("#searchAlert").text("获取数据时发生错误，请更换关键字重试！");
		},
	});
}


function dataPadding(data) {
	$("#showPlane").append('<div id="planeList" class="span8 main-listing"></div>');
	if(data == null || data ==""){
		$("#planeList").append('<div class="alert alert-danger">'
					+ '<h4>找不到有关Topic，请更换关键字重新查询！</h4>' + '</div>');
	}
	$.each(data, function(n, value) {
		var date = new Date(value.publishtime);
		var str = '<article class="format-standard type-post hentry clearfix">'
				+ '<header class="clearfix">' 
				+ '<h3 class="post-title">'
				+ '<a href="topic.html?topicId='
				+ value.topicId
				+ '">'
				+ value.title
				+ '</a>'
				+ '</h3>'
				+ '<div class="post-meta clearfix">'
				+ '<span class="date">'
				+ date.toLocaleString()
				+ '</span>'
				+ '<span class="comments"><a title="'
				+ value.title
				+ '的评论">'
				+ value.replycount
				+ '条评论</a></span>'
				+ '<span class="like-count">'
				+ value.likecount
				+ '</span>'
				+ '</div>'
				+ '</header>'
				+ '<p>'
				+ value.content
				+ '. . . <a class="readmore-link" href="topic.html?topicId='
				+ value.topicId 
				+ '">查看详情</a></p>' 
				+ '</article>';
		$("#planeList").append(str);
	});
}


function pagination(data){
	$("#planeList").append('<ul id="paginationUl" class="pagination"></ul>');
	var paginationUl = $("#paginationUl");
	if(data.hasPreviousPage){
		paginationUl.append('<li><a class="navigatePage" pageNum="'+data.prePage+'">&laquo;</a></li>');
	}else{
		paginationUl.append('<li class="disabled"><a class="navigatePage" pageNum="'+data.prePage+'">&laquo;</a></li>');
	}
	$.each(data.navigatepageNums,function(n,value){
		var str = '<li ';
		if(data.pageNum == value){
			str +=  'class="active"';
		}
		str += '><a class="navigatePage" pagenum="'+value+'">'+value+'</a></li>';
		paginationUl.append(str);
	});
	
	if(data.hasNextPage){
		paginationUl.append('<li><a class="navigatePage" pageNum="'+data.nextPage+'">&laquo;</a></li>');
	}else{
		paginationUl.append('<li class="disabled"><a class="navigatePage" pageNum="'+data.nextPage+'">&raquo;</a></li>');
	}
}

