package com.core.teamwork.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Created by xux on 14-11-24.
 */
public class SerializableUtils {

    public static <T extends Serializable> byte[] serialize(Object value) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

    public static <T extends Serializable> T deSerialize(byte[] bytes) {
        if(null != bytes && bytes.length > 0){
            ByteArrayInputStream bIs = new ByteArrayInputStream(bytes);
            try {
                ObjectInputStream oIs = new ObjectInputStream(bIs);
                return (T) oIs.readObject();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            return null;
        }
       
    }

}
