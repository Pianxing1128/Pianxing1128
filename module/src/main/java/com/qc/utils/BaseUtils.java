package com.qc.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import redis.clients.jedis.Jedis;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
@Slf4j
public class BaseUtils {
    public static int currentSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 判断参数是否为空
     *
     * @param obj 校验的对象
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof List) {
            return obj == null || ((List<?>) obj).size() == 0;
        } else if (obj instanceof Number) {
            DecimalFormat decimalFormat = new DecimalFormat();
            try {
                return decimalFormat.parse(obj.toString()).doubleValue() == 0;
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return obj == null || "".equals(obj.toString());
        }
    }

    /**
     * md5加密
     *
     * @param text 待加密字符串
     * @return
     */
    public static String md5(String text) {
        String encodeStr = "";
        try {
            encodeStr = DigestUtils.md5Hex(text);
        } catch (Exception e) {
            return encodeStr;
        }
        return encodeStr;
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @return
     */
    public static String timeStamp2Date(int seconds) {
        return timeStamp2Date(seconds, null);
    }

    public static String timeStamp2Date(int seconds, String format) {
        if (isEmpty(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    public static String timeStamp2DateGMT(int seconds, String format) {
        if (isEmpty(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @return
     */
    public static int date2TimeStamp(String date_str) {
        return date2TimeStamp(date_str, null);
    }

    public static int date2TimeStamp(String date_str, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            String timestamp = String.valueOf(sdf.parse(date_str).getTime() / 1000);
            int length = timestamp.length();
            if (length > 3) {
                return Integer.valueOf(timestamp.substring(0, length));
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获得指定长度的随机数
     *
     * @param num
     * @return
     */
    public static String getRandNumber(int num) {
        Random random = new Random();
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < num; i++) {
            result.append(random.nextInt(9)+1);
        }
        return result.toString();
    }

    public static String getRandString(int size) {
        String abc = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //指定长度size = 30
        //指定取值范围 abc 如果不指定取值范围，中文环境下会乱码
        String str = RandomStringUtils.random(size, abc);
        return str;
    }

    public static String formatPrice(int price) {
        String prefix = "￥";
        return prefix + formatPriceNum(price);
    }

    public static String formatPrice(BigInteger price) {
        int priceInt = price.intValue();
        return formatPrice(priceInt);
    }

    public static String formatPriceNum(int price){
        int points = (price % 100);
        if (points == 0) {
            return (price / 100) + ".00";
        } else {
            String pointStr = new DecimalFormat("00").format(points);
            return (price / 100) + "." + pointStr;
        }
    }

    public static String formatPriceNum(BigInteger price){
        int priceInt = price.intValue();
        return formatPriceNum(priceInt);
    }

    public static String formatWeight(int weight) {
        String endFix = "Kg";
        int points = (weight % 1000);
        if (points == 0) {
            return (weight / 1000) + ".00" + endFix;
        } else {
            return (weight / 1000) + "." + points + endFix;
        }
    }

    public static String implodeSearchParam(String param1, String param2) {
        String result;
        if (BaseUtils.isEmpty(param1) && BaseUtils.isEmpty(param2)) {
            result = null;
        } else if (!BaseUtils.isEmpty(param1) && !BaseUtils.isEmpty(param2)) {
            result = param1 + "," + param2;
        } else {
            result = BaseUtils.isEmpty(param1) ? param2 : param1;
        }
        return result;
    }

    public static double getFileSize(Long len, String unit) {
        double fileSize = 0;
        if ("B".equals(unit.toUpperCase())) {
            fileSize = (double) len;
        } else if ("K".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1024;
        } else if ("M".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1048576;
        } else if ("G".equals(unit.toUpperCase())) {
            fileSize = (double) len / 1073741824;
        }
        return fileSize;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


    public static String getShortTime(Integer dateline) {
        String shortstring = null;
        String time = timeStamp2Date(dateline);
        Date date = getDateByString(time);
        if(date == null) return shortstring;

        long now = Calendar.getInstance().getTimeInMillis();
        long deltime = (now - date.getTime())/1000;
        if(deltime > 365*24*60*60) {
            shortstring = (int)(deltime/(365*24*60*60)) + "年前";
        } else if(deltime > 24*60*60) {
            shortstring = (int)(deltime/(24*60*60)) + "天前";
        } else if(deltime > 60*60) {
            shortstring = (int)(deltime/(60*60)) + "小时前";
        } else if(deltime > 60) {
            shortstring = (int)(deltime/(60)) + "分前";
        } else if(deltime > 1) {
            shortstring = deltime + "秒前";
        } else {
            shortstring = "1秒前";
       }
       return shortstring;
   }

    public static Date getDateByString(String time) {
        Date date = null;
        if (time == null)
            return date;

        String date_format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(date_format);
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;
    }



    public static String generateOrderNumber() {
        // 获取当前时间
        Date currentTime = new Date();

        // 定义日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        // 将当前时间转换为指定格式的日期字符串
        String dateString = dateFormat.format(currentTime);

        // 生成随机数
        Random random = new Random();
        int randomNumber = random.nextInt(900) + 100;

        // 拼接订单编号
        String orderNumber = dateString + randomNumber;

        // 返回最终的订单编号
        return orderNumber;
    }

    //用户签到实现
//    public static AjaxResult check(@PathVariable String id) {
//        //首先拼接key
//        String day =  DateFormatUtils.format(new Date(), "yyyyMMdd");
//        String key = id + ":" +day;
//        //redis中是否存在该key
//        Boolean flag = redisTemplate.hasKey(key);
//        if(flag){
//            return AjaxResult.error(500,"今日用户已签到");
//        }else{
//            //设置redis中的过期时间，凌晨0点清空；
//            redisTemplate.opsForValue().set(key, day, getRefreshTime(), TimeUnit.SECONDS);
//            //将未签到用户记录在mysql中
//            int i = iPlatUserService.insert(id);
//            if(i > 0){
//                //签到成功
//                return AjaxResult.success("用户成功签到");
//            }else {
//                return AjaxResult.error(500,"由于不正常原因，用户签到失败！");
//            }
//        }
//    }


    /*
     * 获取当前时间离明天凌晨还有多少时间
     * */
    public static int getRefreshTime(){
        Calendar calendar = Calendar.getInstance();
        int now = (int) (calendar.getTimeInMillis()/1000);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY , 0);
        return (int) (calendar.getTimeInMillis()/1000-now);
    }



    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final String SIGN_IN_KEY_PREFIX = "user_sign_in:";


//    public static void main(String[] args) {
//
//        signIn(1);
//        Boolean is1 = hasSignedInToday(1);
//        log.info(String.valueOf(is1));
//        Boolean is2 = hasSignedInToday(2);
//        log.info(String.valueOf(is2));
//    }

    // 用户签到方法
    public static void signIn(BigInteger userId) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            // 使用Set数据结构存储用户的签到日期
            String signInKey = SIGN_IN_KEY_PREFIX + userId;
            LocalDate currentDate = LocalDate.now();
            jedis.sadd(signInKey, currentDate.toString());
            log.info("用户 " + userId + " 签到成功！");
        }
    }

    // 判断用户今天是否签到
    public static boolean hasSignedInToday(BigInteger userId) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            String signInKey = SIGN_IN_KEY_PREFIX + userId;
            LocalDate currentDate = LocalDate.now();

            // 使用Redis的SISMEMBER命令判断今天是否在集合中
            return jedis.sismember(signInKey, currentDate.toString());
        }
    }
    //获取当前年月
    public static String getCurrentYearMonth(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        String currentYear = String.valueOf(year);
        String currentMonth = String.valueOf(month);
        String currentYearMonth = currentYear+"年"+currentMonth+"月";
        return currentYearMonth;
    }

    //用户分享存到redis
    public static void shareIn(BigInteger userId) {
        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            // 使用Set数据结构存储用户的签到日期
            String signInKey = SIGN_IN_KEY_PREFIX + userId;
            jedis.sadd(signInKey, getCurrentYearMonth());
            log.info("用户 " + userId + " 本年本月分享成功！");
        }
    }
    // 判断这个月是否分享
    public static boolean hasSharedInMonth(BigInteger userId){

        try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
            String signInKey = SIGN_IN_KEY_PREFIX + userId;
            // 使用Redis的SISMEMBER命令判断今天是否在集合中
            return jedis.sismember(signInKey, getCurrentYearMonth());
        }

    }

    public static String generateKey(String courseName, String nickName,
                                     Integer showTagId, Integer isVip, Integer orderedType,Integer pageNum) {

        return "courseName" +":"+ courseName+":"+ "nickName" +":"+ nickName +":"+ "showTagId" +":"
                + showTagId+":" + "isVip" +":"+ isVip+":" + "orderedType" +":"+ orderedType + ":" + "pageNum" + ":" +  pageNum;
    }
    //冒号前面的是key，后面的是对应的value
    public static String extractValueInKey(String[] parts, String key) {
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(key)) {
                return parts[i + 1];
            }
        }
        return null;
    }

}
