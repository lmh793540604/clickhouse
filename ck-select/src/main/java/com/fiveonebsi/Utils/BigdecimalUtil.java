package com.fiveonebsi.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @ClassName: BigdecimalUtil
 * @Description: TODO
 * @author: guoxiaobao
 * @date: 2020年09月16日 19:22
 */
public class BigdecimalUtil {

    public static BigDecimal divide(long p1,long p2,int scal){
        BigDecimal t1=new BigDecimal(String.valueOf(p1));
        BigDecimal t2=new BigDecimal(String.valueOf(p2));
        return t1.divide(t2,scal, RoundingMode.HALF_UP);
    }


}
