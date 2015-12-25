package com.bm.projectxxx.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {
	private ExecutorService service;
	private ExecutorService executorService;

	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();
		service = Executors.newFixedThreadPool(num * 2);
		Executors.newCachedThreadPool();
	}

	private static ThreadPoolManager manager;

	public static ThreadPoolManager getInstance() {
		if (manager == null) {
			manager = new ThreadPoolManager();
		}
		return manager;
	}

	public void addTask(Runnable runnable) {
		service.submit(runnable);
	}

	/**
	 * 创建一个缓存的线程池，以便于线程的复用
	 */
	public ExecutorService createCachedThreadPool() {

		if (executorService == null) {
			executorService = Executors.newCachedThreadPool();
		}
		return executorService;
	}
}
