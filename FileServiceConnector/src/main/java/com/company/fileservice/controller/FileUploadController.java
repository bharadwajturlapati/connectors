package com.company.fileservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView fileUpload() {
		return new ModelAndView("upload");
	}

	// TODO: STORE In File System
	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
		modelMap.addAttribute("filename", file.getOriginalFilename());
		modelMap.addAttribute("filetype", file.getContentType());
		return "fileuploadview";
	}
}
