package com.mortal2019.core.exception;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:50
 */
public class IgnoredException {
    /**
     * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
     *
     * @param execution 要执行的 Runnable
     */
    public static void run(Runnable execution) {
        try {
            execution.run();
        }
        catch (Exception ignored) {
        }
    }

    /**
     * 执行中出现异常，忽略不报错并且继续执行（多用于循环体中可忽略的循环执行）
     *
     * @param execution         要执行的 Runnable
     * @param exceptionFunction 异常出现要执行的 Consumer
     */
    public static void run(Runnable execution, Consumer<Exception> exceptionFunction) {
        try {
            execution.run();
        }
        catch (Exception ex) {
            exceptionFunction.accept(ex);
        }
    }

    /**
     * 执行中出现异常，返回默认值
     *
     * @param supplier 要执行的 Supplier
     * @param def      默认值
     * @return T
     */
    public static <T> T get(Supplier<T> supplier, T def) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return def;
        }
    }

    /**
     * 执行中出现异常，返回默认值
     *
     * @param supplier          要执行的 Supplier
     * @param exceptionFunction 异常的时候执行
     * @return T
     */
    public static <T> T get(Supplier<T> supplier, Function<Exception, T> exceptionFunction) {
        try {
            return supplier.get();
        }
        catch (Exception ex) {
            return exceptionFunction.apply(ex);
        }
    }

}
