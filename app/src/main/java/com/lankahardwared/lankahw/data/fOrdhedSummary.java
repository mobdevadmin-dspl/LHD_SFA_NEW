package com.lankahardwared.lankahw.data;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 2/8/2018.
 */

public class fOrdhedSummary {
    private String DebCode;
    private String RefNo;
    private String TotalAmt;
    private String TotalDis;
    private String TxnDate;
    private ArrayList<fOrdDetSummary> list = new ArrayList<fOrdDetSummary>();




    public ArrayList<fOrdDetSummary> getlistChildData() {
        return list;
    }

    public void setlistChildData(ArrayList<fOrdDetSummary> listChildData) {
        this.list = listChildData;
    }


    public String getDebCode() {
        return DebCode;
    }

    public void setDebCode(String debCode) {
        DebCode = debCode;
    }

    public String getRefNo() {
        return RefNo;
    }

    public void setRefNo(String refNo) {
        RefNo = refNo;
    }

    public String getTotalAmt() {
        return TotalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        TotalAmt = totalAmt;
    }

    public String getTotalDis() {
        return TotalDis;
    }

    public void setTotalDis(String totalDis) {
        TotalDis = totalDis;
    }

    public String getTxnDate() {
        return TxnDate;
    }

    public void setTxnDate(String txnDate) {
        TxnDate = txnDate;
    }
}
