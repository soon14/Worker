package com.xsd.jx.bean;

import java.io.Serializable;

/**
 * Date: 2020/9/30
 * author: SmallCake
 */
public class IsInWorkResponse implements Serializable {
    boolean isInWork;

    public boolean isInWork() {
        return isInWork;
    }

    public void setInWork(boolean inWork) {
        isInWork = inWork;
    }
}
