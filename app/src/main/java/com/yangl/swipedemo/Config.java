package com.yangl.swipedemo;

import java.io.Serializable;

/***
 * @Author: YangLang
 * @Version: v1.0
 * @Description: xxx
 */
public class Config implements Serializable {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public boolean isOpen = true;

    public int orientation;  // 0 ： 水平  1： 垂直

    public float sensitiveArea = 0.55f; // 敏感范围

    public int colorA = 153, colorR = 0, colorG = 0, colorB = 0;

    public int speed = 250;

    public boolean edge = true;
}
