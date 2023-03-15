package com.lankahardwared.lankahw.model.mapper;

import com.lankahardwared.lankahw.model.OrdFreeIssue;
import com.lankahardwared.lankahw.model.OrderDisc;
import com.lankahardwared.lankahw.model.TaxDT;
import com.lankahardwared.lankahw.model.TaxRG;
import com.lankahardwared.lankahw.model.TranSODet;

import java.util.ArrayList;

public class PreSalesMapper {

    private String ConsoleDB;
    private String DistDB;

    private String FTRANSOHED_ID;
    private String FTRANSOHED_REFNO;
    private String FTRANSOHED_REFNO1;
    private String FTRANSOHED_TXNDATE;
    private String FTRANSOHED_MANUREF;
    private String FTRANSOHED_COSTCODE;
    private String FTRANSOHED_REMARKS;
    private String FTRANSOHED_TXNTYPE;
    private String FTRANSOHED_TOTALAMT;
    private String FTRANSOHED_CURCODE;
    private String FTRANSOHED_CURRATE;
    private String FTRANSOHED_DEBCODE;
    private String FTRANSOHED_QUOTERM;
    private String FTRANSOHED_REPCODE;
    private String FTRANSOHED_BTOTALDIS;
    private String FTRANSOHED_TOTALDIS;
    private String FTRANSOHED_PTOTALDIS;
    private String FTRANSOHED_BPTOTALDIS;
    private String FTRANSOHED_BTOTALTAX;
    private String FTRANSOHED_TOTALTAX;
    private String FTRANSOHED_BTOTALAMT;
    private String FTRANSOHED_TAXREG;
    private String FTRANSOHED_CONTACT;
    private String FTRANSOHED_CUSADD1;
    private String FTRANSOHED_CUSADD2;
    private String FTRANSOHED_CUSADD3;
    private String FTRANSOHED_CUSTELE;
    private String FTRANSOHED_ADDUSER;
    private String FTRANSOHED_ADDDATE;
    private String FTRANSOHED_ADDMACH;
    private String FTRANSOHED_RECORDID;
    private String FTRANSOHED_TXNDELDATE;
    private String FTRANSOHED_LONGITUDE;
    private String FTRANSOHED_LATITUDE;
    private String FTRANSOHED_IS_SYNCED;
    private String FTRANSOHED_IS_ACTIVE;
    private String FTRANSOHED_START_TIMESO;
    private String FTRANSOHED_END_TIMESO;
    private String FTRANSOHED_ADDRESS;
    private String FTRANSOHED_LOCCODE;
    private String FTRANSOHED_AREACODE;
    private String FTRANSOHED_ROUTECODE;
    private String FTRANSOHED_TOURCODE;


    private boolean Synced;
    private String NextNumVal;

    private ArrayList<TranSODet> ordDet;
    private ArrayList<OrderDisc> ordDisc;
    private ArrayList<OrdFreeIssue> freeIssues;
    private ArrayList<TaxDT> taxDTs;
    private ArrayList<TaxRG> taxRGs;

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

    public String getFTRANSOHED_ID() {
        return FTRANSOHED_ID;
    }

    public void setFTRANSOHED_ID(String fTRANSOHED_ID) {
        FTRANSOHED_ID = fTRANSOHED_ID;
    }

    public String getFTRANSOHED_REFNO() {
        return FTRANSOHED_REFNO;
    }

    public void setFTRANSOHED_REFNO(String fTRANSOHED_REFNO) {
        FTRANSOHED_REFNO = fTRANSOHED_REFNO;
    }

    public String getFTRANSOHED_REFNO1() {
        return FTRANSOHED_REFNO1;
    }

    public void setFTRANSOHED_REFNO1(String fTRANSOHED_REFNO1) {
        FTRANSOHED_REFNO1 = fTRANSOHED_REFNO1;
    }

    public String getFTRANSOHED_TXNDATE() {
        return FTRANSOHED_TXNDATE;
    }

    public void setFTRANSOHED_TXNDATE(String fTRANSOHED_TXNDATE) {
        FTRANSOHED_TXNDATE = fTRANSOHED_TXNDATE;
    }

    public String getFTRANSOHED_MANUREF() {
        return FTRANSOHED_MANUREF;
    }

    public void setFTRANSOHED_MANUREF(String fTRANSOHED_MANUREF) {
        FTRANSOHED_MANUREF = fTRANSOHED_MANUREF;
    }

