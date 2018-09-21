package com.core.teamwork.base.util.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.alibaba.fastjson.JSON;
import com.core.teamwork.base.util.mongoDB.MongoDBUtil;

public class ConsumerLoop extends Thread {
	private KafkaConsumer<String, String> consumer;
	private List<String> topics;
	private Integer timeout;

	/**
	 * @param topics  主题
	 * @param groupId 分组  如果启动两个分组 名称不同 主题相同 两个线程都会接收到生产者的数据
	 * @param timeout 单位 ：ms 多少时间获取一次数据 
	 */
	public ConsumerLoop(List<String> topics,String groupId,Integer timeout) {
		this.topics = topics;
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class.getName());
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		this.consumer = new KafkaConsumer<>(props);
		this.timeout = timeout;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			consumer.subscribe(topics);

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(timeout);
				if(!records.isEmpty()){
					Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
					for (ConsumerRecord<String, String> record : records) {
						List<Map<String, Object>> partlist = resultMap.get(record.key());
						if (null == partlist) {
							List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
							resultList.add(JSON.parseObject(record.value(),Map.class));
							resultMap.put(record.key(), resultList);
						} else {
							partlist.add(JSON.parseObject(record.value(), Map.class));
						}
					}
					consumer.commitSync();
					for (String mapKey : resultMap.keySet()) {
//						MongoDBUtil.getInstance().insertMany(mapKey,resultMap.get(mapKey));
						System.out.println(timeout+"==>"+mapKey+"====>"+resultMap.get(mapKey).toString());
					}
				}
				
			}
		} catch (WakeupException e) {
			// ignore for shutdown
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		consumer.wakeup();
	}
	
	public static void main(String[] args) {
		new ConsumerLoop(Arrays.asList("my-replicated-topic3"), "xxx",1000).start();
	}
}
