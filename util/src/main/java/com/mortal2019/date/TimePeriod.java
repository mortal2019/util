package com.mortal2019.date;

import java.sql.Timestamp;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 11:32
 */
public class TimePeriod {
    final Timestamp beginTime;
    final Timestamp endTime;

    public TimePeriod(String beginTime, String endTime) {
        Timestamp b = TimestampUtil.beginTime(beginTime);
        Timestamp e = TimestampUtil.beginTime(endTime);
        if (b == null || e == null) {
            this.beginTime = null;
            this.endTime = null;
        }
        else {
            this.beginTime = b.before(e) ? b : e;
            this.endTime = b.after(e) ? TimestampUtil.endTime(beginTime) : TimestampUtil.endTime(endTime);
        }
    }

    public TimePeriod(Timestamp beginTime, Timestamp endTime) {
        if (beginTime == null || endTime == null) {
            this.beginTime = null;
            this.endTime = null;
        }
        else {
            this.beginTime = beginTime.before(endTime) ? beginTime : endTime;
            this.endTime = beginTime.after(endTime) ? beginTime : endTime;
        }
    }

    public TimePeriod stringCreate(String beginTime, String endTime) {
        return new TimePeriod(beginTime, endTime);
    }

    public TimePeriod timestampCreate(String beginTime, String endTime) {
        return new TimePeriod(beginTime, endTime);
    }
    /**
     * 检测时间点是否在当前时间段内
     *
     * @param timestamp 检测时间点
     * @return boolean
     * @author wuyiyuan
     * Created DateTime 2023/4/23 11:34
     */
    public boolean isInTimePeriod(Timestamp timestamp) {
        return beginTime.before(timestamp) && endTime.after(timestamp);
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public boolean isValidTime() {
        return beginTime != null;
    }
}
