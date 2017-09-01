function getUserInfo(flag){	
	var result = null;
	var msg = "init";
	 $.ajax({
		  url: "user/getUserinfo.do",
		  type: "POST",
		  async: false,	//同步
		  success:function(data){
				msg = "success";
				result = data;	
		  },
		  error:function(){
			  msg = "error";
		  }
		}); 
	 return [msg,result];

}