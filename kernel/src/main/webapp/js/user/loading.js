$(function() {
	getIndexData();
	Date.prototype.toLocaleString = function() {
		return this.getFullYear() + "年" + (this.getMonth() + 1) + "月"
				+ this.getDate() + "日 ";
	};
	
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
	
	
	
	
	
	 /*	响应式菜单，copy by custom.js
    /*-----------------------------------------------------------------------------------*/
    var $mainNav    = $('.main-nav > div').children('ul');
    var optionsList = '<option value="" selected>Go to...</option>';
    
    $mainNav.find('li').each(function() {
            var $this   = $(this),
                    $anchor = $this.children('a'),
                    depth   = $this.parents('ul').length - 1,
                    indent  = '';
            if( depth ) {
                    while( depth > 0 ) {
                            indent += ' - ';
                            depth--;
                    }
            }
            optionsList += '<option value="' + $anchor.attr('href') + '">' + indent + ' ' + $anchor.text() + '</option>';
    }).end();

    $('.main-nav > div').after('<select class="responsive-nav">' + optionsList + '</select>');

    $('.responsive-nav').on('change', function() {
            window.location = $(this).val();
    });
	
	
	
});

function getIndexData(){
	// 主页数据加载
	getData("index/getLatestTopicList.do", $("#latestTopicList"),
			$("#latestTopicLoading"));
	getData("index/getLikeTopicList.do", $("#LikeTopicList"), $("#LikeTopicLoading"));
}

function getData(toUrl, obj, removeObj) {
	$.ajax({
		type : "post",
		url : JSON.stringify(toUrl).replace("\"", "").replace("\"", ""),
		dataType : "json",
		success : function(data, stauts) {
			removeObj.remove();
			if (stauts == "success") {
				if (data == "") {
					obj.append('<li class="alert alert-info">'
							+ '<h4>暂时没有最新发表的Topic！</h4>' + '</li>');
				} else {
					$.each(data, function(n, value) {
						var date = new Date(value.publishtime);
						var str = '<li class="article-entry standard">'
								+ '<h4><a href="single.html?topicId='
								+ value.topicId + '">' + value.title
								+ '</a></h4>' + '<span class="article-meta">'
								+ date.toLocaleString() + '</span>'
								+ '<span class="like-count ">'
								+ value.likecount + '</span>' + '</li>';
						obj.append(str);
					});
				}
			} else {
				obj.append('<li class="alert alert-danger">'
						+ '<h4>获取数据失败，请稍后刷新重试！</h4>' + '</li>');
			}
		},
		error : function(xhr) {
			removeObj.remove();
			obj.append('<li class="alert alert-danger">'
					+ '<h4>获取数据时发生异常，请稍后刷新重试！</h4>' + '</li>');
		}

	});
}

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
				+ '<a href="single.html?topicId='
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
				+ '. . . <a class="readmore-link" href="single.html?topicId='
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
		str += '><a id="qq" class="navigatePage" pagenum="'+value+'">'+value+'</a></li>';
		paginationUl.append(str);
	});
	
	if(data.hasNextPage){
		paginationUl.append('<li><a class="navigatePage" pageNum="'+data.nextPage+'">&laquo;</a></li>');
	}else{
		paginationUl.append('<li class="disabled"><a class="navigatePage" pageNum="'+data.nextPage+'">&raquo;</a></li>');
	}
}
