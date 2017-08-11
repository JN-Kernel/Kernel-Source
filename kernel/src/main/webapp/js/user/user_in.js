$(function(){
	Date.prototype.toLocaleString = function() {
		return this.getFullYear() + "年" + (this.getMonth() + 1) + "月"
				+ this.getDate() + "日 ";
	};
	
	$.ajax({
		type : "post",
		url : "user/getUserinfo.do",
		dataType:"json",
		success : function(data) {
			
			if(data != null && data != ""){
				$("#username").text(data.username);
				$("#email").text(data.email);
				$("#address").text(data.address);
				$("#creattime").text(new Date(data.creattime).toLocaleString());
				$("#username-input").val(data.username);
				if(data.userinfo.gender == "男"){
					$("#gender-boy").attr("checked","checked");
					$("#gender-girl").removeAttr("checked");
				}else{
					$("#gender-boy").removeAttr("checked");
					$("#gender-girl").attr("checked","checked");

				}
				
			}
			
		}
	
	})
	
});