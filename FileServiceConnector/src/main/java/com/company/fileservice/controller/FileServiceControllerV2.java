package com.company.fileservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.company.fileservice.request.GetContents;

@Controller
@RequestMapping(value = "/api/v2")
public class FileServiceControllerV2 {

	@Autowired
	FileServiceHandler handler;

	private static final Logger LOGGER = LogManager.getLogger(FileServiceControllerV2.class);
	private static final String BASEURL = "/api/v2/getcontents";
	private static final String VIEWCONTENTSURL = "/api/v2/viewcontents";

	@RequestMapping(value = "/getcontents", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getFirstContents(HttpServletRequest request) {
		LOGGER.debug("Fetching contents from the root folder.");

		String mountPath = System.getProperty("FILE_ROOT_FOLDER");

		return handler.getcontents(mountPath, true);
	}

	@RequestMapping(value = "/getcontents/{relativePath}/**", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getContents(@PathVariable String relativePath, HttpServletRequest request) {
		LOGGER.debug("Fetching contents in a relative folder.");
		String completeURL = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String mountPath = System.getProperty("FILE_ROOT_FOLDER");
		String restOfURL = processURL(completeURL, BASEURL);

		return handler.getcontents(mountPath + restOfURL, true);
	}

	@RequestMapping(value = "/viewcontents", method = RequestMethod.GET)
	public ModelAndView viewRootcontents(ModelMap modelMap, HttpServletRequest request) {
		return new ModelAndView("listfileview");
	}

	@RequestMapping(value = "/viewcontents/{relativePath}/**", method = RequestMethod.GET)
	public ModelAndView viewcontents(ModelMap modelMap, HttpServletRequest request) {
		String completeURL = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String restOfURL = processURL(completeURL, VIEWCONTENTSURL);

		modelMap.addAttribute("rootpath", restOfURL);
		return new ModelAndView("listfileview");
	}

	@RequestMapping(value = "/downloadfile", method = RequestMethod.POST)
	public void downloadFile(@RequestBody GetContents payloadObj, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.debug("Downloading the requested file");
		String mountPath = System.getProperty("FILE_ROOT_FOLDER");

		handler.downloadContent(mountPath + payloadObj.getPath(), response);
	}

	private String processURL(String url, String splitter) {
		if (url == null || url == "") {
			return "";
		}
		String[] urls = url.split(splitter);
		return urls[1];
	}
}
