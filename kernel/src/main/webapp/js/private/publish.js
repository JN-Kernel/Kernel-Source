$(function(){
	
	var selected = $("#catoerySelect  option:selected").val();
	
	$("#submitButton").click(function() {
		alert(selected);
		if (selected == null || selected == "0") {
			$(".panel-body > form").before(alertStr("请选择文章类别"));
		}
		
	});
	loadCatorey();
	
});

function alertStr(str) {
	return '<div class="alert alert-danger">'
	+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
	+ str 
	+ '</div>';
}
//加载分类
function loadCatorey(){
	alert("sss");
	$.post("index/getAllCatorey.do",function(data,stauts){
		if(stauts == "success"){
			$("#catoerySelect option:first").text("请选择文章分类");
			$.each(data.data,function(index,value){
				$("#catoerySelect").append('<option value="'+value.catoreyId+'">'+catoreyname+'</option>');
			});
		}else{
			$("#catoerySelect option:first").text("获取数据时发生异常");
		}
	})
}