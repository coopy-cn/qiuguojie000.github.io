package myStudy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionDemo {
	public static void main(String args[]){
        MemberView fromObject = new MemberView();
        fromObject.setUsername("sunchenbin");
        fromObject.setPassword("111111");
        fromObject.setAge(21);
        fromObject.setGender("男");
        fromObject.setNickname("墨白");
        String[] fields = new String[]{}; // 没有设置属性，默认去对比两个对象
        //String[] fields = new String[]{"username","password","gender"};
        try{
            // 将一个对象转换成另一个对象，并把指定的属性值传递给这个对象，如果不指定默认去匹配两个对象的属性，存在则赋值
            Member member = (Member) constructObject(fromObject,new Member(),fields);
            System.out.println(member.toString());
        }catch (Exception e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	private static Object constructObject(Object fromObject,Object toObject,String[] fields) throws Exception{

        // 数据源的class
        Class fromClass = fromObject.getClass();
        // 目标的class
        Class toClass = toObject.getClass();

        for (String field : fields){
            try{
                // 获取fromClass的Field
                Field fromDeclaredField = fromClass.getDeclaredField(field);
                fromDeclaredField.setAccessible(true);

                // 从fromClass中获取属性的值
                Object value = fromDeclaredField.get(fromObject);

                // 获取toClass的Field
                Field toDeclaredField = toClass.getDeclaredField(field);
                toDeclaredField.setAccessible(true);

                // 将fromClass中该属性的值设置给toClass中的该属性
                toDeclaredField.set(toObject, value);

            }catch (NoSuchFieldException e){
                System.out.println(field+"属性不存在");
                e.printStackTrace();
            }
        }

        // 如果没有传递属性过来，那么默认对比from和to中的属性，存在的进行赋值操作
        if(fields.length == 0){
            Field[] fromDeclaredFields = fromClass.getDeclaredFields();
            Field[] toDeclaredFields = toClass.getDeclaredFields();
            List<String> fromList = new ArrayList<String>();
            List<String> toList = new ArrayList<String>();

            // 取出from中所有field
            for (Field field : fromDeclaredFields){
                field.setAccessible(true);
                fromList.add(field.getName());
            }

            // 取出to中所有field
            for (Field field : toDeclaredFields){
                field.setAccessible(true);
                toList.add(field.getName());
            }

            // 循环from属性list
            for (String name : fromList){

                // to中是否包含该属性
                if(toList.contains(name)){

                    // 包含先进行取值
                    Field fromDeclaredField = fromClass.getDeclaredField(name);
                    fromDeclaredField.setAccessible(true);
                    Object value = fromDeclaredField.get(fromObject);

                    // 进行赋值操作
                    Field toDeclaredField = toClass.getDeclaredField(name);
                    toDeclaredField.setAccessible(true);
                    toDeclaredField.set(toObject, value);
                }
            }
        }

        return toObject;
    }
}