    public String getFTRANSOHED_COSTCODE() {
        return FTRANSOHED_COSTCODE;
    }

    public void setFTRANSOHED_COSTCODE(String fTRANSOHED_COSTCODE) {
        FTRANSOHED_COSTCODE = fTRANSOHED_COSTCODE;
    }

    public String getFTRANSOHED_REMARKS() {
        return FTRANSOHED_REMARKS;
    }

    public void setFTRANSOHED_REMARKS(String fTRANSOHED_REMARKS) {
        FTRANSOHED_REMARKS = fTRANSOHED_REMARKS;
    }

    public String getFTRANSOHED_TXNTYPE() {
        return FTRANSOHED_TXNTYPE;
    }

    public void setFTRANSOHED_TXNTYPE(String fTRANSOHED_TXNTYPE) {
        FTRANSOHED_TXNTYPE = fTRANSOHED_TXNTYPE;
    }

    public String getFTRANSOHED_TOTALAMT() {
        return FTRANSOHED_TOTALAMT;
    }

    public void setFTRANSOHED_TOTALAMT(String fTRANSOHED_TOTALAMT) {
        FTRANSOHED_TOTALAMT = fTRANSOHED_TOTALAMT;
    }

    public String getFTRANSOHED_CURCODE() {
        return FTRANSOHED_CURCODE;
    }

    public void setFTRANSOHED_CURCODE(String fTRANSOHED_CURCODE) {
        FTRANSOHED_CURCODE = fTRANSOHED_CURCODE;
    }

    public String getFTRANSOHED_CURRATE() {
        return FTRANSOHED_CURRATE;
    }

    public void setFTRANSOHED_CURRATE(String fTRANSOHED_CURRATE) {
        FTRANSOHED_CURRATE = fTRANSOHED_CURRATE;
    }

    public String getFTRANSOHED_DEBCODE() {
        return FTRANSOHED_DEBCODE;
    }

    public void setFTRANSOHED_DEBCODE(String fTRANSOHED_DEBCODE) {
        FTRANSOHED_DEBCODE = fTRANSOHED_DEBCODE;
    }

    public String getFTRANSOHED_QUOTERM() {
        return FTRANSOHED_QUOTERM;
    }

    public void setFTRANSOHED_QUOTERM(String fTRANSOHED_QUOTERM) {
        FTRANSOHED_QUOTERM = fTRANSOHED_QUOTERM;
    }

    public String getFTRANSOHED_REPCODE() {
        return FTRANSOHED_REPCODE;
    }

    public void setFTRANSOHED_REPCODE(String fTRANSOHED_REPCODE) {
        FTRANSOHED_REPCODE = fTRANSOHED_REPCODE;
    }

    public String getFTRANSOHED_BTOTALDIS() {
        return FTRANSOHED_BTOTALDIS;
    }

    public void setFTRANSOHED_BTOTALDIS(String fTRANSOHED_BTOTALDIS) {
        FTRANSOHED_BTOTALDIS = fTRANSOHED_BTOTALDIS;
    }

    public String getFTRANSOHED_TOTALDIS() {
        return FTRANSOHED_TOTALDIS;
    }

    public void setFTRANSOHED_TOTALDIS(String fTRANSOHED_TOTALDIS) {
        FTRANSOHED_TOTALDIS = fTRANSOHED_TOTALDIS;
    }

    public String getFTRANSOHED_PTOTALDIS() {
        return FTRANSOHED_PTOTALDIS;
    }

    public void setFTRANSOHED_PTOTALDIS(String fTRANSOHED_PTOTALDIS) {
        FTRANSOHED_PTOTALDIS = fTRANSOHED_PTOTALDIS;
    }

    public String getFTRANSOHED_BPTOTALDIS() {
        return FTRANSOHED_BPTOTALDIS;
    }

    public void setFTRANSOHED_BPTOTALDIS(String fTRANSOHED_BPTOTALDIS) {
        FTRANSOHED_BPTOTALDIS = fTRANSOHED_BPTOTALDIS;
    }

    public String getFTRANSOHED_BTOTALTAX() {
        return FTRANSOHED_BTOTALTAX;
    }

    public void setFTRANSOHED_BTOTALTAX(String fTRANSOHED_BTOTALTAX) {
        FTRANSOHED_BTOTALTAX = fTRANSOHED_BTOTALTAX;
    }

