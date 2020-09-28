package com.xsd.utils;

import java.util.Random;

/**
 * MyApplication --  com.smallcake.utils
 * Created by Small Cake on  2018/2/9 11:07.
 */

public class RandomUtils {
    public static float getFloat(float min,float max) {
        return min + new Random().nextFloat() * (max - min);
    }
    public static int getInt(int min,int max) {
        return min+new Random().nextInt(max);
    }
}
