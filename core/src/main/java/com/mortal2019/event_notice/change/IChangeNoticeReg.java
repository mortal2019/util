package com.mortal2019.event_notice.change;

/**
 * 数据变更通知注册
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:53
 */
public interface IChangeNoticeReg<T> {
    void changeNoticeReg(IChangeNotice<T> iChangeNotice);
}
