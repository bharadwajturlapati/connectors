package com.company.fileservice.controller;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.company.fileservice.config.HttpClientUtils;
import com.company.fileservice.security.GoogleOauthConfig;

@Controller
public class LoginController {
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@GetMapping("/oauth/login_success/callback")
	public ModelAndView oauthcallBack(@RequestParam("code") Optional<String> code,
			@RequestParam("error") Optional<String> error, ModelMap modelMap) {
		LOGGER.debug("Redirect URI Callback");

		String response = "";
		if (error.isPresent()) {
			return new ModelAndView("error");
		} else if (code.isPresent()) {
			response = HttpClientUtils.postToGoogleAuth(GoogleOauthConfig.EXCHANGE_URI, code.get(),
					GoogleOauthConfig.CLIENT_ID, GoogleOauthConfig.CLIENT_SECRET, GoogleOauthConfig.REDIRECT_URI,
					code.get());
		} else {
			return new ModelAndView("error");
		}
		modelMap.addAttribute("genericresp", response);
		return new ModelAndView("genericresponse");
	}

	@GetMapping("/oauth2/authorization/google")
	public String initOauth2(ModelMap modelMap) {
		LOGGER.debug("Execute oauth2 window");
		modelMap.addAttribute("googleauthurl", GoogleOauthConfig.GOOGLE_OAUTH2_TEMPLATE);
		return "authwindow";
	}

}
