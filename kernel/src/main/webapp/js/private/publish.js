$(function(){
	
	
	$("#submitButton").on("click",function() {
		$("#alert_publish").remove();	//隐藏警告
		var selected = $("#catoerySelect  option:selected").val();
		var title = $("#topic-title").val().trim();
		var ue = UE.getEditor('container');
		
		if( title == null || title == ""){
			$(".panel-body > form").before(alertStr("请输入标题","danger"));
		}else if (selected == null || selected == "0") {
			$(".panel-body > form").before(alertStr("请选择文章类别","danger"));
		}else if(!ue.hasContents()){
			$(".panel-body > form").before(alertStr("请输入文章！","danger"));
		}else{
			var content = ue.getContent();
			var data = {"title":title,"catoreyId":selected,"content":content};
			alert(JSON.stringify(data));
			var publish_url = "user/publish.do";
			$.post(publish_url,data,function(data,stauts){
				alert("d:"+JSON.stringify(data.data)+" s:"+stauts);
				if(stauts == "success"){
					if(data != null && data.stauts == "success"){
						$(".panel-body > form").before(alertStr(data.data,"success"));
					}else{
						$(".panel-body > form").before(alertStr(data.data,"danger"));
					}
				}else{
					$(".panel-body > form").before(alertStr("发送失败！请重试","danger"));
				}
			});
		}
		
		setTimeout(function(){		//自动隐藏警告
			$("#alert_publish").remove();
		},10000);
		
	});
	loadCatorey();
	
});

function alertStr(str,stauts) {

	return '<div class="alert alert-'+stauts+'" id="alert_publish">'
	+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
	+ str 
	+ '</div>';
}
//加载分类
function loadCatorey(){
	$("#catoerySelect").append('<option value="0">请选择文章分类</option>');
	$.post("index/getAllCatorey.do",function(data,stauts){
		if(stauts == "success"){
			$("#catoerySelect option:first").text("请选择文章分类");
			$.each(data.data,function(index,value){
				$("#catoerySelect").append('<option value="'+value.catoreyId+'">'+value.catoreyname+'</option>');
			});
		}else{
			$("#catoerySelect option:first").text("获取数据时发生异常");
		}
	})
}