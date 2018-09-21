package com.core.teamwork.base.util.mongoDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.core.teamwork.base.util.ReadPro;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

public class MongoDBUtil {

	/**
	 * MongoClient的实例代表数据库连接池，是线程安全的，可以被多线程共享，客户端在多线程条件下仅维持一个实例即可
	 * Mongo是非线程安全的，目前mongodb API中已经建议用MongoClient替代Mongo
	 */
	private MongoClient mongoClient = null;

	private static final String mongodbIp = ReadPro.getValue("mongodbIp","127.0.0.1");

	private static final int mongodbPort = ReadPro.getValue("mongodbPort",27107);
	private static final String database = ReadPro.getValue("mongodbDatabase","mydatabase");

	/**
	 * 构造方法
	 * 
	 * @param ip
	 *            127.0.0.1
	 * @param port
	 *            27107
	 */
	private MongoDBUtil() {
		if (mongoClient == null) {
			
//			MongoCredential credential = MongoCredential.createMongoCRCredential(username, database, pwd.toCharArray());
			
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			/*
			 * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
			 * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
			 * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
			 */
			build.maxWaitTime(1000 * 60 * 2);
			build.connectTimeout(1000 * 60 * 1); // 与数据库建立连接的timeout设置为1分钟
			build.socketTimeout(0);// 套接字超时时间，0无限制
			build.connectionsPerHost(300); // 连接池设置为300个连接,默认为100
			build.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out
																		// ofsemaphorestogetdb”错误。
			build.writeConcern(WriteConcern.ACKNOWLEDGED);

			MongoClientOptions myOptions = build.build();
			try {
				// 数据库连接实例
				mongoClient = new MongoClient(new ServerAddress(mongodbIp,
						mongodbPort)
//				,Arrays.asList(credential)
						, myOptions);
			} catch (MongoException e) {
				e.printStackTrace();
			}

		}
	}

	private static MongoDBUtil mongoDBUtil = null;

	public synchronized static MongoDBUtil getInstance() {
		if (mongoDBUtil == null) {
			mongoDBUtil = new MongoDBUtil();
		}
		return mongoDBUtil;
	}

