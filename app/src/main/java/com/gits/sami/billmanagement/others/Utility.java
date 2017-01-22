package com.gits.sami.billmanagement.others;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Arafat on 18/01/2017.
 */

public class Utility {
    public static Date getDate(String date){
        DateFormat formater = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            return formater.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date(2000,01,01);
    }
    public enum dateEnum{
        ElectricityBillingDate,
        ElectricityPaymentDate,
        ReportDate
    }
    public enum isFullDateEnum{
        TRUE,
        FALSE
    }
    public static String getFullDateAsString(Date date){
        return new SimpleDateFormat("dd-MMM-yyyy").format(date);
    }
    public static String getHalfDateAsString(Date date){
        return new SimpleDateFormat("MMM-yyyy").format(date);
    }

}
