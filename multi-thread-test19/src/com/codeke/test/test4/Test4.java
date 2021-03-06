package com.codeke.test.test4;

import java.util.concurrent.*;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @author: codeke
 * @date: 2018-06-30 13:56
 * @description
 */
public class Test4 {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		String queryStr = "query";

		//构造FutureTask，并且传入需要真正进行业务逻辑处理的类,该类一定是实现了Callable接口的类
		FutureTask<String> future1 = new FutureTask<String>(new FutureUser(queryStr));
		FutureTask<String> future2 = new FutureTask<String>(new FutureUser(queryStr));

		//创建一个固定线程的线程池且线程数为1,
		ExecutorService executor = Executors.newFixedThreadPool(2);

		//这里提交任务future,则开启线程执行RealData的call()方法执行
		//submit和execute的区别： 第一点是submit可以传入实现Callable接口的实例对象， 第二点是submit方法有返回值
		Future f1 = executor.submit(future1);		//单独启动一个线程去执行的
		Future f2 = executor.submit(future2);
		System.out.println("请求完毕");

		try {
			//这里可以做额外的数据操作，也就是主程序执行其他业务逻辑
			System.out.println("处理其他的业务逻辑...");
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//调用获取数据方法,如果call()方法没有执行完成,则依然会进行等待
		System.out.println("数据：" + future1.get());
		System.out.println("数据：" + future2.get());

		executor.shutdown();

	}

}
