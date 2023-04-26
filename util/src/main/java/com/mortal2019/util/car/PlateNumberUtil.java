package com.mortal2019.util.car;

import com.mortal2019.core.asserts.BaseAssert;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 车辆校验车牌号码工具
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/26 10:16
 */
public class PlateNumberUtil {

    /**
     * 判断车牌号码是否为空
     *
     * @param plateNumber 车牌号码
     * @return boolean
     * @author wuyiyuan
     * Created DateTime 2023/4/26 10:20
     */
    public static boolean isEmpty(String plateNumber) {
        if (StringUtils.hasText(plateNumber)) {
            return true;
        }
        return false;
    }

    /**
     * 车牌号码校验
     *
     * @param plateNumber 车牌号码
     * @return boolean
     * @author wuyiyuan
     * Created DateTime 2023/4/26 9:41
     */
    public static boolean isPlateNumberValid(String plateNumber) {
        BaseAssert.assertTrue(isEmpty(plateNumber), "车牌号码不能为空");

        // 普通车辆号牌
        String regex_normal = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{5}$";
        // 新能源汽车号牌
        String regex_newEnergy = "^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        // 外交车辆号牌
        String regex_diplomatic = "^[\u4e00-\u9fa5]{1}[D|F|L]{1}[A-Z0-9]{5}$";

        // 先判断是否为普通车辆号牌
        if (Pattern.matches(regex_normal, plateNumber)) {
            return true;
        }
        // 再判断是否为新能源汽车号牌
        if (Pattern.matches(regex_newEnergy, plateNumber)) {
            return true;
        }
        // 最后判断是否为外交车辆号牌
        if (Pattern.matches(regex_diplomatic, plateNumber)) {
            return true;
        }

        // 如果都不匹配，则返回false
        return false;
    }

}
