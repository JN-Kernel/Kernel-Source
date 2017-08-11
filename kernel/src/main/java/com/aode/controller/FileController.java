package com.aode.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aode.util.ImageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/fileManager")
public class FileController {
	// 图片裁剪功能
	@RequestMapping(value = "/imgCut.do", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
	@ResponseBody
	public Map<String, Object> profile_imgCut(
			@RequestParam("avatar_file") MultipartFile avatar_file, 
			@RequestParam("avatar_src") String avatar_src,
			@RequestParam("avatar_data") String avatar_data,
			HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		String dir = "upload";
		String path = request.getSession().getServletContext().getRealPath(dir);
System.out.println("avatar_data:"+avatar_data);
System.out.println("avatar_src:"+avatar_src);
		String name = avatar_file.getOriginalFilename();
		// 判断文件的MIMEtype
		String type = avatar_file.getContentType();
		if (type == null || !type.toLowerCase().startsWith("image/")) {
			msg.put("info", "不支持的文件类型，仅支持图片！");
			msg.put("success", false);
			return msg;
		}
		System.out.println("file type:" + type);
		String fileName = new Date().getTime() + "" + new Random().nextInt(10000) + "_"
				+ name.substring(name.lastIndexOf('.'));
		System.out.println("文件路径：" + path + ":" + fileName);

		ObjectMapper jsonMapper = new ObjectMapper();
		Map<String, Integer> jsonData;
		Integer x = 0;
		Integer y = 0;
		Integer w = 28;
		Integer h = 28;
		try {
			jsonData = jsonMapper.readValue(avatar_data, Map.class);
			// 用户经过剪辑后的图片的大小
			x = jsonData.get("x") ;
			y = jsonData.get("y") ;
			w = jsonData.get("width");
			h = jsonData.get("height");
		} catch (Exception e) {
			e.printStackTrace();
		} 

		// 开始上传
		File targetFile = new File(path, fileName);
		// 保存
		try {
			if (!targetFile.exists()) {
				targetFile.mkdirs();
				InputStream is = avatar_file.getInputStream();
				ImageUtils.cut(is, targetFile, x.intValue(), y.intValue(), w.intValue(), h.intValue());
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.put("info", "上传失败，出现异常：" + e.getMessage());
			msg.put("success", false);
			return msg;
		}
		msg.put("data", request.getSession().getServletContext().getContextPath() +"/"+ dir+"/" + fileName);
		msg.put("info", "上传成功!" );
		msg.put("url",dir+"/"+fileName);
		msg.put("success",true);
		return msg;
	}
}