    public String getFTRANSOHED_TOTALTAX() {
        return FTRANSOHED_TOTALTAX;
    }

    public void setFTRANSOHED_TOTALTAX(String fTRANSOHED_TOTALTAX) {
        FTRANSOHED_TOTALTAX = fTRANSOHED_TOTALTAX;
    }

    public String getFTRANSOHED_BTOTALAMT() {
        return FTRANSOHED_BTOTALAMT;
    }

    public void setFTRANSOHED_BTOTALAMT(String fTRANSOHED_BTOTALAMT) {
        FTRANSOHED_BTOTALAMT = fTRANSOHED_BTOTALAMT;
    }

    public String getFTRANSOHED_TAXREG() {
        return FTRANSOHED_TAXREG;
    }

    public void setFTRANSOHED_TAXREG(String fTRANSOHED_TAXREG) {
        FTRANSOHED_TAXREG = fTRANSOHED_TAXREG;
    }

    public String getFTRANSOHED_CONTACT() {
        return FTRANSOHED_CONTACT;
    }

    public void setFTRANSOHED_CONTACT(String fTRANSOHED_CONTACT) {
        FTRANSOHED_CONTACT = fTRANSOHED_CONTACT;
    }

    public String getFTRANSOHED_CUSADD1() {
        return FTRANSOHED_CUSADD1;
    }

    public void setFTRANSOHED_CUSADD1(String fTRANSOHED_CUSADD1) {
        FTRANSOHED_CUSADD1 = fTRANSOHED_CUSADD1;
    }

    public String getFTRANSOHED_CUSADD2() {
        return FTRANSOHED_CUSADD2;
    }

    public void setFTRANSOHED_CUSADD2(String fTRANSOHED_CUSADD2) {
        FTRANSOHED_CUSADD2 = fTRANSOHED_CUSADD2;
    }

    public String getFTRANSOHED_CUSADD3() {
        return FTRANSOHED_CUSADD3;
    }

    public void setFTRANSOHED_CUSADD3(String fTRANSOHED_CUSADD3) {
        FTRANSOHED_CUSADD3 = fTRANSOHED_CUSADD3;
    }

    public String getFTRANSOHED_CUSTELE() {
        return FTRANSOHED_CUSTELE;
    }

    public void setFTRANSOHED_CUSTELE(String fTRANSOHED_CUSTELE) {
        FTRANSOHED_CUSTELE = fTRANSOHED_CUSTELE;
    }

    public String getFTRANSOHED_ADDUSER() {
        return FTRANSOHED_ADDUSER;
    }

    public void setFTRANSOHED_ADDUSER(String fTRANSOHED_ADDUSER) {
        FTRANSOHED_ADDUSER = fTRANSOHED_ADDUSER;
    }

    public String getFTRANSOHED_ADDDATE() {
        return FTRANSOHED_ADDDATE;
    }

    public void setFTRANSOHED_ADDDATE(String fTRANSOHED_ADDDATE) {
        FTRANSOHED_ADDDATE = fTRANSOHED_ADDDATE;
    }

    public String getFTRANSOHED_ADDMACH() {
        return FTRANSOHED_ADDMACH;
    }

    public void setFTRANSOHED_ADDMACH(String fTRANSOHED_ADDMACH) {
        FTRANSOHED_ADDMACH = fTRANSOHED_ADDMACH;
    }

    public String getFTRANSOHED_RECORDID() {
        return FTRANSOHED_RECORDID;
    }

    public void setFTRANSOHED_RECORDID(String fTRANSOHED_RECORDID) {
        FTRANSOHED_RECORDID = fTRANSOHED_RECORDID;
    }

    public String getFTRANSOHED_TXNDELDATE() {
        return FTRANSOHED_TXNDELDATE;
    }

    public void setFTRANSOHED_TXNDELDATE(String fTRANSOHED_TXNDELDATE) {
        FTRANSOHED_TXNDELDATE = fTRANSOHED_TXNDELDATE;
    }

    public String getFTRANSOHED_LONGITUDE() {
        return FTRANSOHED_LONGITUDE;
    }

    public void setFTRANSOHED_LONGITUDE(String fTRANSOHED_LONGITUDE) {
        FTRANSOHED_LONGITUDE = fTRANSOHED_LONGITUDE;
    }

    public String getFTRANSOHED_LATITUDE() {
        return FTRANSOHED_LATITUDE;
    }

