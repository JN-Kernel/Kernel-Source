package com.aode.util;

import java.io.IOException;

import com.aode.util.sms.SmsJsonVo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	public static Object getVo(String jsonStr) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);	//忽略在vo中未定义属性的转换
		
		SmsJsonVo sms = mapper.readValue(jsonStr, SmsJsonVo.class);
		return sms;
		
	}
}
