package com.xsd.jx.bean;

/**
 * Date: 2020/9/19
 * author: SmallCake
 */
public class WithdrawInfoResponse {

    /**
     * wx : {"account":""}
     * ali : {"account":"","name":""}
     * division : {"id":0,"name":"","addr":""}
     * bank : {"account":"","name":"","bankName":""}
     */

    private WxBean wx;
    private AliBean ali;
    private DivisionBean division;
    private BankBean bank;

    public WxBean getWx() {
        return wx;
    }

    public void setWx(WxBean wx) {
        this.wx = wx;
    }

    public AliBean getAli() {
        return ali;
    }

    public void setAli(AliBean ali) {
        this.ali = ali;
    }

    public DivisionBean getDivision() {
        return division;
    }

    public void setDivision(DivisionBean division) {
        this.division = division;
    }

    public BankBean getBank() {
        return bank;
    }

    public void setBank(BankBean bank) {
        this.bank = bank;
    }

    public static class WxBean {
        /**
         * account :
         */

        private String account;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }
    }

    public static class AliBean {
        /**
         * account :
         * name :
         */

        private String account;
        private String name;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class DivisionBean {
        /**
         * id : 0
         * name :
         * addr :
         */

        private int id;
        private String name;
        private String addr;

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

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }

    public static class BankBean {
        /**
         * account :
         * name :
         * bankName :
         */

        private String account;
        private String name;
        private String bankName;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }
    }
}
