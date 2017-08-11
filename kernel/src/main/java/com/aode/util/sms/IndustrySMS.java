package com.aode.util.sms;

import java.io.IOException;

import com.aode.util.JsonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 验证码通知短信接口
 * 
 * @ClassName: IndustrySMS
 * @Description: 验证码通知短信接口
 *
 */
public class IndustrySMS
{
	private static String operation = "/industrySMS/sendSMS";

	private static String accountSid = Config.ACCOUNT_SID;

	/**
	 * 验证码通知短信
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static SmsJsonVo execute(String to,String smsContent) throws JsonParseException, JsonMappingException, IOException
	{
		String url = Config.BASE_URL + operation;
		String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + smsContent
				+ HttpUtil.createCommonParam();

		// 提交请求
		String result = HttpUtil.post(url, body);
		SmsJsonVo sms = (SmsJsonVo) JsonUtils.getVo(result);
		System.out.println("SMS result:" + System.lineSeparator() + result);
		return sms;
	}
}
