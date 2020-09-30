package com.xsd.jx.bean;

/**
 * Date: 2020/9/30
 * author: SmallCake
 */
public class CommentRequest {

    /**
     allRate	integer
     总体评分
     content	string
     评价内容
     id	integer
     ID
     isAnonymous	integer
     是否匿名 1:是 2: 否
     rate1	integer
     评分1
     rate2	integer
     评分2
     rate3	integer
     评分3
     toUserId	integer
     被评价用户ID
     userId	integer
     评价用户ID
     workId	integer
     工作ID
     */

    private int allRate;
    private String content;
    private int id;
    private int isAnonymous;
    private int rate1;
    private int rate2;
    private int rate3;
    private int toUserId;
    private int userId;
    private int workId;

    private CommentRequest(Builder builder) {
        setAllRate(builder.allRate);
        setContent(builder.content);
        setId(builder.id);
        setIsAnonymous(builder.isAnonymous);
        setRate1(builder.rate1);
        setRate2(builder.rate2);
        setRate3(builder.rate3);
        setToUserId(builder.toUserId);
        setUserId(builder.userId);
        setWorkId(builder.workId);
    }


    public int getAllRate() {
        return allRate;
    }

    public void setAllRate(int allRate) {
        this.allRate = allRate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(int isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    public int getRate1() {
        return rate1;
    }

    public void setRate1(int rate1) {
        this.rate1 = rate1;
    }

    public int getRate2() {
        return rate2;
    }

    public void setRate2(int rate2) {
        this.rate2 = rate2;
    }

    public int getRate3() {
        return rate3;
    }

    public void setRate3(int rate3) {
        this.rate3 = rate3;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public static final class Builder {
        private int allRate;
        private String content;
        private int id;
        private int isAnonymous;
        private int rate1;
        private int rate2;
        private int rate3;
        private int toUserId;
        private int userId;
        private int workId;

        public Builder() {
        }

        public Builder allRate(int val) {
            allRate = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public Builder id(int val) {
            id = val;
            return this;
        }

        public Builder isAnonymous(int val) {
            isAnonymous = val;
            return this;
        }

        public Builder rate1(int val) {
            rate1 = val;
            return this;
        }

        public Builder rate2(int val) {
            rate2 = val;
            return this;
        }

        public Builder rate3(int val) {
            rate3 = val;
            return this;
        }

        public Builder toUserId(int val) {
            toUserId = val;
            return this;
        }

        public Builder userId(int val) {
            userId = val;
            return this;
        }

        public Builder workId(int val) {
            workId = val;
            return this;
        }

        public CommentRequest build() {
            return new CommentRequest(this);
        }
    }
}
