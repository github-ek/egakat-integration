package com.egakat.integration.files.watcher.service.api;

public interface FileSystemWatchService {

	boolean isRunning();

	void start();
	
	void stop();
}