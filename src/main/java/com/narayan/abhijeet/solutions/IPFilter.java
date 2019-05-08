package com.narayan.abhijeet.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * Filters requests from IP which appear more than
 * 3 times (configurable) in 1 second
 */
public class IPFilter {

	private static Map<String, String> cache = new HashMap<String, String>();
	
	// try values 2,3,4
	private static final long MAX_REQUESTS_PER_SECOND = 3l;
	
	public static void main(String[] args) throws InterruptedException{
		
		Random rnd = new Random();
		String[] ips = new String[]{"192.168.2.1", "192.168.2.3", "192.168.2.2"};
		
		String ip = ips[rnd.nextInt(3)];
		
		for(int i = 0; i<20;i++){
			System.out.println("val " + ipFilter(ip) + " " + ip + " " + System.currentTimeMillis());
			TimeUnit.MILLISECONDS.sleep(50);
			ip = ips[rnd.nextInt(3)];
		}
		
	}
	
	public static boolean ipFilter(String ip){
		String key = ip;
		String value = cache.get(key);
		
		if(value == null){
			value = 1 + "-" + System.currentTimeMillis();
			cache.put(key, value);
			//System.out.println("entering " + key + " at " + System.currentTimeMillis());
			return true;
		}else{
			String[] tokens = value.split("-");
			int count = 1 + Integer.valueOf(tokens[0]) ;
			long insertedTime = Long.valueOf(tokens[1]);
			
			long interval = System.currentTimeMillis() - insertedTime;
			
			if(interval < 1000){
				if(count >= MAX_REQUESTS_PER_SECOND){
					cache.remove(key);
					//System.out.println("FILTERED " + key + " after " + interval);
					return false;
				}else{
					cache.put(key, count + "-" + insertedTime);
					//System.out.println("updating " + key + " after " + interval);
					return true;
				}
			}else{
				value = 1 + "-" + System.currentTimeMillis();
				cache.put(key, value);
				//System.out.println("resetting " + key + " after " + interval);
				return true;
			}
		}
	}
}
