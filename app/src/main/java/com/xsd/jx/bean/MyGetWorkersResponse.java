package com.xsd.jx.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 2020/9/22
 * author: SmallCake
 * 我的招工
 */
public class MyGetWorkersResponse implements Serializable {

    /**
     * items : [{"id":3,"sn":"W16007653110197","userId":5,"typeId":5,"address":"湖北省武汉市江岸区江边城外烤全鱼🐟","startDate":"2020年9月22日","endDate":"2020年9月24日","day":1,"price":280,"settleType":1,"advanceType":1,"num":1,"desc":"招工人师傅，要求踏实肯干，有意者直接报名","isSafe":1,"safeAmount":2,"advanceAmount":40,"status":1,"created_at":"2020-09-22 17:01:52","typeTitle":"油漆工","tobeConfirmNum":0,"confirmedNum":0,"tobeSettledWage":0,"workers":[{"userId":1,"mobile":"","name":"头铁","birthday":"1973-03-07","sex":1,"avatar":"","isCertification":1,"workYears":"5-10年","age":30,"status":1,"checkDay":0,"totalWage":0},{"userId":2,"mobile":"","name":"头铁2号","birthday":"1973-03-07","sex":1,"avatar":"","isCertification":1,"workYears":"5-10年","age":30,"status":1,"checkDay":0,"totalWage":0}]}]
     * page : 1
     * totalPage : 1
     */

    private int page;
    private int totalPage;
    private List<ItemsBean> items;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable, MultiItemEntity {
        /**
         * id : 3
         * sn : W16007653110197
         * userId : 5
         * typeId : 5
         * address : 湖北省武汉市江岸区江边城外烤全鱼🐟
         * startDate : 2020年9月22日
         * endDate : 2020年9月24日
         * day : 1
         * price : 280
         * settleType : 1
         * advanceType : 1
         * num : 1
         * desc : 招工人师傅，要求踏实肯干，有意者直接报名
         * isSafe : 1
         * safeAmount : 2
         * advanceAmount : 40
         * status : 1
         * created_at : 2020-09-22 17:01:52
         * typeTitle : 油漆工
         * tobeConfirmNum : 0
         * confirmedNum : 0
         * tobeSettledWage : 0
         * workers : [{"userId":1,"mobile":"","name":"头铁","birthday":"1973-03-07","sex":1,"avatar":"","isCertification":1,"workYears":"5-10年","age":30,"status":1,"checkDay":0,"totalWage":0},{"userId":2,"mobile":"","name":"头铁2号","birthday":"1973-03-07","sex":1,"avatar":"","isCertification":1,"workYears":"5-10年","age":30,"status":1,"checkDay":0,"totalWage":0}]
         */

        private int id;
        private String sn;
        private String paidAmount;
        private int userId;
        private int typeId;
        private String address;
        private String startDate;
        private String endDate;
        private int day;
        private String price;
        private int settleType;
        private int advanceType;
        private int num;
        private String desc;
        private int isSafe;
        private int safeAmount;
        private int advanceAmount;
        private int status;
        private String created_at;
        private String typeTitle;
        private int tobeConfirmNum;
        private int confirmedNum;
        private int tobeSettledWage;
        private List<WorkerBean> workers;

        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getSettleType() {
            return settleType;
        }

        public void setSettleType(int settleType) {
            this.settleType = settleType;
        }

        public int getAdvanceType() {
            return advanceType;
        }

        public void setAdvanceType(int advanceType) {
            this.advanceType = advanceType;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIsSafe() {
            return isSafe;
        }

        public void setIsSafe(int isSafe) {
            this.isSafe = isSafe;
        }

        public int getSafeAmount() {
            return safeAmount;
        }

        public void setSafeAmount(int safeAmount) {
            this.safeAmount = safeAmount;
        }

        public int getAdvanceAmount() {
            return advanceAmount;
        }

        public void setAdvanceAmount(int advanceAmount) {
            this.advanceAmount = advanceAmount;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getTypeTitle() {
            return typeTitle;
        }

        public void setTypeTitle(String typeTitle) {
            this.typeTitle = typeTitle;
        }

        public int getTobeConfirmNum() {
            return tobeConfirmNum;
        }

        public void setTobeConfirmNum(int tobeConfirmNum) {
            this.tobeConfirmNum = tobeConfirmNum;
        }

        public int getConfirmedNum() {
            return confirmedNum;
        }

        public void setConfirmedNum(int confirmedNum) {
            this.confirmedNum = confirmedNum;
        }

        public int getTobeSettledWage() {
            return tobeSettledWage;
        }

        public void setTobeSettledWage(int tobeSettledWage) {
            this.tobeSettledWage = tobeSettledWage;
        }

        public List<WorkerBean> getWorkers() {
            return workers;
        }

        public void setWorkers(List<WorkerBean> workers) {
            this.workers = workers;
        }

        @Override
        public int getItemType() {
            return status;
        }
    }
}
