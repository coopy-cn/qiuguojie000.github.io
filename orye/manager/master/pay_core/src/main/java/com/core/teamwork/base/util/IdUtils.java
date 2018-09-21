package com.core.teamwork.base.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class IdUtils {
	
	private static Logger logger = Logger.getLogger(IdUtils.class);
	
	public static void main(String[] args) throws Exception {
		/*IdUtils ids = new IdUtils(1, 2);
		System.out.println(ids.nextId());*/
		//System.out.println(createUUID(false));
		//System.out.println(createRandomString(0));
		//System.out.println(createNum(20));
		//System.out.println(createChar(20));
	}
	
	public static String createUUID() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		return createUUID(true);
	}
	
	/**
	 * 常用的ID生成方法(使用的是UUID,但是不带 - )
	 * @param flag 默认为真; 如果为假 就返回16位经过MD5加密后数据(可能就会有特殊字符)
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public static synchronized String createUUID(boolean flag) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		String uuid = UUID.randomUUID().toString().replace("-", "");;
		if(flag){
			//为真 直接返回
			return uuid;
		}else{
			return md5(uuid);
		}
	}
	
	private static final char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G',  
        'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',  
        'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',  
        'x', 'y', 'z', '0', '1' };
	
	/**
	 * 生成指定长度的字符和数字结合(重复率非常低,可放心使用)
	 * @param length
	 * @return
	 */
	public static synchronized String createRandomString(final int length) {  
		if(length==0){
			throw new NullPointerException("生成的字符数字长度不允许为0");
		}
		Random random = new Random();
		if (length > 0) {
			int index = 0;
			char[] temp = new char[length];
			int num = random.nextInt();
			for (int i = 0; i < length % 5; i++) {
				temp[index++] = ch[num & 63];// 取后面六位，记得对应的二进制是以补码形式存在的。
				num >>= 6;// 63的二进制为:111111
				// 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
			}
			for (int i = 0; i < length / 5; i++) {
				num = random.nextInt();
				for (int j = 0; j < 5; j++) {
					temp[index++] = ch[num & 63];
					num >>= 6;
				}
			}
			return new String(temp, 0, length);
		} else {
			throw new IllegalArgumentException();
		}
    }  
	
	
    //A~Z的ASCII为65~90
    //a~z的ASCII为97~122
	/**
	 * 生成指定长度的纯字符(不代表一定唯一)
	 * @param length
	 * @return
	 */
	public static String createChar(final int length){
		if(length==0){
			throw new NullPointerException("生成的字符长度不允许为0");
		}
		StringBuffer sb = new StringBuffer(length);
		Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(2);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
            case 0:
            	 data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                 sb.append((char)data);
                 break;
            case 1:
                data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                sb.append((char)data);
                break;
            }
        }
        return sb.toString();
	}
	
	//0~9的ASCII为48~57
	/**
	 * 创建指定位数的数字(不代表一定唯一)
	 * @param length
	 * @return 因为Integer有最大值 ,而 位数不指定 所以 返回String
	 */
	public static String createNum(final int length){
		if(length==0){
			throw new NullPointerException("生成的数字长度不允许为0");
		}
		Random randdata = new Random();
		StringBuffer sb = new StringBuffer(length);
		for(int i=0;i<length;i++){
			int data=randdata.nextInt(10);//仅仅会生成0~9
			sb.append(data);
		}
       return sb.toString();
	}
	
	private static long INFOID_FLAG = 1260000000000L;
	private static int SERVER_ID = 1;

	/**
	 * 根据当前时间生成的唯一ID
	 * @return	纯数字
	 * @throws InterruptedException
	 */
	public static synchronized long createId() throws InterruptedException{
		if (SERVER_ID <= 0){
			logger.error("Id生成异常");
			throw new ArrayIndexOutOfBoundsException("Id生成异常");
		}
		long infoid = System.currentTimeMillis() - INFOID_FLAG;
		infoid = (infoid << 7) | SERVER_ID;
		Thread.sleep(1);
		return infoid;			
	}
	
	
	//下面这是twitter分布式下的生成方法
	private final static long twepoch = 1288834974657L;
	// 机器标识位数
	private final static long workerIdBits = 5L;
	// 数据中心标识位数
	private final static long datacenterIdBits = 5L;
	// 机器ID最大值
	private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private final static long sequenceBits = 12L;
	// 机器ID偏左移12位
	private final static long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private final static long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private final static long timestampLeftShift = sequenceBits + workerIdBits
			+ datacenterIdBits;

	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;

	private long sequence = 0L;
	private final long workerId;
	private final long datacenterId;
	
	public IdUtils(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(
					"worker Id can't be greater than %d or less than 0");
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(
					"datacenter Id can't be greater than %d or less than 0");
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			try {
				throw new Exception(
						"Clock moved backwards.  Refusing to generate id for "
								+ (lastTimestamp - timestamp) + " milliseconds");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID
		long nextId = ((timestamp - twepoch) << timestampLeftShift)
				| (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;

		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * 只留16位 进行加密
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String id) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String sixteenUUID = StringUtils.substring(id, 0, 16);
        return base64en.encode(md5.digest(sixteenUUID.getBytes("utf-8")));
	}
	
}