    public void setFTRANSOHED_LATITUDE(String fTRANSOHED_LATITUDE) {
        FTRANSOHED_LATITUDE = fTRANSOHED_LATITUDE;
    }

    public ArrayList<TranSODet> getOrdDet() {
        return ordDet;
    }

    public void setOrdDet(ArrayList<TranSODet> ordDet) {
        this.ordDet = ordDet;
    }

    public String getNextNumVal() {
        return NextNumVal;
    }

    public void setNextNumVal(String nextNumVal) {
        NextNumVal = nextNumVal;
    }

    public boolean isSynced() {
        return Synced;
    }

    public void setSynced(boolean synced) {
        Synced = synced;
    }

    public String getFTRANSOHED_IS_SYNCED() {
        return FTRANSOHED_IS_SYNCED;
    }

    public void setFTRANSOHED_IS_SYNCED(String fTRANSOHED_IS_SYNCED) {
        FTRANSOHED_IS_SYNCED = fTRANSOHED_IS_SYNCED;
    }

    public String getFTRANSOHED_IS_ACTIVE() {
        return FTRANSOHED_IS_ACTIVE;
    }

    public void setFTRANSOHED_IS_ACTIVE(String fTRANSOHED_IS_ACTIVE) {
        FTRANSOHED_IS_ACTIVE = fTRANSOHED_IS_ACTIVE;
    }

    public String getFTRANSOHED_START_TIMESO() {
        return FTRANSOHED_START_TIMESO;
    }

    public void setFTRANSOHED_START_TIMESO(String fTRANSOHED_START_TIMESO) {
        FTRANSOHED_START_TIMESO = fTRANSOHED_START_TIMESO;
    }

    public String getFTRANSOHED_END_TIMESO() {
        return FTRANSOHED_END_TIMESO;
    }

    public void setFTRANSOHED_END_TIMESO(String fTRANSOHED_END_TIMESO) {
        FTRANSOHED_END_TIMESO = fTRANSOHED_END_TIMESO;
    }

    public String getFTRANSOHED_ADDRESS() {
        return FTRANSOHED_ADDRESS;
    }

    public void setFTRANSOHED_ADDRESS(String fTRANSOHED_ADDRESS) {
        FTRANSOHED_ADDRESS = fTRANSOHED_ADDRESS;
    }

    public ArrayList<OrderDisc> getOrdDisc() {
        return ordDisc;
    }

    public void setOrdDisc(ArrayList<OrderDisc> ordDisc) {
        this.ordDisc = ordDisc;
    }

    public ArrayList<OrdFreeIssue> getFreeIssues() {
        return freeIssues;
    }

    public void setFreeIssues(ArrayList<OrdFreeIssue> freeIssues) {
        this.freeIssues = freeIssues;
    }

    public ArrayList<TaxDT> getTaxDTs() {
        return taxDTs;
    }

    public void setTaxDTs(ArrayList<TaxDT> taxDTs) {
        this.taxDTs = taxDTs;
    }

    public ArrayList<TaxRG> getTaxRGs() {
        return taxRGs;
    }

    public void setTaxRGs(ArrayList<TaxRG> taxRGs) {
        this.taxRGs = taxRGs;
    }

    public String getFTRANSOHED_LOCCODE() {
        return FTRANSOHED_LOCCODE;
    }

    public void setFTRANSOHED_LOCCODE(String fTRANSOHED_LOCCODE) {
        FTRANSOHED_LOCCODE = fTRANSOHED_LOCCODE;
    }

    public String getFTRANSOHED_AREACODE() {
        return FTRANSOHED_AREACODE;
    }

    public void setFTRANSOHED_AREACODE(String fTRANSOHED_AREACODE) {
        FTRANSOHED_AREACODE = fTRANSOHED_AREACODE;
    }

    public String getFTRANSOHED_ROUTECODE() {
        return FTRANSOHED_ROUTECODE;
    }

    public void setFTRANSOHED_ROUTECODE(String fTRANSOHED_ROUTECODE) {
        FTRANSOHED_ROUTECODE = fTRANSOHED_ROUTECODE;
    }

    public String getFTRANSOHED_TOURCODE() {
        return FTRANSOHED_TOURCODE;
    }

    public void setFTRANSOHED_TOURCODE(String fTRANSOHED_TOURCODE) {
        FTRANSOHED_TOURCODE = fTRANSOHED_TOURCODE;
    }

}