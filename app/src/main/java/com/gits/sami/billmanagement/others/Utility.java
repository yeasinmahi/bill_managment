package com.gits.sami.billmanagement.others;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Arafat on 18/01/2017.
 */

public class Utility {
    public static final Date ErrorDate= new Date(2000,01,01);
    public static  final String DbName = "billManagement.sqlite";
    public static  final int DbVersion = 1;
    public static  final String BillTableName = "bill";
    public enum myDateFormat {
        dd_MMM_yyyy("dd-MMM-yyyy"),
        yyyy_MM_dd("yyyy-MM-dd"),
        MMM_yyyy("MMM-yyyy")
        ;

        private final String text;

        private myDateFormat(final String text) {
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }
    public static Date getDate(String date, myDateFormat dateDFormat){
        DateFormat format = new SimpleDateFormat(dateDFormat.toString());
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ErrorDate;
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
    public static String getDateAsString(Date date,Utility.myDateFormat dateFormat){
        return new SimpleDateFormat(dateFormat.toString()).format(date);
    }

    public enum billType{
        Electricity,
        Wasa,
        Gas
    }

}
