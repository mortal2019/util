package com.mortal2019.crontab;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:33
 */
public interface CrontabReg {
    /**
     * 定时任务注册接口
     *
     * @param name          任务名称
     * @param executionTask 定时任务
     */
    void reg(String name, BaseExecutionTask executionTask);
}
