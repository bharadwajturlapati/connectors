package com.company.fileservice.listeners;

import java.util.HashMap;
import java.util.Map;

public class ApplicationConfig {
	
	Map<String, String> configMap = new HashMap<>();
	
	public void addKVToConfig(String key, String val) {
		configMap.put(key, val);
	}
	
	public Map<String, String> getConfigMap(){
		return configMap;
	}

}
