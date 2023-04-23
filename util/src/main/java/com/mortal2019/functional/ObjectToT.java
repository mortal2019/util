package com.mortal2019.functional;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:40
 */
@FunctionalInterface
public interface ObjectToT<T> {
    /**
     * Object 转换成 类型 T
     * @param o Object
     * @return T
     */
    T objectToT(Object o);
}
