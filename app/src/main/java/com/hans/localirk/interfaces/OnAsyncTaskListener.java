package com.hans.localirk.interfaces;

public interface OnAsyncTaskListener {
	void onTaskBegin();
	void onTaskComplete(boolean isComplete, String message);
}
