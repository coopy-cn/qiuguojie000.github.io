package com.core.teamwork.base.session;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.core.teamwork.base.util.encrypt.MD5;



/**
 * @author cyl
 *
 */
public class RedisSession implements HttpSession,Serializable{

    
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -911344778413939960L;
	private long creationTime;
    private String id;
    private long lastAccessedTime;
    private Map<String,Object> attrMap;
    private int maxInactiveInterval;
    private boolean isInvalidate;
    public RedisSession(String id){
        creationTime=System.currentTimeMillis()/1000;
        if(id!=null){
            this.id=id;
        } else{
            this.id=MD5.GetMD5Code(UUID.randomUUID().toString());
        }
        lastAccessedTime=creationTime;
        attrMap=new HashMap<String, Object>();
        isInvalidate=false;
        maxInactiveInterval=3600;
    }
    
    
    /**
     * @author cyl
     * @Title: getCreationTime
     * @Description: 得到session的创建时间，单位是s 
     * @see javax.servlet.http.HttpSession#getCreationTime()
     * @return
     */
    @Override
    public long getCreationTime() {
        // TODO Auto-generated method stub
        return creationTime;
    }

    @Override
    public String getId() {
        return id;
    }

    
    
    /**
     * @author cyl
     * @Title: getLastAccessedTime
     * @Description: 得到最后访问时间，单位是秒
     * @see javax.servlet.http.HttpSession#getLastAccessedTime()
     * @return
     */
    @Override
    public long getLastAccessedTime() {
        // TODO Auto-generated method stub
        return lastAccessedTime;
    }
    
    public void setLastAccessedTime() {
        // TODO Auto-generated method stub
        lastAccessedTime=System.currentTimeMillis()/1000l;
    }

    @Override
    public ServletContext getServletContext() {
        // TODO Auto-generated method stub
        return null;
    }

    
    /**
     * @author cyl
     * @Title: setMaxInactiveInterval
     * @Description: 设置session的最大生存时间，单位是秒
     * @see javax.servlet.http.HttpSession#setMaxInactiveInterval(int)
     * @param interval
     */
    @Override
    public void setMaxInactiveInterval(int interval) {
        this.maxInactiveInterval=interval;
    }

    
    /**
     * @author cyl
     * @Title: getMaxInactiveInterval
     * @Description: 得到session的最大生存时间，单位是秒 
     * @see javax.servlet.http.HttpSession#getMaxInactiveInterval()
     * @return
     */
    @Override
    public int getMaxInactiveInterval() {
        // TODO Auto-generated method stub
        return maxInactiveInterval;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return attrMap.get(name);
    }

    @Override
    public Object getValue(String name) {
        return getAttribute(name);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        Set<String> names = new HashSet<String>();
        names.addAll(attrMap.keySet());
        return Collections.enumeration(names);
    }

    
    /**
     * @author cyl
     * @Title: getValueNames
     * @Description: 取得session中所有的key 
     * @see javax.servlet.http.HttpSession#getValueNames()
     * @return
     */
    @Override
    public String[] getValueNames() {
        return attrMap.keySet().toArray(new String[]{});
    }

    @Override
    public void setAttribute(String name, Object value) {
        attrMap.put(name, value);
    }

    @Override
    public void putValue(String name, Object value) {
        setAttribute(name, value);
    }

    
    /**
     * @author cyl
     * @Title: removeAttribute
     * @Description: 删除一个值  
     * @see javax.servlet.http.HttpSession#removeAttribute(java.lang.String)
     * @param name
     */
    @Override
    public void removeAttribute(String name) {
        attrMap.remove(name);
    }

    
    /**
     * @author cyl
     * @Title: removeValue
     * @Description: 删除一个值 
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     * @param name
     */
    @Override
    public void removeValue(String name) {
        removeAttribute(name);
    }

    
    /**
     * @author cyl
     * @Title: invalidate
     * @Description: 注销session 
     * @see javax.servlet.http.HttpSession#invalidate()
     */
    @Override
    public void invalidate() {
        isInvalidate=true;
    }

    
    /**
     * @author cyl
     * @Title: isNew
     * @Description: 判断session是不是新的 
     * @see javax.servlet.http.HttpSession#isNew()
     * @return
     */
    @Override
    public boolean isNew() {
        if(creationTime==lastAccessedTime){
            return true;
        } else{
            return false;
        }
    }

}
