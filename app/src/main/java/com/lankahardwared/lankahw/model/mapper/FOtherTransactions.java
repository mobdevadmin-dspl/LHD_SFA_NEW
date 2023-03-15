package com.lankahardwared.lankahw.model.mapper;

public class FOtherTransactions {

    private String amount;
    private String debCode;
    private String refno;
    private String refNo1;
    private String txnDate;
    private String txnType;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDebCode() {
        return debCode;
    }

    public void setDebCode(String debCode) {
        this.debCode = debCode;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getRefNo1() {
        return refNo1;
    }

    public void setRefNo1(String refNo1) {
        this.refNo1 = refNo1;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    @Override
    public String toString() {
        return "FOtherTransactions{" +
                "amount='" + amount + '\'' +
                ", debCode='" + debCode + '\'' +
                ", refno='" + refno + '\'' +
                ", refNo1='" + refNo1 + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", txnType='" + txnType + '\'' +
                '}';
    }
}
