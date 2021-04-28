package com.fiveonebsi.Utils;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    /**
     * 以季度分割时间段
     * 此处季度是以 12-2月   3-5月   6-8月  9-11月 划分
     * @param startTime     开始时间戳(毫秒)
     * @param endTime       结束时间戳(毫秒)
     * @param beginDateList 开始段时间戳 和 结束段时间戳 一一对应
     * @param endDateList   结束段时间戳 和 开始段时间戳 一一对应
     */
    public static void getIntervalTimeByQuarter(Long startTime, Long endTime, List<Long> beginDateList, List<Long> endDateList) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int month = calendar.get(Calendar.MONTH) + 1;
        switch (month) {
            case 1:
            case 4:
            case 7:
            case 10:
                addTime(beginDateList, endDateList, startDate, endDate, calendar, 3);
                break;
            case 2:
            case 5:
            case 8:
            case 11:
                addTime(beginDateList, endDateList, startDate, endDate, calendar, 2);
                break;
            case 3:
            case 6:
            case 9:
            case 12:
                addTime(beginDateList, endDateList, startDate, endDate, calendar, 1);
                break;
        }
    }

    private static void addTime(List<Long> beginDateList, List<Long> endDateList, Date startDate, Date endDate, Calendar calendar, int i) {
        beginDateList.add(startDate.getTime());
        calendar.add(Calendar.MONTH, i);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() > endDate.getTime()) {
            endDateList.add(endDate.getTime());

        } else {
            endDateList.add(calendar.getTimeInMillis());
            while (calendar.getTimeInMillis() < endDate.getTime()) {
                beginDateList.add(calendar.getTimeInMillis());
                calendar.add(Calendar.MONTH, 3);
                if (calendar.getTimeInMillis() < endDate.getTime()) {
                    endDateList.add(calendar.getTimeInMillis());
                } else {
                    endDateList.add(endDate.getTime());
                }
            }
        }
    }


    /**
     * 按照月份分割一段时间
     *
     * @param startTime     开始时间戳(毫秒)
     * @param endTime       结束时间戳(毫秒)
     * @param beginDateList 开始段时间戳 和 结束段时间戳 一一对应
     * @param endDateList   结束段时间戳 和 开始段时间戳 一一对应
     */
    public static void getIntervalTimeByMonth(Long startTime, Long endTime, List<Long> beginDateList, List<Long> endDateList) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        beginDateList.add(calendar.getTimeInMillis());
        while (calendar.getTimeInMillis() < endDate.getTime()) {
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if(calendar.getTimeInMillis() < endDate.getTime()){
                endDateList.add(calendar.getTimeInMillis());
            } else {
                endDateList.add(endDate.getTime());
                break;
            }
            beginDateList.add(calendar.getTimeInMillis());
        }
    }

    /**
     * 按照年度分割一段时间
     *
     * @param startTime     开始时间戳(毫秒)
     * @param endTime       结束时间戳(毫秒)
     * @param beginDateList 开始段时间戳 和 结束段时间戳 一一对应
     * @param endDateList   结束段时间戳 和 开始段时间戳 一一对应
     */
    public static void getIntervalTimeByYear(Long startTime, Long endTime, List<Long> beginDateList, List<Long> endDateList) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        beginDateList.add(calendar.getTimeInMillis());
        while (calendar.getTimeInMillis() < endDate.getTime()) {
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            if(calendar.getTimeInMillis() < endDate.getTime()){
                endDateList.add(calendar.getTimeInMillis());
            } else {
                endDateList.add(endDate.getTime());
                break;
            }
            beginDateList.add(calendar.getTimeInMillis());
        }
    }

//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat fn=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 1.开始时间 2018-12-09 13:16:04
//        Long startTime =fn.parse("2017-11-09 13:16:04").getTime();
//        // 2.结束时间 2019-12-09 13:16:04
//        Long endTime = fn.parse("2019-12-10 17:30:07").getTime();
//        // 3.开始时间段区间集合
//        List<Long> beginDateList = new ArrayList<Long>();
//        // 4.结束时间段区间集合
//        List<Long> endDateList = new ArrayList<Long>();
//        // 5.调用工具类
//        getIntervalTimeByYear(startTime, endTime, beginDateList, endDateList);
//        // 6.打印输出
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        for (int i = 0; i < beginDateList.size(); i++) {
//            Long beginStr = beginDateList.get(i);
//            Long endStr = endDateList.get(i);
//            String begin1 = sdf.format(new Date(beginStr));
//            String end1 = sdf.format(new Date(endStr));
//            System.out.println("第" + i + "段时间区间:" + begin1 + "-------" + end1);
//        }
//    }


}

