package com.core.teamwork.base.util.queue;

import org.apache.log4j.Logger;

import com.core.teamwork.base.util.ReadPro;
import com.core.teamwork.base.util.redis.RedisDBUtil;

/**
 * @author cyl
 * 插入数据缓冲层用于数据存储减少数据库插入IO压力 需配合其他工具使用（例如：定时器）
 * @param <E>
 */
public class CacheQueue<E> {
	Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 调用类型 对应定时器中的方法
	 */
	private String type;

	/**
	 * 队列redisKey
	 */
	private String queueKey = ReadPro.getValue("queueKey", "queue:core_queue_%s");
	
	public CacheQueue(String type){
		this.type = type;
	}

	/**
	 * 加入队列
	 * 
	 * @param item
	 * @throws Exception
	 */
	public void enqueue(E item) {
		try {
			if (null != type) {
				if (null != item) {
					// 右向添加数据返回集合个数
					RedisDBUtil.redisDao.addToListRight(
							String.format(queueKey, type), item.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("enqueue", e);
		}

	}

	/**
	 * 弹出最先进入的队列
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public E delqueue() {
		try {
			if (null != type) {
				String data = RedisDBUtil.redisDao.popFromListLeft(String.format(
						queueKey, type));
				if (null != data) {
					return (E) data;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			logger.error("delqueue", e);
		}
		
		return null;
	}

	/**
	 * 判断队列中是否为空
	 * 
	 * @return
	 */
	public boolean hasItems() {
		int dataSize = RedisDBUtil.redisDao.getLengthOfList(String.format(
				queueKey, type));
		return dataSize == 0 ? false : true;
	}

	/**
	 * 队列个数
	 * 
	 * @return
	 */
	public int size() {
		return RedisDBUtil.redisDao.getLengthOfList(String.format(queueKey,
				type));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 其他队列添加
	 * 
	 * @param q
	 */
	public void addItems(CacheQueue<? extends E> q) {
		while (q.hasItems()) {
			enqueue(q.delqueue());
		}
	}

	public static void main(String[] args) {
		CacheQueue<Integer> cache = new CacheQueue<Integer>("asd");
		cache.enqueue(123);
//		 CacheQueue<Map<String, Object>> empList = new CacheQueue<Map<String,
//		 Object>>();
//		 empList.setType("con");
		//
		// for (int i = 0; i < 30; i++) {
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("name", i);
		// empList.enqueue(map1);
		// }
		// System.out.println("The employees' names are:");
		// if (empList.size() >= empList.getNum()) {
		// for (int i = 0; i < empList.getNum(); i++) {
		// Map<String, Object> emp = empList.delqueue();
		// System.out.println(emp.get("name"));
		// }
		// }
		// System.out.println("==========================");
		// while (empList.hasItems()) {
		// Map<String, Object> emp = empList.delqueue();
		// System.out.println(emp.get("name"));
		// }
		// System.out.println("---------------------------------------------------------------------------");
		// CacheQueue<Map<String, Object>> empList1 = new CacheQueue<Map<String,
		// Object>>();
		// empList1.setNum(20);
		// empList1.setType("con");
		//
		// for (int i = 0; i < 30; i++) {
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("name", i);
		// empList1.enqueue(map1);
		// }
		// System.out.println("The employees' names are:");
		// if (empList1.size() >= empList1.getNum()) {
		// for (int i = 0; i < empList1.getNum(); i++) {
		// Map<String, Object> emp = empList1.delqueue();
		// System.out.println(emp.get("name"));
		// }
		// }
		// System.out.println("==========================");
		// while (empList1.hasItems()) {
		// Map<String, Object> emp = empList1.delqueue();
		// System.out.println(emp.get("name"));
		// }
	}
}
