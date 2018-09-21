package com.orye.business.task.util;

import java.util.Map;

import com.orye.business.task.service.TaskService;

public class TaskUtil implements Runnable {
	
	private TaskService taskService;
	private Map<String,Object> map;
	
	public TaskUtil() {
	}
		      
	public TaskUtil(TaskService taskService,Map<String,Object> map) {
	    this.taskService = taskService;
	    this.map = map;
	}
	
	@Override
	public void run() {
		try {
			taskService.homePersonSave(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
