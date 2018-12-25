package com.company.fileservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.company.fileservice.request.GetContents;

@Controller
@RequestMapping(value = "/api/v1")
public class FileServiceController {

	@Autowired
	FileServiceHandler handler;

	private static final Logger LOGGER = LogManager.getLogger(FileServiceController.class);

	@RequestMapping(value = "/viewcontents", method = RequestMethod.GET)
	public ModelAndView viewcontents(ModelMap modelMap, HttpServletRequest request) {
		String rootPath = System.getProperty("FILE_ROOT_FOLDER");
		modelMap.addAttribute("rootpath", rootPath);
		return new ModelAndView("listfileview");
	}

	@RequestMapping(value = "/getcontents", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getContents(@RequestBody GetContents payloadObj, ModelMap modelMap) {
		LOGGER.debug("Fetching contents in a folder.");
		return handler.getcontents(payloadObj.getPath(), false);
	}

	@RequestMapping(value = "/downloadfile", method = RequestMethod.POST)
	public void downloadFile(@RequestBody GetContents payloadObj, HttpServletResponse response) {
		LOGGER.debug("Downloading the requested file");
		handler.downloadContent(payloadObj.getPath(), response);
	}

}
