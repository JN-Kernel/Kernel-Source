function getUserInfo(){	
	var result = null;
	var msg = "init";
	 $.ajax({
		  url: "user/getUserinfo.do",
		  type: "POST",
		  async: false,	//同步
		  success:function(data){
				if(data != null && data != ""){
					msg = "success";
					result = data;						
				}else{
					msg = "nodata";
				}
		  },
		  error:function(){
			  msg = "error";
		  }
		}); 
	 return [msg,result];
}