package com.lankahardwared.lankahw.model.mapper;

import com.lankahardwared.lankahw.model.Depodet;

import java.util.ArrayList;

/**
 * Created by Yasith on 3/5/2019.
 */

public class DepositMapper {

    public String ConsoleDB;
    public String DistDB;
    public String FBANKDEPOHED_REFNO;
    public String FBANKDEPOHED_TXNDATE;
    public String FBANKDEPOHED_PAYTYPE;
    public String FBANKDEPOHED_SLIPNO;
    public String FBANKDEPOHED_DEPOAMT;
    public String FBANKDEPOHED_REMARKS;
    public String FBANKDEPOHED_ADDDATE;
    public String FBANKDEPOHED_ADDUSER;
    public String FBANKDEPOHED_ADDMACH;
    public ArrayList<Depodet> fbankdepodetList;

    public String NextNumVal;

    private boolean IS_SYNCED;

    public boolean getIS_SYNCED() {
        return IS_SYNCED;
    }

    public void setIS_SYNCED(boolean IS_SYNCED) {
        this.IS_SYNCED = IS_SYNCED;
    }

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    public String getConsoleDB() {
        return ConsoleDB;
    }

    public void setConsoleDB(String consoleDB) {
        ConsoleDB = consoleDB;
    }

    public String getDistDB() {
        return DistDB;
    }

    public void setDistDB(String distDB) {
        DistDB = distDB;
    }

    public String getFBANKDEPOHED_REFNO() {
        return FBANKDEPOHED_REFNO;
    }

    public void setFBANKDEPOHED_REFNO(String FBANKDEPOHED_REFNO) {
        this.FBANKDEPOHED_REFNO = FBANKDEPOHED_REFNO;
    }

    public String getFBANKDEPOHED_TXNDATE() {
        return FBANKDEPOHED_TXNDATE;
    }

    public void setFBANKDEPOHED_TXNDATE(String FBANKDEPOHED_TXNDATE) {
        this.FBANKDEPOHED_TXNDATE = FBANKDEPOHED_TXNDATE;
    }

    public String getFBANKDEPOHED_PAYTYPE() {
        return FBANKDEPOHED_PAYTYPE;
    }

    public void setFBANKDEPOHED_PAYTYPE(String FBANKDEPOHED_PAYTYPE) {
        this.FBANKDEPOHED_PAYTYPE = FBANKDEPOHED_PAYTYPE;
    }

    public String getFBANKDEPOHED_SLIPNO() {
        return FBANKDEPOHED_SLIPNO;
    }

    public void setFBANKDEPOHED_SLIPNO(String FBANKDEPOHED_SLIPNO) {
        this.FBANKDEPOHED_SLIPNO = FBANKDEPOHED_SLIPNO;
    }

    public String getFBANKDEPOHED_DEPOAMT() {
        return FBANKDEPOHED_DEPOAMT;
    }

    public void setFBANKDEPOHED_DEPOAMT(String FBANKDEPOHED_DEPOAMT) {
        this.FBANKDEPOHED_DEPOAMT = FBANKDEPOHED_DEPOAMT;
    }

    public String getFBANKDEPOHED_REMARKS() {
        return FBANKDEPOHED_REMARKS;
    }

    public void setFBANKDEPOHED_REMARKS(String FBANKDEPOHED_REMARKS) {
        this.FBANKDEPOHED_REMARKS = FBANKDEPOHED_REMARKS;
    }

    public String getFBANKDEPOHED_ADDDATE() {
        return FBANKDEPOHED_ADDDATE;
    }

    public void setFBANKDEPOHED_ADDDATE(String FBANKDEPOHED_ADDDATE) {
        this.FBANKDEPOHED_ADDDATE = FBANKDEPOHED_ADDDATE;
    }

    public String getFBANKDEPOHED_ADDUSER() {
        return FBANKDEPOHED_ADDUSER;
    }

    public void setFBANKDEPOHED_ADDUSER(String FBANKDEPOHED_ADDUSER) {
        this.FBANKDEPOHED_ADDUSER = FBANKDEPOHED_ADDUSER;
    }

    public String getFBANKDEPOHED_ADDMACH() {
        return FBANKDEPOHED_ADDMACH;
    }

    public void setFBANKDEPOHED_ADDMACH(String FBANKDEPOHED_ADDMACH) {
        this.FBANKDEPOHED_ADDMACH = FBANKDEPOHED_ADDMACH;
    }

    public ArrayList<Depodet> getFbankdepodetList() {
        return fbankdepodetList;
    }

    public void setFbankdepodetList(ArrayList<Depodet> fbankdepodetList) {
        this.fbankdepodetList = fbankdepodetList;
    }
}
