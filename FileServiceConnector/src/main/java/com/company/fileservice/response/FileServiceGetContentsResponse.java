package com.company.fileservice.response;

import java.util.List;

public class FileServiceGetContentsResponse {

	private List<FileMetadata> paths;

	public List<FileMetadata> getPaths() {
		return paths;
	}

	public void setPaths(List<FileMetadata> paths) {
		this.paths = paths;
	}

}
