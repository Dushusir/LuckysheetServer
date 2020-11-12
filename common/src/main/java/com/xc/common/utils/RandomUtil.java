package com.xc.common.utils;

import java.util.Random;

/**
 * @author Administrator
 */
public class RandomUtil {
    /**
     * 获取某一个范围内的随机数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 返回一个6位随机数
     * @return
     */
    public static int getRandom(){
        return getRandom(100000,1000000);
    }
}
