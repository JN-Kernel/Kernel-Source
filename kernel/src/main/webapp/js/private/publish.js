var selected = $("#catoerySelect  option:selected").val();

$("#submitButton").click(function() {
	alert($(".close"));
	if (selected == null || selected == "0") {
		
		$(".panel-body > form").before(alertStr("请选择文章类别"));
	}

});

function alertStr(str) {
	return '<div class="alert alert-danger">'
			+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
			+ str 
			+ '</div>';
}
