package com.mortal2019.functional;

import java.util.Map;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 14:39
 */
@FunctionalInterface
public interface MapToT<T> {
    /**
     * Object 转换成 类型 T
     * @param map Map<String,Object>
     * @return T
     */
    T mapToT(Map<String,Object> map);
}
