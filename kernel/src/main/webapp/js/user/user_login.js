$("#submit").click(function() {
	$("#alert").hide();
	var alertbox = $("#alert");
	$.ajax({
		type : "post",
		url : "user/login.do",
		data : $("#loginfrom").serialize(),
		dataType:"json",
		success : function(data) {
			var url = (data.url == null || data.url == "")?"inde4x.html":data.url;
			if(data.stauts == "401"){
				alertbox.attr("class","alert alert-danger");
			}else if(data.stauts == "200"){
				alertbox.attr("class","alert alert-success");
				 setTimeout(function () {
					 $(window).attr('location',url);
				    }, 1500);
			}else{
				alertbox.attr("class","alert alert-warning");
			}
			
			alertbox.text(data.msg);
			alertbox.show(500);
		},
		error : function(xhr) {
			alert("错误提示： " + xhr.status + " " + xhr.statusText);
		}
	});

});


