package com.xsd.jx.bean;

import java.util.List;

/**
 * Date: 2020/9/24
 * author: SmallCake
 */
public class ToSettleResponse {

    /**
     * items : [{"id":1,"typeTitle":"木工","address":"湖北省武汉市","startDate":"2020-10-15","endDate":"2020-10-25","settleType":1,"price":300,"safeAmount":60,"advanceAmount":0,"users":[{"id":1,"name":"叶少阳","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","money":600,"checkDays":2},{"id":2,"name":"瓜皮娃儿","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","money":300,"checkDays":1}]}]
     * total : 1500
     */

    private int total;
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 1
         * typeTitle : 木工
         * address : 湖北省武汉市
         * startDate : 2020-10-15
         * endDate : 2020-10-25
         * settleType : 1
         * price : 300
         * safeAmount : 60
         * advanceAmount : 0
         * users : [{"id":1,"name":"叶少阳","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","money":600,"checkDays":2},{"id":2,"name":"瓜皮娃儿","avatar":"https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg","money":300,"checkDays":1}]
         */

        private String id;
        private String typeTitle;
        private String address;
        private String startDate;
        private String endDate;
        private int settleType;
        private String price;
        private int safeAmount;
        private int advanceAmount;
        private String paidAmount;//已支付的工资（元）
        private String needPayAmount;//还需支付金额
        private List<UsersBean> users;

        public String getNeedPayAmount() {
            return needPayAmount;
        }

        public void setNeedPayAmount(String needPayAmount) {
            this.needPayAmount = needPayAmount;
        }

        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTypeTitle() {
            return typeTitle;
        }

        public void setTypeTitle(String typeTitle) {
            this.typeTitle = typeTitle;
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

        public int getSettleType() {
            return settleType;
        }

        public void setSettleType(int settleType) {
            this.settleType = settleType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public List<UsersBean> getUsers() {
            return users;
        }

        public void setUsers(List<UsersBean> users) {
            this.users = users;
        }

        public static class UsersBean {
            /**
             * id : 1
             * name : 叶少阳
             * avatar : https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg
             * money : 600
             * checkDays : 2
             */

            private int id;
            private String name;
            private String avatar;
            private String money;
            private String checkDays;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCheckDays() {
                return checkDays;
            }

            public void setCheckDays(String checkDays) {
                this.checkDays = checkDays;
            }
        }
    }
}
