package com.mortal2019.util.str;

import com.mortal2019.util.array.ArrayUtil;

/**
 * 字符串处理工具
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/25 9:29
 */
public class StrUtil {

    /**
     * 获取一个StringBuilder对象
     *
     * @return StringBuilder
     */
    public static StringBuilder builder() {
        return new StringBuilder();
    }

    /**
     * 获取一个StringBuilder对象
     *
     * @param capacity 初始化大小
     * @return StringBuilder
     */
    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }

    /**
     * 反转字符串
     * 例如：abcd =》dcba
     *
     * @param str - 被反转的字符串
     * @return String - 反转后的字符串
     */
    public static String reverse(String str) {
        return new String(ArrayUtil.reverse(str.toCharArray()));
    }
}
