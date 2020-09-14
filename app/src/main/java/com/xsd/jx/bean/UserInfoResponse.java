package com.xsd.jx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/12
 * author: SmallCake
 */
public class UserInfoResponse implements Serializable {

    /**
     * info : {"id":5,"name":"3nBCaHoK","mobile":"18324138218","birthday":"","sex":1,"avatar":"","nation":"汉族","workAreaId":0,"status":1,"wxUnionId":"","intro":"","idCard":"","isCertification":0,"workYears":"","balance":500,"liveBalance":0,"frozenBalance":500,"withdrawTotal":0,"isHelpRegister":0,"createdAt":"2020-09-08 16:14:14","updatedAt":"2020-09-08 16:14:14","inviteCode":0,"isChooseWork":false}
     * workTypes : [{"id":2,"title":"电工"}]
     */

    private UserInfo info;
    private List<WorkTypeBean> workTypes;

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(UserInfo info) {
        this.info = info;
    }

    public List<WorkTypeBean> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkTypeBean> workTypes) {
        this.workTypes = workTypes;
    }


}
