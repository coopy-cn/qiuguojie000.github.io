package com.core.teamwork.base.util.insertid;

/**
 * @author Administrator
 *-alias     �������(default��mykey,ÿ��keystore��������һ����һ�޶���alias������ִ�Сд)
-keystore     ָ����Կ������(����ĸ�����Ϣ������.keystore�ļ���)
-keyalg     ָ����Կ���㷨 (��RSA��DSA��default��DSA)
-validity     ָ��������֤����Ч�ڶ�����(default��90)
-keysize     ָ����Կ����(default��1024����Χ��512 ~ 1024)
-storepass     ָ����Կ�������(��ȡkeystore��Ϣ���������)
-keypass     ָ��������Ŀ������(˽Կ������)
-dname     ָ��֤��ӵ������Ϣ 
 "CN=����������,OU=��֯��λ���,O=��֯���,L=���л��������,ST=�ݻ�ʡ�����,C=��λ������ĸ��Ҵ���"
 */
public class KeystoreInfo {
	public String alias;
	public String keypass;
	public String keyalg;
	public String keysize;
	public String validity;
	public String keystore;
	public String storepass;
	public String CN;
	public String OU;
	public String O;
	public String L;
	public String ST;
	public String C;
}
