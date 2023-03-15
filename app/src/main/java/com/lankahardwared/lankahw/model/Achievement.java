package com.lankahardwared.lankahw.model;

/*
*********kaveesha - 30/07/2020 *****
 */

public class Achievement {

    private int CumInvCount;
    private double CumInvVal;
    private int DayInvCount;
    private double DayInvVal;
    private String RepCode;
    private String RepName;

    public int getCumInvCount() {
        return CumInvCount;
    }

    public void setCumInvCount(int cumInvCount) {
        CumInvCount = cumInvCount;
    }

    public double getCumInvVal() {
        return CumInvVal;
    }

    public void setCumInvVal(double cumInvVal) {
        CumInvVal = cumInvVal;
    }

    public int getDayInvCount() {
        return DayInvCount;
    }

    public void setDayInvCount(int dayInvCount) {
        DayInvCount = dayInvCount;
    }

    public double getDayInvVal() {
        return DayInvVal;
    }

    public void setDayInvVal(double dayInvVal) {
        DayInvVal = dayInvVal;
    }

    public String getRepCode() {
        return RepCode;
    }

    public void setRepCode(String repCode) {
        RepCode = repCode;
    }

    public String getRepName() {
        return RepName;
    }

    public void setRepName(String repName) {
        RepName = repName;
    }
}
