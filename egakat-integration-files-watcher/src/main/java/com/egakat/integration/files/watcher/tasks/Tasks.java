package com.egakat.integration.files.watcher.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.egakat.integration.files.watcher.service.api.WatchFileSystemService;

@Component
public class Tasks {

	@Autowired
	private WatchFileSystemService watcher;

	@Scheduled(cron = "${schedule.start}")
	public void start() {
		while (!watcher.isRunning()) {
			watcher.start();
		}
	}

	@Scheduled(cron = "${schedule.restart}")
	public void restart() {
		watcher.stop();
	}
}
