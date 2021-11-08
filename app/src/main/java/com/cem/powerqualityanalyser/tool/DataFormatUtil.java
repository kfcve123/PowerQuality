package com.cem.powerqualityanalyser.tool;

import com.cem.powerqualityanalyser.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

public class DataFormatUtil {

    public static boolean isNumber(String str) {
        if (isEmpty(str)) {
            return false;
        }
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 仪表保留的小数位数
     * @param sourceDate
     * @param formatLength
     * @return
     */
    public static String frontCompWithZore(float sourceDate, int formatLength) {
        String newString = String.format("%." + formatLength + "f", sourceDate);
        return newString;
    }

    public static String getTime(int second) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(second);
        return hms;
    }

    /**
     * 数值小数点为处理
     * @param value
     * @param point
     * @return
     */
    public static String formatValue(float value,int point){
        StringBuilder format = new StringBuilder("#0");
        for (int i = 0; i < point; i++) {
            if (i == 0)
                format.append(".0");
            else
                format.append("0");
        }
        return new DecimalFormat(format.toString()).format(value);
//        return  String.format("%." + 2 + "f",value );
    }


}
