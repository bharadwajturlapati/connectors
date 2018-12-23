package com.company.fileservice.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HttpClientUtils {

	private static final Logger LOGGER = LogManager.getLogger(HttpClientUtils.class);

	public static void get(String url) {
		LOGGER.debug("executing the url {}", url);
		HttpGet httpGet = new HttpGet(url);
		HttpClientReponse responseBean = new HttpClientReponse();

		try (CloseableHttpResponse response = HttpClients.createDefault().execute(httpGet);) {
			responseBean.setHttpStatus(String.valueOf(response.getStatusLine().getStatusCode()));
			HttpEntity entity = response.getEntity();
			String resp = EntityUtils.toString(entity);
			responseBean.setResponse(resp);
			EntityUtils.consume(entity);
		} catch (Exception e) {
			LOGGER.error("Error occured while executing the url {}", url, e);
		}
	}

	public static String postToGoogleAuth(String url, String code, String client_id, String client_secret,
			String redrect_uri, String grant_type) {
		String responseString = "";
		HttpPost httpPost = new HttpPost(url);
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("code", code));
			params.add(new BasicNameValuePair("client_id", client_id));
			params.add(new BasicNameValuePair("client_secret", client_secret));
			params.add(new BasicNameValuePair("redirect_uri", redrect_uri));
			params.add(new BasicNameValuePair("grant_type", "authorization_code"));
			httpPost.setEntity(new UrlEncodedFormEntity(params));

			CloseableHttpResponse response = client.execute(httpPost);
			responseString = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			LOGGER.error("Error occured while executing the url {}", url, e);
		}
		return responseString;
	}

	private HttpClientUtils() {

	}
}
