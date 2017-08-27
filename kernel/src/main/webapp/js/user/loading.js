$(function() {
	getIndexData();
	Date.prototype.toLocaleString = function() {
		return this.getFullYear() + "年" + (this.getMonth() + 1) + "月"
				+ this.getDate() + "日 ";
	};
	
	
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
								+ '<h4><a href="topic.html?topicId='
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



