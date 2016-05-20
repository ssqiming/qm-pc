package com.qm.common.utils;

public class ThreadUtils {
	
	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
		}
	}
}