	/**
	 * 判断数据是否存在
	 * @param collectionName
	 * @param filterMap
	 * @return
	 */
	public boolean isExits(String collectionName,
			Map<String, Object> filterMap) {
		if (filterMap != null) {
			FindIterable<Document> docs = mongoClient.getDatabase(database)
					.getCollection(collectionName)
					.find(new Document(filterMap));
			Document doc = docs.first();
			if (doc != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 插入单条数据
	 * @param collectionName
	 * @param insertMap
	 * @return
	 */
	public boolean insert(String collectionName,
			Map<String, Object> insertMap) {
		if (insertMap != null) {
			mongoClient.getDatabase(database).getCollection(collectionName)
					.insertOne(new Document(insertMap));
			return true;
		}
		return false;
	}

	/**
	 * 插入多条数据
	 * @param collectionName
	 * @param insertMapList
	 * @return
	 */
	public boolean insertMany(String collectionName,
			List<Map<String, Object>> insertMapList) {
		if (null != insertMapList && insertMapList.size() > 0) {
			List<Document> list = new ArrayList<Document>();
			for (int i = 0; i < insertMapList.size(); i++) {
				list.add(new Document(insertMapList.get(i)));
			}
			mongoClient.getDatabase(database).getCollection(collectionName)
					.insertMany(list);
			return true;
		}
		return false;
	}

	/**
	 * 根据mongodb的id进行删除
	 * @param collectionName
	 * @param _id
	 * @return
	 */
	public boolean deleteById(String collectionName, String _id) {
		ObjectId objectId = new ObjectId(_id);
		Bson filter = Filters.eq("_id", objectId);

		DeleteResult deleteResult = getDatabase(database).getCollection(
				collectionName).deleteOne(filter);
		long deletedCount = deleteResult.getDeletedCount();

		return deletedCount > 0 ? true : false;
	}

	/**
	 * 根据用户自定义map查找数据多条删除
	 * @param collectionName
	 * @param map
	 * @return
	 */
	public boolean delete(String collectionName,
			Map<String, Object> map) {
		if (map != null) {
			DeleteResult result = mongoClient.getDatabase(database)
					.getCollection(collectionName)
					.deleteMany(new Document(map));
			long deletedCount = result.getDeletedCount();
			return deletedCount > 0 ? true : false;
		}
		return false;
	}

	/**
	 * 更新
	 * @param collectionName
	 * @param filterMap
	 * @param updateMap
	 * @return
	 */
	public boolean updateOne(String collectionName,
			Map<String, Object> filterMap, Map<String, Object> updateMap) {
		if (filterMap != null && filterMap.size() > 0 && updateMap != null) {
			UpdateResult result = mongoClient
					.getDatabase(database)
					.getCollection(collectionName)
					.updateOne(new Document(filterMap),
							new Document("$set", new Document(updateMap)));
			long modifiedCount = result.getModifiedCount();
			return modifiedCount > 0 ? true : false;
		}

		return false;
	}

	/**
	 * @param collectionName
	 * @param _id
	 * @param updateDoc
	 * @return
	 */
	public boolean updateById(String collectionName, String _id,
			Document updateDoc) {
		ObjectId objectId = new ObjectId(_id);
		Bson filter = Filters.eq("_id", objectId);

		UpdateResult result = getDatabase(database).getCollection(collectionName)
				.updateOne(filter, new Document("$set", updateDoc));
		long modifiedCount = result.getModifiedCount();

		return modifiedCount > 0 ? true : false;
	}

	/**
	 * 根据条件查询
	 * @param collectionName
	 * @param filter
	 * @return
	 */
	public List<Document> find(String collectionName, Bson filter) {
		final List<Document> resultList = new ArrayList<Document>();
		if (filter != null) {
			FindIterable<Document> docs = mongoClient.getDatabase(database)
					.getCollection(collectionName).find(filter);
			docs.forEach(new Block<Document>() {
				public void apply(Document document) {
					resultList.add(document);
				}
			});
		}

		return resultList;
	}
	
	/**
	 * 查询集合
	 * @param collectionName
	 * @return
	 */
	public List<Document> find(String collectionName) {
		final List<Document> resultList = new ArrayList<Document>();
		FindIterable<Document> docs = mongoClient.getDatabase(database).getCollection(collectionName).find();
		docs.forEach(new Block<Document>() {
			public void apply(Document document) {
				resultList.add(document);
			}
		});

		return resultList;
	}

	/**
	 * 根据mongoDB查询
	 * @param collectionName
	 * @param _id
	 * @return
	 */
	public Document findById(String collectionName, String _id) {
		ObjectId objectId = new ObjectId(_id);

		Document doc = getDatabase(database).getCollection(collectionName)
				.find(Filters.eq("_id", objectId)).first();
		return doc;
	}

	/**
	 * 分页查询
	 * 
	 * @param dbName
	 * @param collectionName
	 * @param filter
	 * @param pageIndex
	 *            从1开始
	 * @param pageSize
	 * @return
	 */
	public List<Document> findByPage(String collectionName,
			Bson filter, int pageIndex, int pageSize) {
		Bson orderBy = new BasicDBObject("_id", 1);

		final List<Document> resultList = new ArrayList<Document>();
		FindIterable<Document> docs = getDatabase(database)
				.getCollection(collectionName).find(filter).sort(orderBy)
				.skip((pageIndex - 1) * pageSize).limit(pageSize);
		docs.forEach(new Block<Document>() {
			public void apply(Document document) {
				resultList.add(document);
			}
		});

		return resultList;
	}

	@SuppressWarnings("rawtypes")
	public MongoCollection getCollection(String dbName, String collectionName) {
		return mongoClient.getDatabase(dbName).getCollection(collectionName);
	}

	public MongoDatabase getDatabase(String dbName) {
		return mongoClient.getDatabase(dbName);
	}

	public long getCount(String dbName, String collectionName) {
		return getDatabase(dbName).getCollection(collectionName).count();
	}

	/**
	 * 查询dbName下的所有表名
	 * 
	 * @param dbName
	 * @return
	 */
	public List<String> getAllCollections(String dbName) {
		MongoIterable<String> cols = getDatabase(dbName).listCollectionNames();
		List<String> _list = new ArrayList<String>();
		for (String s : cols) {
			_list.add(s);
		}
		return _list;
	}

	/**
	 * 获取所有数据库名称列表
	 * 
	 * @return
	 */
	public MongoIterable<String> getAllDatabaseName() {
		MongoIterable<String> s = mongoClient.listDatabaseNames();
		return s;
	}

	/**
	 * 删除一个数据库
	 * 
	 * @param dbName
	 */
	public void dropDatabase(String dbName) {
		getDatabase(dbName).drop();
	}

	/**
	 * 删除collection
	 * 
	 * @param dbName
	 * @param collectionName
	 */
	public void dropCollection(String dbName, String collectionName) {
		getDatabase(dbName).getCollection(collectionName).drop();
	}

	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
	}
	
	public static void main(String[] args) {
//		      MongoDBUtil.getInstance().find("test", filter);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userName", "aaa");
//		map.put("userName1", "aaa1");
//		map.put("userName2", "aaa2");
//		System.out.println(MongoDBUtil.getInstance().insert("test", map));
//		System.out.println(MongoDBUtil.getInstance().find("test",Filters.eq("userName", "aaa")));
		
		System.out.println(MongoDBUtil.getInstance().find("test"));
		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("userName3", "aaa3");
//		MongoDBUtil.getInstance().delete("test", map);
	}
}
