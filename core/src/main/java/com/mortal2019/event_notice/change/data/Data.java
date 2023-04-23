package com.mortal2019.event_notice.change.data;

/**
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:58
 */
public class Data<T> {
    Object sender;
    ChangeData<T> changeData;

    public Data(Object sender, ChangeData<T> changeData) {
        this.sender = sender;
        this.changeData = changeData;
    }

    public Object getSender() {
        return sender;
    }

    public ChangeData<T> getChangeData() {
        return changeData;
    }
}
