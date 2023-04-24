package com.mortal2019.core.change.data;

import java.util.Locale;

/**
 * 变更数据模型
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:47
 */
public class ChangeData<T> {
    T data;
    T oldData;
    DataStatus dataStatus;
    String changeModel;

    /**
     * 变更数据模型
     *
     * @param data 数据信息
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:07
     */
    public ChangeData(T data) {
        this(null, data, null, null);
    }

    /**
     * 变更数据模型
     *
     * @param data        数据信息
     * @param dataStatus  数据状态
     * @param changeModel 数据模式
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:07
     */
    public ChangeData(T data, DataStatus dataStatus, String changeModel) {
        this(null, data, dataStatus, changeModel);
    }

    /**
     * 变更数据模型
     *
     * @param data        数据信息
     * @param dataStatus  数据状态
     * @param changeModel 数据模式
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:07
     */
    public ChangeData(T oldData, T data, DataStatus dataStatus, String changeModel) {
        this.oldData = oldData;
        this.data = data;
        this.dataStatus = dataStatus == null ? DataStatus.U : dataStatus;
        this.changeModel = changeModel == null ? "" : changeModel;
    }

    /**
     * 获取数据
     *
     * @return T
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:07
     */
    public T getData() {
        return data;
    }

    public T getOldData() {
        return oldData;
    }

    /**
     * 获取数据状态
     *
     * @return com.liuhuiyu.core.data.change.ChangeData.DataStatus
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:07
     */
    public DataStatus getDataStatus() {
        return dataStatus;
    }

    /**
     * 获取更新模式
     *
     * @return java.lang.String
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:07
     */
    public String getChangeModel() {
        return changeModel;
    }

    /**
     * 是指定的更新模式
     *
     * @param changeModel 更新模式名称
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-02-07 14:06
     */
    public boolean isChangeModel(String changeModel) {
        return this.changeModel.equals(changeModel);
    }

    /**
     * 记录行状态
     *
     * @author LiuHuiYu
     * Created DateTime 2022-05-02 7:45
     */
    public enum DataStatus {
        /**
         * 添加
         */
        A,
        /**
         * 修改
         */
        E,
        /**
         * 删除
         */
        D,
        /**
         * 未知
         */
        U;

        DataStatus() {
        }

        public boolean isAdd() {
            return this.equals(A);
        }

        public boolean isUpdate() {
            return this.equals(E);
        }

        public boolean isDelete() {
            return this.equals(D);
        }

        public boolean isUnknown() {
            return this.equals(U);
        }

        public static DataStatus fromString(String value) {
            try {
                return valueOf(value.toUpperCase(Locale.US));
            }
            catch (Exception var2) {
                throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'A' 'E' 'D' 'U' (case insensitive).", value), var2);
            }
        }
    }
}
