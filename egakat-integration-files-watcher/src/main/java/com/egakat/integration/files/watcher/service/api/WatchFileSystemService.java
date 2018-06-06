package com.egakat.integration.files.watcher.service.api;

public interface WatchFileSystemService {

	boolean isRunning();

	void start();
	
	void stop();
}