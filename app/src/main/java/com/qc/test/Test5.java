package com.qc.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Test5 {
    public static void main(String[] args) {
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
