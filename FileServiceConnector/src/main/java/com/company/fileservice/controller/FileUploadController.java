package com.company.fileservice.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/api/v1")
public class FileUploadController {

	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public ModelAndView fileUpload() {
		return new ModelAndView("upload");
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
	public String submit(@RequestParam("file") MultipartFile file, @RequestParam("pathToUpload") String copyToPath,
			ModelMap modelMap) {
		String mountPath = System.getProperty("FILE_ROOT_FOLDER");

		try {
			Files.copy(file.getInputStream(), Paths.get(mountPath, copyToPath, "/" + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}

		modelMap.addAttribute("filename", file.getOriginalFilename());
		modelMap.addAttribute("filetype", file.getContentType());
		return "fileuploadview";
	}
}
