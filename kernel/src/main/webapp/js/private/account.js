$(function(){
	var userinfo = getUserInfo();
	userinfoPlaneHandle(userinfo[0],userinfo[1]);
	editUserinfoPlane(userinfo[0],userinfo[1]);
	//头像剪切处理
	$('.container > img').cropper({
		aspectRatio : 4 / 3,
	});
	
	//修改信息
	$("#userinfo_submit").click(function(){
		$("#alert").remove();
		
		//绑定提示框提交按钮,on绑定好
		$("#alert-submit").on("click",updateinfo);	
		var title = "修改信息";
		var body = "确定要修改您的个人信息？"
		modalShow('#bigModal', '', modalDataInit(title,body));
	});
	
	
	//修改密码
	$(":password").on("keyup focusout",checkPass);
	$("#account-submit").click(function(){
		$("#alert").remove();
		if(checkPass()){
			
			//绑定提示框提交按钮
			$("#alert-submit").click(function(){
				var newPassword = $("#new-pw").val();
				var oldPassword = $("#old-pw").val();
				var data = {"newPassword":newPassword,"oldPassword":oldPassword};
				var url = "user/changePassword.do";
				
				var result = post(url,data);
				modalHide('#bigModal', '',alertHandle($("#editAccount"),result[0],result[1]) );
				
			});	
			var title = "修改密码";
			var body = "确定要修改您的密码？"
			modalShow('#bigModal', '', modalDataInit(title,body));		
		}
	});
	
});

function alertHandle(obj,flag,data){
	if(flag == "success"){
		if(data == null){
			$(obj).prepend('<div class="alert alert-danger" role="alert" id="alert">返回错误数据！code:null</div>');
		}else if( data == "success"){
			$(obj).prepend('<div class="alert alert-success" role="alert" id="alert">修改成功</div>');
			var userinfo = getUserInfo();
			userinfoPlaneHandle(userinfo[0],userinfo[1]);
			editUserinfoPlane(userinfo[0],userinfo[1]);
		}else if(data == "nologin"){
			$(obj).prepend('<div class="alert alert-danger" role="alert" id="alert">请登陆！！！</div>');
		}else if(data == "errorPass"){
			$(obj).prepend('<div class="alert alert-danger" role="alert" id="alert">旧密码错误！！！</div>');
		}else{
			$(obj).prepend('<div class="alert alert-danger" role="alert" id="alert">修改失败！请重试! </div>');
		}
	}else{
		$(obj).prepend('<div class="alert alert-danger" role="alert" id="alert">提交失败！请重试! code:send faile</div>');
	}
}

// 左侧面板处理
function userinfoPlaneHandle(msg,data){
	var $table = $("table.userinfo tbody:first");
	if(msg == "error"){
		alert("sssss");
		var str = '<tr><td>获取用户信息失败！</td><td>code: faile send</td></tr>';
		$table.append(str);
	}else{
		if(data != null && data != ""){
		var userinfo = data.userinfo;
		var userName = data.username;
		var email = data.email;
		var address = (data.address == null)?"未填写地址":userinfo.address;
		var phone = data.phone;
		var creatTime = new Date(data.creattime).toLocaleString();
		var birthday = (userinfo.birthday == null)?"未填写生日":userinfo.birthday;
		var gender = (userinfo.gender == null)?"保密":userinfo.gender;
		
		
		$table.append('<tr><td>用户名</td><td>'+userName+'</td></tr>');
		$table.append('<tr><td>邮箱</td><td>'+email+'</td></tr>');
		$table.append('<tr><td>手机</td><td>'+phone+'</td></tr>');
		$table.append('<tr><td>性别</td><td>'+gender+'</td></tr>');
		$table.append('<tr><td>生日</td><td>'+birthday+'</td></tr>');
		$table.append('<tr><td>地址</td><td>'+address+'</td></tr>');
		$table.append('<tr><td>创建时间</td><td>'+creatTime+'</td></tr>');
		}else{
			var str = '<tr><td>获取用户信息失败！</td><td>code:user null</td></tr>';
			$table.append(str);
			
			
		}
	}
	
}

