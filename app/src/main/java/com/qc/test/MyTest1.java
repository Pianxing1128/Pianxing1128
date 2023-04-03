package com.qc.test;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import sun.awt.SunHints;

import java.math.BigInteger;
import java.security.Key;
import java.util.*;
import java.util.concurrent.CyclicBarrier;

public class MyTest1 {

    public static void main(String[] args) {
        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);

        System.out.println(list.toString());

        String str="12345jpg";
        boolean numeric = StringUtils.isNumeric(str);
        System.out.println(numeric);
        String[] suffix = str.split("\\.");
        System.out.println(suffix.toString());
        int len = suffix.length;
        System.out.println(len);
        String postfix = suffix[len-1];
        System.out.println(postfix);
        HashMap<String, Object> map = new HashMap<>();
        map.put("id",1);
        map.put("nick_name","张三");
        System.out.println(map);
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, Object> next = it.next();
            if(next.getKey().equals("id")){
                System.out.println(next.getValue());
            }
        }
        System.out.println("==============================");
        Double i1= 100.0;
        Double i2= 100.0;
        Double i3=2200.0;
        Double i4=2200.0;

        System.out.println(i1==i2);
        System.out.println(i3==i4);

        System.out.println("==============================");
        Integer a = new Integer(3);
        Integer b = new Integer(3);
        System.out.println(a == b); //false, new出来的对象，是不同的对象

        Integer c = 3;
        Integer d = 3;
        System.out.println(c==d); //true,在栈中拿，对象c和对象d就是自动装箱的过程

        Integer e=128;
        Integer f=128;
        System.out.println(e==f); //false, 128不在-128~127中，需要在堆中创建新对象

        int x = 100;
        Integer y = new Integer(100);
        System.out.println(x==y);  //true, y会发生自动拆箱过程，基本数据类型比较数值，为true

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for(int i = 0;i < 3;i ++) {
            new Thread(() -> {
                try {
                    Thread.sleep((long)(Math.random() * 2000));

                    int randomInt = new Random().nextInt(500);
                    System.out.println("hello " + randomInt);

                    cyclicBarrier.await();

                    System.out.println("world " + randomInt);

                } catch (Exception s) {
                    s.printStackTrace();
                }
            }).start();
        }
    }
}
