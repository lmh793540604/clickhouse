package com.fiveonebsi.entity;

import lombok.Data;

public enum  Constant {
    JKXL("架空线路", "0"),
    BYQ("变压器", "1"),
    DKQ("电抗器", "2"),
    DLQ("断路器", "3"),
    DLHGQ("电流互感器", "4"),
    DYHGQ("电压互感器", "5"),
    GLKG("隔离开关", "6"),
    BLQ("避雷器", "7"),
    OHDRQ("耦合电容器", "8"),
    ZBQ("阻波器", "9"),
    DLXL("电缆线路", "A"),
    ZHDQ("组合电器", "B"),
    MX("母线", "C"),
    ZHDQYJ("组合电器元件", "Y"),
    ZHDQJG("组合电器间隔", "J");

    private String key;

    private String value;

    Constant(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }


    public String getValue() {
        return value;
    }

}
