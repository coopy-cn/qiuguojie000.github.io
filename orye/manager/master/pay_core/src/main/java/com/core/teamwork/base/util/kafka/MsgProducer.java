package com.core.teamwork.base.util.kafka;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.alibaba.fastjson.JSON;

/**
 * 消息生产者
 * 
 * @author cyl 2017/3/29 14:27
 */
public class MsgProducer {

	public Future<RecordMetadata> send(String bootstrapServers,String topic,String clientId,String key, String value) {
		Producer<String, String> producer = null;
		try {
			Properties properties = new Properties();
			properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
					bootstrapServers);// broker 集群地址
			properties.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);// 自定义客户端id
			properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
					StringSerializer.class.getName());// key 序列号方式
			properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
					StringSerializer.class.getName());// value 序列号方式

			producer = new KafkaProducer<String, String>(properties);
			Future<RecordMetadata> future =  producer.send(new ProducerRecord<String, String>(topic, key, value));
			return future;
		} catch (Exception e) {
			throw e;
		}finally{
			if(null != producer){
				producer.close();
			}
		}
	}

	public static void main(String args[]) {
		 for(int i = 0; i < 100; i++){
			 Map<String, Object> map  = new HashMap<String, Object>();
			 map.put("q", i);
			 map.put("w", i);
			 map.put("e", i);
			 map.put("r", i);
			 new MsgProducer().send("localhost:9092","my-replicated-topic3", "msgProducer", "cyl", JSON.toJSONString(map));
		 }
	}
}
