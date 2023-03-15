package com.lankahardwared.lankahw.model.mapper;

import com.lankahardwared.lankahw.model.FDayExpDet;

import java.util.ArrayList;

public class ExpenseMapper {

    private String ConsoleDB;
    private String EXP_REFNO;
    private String EXP_TXNDATE;
    private String EXP_DEALCODE;
    private String EXP_REPCODE;
    private String EXP_REPNAME;
    private String EXP_REMARK;
    private String EXP_COSTCODE;
    private String EXP_AREACODE;
    private String EXP_ADDUSER;
    private String EXP_ADDDATE;
    private String EXP_ADDMACH;
    private String EXP_LONGITUDE;
    private String EXP_LATITUDE;
    private String EXP_IS_SYNCED;
    private String EXP_ADDRESS;
    private String EXP_TOTAMT;
    private String EXP_ACTIVESTATE;

    private boolean isSynced;
    private String NextNumVal;

    private ArrayList<FDayExpDet> ExpnseDetList;

    public String getConsoleDB() {
        return ConsoleDB;
    }

    public void setConsoleDB(String consoleDB) {
        ConsoleDB = consoleDB;
    }

    public String getEXP_REFNO() {
        return EXP_REFNO;
    }

    public void setEXP_REFNO(String eXP_REFNO) {
        EXP_REFNO = eXP_REFNO;
    }

    public String getEXP_TXNDATE() {
        return EXP_TXNDATE;
    }

    public void setEXP_TXNDATE(String eXP_TXNDATE) {
        EXP_TXNDATE = eXP_TXNDATE;
    }

    public String getEXP_DEALCODE() {
        return EXP_DEALCODE;
    }

    public void setEXP_DEALCODE(String eXP_DEALCODE) {
        EXP_DEALCODE = eXP_DEALCODE;
    }

    public String getEXP_REPCODE() {
        return EXP_REPCODE;
    }

    public void setEXP_REPCODE(String eXP_REPCODE) {
        EXP_REPCODE = eXP_REPCODE;
    }

    public String getEXP_REPNAME() {
        return EXP_REPNAME;
    }

    public void setEXP_REPNAME(String eXP_REPNAME) {
        EXP_REPNAME = eXP_REPNAME;
    }

    public String getEXP_REMARK() {
        return EXP_REMARK;
    }

    public void setEXP_REMARK(String eXP_REMARK) {
        EXP_REMARK = eXP_REMARK;
    }

    public String getEXP_COSTCODE() {
        return EXP_COSTCODE;
    }

    public void setEXP_COSTCODE(String eXP_COSTCODE) {
        EXP_COSTCODE = eXP_COSTCODE;
    }

    public String getEXP_AREACODE() {
        return EXP_AREACODE;
    }

    public void setEXP_AREACODE(String eXP_AREACODE) {
        EXP_AREACODE = eXP_AREACODE;
    }

    public String getEXP_ADDUSER() {
        return EXP_ADDUSER;
    }

    public void setEXP_ADDUSER(String eXP_ADDUSER) {
        EXP_ADDUSER = eXP_ADDUSER;
    }

    public String getEXP_ADDDATE() {
        return EXP_ADDDATE;
    }

    public void setEXP_ADDDATE(String eXP_ADDDATE) {
        EXP_ADDDATE = eXP_ADDDATE;
    }

    public String getEXP_ADDMACH() {
        return EXP_ADDMACH;
    }

    public void setEXP_ADDMACH(String eXP_ADDMACH) {
        EXP_ADDMACH = eXP_ADDMACH;
    }

    public String getEXP_LONGITUDE() {
        return EXP_LONGITUDE;
    }

    public void setEXP_LONGITUDE(String eXP_LONGITUDE) {
        EXP_LONGITUDE = eXP_LONGITUDE;
    }

    public String getEXP_LATITUDE() {
        return EXP_LATITUDE;
    }

    public void setEXP_LATITUDE(String eXP_LATITUDE) {
        EXP_LATITUDE = eXP_LATITUDE;
    }

    public String getEXP_IS_SYNCED() {
        return EXP_IS_SYNCED;
    }

    public void setEXP_IS_SYNCED(String eXP_IS_SYNCED) {
        EXP_IS_SYNCED = eXP_IS_SYNCED;
    }

    public String getEXP_ADDRESS() {
        return EXP_ADDRESS;
    }

    public void setEXP_ADDRESS(String eXP_ADDRESS) {
        EXP_ADDRESS = eXP_ADDRESS;
    }

    public String getEXP_TOTAMT() {
        return EXP_TOTAMT;
    }

    public void setEXP_TOTAMT(String eXP_TOTAMT) {
        EXP_TOTAMT = eXP_TOTAMT;
    }

    public String getEXP_ACTIVESTATE() {
        return EXP_ACTIVESTATE;
    }

    public void setEXP_ACTIVESTATE(String eXP_ACTIVESTATE) {
        EXP_ACTIVESTATE = eXP_ACTIVESTATE;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean isSynced) {
        this.isSynced = isSynced;
    }

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    public ArrayList<FDayExpDet> getExpnseDetList() {
        return ExpnseDetList;
    }

    public void setExpnseDetList(ArrayList<FDayExpDet> expnseDetList) {
        ExpnseDetList = expnseDetList;
    }


}
