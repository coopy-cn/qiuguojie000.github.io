package com.core.teamwork.base.j2cache;

import com.core.teamwork.base.util.ReadPro;

/**
 * 缓存入口
 * @author winterlau
 */
public class J2Cache {

//	private final static Logger log = LoggerFactory.getLogger(J2Cache.class);

//	private final static String CONFIG_FILE = "/j2cache.properties";
	private final static CacheChannel channel;
//	private final static Properties config;

	static {
		try {
//			config = loadConfig();
//			String cache_broadcast = config.getProperty("cache.broadcast");
			String cache_broadcast = ReadPro.getValue("cache.broadcast");
			if ("redis".equalsIgnoreCase(cache_broadcast))
				channel = RedisCacheChannel.getInstance();
			else
				throw new CacheException("Cache Channel not defined. name = " + cache_broadcast);
		} catch (Exception e) {
			throw new CacheException("Unabled to load j2cache configuration ", e);
		}
	}

	public static CacheChannel getChannel(){
		return channel;
	}

//	public static Properties getConfig(){
//		return config;
//	}

//	/**
//	 * 加载配置
//	 * @return
//	 * @throws IOException
//	 */
//	private static Properties loadConfig() throws IOException {
//		log.info("Load J2Cache Config File : [{}].", CONFIG_FILE);
//		InputStream configStream = J2Cache.class.getClassLoader().getParent().getResourceAsStream(CONFIG_FILE);
//		if(configStream == null)
//			configStream = CacheManager.class.getResourceAsStream(CONFIG_FILE);
//		if(configStream == null)
//			throw new CacheException("Cannot find " + CONFIG_FILE + " !!!");
//
//		Properties props = new Properties();
//
//		try{
//			props.load(configStream);
//		}finally{
//			configStream.close();
//		}
//
//		return props;
//	}

}
