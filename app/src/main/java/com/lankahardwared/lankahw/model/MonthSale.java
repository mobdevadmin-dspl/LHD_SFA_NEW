package com.lankahardwared.lankahw.model;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 2/12/2018.
 */

public class MonthSale {

    public String month;
    public float salesAmount;
    public ArrayList<TargetVsAchivement> targetVsAchivements;
    public ArrayList<SalesStat> salesStats;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(float salesAmount) {
        this.salesAmount = salesAmount;
    }

    public ArrayList<TargetVsAchivement> getTargetVsAchivements() {
        return targetVsAchivements;
    }

    public void setTargetVsAchivements(ArrayList<TargetVsAchivement> targetVsAchivements) {
        this.targetVsAchivements = targetVsAchivements;
    }

    public ArrayList<SalesStat> getSalesStats() {
        return salesStats;
    }

    public void setSalesStats(ArrayList<SalesStat> salesStats) {
        this.salesStats = salesStats;
    }
}
