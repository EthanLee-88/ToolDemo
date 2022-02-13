package com.ethan.tooldemo.download;

import android.content.Context;

import java.text.DecimalFormat;
import java.util.Random;

public class RegularUtil {
    private static final String TAG = "RegularUtil";
    public RegularUtil(){

    }
    public RegularUtil(Context context){

    }

    public static boolean isDigit(String str) {   //仅含数字
        String regex = "^[0-9]+$";
        return str.matches(regex);
    }

    public static boolean isLetterDigit(String str) {  //仅含数字字母，
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    public static boolean isLetterDigitAndEl(String str) {  //仅含且同时含数字字母，
        String regex = "^[a-z0-9A-Z]+$";
        String regex1 = ".*[a-zA-z].*"; //含有英文
        String regex2 = ".*[0-9].*"; //含有数字
        return ((str.matches(regex)) && ((str.matches(regex1)) && (str.matches(regex2))));
    }

   public static boolean isChinese(String str){   //仅含中文
      if (str.matches("[\\u4e00-\\u9fa5]+")){
          return true;
      }else {
          return false;
      }
   }

    public static boolean isEnglish(String str) {  //仅含字母，
        String regex = "^[a-zA-Z]+$";
        return str.matches(regex);
    }

    public static boolean isChineseOrEnglish(String str){
        if ((str.matches("[\\u4e00-\\u9fa5]+")) || (str.matches("^[a-zA-Z]+$"))){
            return true;
        }else {
            return false;
        }
    }

    public static boolean isEmpty(String s){
        if ((s != null) && ((s.length() != 0) && (!s.equals("null")))){
            return false;
        }else {
            return true;
        }
    }
    public static boolean isNumber(String str){   //是否是数字
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public static float dataDivider(int dividend , int divisor){
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.parseFloat(df.format((float)dividend/divisor));
    }

    public static float dataDivider(float dividend , float divisor){
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.parseFloat(df.format(dividend/divisor));
    }

    public static int getRandomInt(int min , int max){  //生成一定范围的随机数
       return (new Random().nextInt(max - min + 1) + min);
    }
}
