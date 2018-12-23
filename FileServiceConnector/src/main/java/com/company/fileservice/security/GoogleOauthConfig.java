package com.company.fileservice.security;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GoogleOauthConfig {

	private static final Path CREDS = Paths.get("E:\\security.txt");

	private static final Logger LOGGER = LogManager.getLogger(GoogleOauthConfig.class);

	private static final Properties SECURITY_PROPERTIES = new Properties();

	static {
		try {
			SECURITY_PROPERTIES.load(new FileInputStream(CREDS.toFile()));
		} catch (Exception e) {
			LOGGER.error("Error while loading properties");
		}
	}

	public static final String EXCHANGE_URI = "https://www.googleapis.com/oauth2/v4/token";
	public static final String CLIENT_ID = getClientId();
	public static final String CLIENT_SECRET = getClientSecret();
	public static final String REDIRECT_URI = "http://localhost:8080/services/oauth/login_success/callback";

	public static final String GOOGLE_OAUTH2_TEMPLATE = "https://accounts.google.com/o/oauth2/v2/auth?"
			+ "scope=email%20profile&" + "access_type=offline&" + "include_granted_scopes=true&"
			+ "state=state_parameter_passthrough_value&"
			+ "redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fservices%2Foauth%2Flogin_success%2Fcallback&"
			+ "response_type=code&" + "client_id=" + CLIENT_ID;

	private static String getClientId() {
		if (SECURITY_PROPERTIES == null || !SECURITY_PROPERTIES.containsKey("META")) {
			setsecurityproperties();
		}
		return SECURITY_PROPERTIES.getProperty("CLIENT_ID");
	}

	private static String getClientSecret() {
		if (SECURITY_PROPERTIES == null || !SECURITY_PROPERTIES.containsKey("META")) {
			setsecurityproperties();
		}
		return SECURITY_PROPERTIES.getProperty("CLIENT_SECRET");
	}

	private static void setsecurityproperties() {
		try {
			SECURITY_PROPERTIES.load(new FileInputStream(CREDS.toFile()));
		} catch (Exception e) {
			LOGGER.error("Error while loading properties");
		}

	}

	private GoogleOauthConfig() {
	}

}
