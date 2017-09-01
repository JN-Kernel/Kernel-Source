$(function(){
	var userinfo = getUserInfo();
	userinfoPlaneHandle(userinfo[0],userinfo[1]);
	//头像剪切处理
	$('.container > img').cropper({
		aspectRatio : 4 / 3,
	});
	
});



// 左侧面板处理
function userinfoPlaneHandle(msg,data){
	alert(msg);
	var $table = $("table.userinfo tbody:first");
	if(msg == "error"){
		var str = '<tr><td>获取用户信息失败！</td><td>code: faile send</td></tr>';
		$table.append(str);
	}else{
		if(data != null){
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

function editUserinfoPlane(msg,data){
	
}

	