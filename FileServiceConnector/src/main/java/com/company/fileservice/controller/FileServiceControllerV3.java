package com.company.fileservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.fileservice.request.GetContents;
import com.company.fileservice.response.LowNetworkDeviceResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
@RequestMapping(value = "/api/v3")
public class FileServiceControllerV3 {
	private static final Gson GSON = new GsonBuilder().create();

	@RequestMapping(value = "/downloadfile", method = RequestMethod.POST)
	@ResponseBody
	public String downloadFile(@RequestBody GetContents payloadObj, HttpServletRequest request,
			HttpServletResponse response) {
		LowNetworkDeviceResponse apiResponse = new LowNetworkDeviceResponse();
		apiResponse.setCdnURL("url");
		apiResponse.setExpiresIn("1000");
		return GSON.toJson(apiResponse, LowNetworkDeviceResponse.class);
	}

}
