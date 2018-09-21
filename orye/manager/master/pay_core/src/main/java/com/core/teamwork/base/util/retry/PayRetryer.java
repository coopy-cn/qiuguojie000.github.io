package com.core.teamwork.base.util.retry;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

public class PayRetryer {
	
	private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	/**
	 * @param notIncludeRetryResult 不包含这个内容就重试
	 * @param timer 重试时间  （斐波那契数列 最大两小时） 单位秒 
	 * @param retryNum 重试次数
	 * @param call 回调
	 * @param linster 监听器
	 */
	public void retryer(final String notIncludeRetryResult,final Integer timer,final Integer retryNum,final Callable<String> call,final RetryListener linster){
		cachedThreadPool.execute(new Runnable() {
			public void run() {
				Retryer<String> retryer = RetryerBuilder
						.<String> newBuilder()
						.retryIfResult(
								Predicates.not(Predicates.equalTo(notIncludeRetryResult)))
						.retryIfException()
						.withWaitStrategy(
								WaitStrategies.fibonacciWait(timer*1000, 2,
										TimeUnit.HOURS))
						.withStopStrategy(StopStrategies.stopAfterAttempt(retryNum))
						.withRetryListener(linster).build();

				try {
					retryer.call(call);
				} catch (Exception e) {
					System.out.println("still failed after retry.");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		
		PayRetryer pr = new PayRetryer();
		
		Callable<String> maySuccessTask = new Callable<String>() {
	        private int times = 0;
	        @Override
	        public String call() throws Exception {
	            System.out.println("called"+times);
	            times++;
	 
	            if (times == 1) {
	                throw new NullPointerException();
	            } else if (times == 10) {
	                return "success";
	            } else {
	                return "fail";
	            }
	        }
	    };
		
	    final long time = System.currentTimeMillis();
	    
		pr.retryer("success", 10, 10, maySuccessTask, new RetryListener() {
			
			@Override
			public <V> void onRetry(Attempt<V> attempt) {
				// TODO Auto-generated method stub
				System.out.println(System.currentTimeMillis()-time);
			}
		});
	}
}
