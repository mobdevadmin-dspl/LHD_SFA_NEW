package com.lankahardwared.lankahw.model;

/*
*******Kaveesha 29-07-2020*********
 */

public class TargetDet {

    private String Monthn;
    private String RefNo;
    private String TrAmt;
    private String YearT;
    private String txndate;

    public String getMonthn() {
        return Monthn;
    }

    public void setMonthn(String monthn) {
        Monthn = monthn;
    }

    public String getRefNo() {
        return RefNo;
    }

    public void setRefNo(String refNo) {
        RefNo = refNo;
    }

    public String getTrAmt() {
        return TrAmt;
    }

    public void setTrAmt(String trAmt) {
        TrAmt = trAmt;
    }

    public String getYearT() {
        return YearT;
    }

    public void setYearT(String yearT) {
        YearT = yearT;
    }

    public String getTxndate() {
        return txndate;
    }

    public void setTxndate(String txndate) {
        this.txndate = txndate;
    }
}
