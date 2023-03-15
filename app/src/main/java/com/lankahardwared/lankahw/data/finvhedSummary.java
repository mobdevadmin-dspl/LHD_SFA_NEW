package com.lankahardwared.lankahw.data;

import java.util.ArrayList;

/**
 * Created by Dhanushika on 2/16/2018.
 */

public class finvhedSummary {
    private String DebCode;
    private String RefNo;
    private String TotalAmt;
    private String TotalDis;
    private String TxnDate;
    private ArrayList<finvDetSummary> list = new ArrayList<finvDetSummary>();




    public ArrayList<finvDetSummary> getlistChildData() {
        return list;
    }

    public void setlistChildData(ArrayList<finvDetSummary> listChildData) {
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
