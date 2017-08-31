/*
 * 分页显示处理文件
 */
function pagination(data){
	if(data.size <= 0){
		return 0;
	}
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
