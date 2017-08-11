$(document).ready(function() {
	var name = $("#name");
	var email = $("#email");
	var password = $("#password");
	var rePassword = $("#rePassword");
	var mobile = $("#mobile");
	var verificationCode = $("#verificationCode");
	var items = [ name, email, mobile ];
	var emailRegex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
	var mobileRegex = "(\\+\\d+)?1[34578]\\d{9}$";
	var nameRegex = "^[a-zA-Z]\\w{3,31}$";
	var passwordRegex= "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$";
	var verificationCodeRegex = "^\\d{4}$";
	var rePasswordRegex = passwordRegex;
	
	var validateItems = [ name, email, mobile, password, verificationCode,rePassword];
	var validateRegexItems = [nameRegex,emailRegex,mobileRegex,passwordRegex,verificationCodeRegex,rePasswordRegex];
	
	var alertbox = $("#alert");

	setTimeout(function(){
		alertbox.hide(500);
	},6000);
	
	
	// 获取帐号是否可用
	$(items).each(function() {
		$(this).change(function() {
			var id = $(this).attr("id");
			var inputName = $(this).siblings("label").html();
			var div = $(this).parent();		//输入框的父div
			var spanId = id+"_alert";		//提示id
			var isQuery = $(this).attr("pass")=="false"?false:true;	//是否查询
			
			if(isQuery){	
				$.ajax({
					type : "post",
					url : "user/checklogin.do",
					data : "checkedParams="+$(this).val(),
					dataType : "json",
					success : function(data) {
						if (data.stauts == "error") {
							$(div).append('<span id="'+spanId+'" class="text-danger">您输入的'+inputName+'已经被注册</span>');
							$("#"+id).attr("pass","false");
						} else if (data.stauts == "success") {
							$("#"+spanId).remove("span");	// 移除提示
						}
					},
					error : function(xhr) {
						alert("账户检验失败！代码： " + xhr.status + " " + xhr.statusText);
						$("#"+id).attr("pass","false");
					}
				});
			}else{
				 $(this).keyup();
			}

		});
		
		
		
	});
	
	
	// 数据检验
	$(validateItems).each(function(index) {
		var div = $(this).parent();		//输入框的父div
		$(this).on("keyup",{index,validateRegexItems,div},test);
	});
	
	//数据提交
	$("#“register”").click(function(){
		
		$(validateItems).each(function(){
			var pass = $(this).attr("pass");
			var str = $(this).val();
			
			if(str == "" || str == null || pass == "false" ){
				$(this).keyup();	//检验
			}else{
				//注册
				$.ajax({
					type : "post",
					url : "user/register.do",
					data : $("#register").serialize(),
					dataType:"json",
					success : function(data) {
						
						if(data.stauts == "erroe"){
							alertbox.attr("class","alert alert-danger");
						}else if(data.stauts == "success"){
							alertbox.attr("class","alert alert-success");
							
							 setTimeout(function () {
								 $(window).attr('location',document.referrer);
							    }, 1500);
						}else{
							alertbox.attr("class","alert alert-warning");
						}
						
						alertbox.text(data.msg);
						alertbox.show(500);
					},
					error : function(xhr) {
						alert("获取注册信息失败！代码： " + xhr.status + " " + xhr.statusText);
					}
				});
				
			}
		});
	});
	
	//获取验证码
	$("#getVerificationCode").click(function(){
		
		if($("#mobile").attr("pass") == "true"){
			$(this).text("正在发送验证码...");
			$(this).addClass("disabled");
			$.ajax({
				type : "post",
				url : "user/sendVerificationCode.do",
				data : "mobile="+$("#mobile").val(),
				dataType:"json",
				success : function(data) {
					$("#getVerificationCode").attr("title",data.msg);
					$("#getVerificationCode").tooltip("show");
					if(data.stauts == "success"){		
						countDown(60);
					}
				},
				error : function(xhr) {
					alert("发送验证码失败！代码： " + xhr.status + " " + xhr.statusText);
					$("#getVerificationCode").removeClass("disabled");
				}
				
			});
		}else{
			$("#mobile").keyup();
		}
	});
	
	
});


function test(event){

	var index = event.data.index;//获取绑定的数据
	var div = event.data.div;
	var regex = event.data.validateRegexItems[index];

	var str = $(this).val();
	var target = $(this).attr("id");
	var name = $(this).attr("placeholder");	
	var spanId = target+"_alert";		//提示id
	
	var result = validate(str,regex,target,name);
	//验证确认密码
	if(target == "rePassword" && result == null){
		var password = $("#password");
		if(password.val() == str){
			$(this).attr("pass","true");
			$("#"+spanId).remove("span");	// 移除提示	
		}else{
			result = "密码与确认密码不一致";
		}
	}
	
	//alert(result);
	if (result != null) {
		$("#"+spanId).remove("span");	// 移除提示
		$(this).attr("pass","false");
		$(div).append('<span id="'+spanId+'" class="text-danger">'+result+'</span>');
	} else {	//验证通过		
			$(this).attr("pass","true");
			$("#"+spanId).remove("span");	// 移除提示	
	}
}


function validate(str,regstr,target,chName){
	var name = $("#name");
	var email = $("#email");
	var password = $("#password");
	var rePassword = $("#rePassword");
	var mobile = $("#mobile");
	var verificationCode = $("#verificationCode");
	var reg = new RegExp(regstr);	//取得验证表达式
	if(str == '' || str == null){
		return "请输入"+chName;
	}
	if(reg.test(str)){
		return null;
	}else{
		if(target == name.attr("id")){
			return "请输入4-32个以字母开头、可带数字、'_'的字串";	
		}else if(target == mobile.attr("id")){
			return "请输入正确的手机号码";
		}else if(target == email.attr("id")){
			return "请输入正确的邮箱";
		}else if(target == password.attr("id")){
			return "请输入6-20位由字母、数字或特殊字符组合的字串";
		}else if(target == rePassword.attr("id")){
			return "确认密码与密码不一致";
		}else if(target == verificationCode.attr("id")){
			return "验证码格式不正确"
		}else{
			return "输入错误";
		}
	}
	
}


function countDown(time){
	var getVerificationCode = $("#getVerificationCode");
	time=time-1;
	if(time > 0){
		getVerificationCode.text("重新发送验证码("+time+")");
		getVerificationCode.addClass("disabled");
		setTimeout(function(){countDown(time)},1000);
	}else{
		getVerificationCode.text("重新获取手机验证码");
		getVerificationCode.removeClass("disabled");
	}
}