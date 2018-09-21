package com.core.teamwork.base.util;


/**
 * 地区＋机构号＋客户号＋货币号
 * 
 * @ClassName: BackCardUtil
 * @Description: 银行卡生成
 * @author buyuer
 * @date 2015年10月12日 下午3:10:10
 * 
 */
public class BackCardUtil {

    /**
     * 每位允许的字符
     */
    private static final String IZC_NUMBER = "10512299";

    /**
     * 
     * @author buyuer
     * @Title: getGenNum
     * @Description: 获取UUid生成的数字
     */
    private static String getGenNum() {
        String abc = new UUIDGenerator().generate().toString();
        char[] ca = abc.toCharArray();
        String num = "";
        for (char c : ca) {
            num += (c + 0);
        }
        return num;
    }

    /**
     * 
     * @author buyuer
     * @Title: getCard
     * @Description:获取银行卡
     * @param hash
     * @return
     */
    public static String getCard(String hash) {
        String zic_number = "10512299";
        int len = zic_number.length() + hash.length();
        int end_len = 16 - len;
        if (end_len > 0) {
            String genStr = getGenNum();
            return zic_number + hash + genStr.substring(genStr.length() - end_len);
        } else {
            return (zic_number + hash).substring(0, 16);
        }
    }

    /**
     * 
     * @author buyuer
     * @Title: getCard
     * @Description:获取银行卡
     * @param hash
     * @return
     */
    public static String getOrder(String hash) {
        String zic_number = "10512299";
        int len = zic_number.length() + hash.length();
        int end_len = 20 - len;
        if (end_len > 0) {
            String genStr = getGenNum();
            return zic_number + hash + genStr.substring(genStr.length() - end_len);
        } else {
            return (zic_number + hash).substring(0, 20);
        }
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getCard(i+""));
        }
    }
}
