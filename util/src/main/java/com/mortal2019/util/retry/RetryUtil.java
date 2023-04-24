package com.mortal2019.util.retry;

import java.util.concurrent.Callable;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:23
 */
public class RetryUtil {
    /**
     * 多次重试
     *
     * @param num           重试次数
     * @param callFunctions 重试调用函数
     */
    public static <R> R retry(int num, Callable<R> callFunctions) {
        return retry(num, false, callFunctions);
    }

    /**
     * 多次重试
     *
     * @param num           执行次数(失败后重试)
     * @param callFunctions 重试调用函数
     * @param failedCall    调用函数失败后执行
     */
    public static <R> R retry(int num, Callable<R> callFunctions, Runnable failedCall) {
        return retry(num, true, callFunctions, failedCall);
    }

    /**
     * 重试一次
     *
     * @param callFunctions 重试调用函数
     * @param failedCall    调用函数失败后执行
     */
    public static <R> R retry(Callable<R> callFunctions, Runnable failedCall) {
        return retry(2, true, callFunctions, failedCall);
    }

    /**
     * 失败重试
     *
     * @param num           重试次数
     * @param callFunctions [0]重拾函数，>0 失败后按照失败次数顺序执行 （失败1 执行【1】）
     */
    public static <R> R retry(int num, Callable<R> callable, Runnable... callFunctions) {
        return retry(num, true, callable, callFunctions);
    }

    /**
     * 失败重试
     *
     * @param num           重试次数
     * @param loop          循环执行
     * @param callable      执行调用
     * @param callFunctions [0]重拾函数，>0 失败后按照失败次数顺序执行 （失败1 执行【1】）
     */
    public static <R> R retry(int num, boolean loop, Callable<R> callable, Runnable... callFunctions) {
        int pointer = -1;
        try {
            for (int i = 0; i < num - 1; i++) {
                try {
                    return callable.call();
                }
                catch (Exception e) {
                    if (pointer >= callFunctions.length - 1) {
                        if (loop) {
                            pointer = 0;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        pointer++;
                    }
                    callFunctions[pointer].run();
                }
            }
            return callable.call();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