//编辑用户信息
function editUserinfoPlane(msg,data){
	if(msg == "error"){
		$("#generalTabContent").before(alertStr("获取用户信息失败！"));
	}else{
		if(data != null && data != ""){
			var userinfo = data.userinfo;
			//用户名
			$("#username-input").val(data.username);
			
			//性别
			$("#gender-boy").removeAttr("checked");
			$("#gender-girl").removeAttr("checked");
			if(userinfo.gender == "男"){
				$("#gender-boy").attr("checked","checked");
			}else if(userinfo.gender == "女"){
				$("#gender-girl").attr("checked","checked");
			}
			
			//生日
			if(userinfo.birthday != null){
				$("#birthday").val(new Date(userinfo.birthday).toLocaleString());				
			}
			
			//地址
			if(userinfo.address != null){
				$("#address-input").val(userinfo.address);				
			}
		}
	}
}

//显示
function alertStr(str) {
	return '<div class="alert alert-danger">'
	+ '<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>'
	+ str 
	+ '</div>';
}

Date.prototype.toLocaleString = function() {
	return this.getFullYear() + "-" + (this.getMonth() + 1) + "-"
			+ this.getDate();
}

//获取数据
function post(dstUrl,postData){
	var msg = null;
	var result = null;
//	alert(JSON.stringify(postData));
	$.ajax({
		  url: dstUrl,
		  type: "POST",
		  data: postData,
		  dataType : "text",
		  cache: false,
		  async: false,	//同步
		  success:function(data){
				if(data != null && data != ""){
					msg = "success";
					result = data;						
				}else{
					msg = "nodata";
				}
		  },
		  error:function(xhr){
			  alert("代码： " + xhr.status + " " + xhr.statusText);
			  msg = "error";
		  }
		}); 
	return [msg,result];

}


//密码验证
function checkPass(){
	var result = false;
	var passwordRegex= "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$";
	var reg = new RegExp(passwordRegex);
	var newPassword = $("#new-pw");
	var rePassword = $("#re-pw");
	var oldPassword = $("#old-pw");
	var newPasswordStr = $(newPassword).val().trim();
	var rePasswordStr = $(rePassword).val().trim();
	var oldPasswordStr = $(oldPassword).val().trim();
	$("#alert-pass").remove();
	if(oldPasswordStr == null || oldPasswordStr == ""){
		$(oldPassword).after('<span id="alert-pass">请输入旧密码</span>'+oldPasswordStr);
	}else if(newPasswordStr == null || newPasswordStr == ""){
		$(newPassword).after('<span id="alert-pass">请输入新密码</span>');
	}else if(!reg.test(newPasswordStr)){
		$(newPassword).after('<span id="alert-pass">输入格式不正确，密码由数字，英文，特殊字符至少两种以及最少六位字符组成！</span>');
	}else if(rePasswordStr == null || rePasswordStr == ""){
		$(rePassword).after('<span id="alert-pass">请输入确认密码</span>');
	}else if(newPasswordStr != rePasswordStr){
		$(newPassword).after('<span id="alert-pass">新密码与确认密码不同！</span>');
		$(rePasswordStr).after('<span id="alert-pass">确认密码与新密码不同！</span>');
	}else{
		result = true;
	}
	
	return result;
}

//信息修改
function updateinfo(){
	var gender = $('input:radio[name="gender"]:checked').val();
	var birthday = $("#birthday").val();
	var address = $("#address-input").val().trim();
	var data = {"gender":gender,"birthday":birthday,"address":address};
	
	var url = "user/updateUserinfo.do";
	
	var result = post(url,data);
	modalHide('#bigModal', '',alertHandle($("#userinfo-form"),result[0],result[1]) );
	
}