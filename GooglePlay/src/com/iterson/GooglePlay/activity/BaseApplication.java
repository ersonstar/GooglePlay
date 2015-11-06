package com.iterson.GooglePlay.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Application;
import android.os.Handler;
//代表整个应用程序   必须在清单文件中配置
public class BaseApplication extends Application {

private static BaseApplication application;
//  程序运行 最早调用的方法
private static int MainId;
private static Handler handler;
@Override
public void onCreate() {
	super.onCreate();
	System.out.println("BaseApplication");
	application=this;
	MainId = android.os.Process.myTid();
	System.out.println("BaseApplication:"+MainId);
	handler=new Handler();// 在主线程的Handler
	// 设置没有捕获异常的处理器
	//  当程序上线的时候 在添加没有捕获异常的处理器
	//Thread.currentThread().setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
}

public static Handler getHandler() {
	return handler;
}

private  class MyUncaughtExceptionHandler implements UncaughtExceptionHandler{
	//  当发生异常没有捕获   调用该方法
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		//  死前的遗言
		System.out.println("程序发生异常了,当时被哥发现了...");

		ex.printStackTrace();// 把日志输出到控制台
		//ex.printStackTrace(err)  通过流写到文件中 ,   然后可以通过网络传输到服务器中 
		// 自杀  杀死自己进程
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	
}

public static int getMainId() {
	return MainId;
}

public static BaseApplication getApplication() {
	return application;
}

}