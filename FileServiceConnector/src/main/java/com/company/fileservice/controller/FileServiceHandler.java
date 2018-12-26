package com.company.fileservice.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.company.fileservice.response.FileMetadata;
import com.company.fileservice.response.FileServiceGetContentsResponse;
import com.company.fileservice.response.FileServiceOperationsResponse;
import com.company.fileservice.response.ResponseOpStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FileServiceHandler {

	private static final Gson GSON = new GsonBuilder().create();
	private static final Logger LOGGER = LogManager.getLogger(FileServiceHandler.class);

	public String getcontents(String path, boolean unixStyle) {
		LOGGER.debug("Fetching contents from the path {}", path);
		return GSON.toJson(getFirstLevelContents(path, unixStyle),
				new TypeToken<FileServiceOperationsResponse<FileServiceGetContentsResponse>>() {
				}.getType());
	}

	public FileServiceOperationsResponse<FileServiceGetContentsResponse> getFirstLevelContents(String path,
			boolean unixStyle) {
		List<FileMetadata> listOfFileMetadata = new ArrayList<>();
		FileServiceOperationsResponse<FileServiceGetContentsResponse> response = new FileServiceOperationsResponse<>(
				new FileServiceGetContentsResponse());

		String mountPath = System.getProperty("FILE_ROOT_FOLDER");

		if (checkPathInput(path)) {
			response.setOpstatus(ResponseOpStatus.GENERIC_EXCEPTION.getStatus());
			return response;
		}

		try (Stream<Path> lazyPath = Files.list(Paths.get(path))) {
			lazyPath.forEach(eachpath -> {
				FileMetadata metadata = new FileMetadata();
				metadata.setFileName(eachpath.getFileName().toString());
				String assetPath = eachpath.toString();
				if (unixStyle) {
					String unixStylePath = assetPath.replaceAll("\\\\", "/");
					String relativePath = unixStylePath.split(mountPath)[1];
					metadata.setFilePath(relativePath);
				} else {
					metadata.setFilePath(eachpath.toString());
				}
				metadata.setFileType(Files.isDirectory(eachpath) ? "dir" : "file");
				listOfFileMetadata.add(metadata);
			});
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching file information");
			response.setOpstatus(ResponseOpStatus.GENERIC_EXCEPTION.getStatus());
		}

		response.getOperationType().setPaths(listOfFileMetadata);
		response.setOpstatus(ResponseOpStatus.SUCCESS.getStatus());
		return response;

	}

	private boolean checkPathInput(String path) {
		return "" == path || null == path;
	}

	private String getFileName(String path) {
		String separator = "\\";
		String[] paths = path.split(Pattern.quote(separator));
		return paths[paths.length - 1];
	}

	public void downloadContent(String path, HttpServletResponse response) {
		String fileName = getFileName(path);
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		try {
			IOUtils.copy(Files.newInputStream(Paths.get(path)), response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			LOGGER.error("Exception occurred while downloading content", e);
		}

	}

}
