package com.qc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test5 {
    public static void main(String[] args) {
        //判断手机号
        String regex = "[1|2]\\d{10}";
        boolean b1 = Pattern.matches(regex, "15605482190");
        System.out.println(b1);
        //判断邮箱
        final String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(check);
        final Matcher mat = pattern.matcher("1799953029@qq.com");
        System.out.println(mat.matches());
        //marsha密码
        String mm = DigestUtils.md5Hex("qcqc12345");
        System.out.println(mm);
        String name;
        String distance;
        String nearby ="[{name:'碧梧小筑',distance:'123.1km'},{name:'古城墙',distance:'56km'}]";
        JSONArray nearbySplit = null;
        try {
             nearbySplit = JSONArray.parseArray(nearby);
        }catch (Exception e){
            throw new RuntimeException(".....");
        }
        List<HotelVo> hotelVoList = new ArrayList<>();

        for(int i=0;i<nearbySplit.size();i++){
            JSONObject jsonObject = JSON.parseObject(nearbySplit.get(i).toString());
            HotelVo h = new HotelVo();
            try {
                 name = jsonObject.getString("name");
            }catch (Exception e){
                throw new RuntimeException("名字不存在");
            }

            try {
                distance = jsonObject.getString("distance");
            }catch (Exception e){
                throw new RuntimeException("距离不存在");
            }
            h.setName(name);
            h.setDistance(distance);
            hotelVoList.add(h);
        }
        System.out.println(hotelVoList); //[HotelVo(name=碧梧小筑, distance=123.1km), HotelVo(name=小宫殿, distance=56km)]
    }
}
