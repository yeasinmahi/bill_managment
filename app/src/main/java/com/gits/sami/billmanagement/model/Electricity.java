package com.gits.sami.billmanagement.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Arafat on 18/01/2017.
 */

public class Electricity {
    public int id;
    public String serialNo;
    public String meterNo;
    public double amount;
    public Date billingMonth;
    public Date paymentDate;
    public boolean isLate;
    public double fineAmount;

}
