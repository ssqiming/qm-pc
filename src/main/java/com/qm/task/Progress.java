package com.qm.task;

public class Progress extends Thread {

	private String percent = "";
	private boolean over = false;
	
	public String getPercent() {
		return percent;
	}
	public boolean isOver() {
		return over;
	}
	
	public void run() {
		for (int i = 1; i <= 100; i++) {
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			percent = i + "%";
		}
		over = true;
	}
}
