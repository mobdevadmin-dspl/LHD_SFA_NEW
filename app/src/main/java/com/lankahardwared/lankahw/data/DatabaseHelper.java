package com.lankahardwared.lankahw.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lankahardwared.lankahw.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Menaka Comment
    public static final int DATABASE_VERSION = 1;
    /**
     * ############################ server table Details
     * #################################
     */
    // table
    public static final String TABLE_SERVER_DB = "serverdb";
    // table attributes
    public static final String SERVER_DB_ID = "server_db_id";
    public static final String SERVER_DB_NAME = "server_db_name";
    /**
     * ############################ fDebtor table Details
     * ################################
     */

    // table
    public static final String TABLE_FDEBTOR = "fDebtor";
    // table attributes
    public static final String FDEBTOR_ID = "id";
    public static final String FDEBTOR_CODE = "DebCode";
    public static final String FDEBTOR_NAME = "DebName";
    public static final String FDEBTOR_ADD1 = "DebAdd1";
    public static final String FDEBTOR_ADD2 = "DebAdd2";
    public static final String FDEBTOR_ADD3 = "DebAdd3";
    public static final String FDEBTOR_TELE = "DebTele";
    public static final String FDEBTOR_MOB = "DebMob";
    public static final String FDEBTOR_EMAIL = "DebEMail";
    public static final String FDEBTOR_CREATEDATE = "CretDate";
    public static final String FDEBTOR_REM_DIS = "RemDis";
    public static final String FDEBTOR_TOWN_CODE = "TownCode";
    public static final String FDEBTOR_AREA_CODE = "AreaCode";
    public static final String FDEBTOR_DEB_CAT_CODE = "DebCatCode";
    public static final String FDEBTOR_DBGR_CODE = "DbGrCode";
    public static final String FDEBTOR_DEB_CLS_CODE = "DebClsCode";
    public static final String FDEBTOR_STATUS = "Status";
    public static final String FDEBTOR_LYLTY = "DebLylty";
    public static final String FDEBTOR_DEAL_CODE = "DealCode";
    public static final String FDEBTOR_ADD_USER = "AddUser";
    public static final String FDEBTOR_ADD_DATE_DEB = "AddDateDEB";
    public static final String FDEBTOR_ADD_MACH = "AddMach";
    public static final String FDEBTOR_RECORD_ID = "RecordId";
    public static final String FDEBTOR_TIME_STAMP = "timestamp_column";
    public static final String FDEBTOR_CRD_PERIOD = "CrdPeriod";
    public static final String FDEBTOR_CHK_CRD_PRD = "ChkCrdPrd";
    public static final String FDEBTOR_CRD_LIMIT = "CrdLimit";
    public static final String FDEBTOR_CHK_CRD_LIMIT = "ChkCrdLmt";
    public static final String FDEBTOR_REP_CODE = "RepCode";
    public static final String FDEBTOR_RANK_CODE = "RankCode";
    public static final String FDEBTOR_TRAN_DATE = "txndate";
    public static final String FDEBTOR_TRAN_BATCH = "TranBatch";
    public static final String FDEBTOR_SUMMARY = "DebSumary";
    public static final String FDEBTOR_OUT_DIS = "OutDis";
    public static final String FDEBTOR_DEB_FAX = "DebFax";
    public static final String FDEBTOR_DEB_WEB = "DebWeb";
    public static final String FDEBTOR_DEBCT_NAM = "DebCTNam";
    public static final String FDEBTOR_DEBCT_ADD1 = "DebCTAdd1";
    public static final String FDEBTOR_DEBCT_ADD2 = "DebCTAdd2";
    public static final String FDEBTOR_DEBCT_ADD3 = "DebCTAdd3";
    public static final String FDEBTOR_DEBCT_TELE = "DebCTTele";
    public static final String FDEBTOR_DEBCT_FAX = "DebCTFax";
    public static final String FDEBTOR_DEBCT_EMAIL = "DebCTEmail";
    public static final String FDEBTOR_DEL_PERSN = "DelPersn";
    public static final String FDEBTOR_DEL_ADD1 = "DelAdd1";
    public static final String FDEBTOR_DEL_ADD2 = "DelAdd2";
    public static final String FDEBTOR_DEL_ADD3 = "DelAdd3";
    public static final String FDEBTOR_DEL_TELE = "DelTele";
    public static final String FDEBTOR_DEL_FAX = "DelFax";
    public static final String FDEBTOR_DEL_EMAIL = "DelEmail";
    public static final String FDEBTOR_DATE_OFB = "DateOfB";
    public static final String FDEBTOR_TAX_REG = "TaxReg";
    public static final String FDEBTOR_CUSDISPER = "CusDIsPer";
    public static final String FDEBTOR_PRILLCODE = "PrillCode";
    public static final String FDEBTOR_CUSDISSTAT = "CusDisStat";
    public static final String FDEBTOR_BUS_RGNO = "BusRgNo";
    public static final String FDEBTOR_POSTCODE = "PostCode";
    public static final String FDEBTOR_GEN_REMARKS = "GenRemarks";
    public static final String FDEBTOR_BRANCODE = "BranCode";
    public static final String FDEBTOR_BANK = "Bank";
    public static final String FDEBTOR_BRANCH = "Branch";
    public static final String FDEBTOR_ACCTNO = "AcctNo";
    public static final String FDEBTOR_CUS_VATNO = "CusVatNo";
    public static final String FDEBTOR_LATITUDE = "Latitude";
    public static final String FDEBTOR_LONGITUDE = "Longitude";
    public static final String FDEBTOR_NIC = "NIC";
    public static final String FDEBTOR_BIS_REG = "BisRegNo";
    public static final String FDEBTOR_IS_SYNC = "IsSync";
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-FSIZE TABLE-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String TABLE_FSIZE = "fSize";
    public static final String FSIZE_ID = "_id";
    public static final String FSIZE_CODE = "SizeCode";
    public static final String FSIZE_DESC = "SizeDesc";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Ebony modifications-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String TABLE_FGRCOMBDET = "fGrCombDet";
    public static final String FGRCOMBDET_ID = "_id";
    public static final String FGRCOMBDET_SIZECODE = "SizeCode";
    public static final String FGRCOMBDET_GROUPCODE = "GroupCode";
    public static final String TABLE_FTERMS = "fTerms";

    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FGRCOMBDET-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FTERMS_ID = "_id";
    public static final String FTERMS_CODE = "TermCode";
    public static final String FTERMS_DES = "TermDes";
    public static final String FTERMS_DISPER = "TermDisPer";
    public static final String TABLE_FSIZECOMB = "fSizeComb";

    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FTERMS-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FSIZECOMB_ID = "_Id";
    public static final String FSIZECOMB_ITEMCODE = "ItemCode";
    public static final String FSIZECOMB_SIZECODE = "SizeCode";
    public static final String FSIZECOMB_GROUPCODE = "GroupCode";
    public static final String FSIZECOMB_PRICE = "Price";
    public static final String FSIZECOMB_DEG_PRICE = "DPrice";

    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FSIZECOMB-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FSIZECOMB_OBS_PRICE = "ObsPrice";
    public static final String TABLE_FCRCOMB = "fCrComb";
    public static final String FCRCOMB_ID = "_Id";
    public static final String FCRCOMB_CODE = "Code";
    public static final String FCRCOMB_DEBCODE = "DebCode";
    public static final String FCRCOMB_REPCODE = "RepCode";
    public static final String FCRCOMB_GROUPCODE = "GroupCode";
    public static final String FCRCOMB_TERMCODE = "TermCode";
    public static final String FCRCOMB_CRDLIMIT = "CrdLimit";
    public static final String FCRCOMB_CHKCRDLMT = "ChkCrdLmt";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-TABLE_FCRCOMB*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FCRCOMB_CHKCRDPRD = "ChkCrdPrd";
    public static final String FCRCOMB_CRD_PERIOD = "CrdPeriod";
    public static final String FCRCOMB_COM_DIS = "ComDis";
    public static final String TABLE_FTRANSOHED = "FTranSOHed";
    public static final String FTRANSOHED_ID = "Id";
    public static final String FTRANSOHED_REFNO = "RefNo";
    public static final String FTRANSOHED_REFNO1 = "RefNo1";
    public static final String FTRANSOHED_TXNDATE = "TxnDate";
    public static final String FTRANSOHED_MANUREF = "ManuRef";
    public static final String FTRANSOHED_COSTCODE = "CostCode";
    public static final String FTRANSOHED_REMARKS = "Remarks";
    public static final String FTRANSOHED_TXNTYPE = "TxnType";
    public static final String FTRANSOHED_TOTALAMT = "TotalAmt";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*FTranSOHed-*-*-*-*-*-*-*-*-*-*-*-*-*--**--*/
    public static final String FTRANSOHED_CURCODE = "CurCode";
    public static final String FTRANSOHED_CURRATE = "CurRate";
    public static final String FTRANSOHED_DEBCODE = "DebCode";
    public static final String FTRANSOHED_REPCODE = "RepCode";
    public static final String FTRANSOHED_BTOTALDIS = "BTotalDis";
    public static final String FTRANSOHED_TOTALDIS = "TotalDis";
    public static final String FTRANSOHED_PTOTALDIS = "PTotalDis";
    public static final String FTRANSOHED_BPTOTALDIS = "BPTotalDis";
    public static final String FTRANSOHED_BTOTALTAX = "BTotalTax";
    public static final String FTRANSOHED_TOTALTAX = "TotalTax";
    public static final String FTRANSOHED_BTOTALAMT = "BTotalAmt";
    public static final String FTRANSOHED_TAXREG = "TaxReg";
    public static final String FTRANSOHED_CONTACT = "Contact";
    public static final String FTRANSOHED_CUSADD1 = "CusAdd1";
    public static final String FTRANSOHED_CUSADD2 = "CusAdd2";
    public static final String FTRANSOHED_CUSADD3 = "CusAdd3";
    public static final String FTRANSOHED_CUSTELE = "CusTele";
    public static final String FTRANSOHED_ADDMACH = "AddMach";
    public static final String FTRANSOHED_TXNDELDATE = "TxnDelDate";
    public static final String FTRANSOHED_TOTQTY = "TotQty";
    public static final String FTRANSOHED_TOTFREE = "TotFree";
    public static final String FTRANSOHED_IS_SYNCED = "IsSynced";
    public static final String FTRANSOHED_IS_ACTIVE = "IsActive";
    public static final String FTRANSOHED_LONGITUDE = "Longitude";
    public static final String FTRANSOHED_LATITUDE = "Latitude";
    public static final String FTRANSOHED_START_TIMESO = "StartTime";
    public static final String FTRANSOHED_END_TIMESO = "EndTime";
    public static final String FTRANSOHED_ADDRESS = "Address";
    public static final String FTRANSOHED_TOURCODE = "TourCode";
    public static final String FTRANSOHED_LOCCODE = "LocCode";
    public static final String FTRANSOHED_AREACODE = "AreaCode";
    public static final String FTRANSOHED_ROUTECODE = "RouteCode";

    // ------------------------------------------ Nuwan --------------------------- 05/04/2019 -------------------------------------

    public static final String TABLE_FAPPRORDHED = "FApprOrdHed";
    public static final String FAPPRORDHED_ID = "Id";
    public static final String FAPPRORDHED_REFNO = "RefNo";
    public static final String FAPPRORDHED_TOTALAMT = "TotalAmt";
    public static final String FAPPRORDHED_DEBCODE = "DebCode";
    // ---------------------------------------------------------------------------------------------------------------------------------


    public static final String TABLE_FTRANSODET = "FTranSODet";
    public static final String FTRANSODET_ID = "id";
    public static final String FTRANSODET_REFNO = "RefNo";
    public static final String FTRANSODET_TXNDATE = "TxnDate";
    public static final String FTRANSODET_LOCCODE = "LocCode";
    public static final String FTRANSODET_TXNTYPE = "TxnType";
    public static final String FTRANSODET_SEQNO = "SeqNo";
    public static final String FTRANSODET_ITEMCODE = "ItemCode";
    public static final String FTRANSODET_QTY = "Qty";
    public static final String FTRANSODET_COSTPRICE = "CostPrice";
    public static final String FTRANSODET_AMT = "Amt";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*FTranSODet-*-*-*-*-*-*-*-*-*-*-*-*-*--**--*/
    public static final String FTRANSODET_BALQTY = "BalQty";
    public static final String FTRANSODET_TAXCOMCODE = "TaxComCode";
    public static final String FTRANSODET_BSELLPRICE = "BSellPrice";
    public static final String FTRANSODET_SELLPRICE = "SellPrice";
    public static final String FTRANSODET_BTSELLPRICE = "BTSellPrice";
    public static final String FTRANSODET_TSELLPRICE = "TSellPrice";
    public static final String FTRANSODET_PRILCODE = "PrillCode";
    public static final String FTRANSODET_BDISAMT = "BDisAmt";
    public static final String FTRANSODET_DISAMT = "DisAmt";
    public static final String FTRANSODET_PDISAMT = "PDisAmt";
    public static final String FTRANSODET_BPDISAMT = "BPDisAmt";
    public static final String FTRANSODET_BTAXAMT = "BTaxAmt";
    public static final String FTRANSODET_TAXAMT = "TaxAmt";
    public static final String FTRANSODET_BAMT = "BAmt";
    public static final String FTRANSODET_PICE_QTY = "PieceQty";
    public static final String FTRANSODET_IS_ACTIVE = "IsActive";
    public static final String FTRANSODET_IS_SYNCED = "IsSynced";
    public static final String FTRANSODET_QOH = "Qoh";
    public static final String FTRANSODET_TYPE = "Type";
    public static final String FTRANSODET_DISVALAMT = "DisValAmt";
    public static final String FTRANSODET_COMP_DISPER = "CompDisPer";
    public static final String FTRANSODET_BRAND_DISPER = "BrandDisPer";
    public static final String FTRANSODET_BRAND_DIS = "BrandDis";
    public static final String FTRANSODET_COMP_DIS = "CompDis";
    public static final String FTRANSODET_SCH_DISPER = "SchDisPer";
    public static final String FTRANSODET_PRICE = "price";
    public static final String FTRANSODET_DISCTYPE = "DiscType";
    public static final String FTRANSODET_QTY_SLAB_DISC = "SlabDisc";
    public static final String FTRANSODET_ORG_PRICE = "OrgPrice";
    public static final String FTRANSODET_DISFLAG = "DisFlag";

    public static final String TABLE_PRETAXRG = "fPreTaxRg";
    public static final String PRETAXRG_ID = "Id";
    public static final String PRETAXRG_REFNO = "RefNo";
    public static final String PRETAXRG_TAXCODE = "TaxCode";
    public static final String PRETAXRG_RGNO = "RGNo";

    // ------------------------------- Nuwan ------------ SalesReturnTaxRg -------------------------------------

    public static final String TABLE_INVRTAXRG = "fInvRTaxRg";
    public static final String INVRTAXRG_ID = "Id";
    public static final String INVRTAXRG_REFNO = "RefNo";
    public static final String INVRTAXRG_TAXCODE = "TaxCode";
    public static final String INVRTAXRG_RGNO = "RGNo";

    public static final String CREATE_FINVRTAXRG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVRTAXRG + " (" + INVRTAXRG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INVRTAXRG_REFNO + " TEXT, " + INVRTAXRG_TAXCODE + " TEXT, " + INVRTAXRG_RGNO + " TEXT ); ";

    // ------------------------------------------------ SalesReturnTaxDt -------------------------------------

    public static final String TABLE_INVRTAXDT = "fInvRTaxDT";
    public static final String INVRTAXDT_ID = "Id";
    public static final String INVRTAXDT_REFNO = "RefNo";
    public static final String INVRTAXDT_ITEMCODE = "ItemCode";
    public static final String INVRTAXDT_TAXCOMCODE = "TaxComCode";
    public static final String INVRTAXDT_TAXCODE = "TaxCode";
    public static final String INVRTAXDT_TAXPER = "TaxPer";
    public static final String INVRTAXDT_RATE = "TaxRate";
    public static final String INVRTAXDT_SEQ = "TaxSeq";
    public static final String INVRTAXDT_DETAMT = "TaxDetAmt";
    public static final String INVRTAXDT_BDETAMT = "BTaxDetAmt";
    public static final String INVRTAXDT_TAXTYPE = "TaxType";

    public static final String CREATE_FINVRTAXDT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVRTAXDT + " (" + INVRTAXDT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INVRTAXDT_REFNO + " TEXT, " + INVRTAXDT_ITEMCODE + " TEXT, " + INVRTAXDT_TAXCOMCODE + " TEXT, " + INVRTAXDT_TAXCODE + " TEXT, " + INVRTAXDT_TAXPER + " TEXT, " + INVRTAXDT_RATE + " TEXT, " + INVRTAXDT_SEQ + " TEXT, " + INVRTAXDT_DETAMT + " TEXT, " + INVRTAXDT_TAXTYPE + " TEXT, " + INVRTAXDT_BDETAMT + " TEXT ); ";

    // ------------------------------------------------ Receipt Paymode -------------------------------------

    public static final String TABLE_FPAYMODE = "fPayMode";
    public static final String FPAYMODE_PAID_ID = "Id";
    public static final String FPAYMODE_PAID_REF_NO = "RefNo";
    public static final String FPAYMODE_PAID_TYPE = "PayType";
    public static final String FPAYMODE_PAID_DATE = "PayDate";
    public static final String FPAYMODE_PAID_BANK = "PayBank";
    public static final String FPAYMODE_PAID_CREDIT_CARD_TYPE = "CreditCardType";
    public static final String FPAYMODE_PAID_CHEQUE_DATE = "ChequeDate";
    public static final String FPAYMODE_PAID_CARD_EXP_DATE = "CreditCardExpDate";
    public static final String FPAYMODE_PAID_AMOUNT = "PayAmount";
    public static final String FPAYMODE_PAID_CHEQUE_NO = "ChequeNo";
    public static final String FPAYMODE_PAID_CREDIT_CARD_NO = "CreditCardNo";
    public static final String FPAYMODE_PAID_SLIP_NO = "SlipNo";
    public static final String FPAYMODE_PAID_DRAFT_NO = "DraftNo";
    public static final String FPAYMODE_PAID_ALLOAMT = "AlloAmt";
    public static final String FPAYMODE_PAID_REMAMT = "RemAmt";

    public static final String CREATE_TABLE_FPAYMODE = "CREATE TABLE IF NOT EXISTS " + TABLE_FPAYMODE + " (" + FPAYMODE_PAID_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FPAYMODE_PAID_REF_NO + " TEXT, "+ FPAYMODE_PAID_TYPE + " TEXT, " + FPAYMODE_PAID_DATE + " TEXT, " + FPAYMODE_PAID_BANK + " TEXT, " + FPAYMODE_PAID_CREDIT_CARD_TYPE + " TEXT, " + FPAYMODE_PAID_CHEQUE_DATE + " TEXT, " + FPAYMODE_PAID_CARD_EXP_DATE + " TEXT, " + FPAYMODE_PAID_AMOUNT + " TEXT, " + FPAYMODE_PAID_CHEQUE_NO + " TEXT, " + FPAYMODE_PAID_CREDIT_CARD_NO + " TEXT, " + FPAYMODE_PAID_SLIP_NO + " TEXT, " + FPAYMODE_PAID_ALLOAMT + " TEXT, " + FPAYMODE_PAID_REMAMT + " TEXT, " + FPAYMODE_PAID_DRAFT_NO + " TEXT ); ";


    // --------------------------------------------------- Receipt Payment Allocate -----------------------------------

    public static final String TABLE_FPAYMENT_ALLOCATE = "fPaymentAllocate";
    public static final String FPAYMENT_ALLOCATE_ID = "Id";
    public static final String FPAYMENT_ALLOCATE_REFNO = "AllocRefNo";
    public static final String FPAYMENT_ALLOCATE_COMMON_REFNO = "AllocComRefNo";
    public static final String FPAYMENT_ALLOCATE_FDD_REFNO = "AllocFddRefNo";
    public static final String FPAYMENT_ALLOCATE_FDD_TXN_DATE = "AllocTxnDate";
    public static final String FPAYMENT_ALLOCATE_FDD_PAID_AMT = "AllocFddPaidAmt";
    public static final String FPAYMENT_ALLOCATE_FDD_TOTAL_BAL = "AllocFddTotalBal";
    public static final String FPAYMENT_ALLOCATE_PAY_REF_NO = "AllocPayRefNo";
    public static final String FPAYMENT_ALLOCATE_PAY_MODE = "AllocPayMode";
    public static final String FPAYMENT_ALLOCATE_PAY_DATE = "AllocPayDate";
    public static final String FPAYMENT_ALLOCATE_PAY_AMT = "AllocPayAmt";
    public static final String FPAYMENT_ALLOCATE_PAY_REM_AMT = "AllocPayRemAmt";
    public static final String FPAYMENT_ALLOCATE_PAY_ALLO_AMT = "AllocPayAllocAmt";
    public static final String FPAYMENT_ALLOCATE_PAY_BANK = "AllocPayBank";
    public static final String FPAYMENT_ALLOCATE_PAY_CHEQUE_NO = "AllocPayCheqNo";
    public static final String FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO = "AllocPayCardNo";
    public static final String FPAYMENT_ALLOCATE_PAY_SLIP_NO = "AllocPaySlipNo";
    public static final String FPAYMENT_ALLOCATE_PAY_DRAFT_NO = "AllocPayDraftNo";
    public static final String FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE = "AllocPayCheqDate";

    public static final String CREATE_TABLE_FPAYMENT_ALLOCATE = "CREATE TABLE IF NOT EXISTS " + TABLE_FPAYMENT_ALLOCATE + " (" + FPAYMENT_ALLOCATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FPAYMENT_ALLOCATE_REFNO + " TEXT, " + FPAYMENT_ALLOCATE_COMMON_REFNO + " TEXT, " + FPAYMENT_ALLOCATE_FDD_REFNO + " TEXT, " + FPAYMENT_ALLOCATE_FDD_TXN_DATE + " TEXT, " + FPAYMENT_ALLOCATE_FDD_PAID_AMT + " TEXT, " + FPAYMENT_ALLOCATE_FDD_TOTAL_BAL + " TEXT, " + FPAYMENT_ALLOCATE_PAY_REF_NO + " TEXT, " + FPAYMENT_ALLOCATE_PAY_MODE + " TEXT, " + FPAYMENT_ALLOCATE_PAY_DATE + " TEXT, " + FPAYMENT_ALLOCATE_PAY_AMT + " TEXT, " + FPAYMENT_ALLOCATE_PAY_REM_AMT + " TEXT, " + FPAYMENT_ALLOCATE_PAY_ALLO_AMT + " TEXT, " + FPAYMENT_ALLOCATE_PAY_BANK + " TEXT, " + FPAYMENT_ALLOCATE_PAY_CHEQUE_NO + " TEXT, " + FPAYMENT_ALLOCATE_PAY_CHEQUE_DATE + " TEXT, " + FPAYMENT_ALLOCATE_PAY_CREDIT_CARD_NO + " TEXT, " + FPAYMENT_ALLOCATE_PAY_SLIP_NO + " TEXT, " + FPAYMENT_ALLOCATE_PAY_DRAFT_NO + " TEXT ); ";


    // ----------------------------------------------------------------------------------------------------------


    public static final String CREATE_FPRETAXRG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRETAXRG + " (" + PRETAXRG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRETAXRG_REFNO + " TEXT, " + PRETAXRG_TAXCODE + " TEXT, " + PRETAXRG_RGNO + " TEXT ); ";
    public static final String TABLE_PRETAXDT = "fPreTaxDT";
    public static final String PRETAXDT_ID = "Id";
    public static final String PRETAXDT_REFNO = "RefNo";
    public static final String PRETAXDT_ITEMCODE = "ItemCode";
    public static final String PRETAXDT_TAXCOMCODE = "TaxComCode";
    public static final String PRETAXDT_TAXCODE = "TaxCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-PRETaxRG-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String PRETAXDT_TAXPER = "TaxPer";
    public static final String PRETAXDT_RATE = "TaxRate";
    public static final String PRETAXDT_SEQ = "TaxSeq";
    public static final String PRETAXDT_DETAMT = "TaxDetAmt";
    public static final String PRETAXDT_BDETAMT = "BTaxDetAmt";
    public static final String PRETAXDT_TAXTYPE = "TaxType";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-pre TAX DT-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String CREATE_FPRETAXDT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRETAXDT + " (" + PRETAXDT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PRETAXDT_REFNO + " TEXT, " + PRETAXDT_ITEMCODE + " TEXT, " + PRETAXDT_TAXCOMCODE + " TEXT, " + PRETAXDT_TAXCODE + " TEXT, " + PRETAXDT_TAXPER + " TEXT, " + PRETAXDT_RATE + " TEXT, " + PRETAXDT_SEQ + " TEXT, " + PRETAXDT_DETAMT + " TEXT, " + PRETAXDT_TAXTYPE + " TEXT, " + PRETAXDT_BDETAMT + " TEXT ); ";
    // table
    public static final String TABLE_FCONTROL = "fControl";
    // table attributes
    public static final String FCONTROL_ID = "fcontrol_id";
    public static final String FCONTROL_COM_NAME = "ComName";
    public static final String FCONTROL_COM_ADD1 = "ComAdd1";
    public static final String FCONTROL_COM_ADD2 = "ComAdd2";
    public static final String FCONTROL_COM_ADD3 = "ComAdd3";
    public static final String FCONTROL_COM_TEL1 = "comtel1";
    public static final String FCONTROL_COM_TEL2 = "comtel2";
    public static final String FCONTROL_COM_FAX = "comfax1";
    public static final String FCONTROL_COM_EMAIL = "comemail";
    public static final String FCONTROL_COM_WEB = "comweb";
    public static final String FCONTROL_FYEAR = "confyear";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- fControl table Details-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FCONTROL_TYEAR = "contyear";
    public static final String FCONTROL_COM_REGNO = "comRegNo";
    public static final String FCONTROL_FTXN = "ConfTxn";
    public static final String FCONTROL_TTXN = "ContTxn";
    public static final String FCONTROL_CRYSTALPATH = "Crystalpath";
    public static final String FCONTROL_VATCMTAXNO = "VatCmTaxNo";
    public static final String FCONTROL_NBTCMTAXNO = "NbtCmTaxNo";
    public static final String FCONTROL_SYSTYPE = "SysType";
    public static final String FCONTROL_DEALCODE = "DealCode";
    public static final String FCONTROL_BASECUR = "basecur";
    public static final String FCONTROL_BALGCRLM = "BalgCrLm";
    public static final String FCONTROL_CONAGE1 = "conage1";
    public static final String FCONTROL_CONAGE2 = "conage2";
    public static final String FCONTROL_CONAGE3 = "conage3";
    public static final String FCONTROL_CONAGE4 = "conage4";
    public static final String FCONTROL_CONAGE5 = "conage5";
    public static final String FCONTROL_CONAGE6 = "conage6";
    public static final String FCONTROL_CONAGE7 = "conage7";
    public static final String FCONTROL_CONAGE8 = "conage8";
    public static final String FCONTROL_CONAGE9 = "conage9";
    public static final String FCONTROL_CONAGE10 = "conage10";
    public static final String FCONTROL_CONAGE11 = "conage11";
    public static final String FCONTROL_CONAGE12 = "conage12";
    public static final String FCONTROL_CONAGE13 = "conage13";
    public static final String FCONTROL_CONAGE14 = "conage14";
    public static final String FCONTROL_SALESACC = "salesacc";
    public static final String FCONTROL_COMDISPER = "ComDisPer";
    // table
    public static final String TABLE_FCOMPANYSETTING = "fCompanySetting";
    // table attributes
    public static final String FCOMPANYSETTING_ID = "fcomset_id";// ok
    public static final String FCOMPANYSETTING_SETTINGS_CODE = "cSettingsCode";// ok
    public static final String FCOMPANYSETTING_GRP = "cSettingGrp";// ok
    public static final String FCOMPANYSETTING_LOCATION_CHAR = "cLocationChar";// ok
    public static final String FCOMPANYSETTING_CHAR_VAL = "cCharVal";// ok
    public static final String FCOMPANYSETTING_NUM_VAL = "nNumVal";// ok
    public static final String FCOMPANYSETTING_REMARKS = "cRemarks";// ok
    public static final String FCOMPANYSETTING_TYPE = "nType";// ok
    public static final String FCOMPANYSETTING_COMPANY_CODE = "cCompanyCode";// ok
    /**
     * ############################ fRoute table Details
     * ################################
     */

    // table
    public static final String TABLE_FROUTE = "fRoute";
    // table attributes
    public static final String FROUTE_ID = "route_id";
    public static final String FROUTE_REPCODE = "RepCode";

    /* fCompanySetting table Details */
    public static final String FROUTE_ROUTECODE = "RouteCode";
    public static final String FROUTE_ROUTE_NAME = "RouteName";
    public static final String FROUTE_RECORDID = "RecordId";
    public static final String FROUTE_ADDDATE = "AddDate";
    public static final String FROUTE_ADD_MACH = "AddMach";
    public static final String FROUTE_ADD_USER = "AddUser";
    public static final String FROUTE_AREACODE = "AreaCode";
    public static final String FROUTE_DEALCODE = "DealCode";
    public static final String FROUTE_FREQNO = "FreqNo";
    public static final String FROUTE_KM = "Km";
    public static final String FROUTE_MINPROCALL = "MinProcall";
    public static final String FROUTE_RDALORATE = "RDAloRate";
    public static final String FROUTE_RDTARGET = "RDTarget";
    public static final String FROUTE_REMARKS = "Remarks";
    public static final String FROUTE_STATUS = "Status";
    public static final String FROUTE_TONNAGE = "Tonnage";

    /**Download table**/
    // table
    public static final String TABLE_DOWNLOAD = "table_download";
    public static final String DOWNLOAD_COUNT = "download_due";
    public static final String DOWNLOADED_COUNT = "download_done";
    public static final String DOWNLOAD_TITLE = "download_title";
    public static final String DOWNLOAD_ID = "download_id";




    /**
     * ############################ fBank table Details
     * ################################
     */



    public static final String TABLE_NEW_CUSTOMER = "NewCustomer";
    public static final String TABLE_REC_ID = "recID"; //1
    public static final String CUSTOMER_ID = "customerID"; //2
    public static final String CUSTOMER_OTHER_CODE = "otherCode";//3
    public static final String COMPANY_REG_CODE = "comRegCode"; //4
    public static final String NAME = "Name"; //5
    public static final String NIC = "Nic"; //6
    public static final String ADDRESS1 = "Address1"; //7
    public static final String ADDRESS2 = "Address2"; //8
    public static final String CITY = "City"; //9
    public static final String PHONE = "Phone"; //10
    public static final String MOBILE = "Mobile"; //27
    public static final String FAX = "Fax"; //11
    public static final String E_MAIL = "Email"; //12
    public static final String C_TOWN = "customer_Town";  //13
    public static final String ROUTE_ID = "route_ID"; //14
    public static final String DISTRICT = "District"; //15
    public static final String OLD_CODE = "old_Code"; //16
    public static final String TxnDate = "tnxDate"; //17
    public static final String C_IMAGE = "Image"; //18
    public static final String C_IMAGE1 = "Image1";  //19
    public static final String C_IMAGE2 = "Image2"; //20
    public static final String C_IMAGE3 = "Image3";  //21
    public static final String C_LONGITUDE = "lng";  //22
    public static final String C_LATITUDE = "lat"; //23
    public static final String C_ADD_DATE = "AddDate"; //24
    public static final String C_ADD_MACH = "AddMach"; //25
    public static final String C_IS_SYNCED = "isSynced"; //26

    private static final String CREATE_NEW_CUSTOMER = "CREATE  TABLE IF NOT EXISTS " + TABLE_NEW_CUSTOMER + " ("
            + TABLE_REC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CUSTOMER_ID + " TEXT, "
            + NAME + " TEXT, "
            + NIC + " TEXT, "
            + CUSTOMER_OTHER_CODE + " TEXT, "
            + COMPANY_REG_CODE + " TEXT, "
            + DISTRICT + " TEXT, "
            + C_TOWN + " TEXT, "
            + ROUTE_ID + " TEXT, "
            + ADDRESS1 + " TEXT, "
            + ADDRESS2 + " TEXT, "
            + CITY + " TEXT, "
            + MOBILE + " TEXT, "
            + PHONE + " TEXT, "
            + FAX + " TEXT, "
            + E_MAIL + " TEXT, "
            + OLD_CODE + " TEXT, "
            + C_IMAGE + " TEXT, "
            + C_IMAGE1 + " TEXT, "
            + C_IMAGE2 + " TEXT, "
            + C_IMAGE3 + " TEXT, "
            + C_LONGITUDE + " TEXT, "
            + C_LATITUDE + " TEXT, "
            + C_ADD_DATE + " TEXT, "
            + C_ADD_MACH + " TEXT, "
            + C_IS_SYNCED + " TEXT); ";
    // table
    public static final String TABLE_FBANK = "fBank";
    // table attributes
    public static final String FBANK_ID = "bankre_id";
    public static final String FBANK_RECORD_ID = "RecordId";
    public static final String FBANK_BANK_CODE = "bankcode";
    public static final String FBANK_BANK_NAME = "bankname";
    public static final String FBANK_BANK_ACC_NO = "bankaccno";
    public static final String FBANK_BRANCH = "Branch";
    public static final String FBANK_ADD1 = "bankadd1";
    public static final String FBANK_ADD2 = "bankadd2";
    public static final String FBANK_ADD_DATE = "AddDate";
    public static final String FBANK_ADD_MACH = "AddMach";
    public static final String FBANK_ADD_USER = "AddUser";
    /**
     * ############################ fReason table Details
     * ################################
     */

    public static final String TABLE_FREASON = "fReason";
    // table attributes
    public static final String FREASON_ID = "freason_id";
    public static final String FREASON_ADD_DATE = "AddDate";
    public static final String FREASON_ADD_MACH = "AddMach";
    public static final String FREASON_ADD_USER = "AddUser";
    public static final String FREASON_CODE = "ReaCode";
    public static final String FREASON_NAME = "ReaName";
    public static final String FREASON_REATCODE = "ReaTcode";
    public static final String FREASON_RECORD_ID = "RecordId";
    public static final String FREASON_TYPE = "Type";
    /**
     * ############################ fExpense table Details
     * ################################
     */

    // fDebtor table
    public static final String TABLE_FEXPENSE = "fExpense";
    // fDebtor table attributes
    public static final String FEXPENSE_ID = "uexp_id";
    public static final String FEXPENSE_CODE = "ExpCode";
    public static final String FEXPENSE_GRP_CODE = "ExpGrpCode";
    public static final String FEXPENSE_NAME = "ExpName";
    public static final String FEXPENSE_RECORDID = "RecordId";
    public static final String FEXPENSE_STATUS = "Status";
    public static final String FEXPENSE_ADD_MACH = "AddMach";
    public static final String FEXPENSE_ADD_USER = "AddUser";
    public static final String FEXPENSE_ADD_DATE = "AddDate";
    /**
     * ############################ fTown table Details
     * ################################
     */

    // table
    public static final String TABLE_FTOWN = "fTown";
    // table attributes
    public static final String FTOWN_ID = "townre_id";
    public static final String FTOWN_RECORDID = "RecordId";
    public static final String FTOWN_CODE = "TownCode";
    public static final String FTOWN_NAME = "TownName";
    public static final String FTOWN_DISTR_CODE = "DistrCode";
    public static final String FTOWN_ADDDATE = "AddDate";
    public static final String FTOWN_ADD_MACH = "AddMach";
    public static final String FTOWN_ADD_USER = "AddUser";
    /**
     * ############################ FTrgCapUL table Details
     * ################################
     */

    //--------------------2018-4-4 dhanushika added FDistricts------------------------------------

    // table
    public static final String TABLE_FDISTRICT = "fDistrict";
    // table attributes
    public static final String FDISTRICT_ID = "_id";
    public static final String FDISTRICT_CODE = "DistrCode";
    public static final String FDISTRICT_NAME = "DistrName";
    public static final String FDISTRICT_PROVECODE = "ProvCode";



            //-----------------------------------------------------------------------------------------


    // table
    public static final String TABLE_FTRGCAPUL = "FTrgCapUL";
    // table attributes
    public static final String FTRGCAPUL_ID = "ftrg_id";
    public static final String FTRGCAPUL_ADD_DATE = "AddDate";
    public static final String FTRGCAPUL_ADD_MACH = "AddMach";
    public static final String FTRGCAPUL_ADD_USER = "AddUser";
    public static final String FTRGCAPUL_DEAL_CODE = "DealCode";
    public static final String FTRGCAPUL_MONTH = "Month";
    public static final String FTRGCAPUL_QTY = "Qty";
    public static final String FTRGCAPUL_RECORDID = "RecordId";
    public static final String FTRGCAPUL_REP_CODE = "RepCode";
    public static final String FTRGCAPUL_SKU_CODE = "SKUCode";
    public static final String FTRGCAPUL_YEAR = "Year";
    /**
     * ############################ fType table Details
     * ################################
     */

    // table
    public static final String TABLE_FTYPE = "fType";
    // table attributes
    public static final String FTYPE_ID = "ftype_id";
    public static final String FTYPE_ADD_DATE = "AddDate";
    public static final String FTYPE_ADD_MACH = "AddMach";
    public static final String FTYPE_ADD_USER = "AddUser";
    public static final String FTYPE_RECORDID = "RecordId";
    public static final String FTYPE_CODE = "TypeCode";
    public static final String FTYPE_NAME = "TypeName";
    /**
     * ############################ fSubBrand table Details
     * ################################
     */

    // table
    public static final String TABLE_FSUBBRAND = "fSubBrand";
    // table attributes
    public static final String FSUBBRAND_ID = "fsubbrand_id";
    public static final String FSUBBRAND_ADD_DATE = "AddDate";
    public static final String FSUBBRAND_ADD_MACH = "AddMach";
    public static final String FSUBBRAND_ADD_USER = "AddUser";
    public static final String FSUBBRAND_RECORDID = "RecordId";
    public static final String FSUBBRAND_CODE = "SBrandCode";
    public static final String FSUBBRAND_NAME = "SBrandName";
    /**
     * ############################ fGroup table Details
     * ################################
     */

    // table
    public static final String TABLE_FGROUP = "fGroup";
    // table attributes
    public static final String FGROUP_ID = "fgroup_id";
    public static final String FGROUP_ADD_DATE = "AddDate";
    public static final String FGROUP_ADD_MACH = "AddMach";
    public static final String FGROUP_ADD_USER = "AddUser";
    public static final String FGROUP_CODE = "GroupCode";
    public static final String FGROUP_NAME = "GroupName";
    public static final String FGROUP_RECORDID = "RecordId";
    /**
     * ############################ fSku table Details
     * ################################
     */

    // table
    public static final String TABLE_FSKU = "fSku";
    // table attributes
    public static final String FSKU_ID = "fsku_id";
    public static final String FSKU_ADD_DATE = "AddDate";
    public static final String FSKU_ADD_MACH = "AddMach";
    public static final String FSKU_ADD_USER = "AddUser";
    public static final String FSKU_BRAND_CODE = "BrandCode";
    public static final String FSKU_GROUP_CODE = "GroupCode";
    public static final String FSKU_ITEM_STATUS = "ItemStatus";
    public static final String FSKU_MUST_SALE = "MustSale";
    public static final String FSKU_NOUCASE = "NOUCase";
    public static final String FSKU_ORDSEQ = "OrdSeq";
    public static final String FSKU_RECORDID = "RecordId";
    public static final String FSKU_SUB_BRAND_CODE = "SBrandCode";
    public static final String FSKU_CODE = "SKUCode";
    public static final String FSKU_NAME = "SkuName";
    public static final String FSKU_SIZE_CODE = "SkuSizCode";
    public static final String FSKU_TONNAGE = "Tonnage";
    public static final String FSKU_TYPE_CODE = "TypeCode";
    public static final String FSKU_UNIT = "Unit";
    /**
     * ############################ fbrand table Details
     * ################################
     */

    // table
    public static final String TABLE_FBRAND = "fbrand";
    // table attributes
    public static final String FBRAND_ID = "fbrand_id";
    public static final String FBRAND_ADD_MACH = "AddMach";
    public static final String FBRAND_ADD_USER = "AddUser";
    public static final String FBRAND_CODE = "BrandCode";
    public static final String FBRAND_NAME = "BrandName";
    public static final String FBRAND_RECORDID = "RecordId";
    public static final String TABLE_FORDDISC = "FOrdDisc";
    public static final String FORDDISC_REFNO = "RefNo";
    public static final String FORDDISC_TXNDATE = "TxnDate";
    public static final String FORDDISC_REFNO1 = "RefNo1";
    public static final String FORDDISC_ITEMCODE = "ItemCode";
    public static final String FORDDISC_DISAMT = "DisAmt";
    public static final String FORDDISC_DISPER = "DisPer";


    public static final String CREATE_FORDDISC_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDDISC + " (" + FORDDISC_REFNO + " TEXT, " + FORDDISC_TXNDATE + " TEXT, " + FORDDISC_REFNO1 + " TEXT, " + FORDDISC_ITEMCODE + " TEXT, " + FORDDISC_DISAMT + " TEXT, " + FORDDISC_DISPER + " TEXT ); ";

    public static final String TABLE_FORDFREEISS = "FOrdFreeIss";
    public static final String FORDFREEISS_REFNO = "RefNo";
    public static final String FORDFREEISS_TXNDATE = "TxnDate";
    public static final String FORDFREEISS_REFNO1 = "RefNo1";
    public static final String FORDFREEISS_ITEMCODE = "ItemCode";
    public static final String FORDFREEISS_QTY = "Qty";
    public static final String CREATE_FORDFREEISS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDFREEISS + " (" + FORDFREEISS_REFNO + " TEXT, " + FORDFREEISS_TXNDATE + " TEXT, " + FORDFREEISS_REFNO1 + " TEXT, " + FORDFREEISS_ITEMCODE + " TEXT, " + FORDFREEISS_QTY + " TEXT ); ";
    public static final String TABLE_FINVDISCOUNTS = "FInvDiscounts";
    public static final String FINVDISCOUNTS_INV_REFNO = "InvRefNo";
    public static final String FINVDISCOUNTS_REFNO = "RefNo";
    public static final String FINVDISCOUNTS_DISC_AMT = "DiscAmt";
    public static final String FINVDISCOUNTS_ITEMCODE = "ItemCode";
    public static final String FINVDISCOUNTS_UNITPRICE = "UnitPrice";
    public static final String FINVDISCOUNTS_QTY = "Qty";
    public static final String FINVDISCOUNTS_GROSS_AMT = "GrossAmt";
    public static final String FINVDISCOUNTS_TOTAL_AMT = "TotalAmt";
    public static final String CREATE_FINVDISCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINVDISCOUNTS + " (" + FINVDISCOUNTS_INV_REFNO + " TEXT, " + FINVDISCOUNTS_REFNO + " TEXT, " + FINVDISCOUNTS_DISC_AMT + " TEXT, " + FINVDISCOUNTS_QTY + " TEXT, " + FINVDISCOUNTS_ITEMCODE + " TEXT, " + FINVDISCOUNTS_GROSS_AMT + " TEXT, " + FINVDISCOUNTS_UNITPRICE + " TEXT, " + FINVDISCOUNTS_TOTAL_AMT + " TEXT ); ";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-FOrdDisc table details*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String TABLE_FDSCHHED = "fDSchHed";
    public static final String FDSCHHED_ID = "Id";
    public static final String FDSCHHED_REFNO = "RefNo";
    public static final String FDSCHHED_DEBCODE = "DebCode";
    public static final String FDSCHHED_F_DATE = "FromDate";
    public static final String FDSCHHED_T_DATE = "ToDate";
    public static final String FDSCHHED_SCHSTATUS = "SchStatus";
    public static final String FDSCHHED_REMARKS = "Remarks";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-FOrdFreeIss table info-*-**-**-**-**-**-**-**-*-*-*-*/
    public static final String CREATE_FDSCHHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FDSCHHED + " (" + FDSCHHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FDSCHHED_REFNO + " TEXT, " + FDSCHHED_DEBCODE + " TEXT, " + FDSCHHED_F_DATE + " TEXT, " + FDSCHHED_T_DATE + " TEXT, " + FDSCHHED_SCHSTATUS + " TEXT, " + FDSCHHED_REMARKS + " TEXT); ";
    public static final String TABLE_FDSCHDET = "fDSchDet";
    public static final String FDSCHDET_ID = "Id";
    public static final String FDSCHDET_REFNO = "RefNo";
    public static final String FDSCHDET_GROUPCODE = "GroupCode";
    public static final String FDSCHDET_CAISSPER = "CAFIssPer";
    public static final String FDSCHDET_CADISPER = "CADisPer";

    /*-*-*-*-*-*-*-*-*-*-*-*-*FINVDISCounts Table Info-*-**-*-**-*-**-*-**-*-**-*-**-*-**-*-**-*-*/
    public static final String FDSCHDET_CRFISSPER = "CRFIssPer";
    public static final String FDSCHDET_CRDISPER = "CRDisPer";
    public static final String CREATE_FDSCHDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FDSCHDET + " (" + FDSCHDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FDSCHDET_REFNO + " TEXT, " + FDSCHDET_GROUPCODE + " TEXT, " + FDSCHDET_CAISSPER + " TEXT, " + FDSCHDET_CADISPER + " TEXT, " + FDSCHDET_CRFISSPER + " TEXT, " + FDSCHDET_CRDISPER + " TEXT); ";
    public static final String TABLE_FORDHED = "FOrdHed";
    public static final String FORDHED_ID = "Id";
    public static final String FORDHED_REFNO = "RefNo";
    public static final String FORDHED_ADD_DATE = "AddDate";
    public static final String FORDHED_ADD_MACH = "AddMach";
    public static final String FORDHED_ADD_USER = "AddUser";
    public static final String FORDHED_APP_DATE = "Appdate";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*Discount header table-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FORDHED_APPSTS = "Appsts";
    public static final String FORDHED_APP_USER = "AppUser";
    public static final String FORDHED_BP_TOTAL_DIS = "BPTotalDis";
    public static final String FORDHED_B_TOTAL_AMT = "BTotalAmt";
    public static final String FORDHED_B_TOTAL_DIS = "BTotalDis";
    public static final String FORDHED_B_TOTAL_TAX = "BTotalTax";
    public static final String FORDHED_COST_CODE = "CostCode";
    public static final String FORDHED_CUR_CODE = "CurCode";
    public static final String FORDHED_CUR_RATE = "CurRate";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-Discount details table*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FORDHED_DEB_CODE = "DebCode";
    public static final String FORDHED_DIS_PER = "DisPer";
    public static final String FORDHED_START_TIME_SO = "startTimeSO";
    public static final String FORDHED_END_TIME_SO = "endTimeSO";
    public static final String FORDHED_LONGITUDE = "Longitude";
    public static final String FORDHED_LATITUDE = "Latitude";
    public static final String FORDHED_LOC_CODE = "LocCode";
    public static final String FORDHED_MANU_REF = "ManuRef";
    public static final String FORDHED_RECORD_ID = "RecordId";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-FOrdHed table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FORDHED_REMARKS = "Remarks";
    public static final String FORDHED_REPCODE = "RepCode";
    public static final String FORDHED_TAX_REG = "TaxReg";
    public static final String FORDHED_TIMESTAMP_COLUMN = "Timestamp_column";
    public static final String FORDHED_TOTAL_AMT = "TotalAmt";
    public static final String FORDHED_TOTALDIS = "TotalDis";
    public static final String FORDHED_TOTAL_TAX = "TotalTax";
    public static final String FORDHED_TXN_TYPE = "TxnType";
    public static final String FORDHED_TXN_DATE = "TxnDate";
    public static final String FORDHED_ADDRESS = "gpsAddress";
    public static final String FORDHED_TOTAL_ITM_DIS = "TotalItemDis";
    public static final String FORDHED_TOT_MKR_AMT = "TotMkrAmt";
    public static final String FORDHED_IS_SYNCED = "isSynced";
    public static final String FORDHED_IS_ACTIVE = "isActive";
    public static final String FORDHED_DELV_DATE = "DelvDate";
    public static final String FORDHED_HED_DIS_VAL = "HedDisVal";
    public static final String FORDHED_HED_DIS_PER_VAL = "HedDisPerVal";
    public static final String FORDHED_ROUTE_CODE = "RouteCode";
    public static final String FORDHED_PAYMENT_TYPE = "PaymentType";
    public static final String FORDHED_GROUPCODE = "GroupCode";
    public static final String FORDHED_ORD_REFNO = "OrdRefNo";
    public static final String FORDHED_ORD_TYPE = "OrdType";
    public static final String FORDHED_FIPAY_TYPE = "FIPayType";
    public static final String FORDHED_FISS_TYPE = "FIssType";
    public static final String FORDHED_FIPER_CAL = "FIPerCal";
    public static final String FORDHED_FIAMT_CAL = "FIAmtCal";
    public static final String FORDHED_FICASH_DISPER = "FICashDisPer";
    public static final String FORDHED_FICASH_DISAMT = "FICashDisAmt";
    public static final String FORDHED_FIPER = "FIPer";
    public static final String FORDHED_FIAMT = "FIAmt";
    public static final String FORDHED_TOTAL_QTY = "TotalQty";
    public static final String FORDHED_FICAMT = "FICAmt";
    public static final String FORDHED_FIQTY = "FIQty";
    public static final String FORDHED_FICHG_AMT = "FIChgAmt";
    public static final String TABLE_FORDDET = "FOrddet";
    public static final String FORDDET_ID = "Id";
    public static final String FORDDET_REFNO = "RefNo";
    public static final String FORDDET_AMT = "Amt";
    public static final String FORDDET_B_AMT = "BAmt";
    public static final String FORDDET_B_SELL_PRICE = "BSellPrice";
    public static final String FORDDET_DIS_AMT = "DisAmt";
    public static final String FORDDET_DIS_PER = "DisPer";
    public static final String FORDDET_ITEM_CODE = "Itemcode";
    public static final String FORDDET_QTY = "Qty";
    public static final String FORDDET_PICE_QTY = "PiceQty";
    public static final String FORDDET_TYPE = "Types";
    public static final String FORDDET_SELL_PRICE = "SellPrice";
    public static final String FORDDET_SEQNO = "SeqNo";
    public static final String FORDDET_TXN_DATE = "TxnDate";
    public static final String FORDDET_TXN_TYPE = "TxnType";
    public static final String FORDDET_IS_ACTIVE = "isActive";
    public static final String FORDDET_SIZECODE = "SizeCode";
    public static final String FORDDET_GROUPCODE = "GroupCode";
    public static final String TABLE_FORDDISCOUNT = "FOrdDiscounts";
    public static final String FORDISCOUNTS_ORD_REFNO = "OrdRefNo";
    public static final String FORDISCOUNTS_REFNO = "RefNo";
    public static final String FORDISCOUNTS_DISC_AMT = "DiscAmt";
    public static final String FORDISCOUNTS_ITEMCODE = "ItemCode";
    public static final String FORDISCOUNTS_UNITPRICE = "UnitPrice";
    public static final String FORDISCOUNTS_QTY = "Qty";
//-----------------------------------------------------------------------last 3 bill hed data saved this tbl-------addded by dhanushika----------------------------------------------------------
public static  final String FORDHEDTEMP ="FOrdHed_temp" ;
    public static final String INVTAX_ID = "Id";
    public static  final String FORDHEDTEMP_debcode ="debCode_temp" ;
    public static  final String FORDHEDTEMP_refNo ="RefNo_temp" ;
    public static  final String FORDHEDTEMP_totAmt ="TotalAmt_temp" ;
    public static  final String FORDHEDTEMP_totDis ="TotalDis_temp" ;
    public static  final String FORDHEDTEMP_txnDate ="TxnDate_temp" ;

    public static final String FORDDETTEMP="FOrdDet_temp";
    public static final String FORDDETTEMP_REFNO="refNo_FOrdDet_temp";
    public static final String FORDDETTEMP_TXNDATE="TxnDate_FOrdDet_temp";
    public static final String FORDDETTEMP_ITEMCODE="ItemCode_FOrdDet_temp";
    public static final String FORDDETTEMP_QTY="Qty_FOrdDet_temp";
    public static final String FORDDETTEMP_TAX="TaxComCode_OrdDet_temp";
    public static final String FORDDETTEMP_Amt="Amt_FOrdDet_temp";
    public static final String FORDDETTEMP_Type="Type_FOrdDet_temp";

    public static final String CREATE_FORDHEDTEMP = "CREATE TABLE IF NOT EXISTS " + FORDHEDTEMP + " ("
            + INVTAX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //0
            + FORDHEDTEMP_debcode + " TEXT, " //1
            + FORDHEDTEMP_refNo + " TEXT, " //2
            + FORDHEDTEMP_totAmt + " TEXT, " //3
            + FORDHEDTEMP_totDis + " TEXT, "//4
            + FORDHEDTEMP_txnDate + " TEXT ); ";//5



    public static final String CREATE_FORDDETTEMP = "CREATE TABLE IF NOT EXISTS " + FORDDETTEMP + " ("
            + INVTAX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //0
            + FORDDETTEMP_REFNO + " TEXT, " //1
            + FORDDETTEMP_TXNDATE + " TEXT, " //2
            + FORDDETTEMP_ITEMCODE + " TEXT, " //3
            + FORDDETTEMP_QTY + " TEXT, " //4
            + FORDDETTEMP_TAX + " TEXT, "  //5
            + FORDDETTEMP_Type + " TEXT, "//6
            + FORDDETTEMP_Amt + " TEXT ); "; //7

//---------------------------------------------------INvHedTemp  table for saved last --------------------------------------------------

    public static  final String FINVHEDTEMP ="FInvHed_temp" ;
    public static final String FINVHEDID = "Id";
    public static  final String FINVHED_debcode ="debCode_temp" ;
    public static  final String FINVHED_refNo ="RefNo_temp" ;
    public static  final String FINVHED_totAmt ="TotalAmt_temp" ;
    public static  final String FINVHED_totDis ="TotalDis_temp" ;
    public static  final String FINVHED_txnDate ="TxnDate_temp" ;

    public static final String FINVDETTEMP="FInvDet_temp";
    public static final String FINVDETTEMP_REFNO="refNo_FInvDet_temp";
    public static final String FINVDETTEMP_TXNDATE="TxnDate_FInvDet_temp";
    public static final String FINVDETTEMP_ITEMCODE="ItemCode_FInvDet_temp";
    public static final String FINVDETTEMP_QTY="Qty_FInvDet_temp";
    public static final String FINVDETTEMP_TAX="TaxComCode_FInvDet_temp";
    public static final String FINVDETTEMP_Amt="Amt_FInvDet_temp";
    public static final String FINVDETTEMP_Type="Type_FInvDet_temp";



    public static final String CREATE_FINVHEDTEMP = "CREATE TABLE IF NOT EXISTS " + FINVHEDTEMP + " ("
            + FINVHEDID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //0
            + FINVHED_debcode + " TEXT, " //1
            + FINVHED_refNo + " TEXT, " //2
            + FINVHED_totAmt + " TEXT, " //3
            + FINVHED_totDis + " TEXT, "//4
            + FINVHED_txnDate + " TEXT ); ";//5


    public static final String CREATE_FINVDETTEMP = "CREATE TABLE IF NOT EXISTS " + FINVDETTEMP + " ("
            + INVTAX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //0
            + FINVDETTEMP_REFNO + " TEXT, " //1
            + FINVDETTEMP_TXNDATE + " TEXT, " //2
            + FINVDETTEMP_ITEMCODE + " TEXT, " //3
            + FINVDETTEMP_QTY + " TEXT, " //4
            + FINVDETTEMP_TAX + " TEXT, "  //5
            + FINVDETTEMP_Amt + " TEXT, "//6
            + FINVDETTEMP_Type + " TEXT ); "; //7

//--------------------------------------------------------------------------------------------------------------------------------------

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*FOrddet table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FORDISCOUNTS_GROSS_AMT = "GrossAmt";
    public static final String FORDISCOUNTS_TOTAL_AMT = "TotalAmt";
    public static final String CREATE_FORDISCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDDISCOUNT + " (" + FORDISCOUNTS_ORD_REFNO + " TEXT, " + FORDISCOUNTS_REFNO + " TEXT, " + FORDISCOUNTS_DISC_AMT + " TEXT, " + FORDISCOUNTS_QTY + " TEXT, " + FORDISCOUNTS_ITEMCODE + " TEXT, " + FORDISCOUNTS_GROSS_AMT + " TEXT, " + FORDISCOUNTS_UNITPRICE + " TEXT, " + FORDISCOUNTS_TOTAL_AMT + " TEXT ); ";
    public static final String TABLE_SMS_DATE = "fSMSDate";
    public static final String FSMS_ID = "Id";
    public static final String FSMS_C_DATE = "CurrentDate";
    public static final String CREATE_SMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SMS_DATE + " (" + FSMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSMS_C_DATE + " TEXT ); ";
    public static final String TABLE_FINVHED = "finvHed";
    public static final String FINVHED_ID = "Id";
    public static final String FINVHED_REFNO = "RefNo";
    public static final String FINVHED_REFNO1 = "RefNo1";
    public static final String FINVHED_TXNDATE = "TxnDate";
    public static final String FINVHED_MANUREF = "ManuRef";
    public static final String FINVHED_COSTCODE = "CostCode";
    public static final String FINVHED_CURCODE = "CurCode";
    public static final String FINVHED_CURRATE = "CurRate";
    public static final String FINVHED_DEBCODE = "DebCode";
    public static final String FINVHED_REMARKS = "Remarks";
    public static final String FINVHED_TXNTYPE = "TxnType";
    public static final String FINVHED_LOCCODE = "LocCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-order discount table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String FINVHED_REPCODE = "RepCode";
    public static final String FINVHED_CONTACT = "Contact";
    public static final String FINVHED_CUSADD1 = "CusAdd1";
    public static final String FINVHED_CUSADD2 = "CusAdd2";
    public static final String FINVHED_CUSADD3 = "CusAdd3";
    public static final String FINVHED_CUSTELE = "CusTele";
    public static final String FINVHED_TOTALDIS = "TotalDis";
    public static final String FINVHED_TOTALTAX = "TotalTax";
    public static final String FINVHED_TOTALAMT = "TotalAmt";
    public static final String FINVHED_TAXREG = "TaxReg";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-fSMS table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FINVHED_ADDUSER = "AddUser";
    public static final String FINVHED_ADDDATE = "AddDate";
    public static final String FINVHED_ADDMACH = "AddMach";
    public static final String FINVHED_START_TIME_SO = "startTime";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-finvHed table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FINVHED_END_TIME_SO = "endTime";
    public static final String FINVHED_LONGITUDE = "Longitude";
    public static final String FINVHED_LATITUDE = "Latitude";
    public static final String FINVHED_ADDRESS = "Address";
    public static final String FINVHED_IS_SYNCED = "isSynced";
    public static final String FINVHED_IS_ACTIVE = "isActive";
    public static final String FINVHED_AREACODE = "areacode";
    public static final String FINVHED_ROUTECODE = "routecode";
    public static final String FINVHED_TOURCODE = "tourcode";
    public static final String TABLE_FINVDET = "finvDet";
    public static final String FINVDET_ID = "id";
    public static final String FINVDET_REFNO = "RefNo";
    public static final String FINVDET_PICE_QTY = "PiceQty";
    public static final String FINVDET_TYPE = "Types";
    public static final String FINVDET_IS_ACTIVE = "isActive";
    public static final String FINVDET_AMT = "Amt";
    public static final String FINVDET_BAL_QTY = "BalQty";
    public static final String FINVDET_B_AMT = "BAmt";
    public static final String FINVDET_B_SELL_PRICE = "BSellPrice";
    public static final String FINVDET_BT_TAX_AMT = "BTaxAmt";
    public static final String FINVDET_BT_SELL_PRICE = "BTSellPrice";
    public static final String FINVDET_DIS_AMT = "DisAmt";
    public static final String FINVDET_DIS_PER = "DisPer";
    public static final String FINVDET_ITEM_CODE = "Itemcode";
    public static final String FINVDET_PRIL_CODE = "PrilCode";
    public static final String FINVDET_QTY = "Qty";
    public static final String FINVDET_RECORD_ID = "RecordId";
    public static final String FINVDET_SELL_PRICE = "SellPrice";
    public static final String FINVDET_SEQNO = "SeqNo";
    public static final String FINVDET_TAX_AMT = "TaxAmt";
    public static final String FINVDET_TAX_COM_CODE = "TaxComCode";
    public static final String FINVDET_T_SELL_PRICE = "TSellPrice";
    public static final String FINVDET_TXN_DATE = "TxnDate";
    public static final String FINVDET_TXN_TYPE = "TxnType";
    public static final String FINVDET_FREEQTY = "FreeQty";
    public static final String FINVDET_COMDISPER = "ComDisPer";
    public static final String FINVDET_BRAND_DISPER = "BrandDisPer";

    /*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-finvDet table Details-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String FINVDET_DISVALAMT = "DisValAmt";
    public static final String FINVDET_COMPDISC = "CompDisc";
    public static final String FINVDET_BRAND_DISC = "BrandDisc";
    public static final String FINVDET_QOH = "Qoh";
    public static final String FINVDET_SCHDISPER = "SchDisPer";
    public static final String FINVDET_PRICE = "Price";
    public static final String TABLE_INVTAXRG = "fInvTaxRg";
    public static final String INVTAXRG_ID = "Id";
    public static final String INVTAXRG_REFNO = "RefNo";
    public static final String INVTAXRG_TAXCODE = "TaxCode";
    public static final String INVTAXRG_RGNO = "RGNo";
    public static final String CREATE_FINVTAXRG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVTAXRG + " (" + INVTAXRG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INVTAXRG_REFNO + " TEXT, " + INVTAXRG_TAXCODE + " TEXT, " + INVTAXRG_RGNO + " TEXT ); ";
    public static final String TABLE_INVTAXDT = "fInvTaxDT";
    public static final String INVTAXDT_ID = "Id";
    public static final String INVTAXDT_REFNO = "RefNo";
    public static final String INVTAXDT_ITEMCODE = "ItemCode";
    public static final String INVTAXDT_TAXCOMCODE = "TaxComCode";
    public static final String INVTAXDT_TAXCODE = "TaxCode";
    public static final String INVTAXDT_TAXPER = "TaxPer";
    public static final String INVTAXDT_RATE = "TaxRate";
    public static final String INVTAXDT_SEQ = "TaxSeq";
    public static final String INVTAXDT_DETAMT = "TaxDetAmt";
    public static final String INVTAXDT_BDETAMT = "BTaxDetAmt";
    public static final String INVTAXDT_TAXTYPE = "TaxType";
    public static final String CREATE_FINVTAXDT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INVTAXDT + " (" + INVTAXDT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + INVTAXDT_REFNO + " TEXT, " + INVTAXDT_ITEMCODE + " TEXT, " + INVTAXDT_TAXCOMCODE + " TEXT, " + INVTAXDT_TAXCODE + " TEXT, " + INVTAXDT_TAXPER + " TEXT, " + INVTAXDT_RATE + " TEXT, " + INVTAXDT_SEQ + " TEXT, " + INVTAXDT_DETAMT + " TEXT, " + INVTAXDT_TAXTYPE + " TEXT, " + INVTAXDT_BDETAMT + " TEXT ); ";
    public static final String TABLE_FTAX = "fTax";
    public static final String FTAX_ID = "Id";
    public static final String FTAX_TAXCODE = "TaxCode";
    public static final String FTAX_TAXNAME = "TaxName";
    public static final String FTAX_TAXPER = "TaxPer";
    public static final String FTAX_TAXREGNO = "TaxRegNo";
    public static final String CREATE_FTAX_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTAX + " (" + FTAX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTAX_TAXCODE + " TEXT, " + FTAX_TAXNAME + " TEXT, " + FTAX_TAXPER + " TEXT, " + FTAX_TAXREGNO + " TEXT ); ";
    public static final String TABLE_FTAXHED = "fTaxHed";
    public static final String FTAXHED_ID = "Id";
    public static final String FTAXHED_COMCODE = "TaxComCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-invoice TaxRG -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String FTAXHED_COMNAME = "TaxComName";
    public static final String FTAXHED_ACTIVE = "Active";
    public static final String CREATE_FTAXHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTAXHED + " (" + FTAXHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTAXHED_COMCODE + " TEXT, " + FTAXHED_COMNAME + " TEXT, " + FTAXHED_ACTIVE + " TEXT ); ";
    public static final String TABLE_FTAXDET = "fTaxDet";
    public static final String FTAXDET_ID = "Id";
    public static final String FTAXDET_COMCODE = "TaxComCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-invoice TAX DT-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*/
    public static final String FTAXDET_TAXCODE = "TaxCode";
    public static final String FTAXDET_RATE = "Rate";
    public static final String FTAXDET_SEQ = "Seq";
    public static final String FTAXDET_TAXVAL = "TaxVal";
    public static final String FTAXDET_TAXTYPE = "TaxType";
    public static final String CREATE_FTAXDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTAXDET + " (" + FTAXDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTAXDET_COMCODE + " TEXT, " + FTAXDET_TAXCODE + " TEXT, " + FTAXDET_RATE + " TEXT, " + FTAXDET_TAXVAL + " TEXT, " + FTAXDET_TAXTYPE + " TEXT, " + FTAXDET_SEQ + " TEXT ); ";
    public static final String TABLE_FTOUR = "fTour";
    public static final String FTOUR_ID = "Id";
    public static final String FTOUR_REFNO = "RefNo";
    public static final String FTOUR_DATE = "tDate";
    public static final String FTOUR_S_TIME = "StartTime";
    public static final String FTOUR_F_TIME = "EndTime";
    public static final String FTOUR_VEHICLE = "Vehicle";
    public static final String FTOUR_S_KM = "StartKm";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-fTax table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FTOUR_F_KM = "EndKm";
    public static final String FTOUR_ROUTE = "Route";
    public static final String FTOUR_DRIVER = "Driver";
    public static final String FTOUR_ASSIST = "Assist";
    public static final String FTOUR_DISTANCE = "Distance";
    public static final String FTOUR_IS_SYNCED = "IsSynced";
    public static final String FTOUR_REPCODE = "RepCode";
    public static final String FTOUR_LOCCODE = "LocCode";
    public static final String FTOUR_TERRITORY = "Territory";
    public static final String FTOUR_COST_CENTER = "CostCenter";


    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-fTaxHed table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FTOUR_MAC = "MacAdd";
    public static final String CREATE_FTOUR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTOUR + " (" + FTOUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            FTOUR_DATE + " TEXT, " + FTOUR_S_TIME + " TEXT, " + FTOUR_REFNO + " TEXT," + FTOUR_F_TIME + " TEXT, " + FTOUR_VEHICLE + " TEXT, " + FTOUR_S_KM + " TEXT, " + FTOUR_F_KM + " TEXT, " + FTOUR_DISTANCE + " TEXT, " + FTOUR_IS_SYNCED + " TEXT, "

            + FTOUR_REPCODE + " TEXT, " + FTOUR_DRIVER + " TEXT, " + FTOUR_ASSIST + " TEXT, " + FTOUR_MAC + " TEXT, " + FTOUR_LOCCODE + " TEXT, " + FTOUR_TERRITORY + " TEXT, " + FTOUR_COST_CENTER + " TEXT," + FTOUR_ROUTE + " TEXT ); ";

    public static final String TABLE_FSTKIN = "fSTKIn";
    public static final String FSTKIN_ID = "Id";
    public static final String FSTKIN_BALQTY = "BalQty";
    public static final String FSTKIN_COSTPRICE = "CostPrice";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-fTaxDet table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--*-*-*/
    public static final String FSTKIN_INQTY = "InQty";
    public static final String FSTKIN_ITEMCODE = "ItemCode";
    public static final String FSTKIN_LOCCODE = "LocCode";
    public static final String FSTKIN_OTHCOST = "OthCost";
    public static final String FSTKIN_REFNO = "RefNo";
    public static final String FSTKIN_STKREC_DATE = "StkRecDate";
    public static final String FSTKIN_STKRECNO = "StkRecNo";
    public static final String FSTKIN_TXNDATE = "TxnDate";
    public static final String FSTKIN_TXNTYPE = "TxnType";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-fTOUR-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String TABLE_FSIZEIN = "fSizeIn";
    public static final String FSIZEIN_ID = "Id";
    public static final String FSIZEIN_REFNO = "RefNo";
    public static final String FSIZEIN_TXNDATE = "TxnDate";
    public static final String FSIZEIN_STKRECDATE = "STKRecDate";
    public static final String FSIZEIN_LOCCODE = "LocCode";
    public static final String FSIZEIN_TXNTYPE = "TxnType";
    public static final String FSIZEIN_STKRECNO = "STKRecNo";
    public static final String FSIZEIN_ITEMCODE = "ItemCode";
    public static final String FSIZEIN_SIZECODE = "SizeCode";
    public static final String FSIZEIN_GROUPCODE = "GroupCode";
    public static final String FSIZEIN_QTY = "Qty";
    public static final String FSIZEIN_BALQTY = "BalQty";
    public static final String FSIZEIN_COSTPRICE = "CostPrice";
    public static final String FSIZEIN_PRICE = "Price";
    public static final String FSIZEIN_OTHCOST = "OthCost";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-STKIN-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FSIZEIN_APPSTAT = "AppStat";
    public static final String FSIZEIN_ADDDATE = "AddDate";
    public static final String FSIZEIN_MRPPRICE = "MRPPrice";
    public static final String FSIZEIN_ORDREFNO = "OrdRefNo";
    public static final String TABLE_FSTKISS = "fStkIss";
    public static final String FSTKISS_ID = "Id";
    public static final String FSTKISS_REFNO = "RefNo";
    public static final String FSTKISS_TXNDATE = "TxnDate";
    public static final String FSTKISS_LOCCODE = "LocCode";
    public static final String FSTKISS_STKRECNO = "STKRecNo";
    public static final String FSTKISS_STKTXNNO = "STKTxnNo";
    public static final String FSTKISS_STKTXNDATE = "STKTxnDate";
    public static final String FSTKISS_STKRECDATE = "STKRecDate";
    public static final String FSTKISS_STKTXNTYPE = "STKTxnType";

    /*-*-*-*--*-*-*-*-*-*-fSizeIn table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FSTKISS_ITEMCODE = "ItemCode";
    public static final String FSTKISS_QTY = "Qty";
    public static final String FSTKISS_COSTPRICE = "CostPrice";
    // table
    public static final String TABLE_FCOMPANYBRANCH = "FCompanyBranch";
    // table attributes
    public static final String FCOMPANYBRANCH_ID = "fcombra_id";
    public static final String FCOMPANYBRANCH_BRANCH_CODE = "BranchCode";
    public static final String FCOMPANYBRANCH_RECORD_ID = "RecordId";
    public static final String FCOMPANYBRANCH_CSETTINGS_CODE = "cSettingsCode";
    public static final String FCOMPANYBRANCH_NNUM_VAL = "nNumVal";
    public static final String FCOMPANYBRANCH_YEAR = "nYear";
    public static final String FCOMPANYBRANCH_MONTH = "nMonth";
    /**
     * ############################ fSalRep table Details
     * ################################
     */

    public static final String TABLE_FSALREP = "fSalRep";
    public static final String FSALREP_ID = "_id";
    public static final String FSALREP_ADDMACH = "AddMach";
    public static final String FSALREP_ADDUSER = "AddUser";
    public static final String FSALREP_PASSWORD = "Password";
    public static final String FSALREP_RECORDID = "RecordId";
    public static final String FSALREP_REPCODE = "RepCode";
    public static final String FSALREP_EMAIL = "Email";
    public static final String FSALREP_REPID = "RepId";
    public static final String FSALREP_MOBILE = "Mobile";
    public static final String FSALREP_LOCCODE = "LocCode";

    // private static final String INDEX_FSIZEIN =
    // "CREATE UNIQUE INDEX IF NOT EXISTS ui_sizein ON " + TABLE_FSIZEIN +
    // " (RefNo, ItemCode, SizeCode, STKRecNo);";

    /*-*-*-*--*-*-*-*-*-*-fSizeIss table-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FSALREP_NAME = "Name";
    public static final String FSALREP_PREFIX = "Prefix";
    public static final String FSALREP_TELE = "Tele";
    public static final String FSALREP_STATUS = "Status";
    public static final String FSALREP_MACID = "MacId";
    /**
     * ############################ FDDbNote table Details
     * ################################
     */


	/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* FCompanyBranch table Details-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    // table
    public static final String TABLE_FDDBNOTE = "FDDbNote";
    // table attributes
    public static final String FDDBNOTE_ID = "recinv_id";
    public static final String FDDBNOTE_RECORD_ID = "RecordId";
    public static final String FDDBNOTE_REFNO = "RefNo";
    public static final String FDDBNOTE_REF_INV = "RefInv";
    public static final String FDDBNOTE_SALE_REF_NO = "SaleRefNo";
    public static final String FDDBNOTE_MANU_REF = "ManuRef";
    public static final String FDDBNOTE_TXN_TYPE = "TxnType";
    public static final String FDDBNOTE_TXN_DATE = "TxnDate";
    public static final String FDDBNOTE_CUR_CODE = "CurCode";
    public static final String FDDBNOTE_CUR_RATE = "CurRate";
    public static final String FDDBNOTE_DEB_CODE = "DebCode";
    public static final String FDDBNOTE_REP_CODE = "RepCode";
    public static final String FDDBNOTE_TAX_COM_CODE = "TaxComCode";
    public static final String FDDBNOTE_TAX_AMT = "TaxAmt";
    public static final String FDDBNOTE_B_TAX_AMT = "BTaxAmt";
    public static final String FDDBNOTE_AMT = "Amt";
    public static final String FDDBNOTE_B_AMT = "BAmt";
    public static final String FDDBNOTE_TOT_BAL = "TotBal";
    public static final String FDDBNOTE_TOT_BAL1 = "TotBal1";
    public static final String FDDBNOTE_OV_PAY_AMT = "OvPayAmt";
    public static final String FDDBNOTE_REMARKS = "Remarks";
    public static final String FDDBNOTE_CR_ACC = "CrAcc";
    public static final String FDDBNOTE_PRT_COPY = "PrtCopy";
    public static final String FDDBNOTE_GL_POST = "GlPost";
    public static final String FDDBNOTE_GL_BATCH = "glbatch";
    public static final String FDDBNOTE_ADD_USER = "AddUser";
    public static final String FDDBNOTE_ADD_DATE = "AddDate";
    public static final String FDDBNOTE_ADD_MACH = "AddMach";
    public static final String FDDBNOTE_REFNO1 = "RefNo1";
    public static final String FDDBNOTE_REPNAME = "RepName";
    public static final String FDDBNOTE_ENTER_AMT = "EnterAmt";
    public static final String FDDBNOTE_ENT_REMARK = "EntRemark";
    public static final String FDDBNOTE_PDAAMT = "PdaAmt";




    // create String
    private static final String CREATE_FDDBNOTE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FDDBNOTE + " (" + FDDBNOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDDBNOTE_RECORD_ID + " TEXT, " + FDDBNOTE_REFNO + " TEXT, " + FDDBNOTE_REF_INV + " TEXT, " + FDDBNOTE_SALE_REF_NO + " TEXT, " + FDDBNOTE_MANU_REF + " TEXT, " + FDDBNOTE_TXN_TYPE + " TEXT, " + FDDBNOTE_TXN_DATE + " TEXT, " + FDDBNOTE_CUR_CODE + " TEXT, " + FDDBNOTE_CUR_RATE + " TEXT, " + FDDBNOTE_DEB_CODE + " TEXT, " + FDDBNOTE_REP_CODE + " TEXT, " + FDDBNOTE_TAX_COM_CODE + " TEXT, " + FDDBNOTE_TAX_AMT + " TEXT, " + FDDBNOTE_B_TAX_AMT + " TEXT, " + FDDBNOTE_AMT + " TEXT, " + FDDBNOTE_B_AMT + " TEXT, " + FDDBNOTE_TOT_BAL + " TEXT, " + FDDBNOTE_TOT_BAL1 + " TEXT, " + FDDBNOTE_OV_PAY_AMT + " TEXT, " + FDDBNOTE_REMARKS + " TEXT, " + FDDBNOTE_CR_ACC + " TEXT, " + FDDBNOTE_PRT_COPY + " TEXT, " + FDDBNOTE_GL_POST + " TEXT, " + FDDBNOTE_GL_BATCH + " TEXT, " + FDDBNOTE_ADD_USER + " TEXT, " + FDDBNOTE_ADD_DATE + " TEXT, " + FDDBNOTE_ADD_MACH + " TEXT, " + FDDBNOTE_REFNO1 + " TEXT, " + FDDBNOTE_REPNAME + " TEXT, " + FDDBNOTE_ENTER_AMT + " TEXT, " + FDDBNOTE_ENT_REMARK + " TEXT, " + FDDBNOTE_PDAAMT + " TEXT); ";




    // table otherTransactions
    public static final String TABLE_FOTHERTRANS = "FOtherTransact";
    // table attributes
    public static final String FOTHERTRANS_AMOUNT = "Amount";
    public static final String FOTHERTRANS_DEBCODE= "DebCode";
    public static final String FOTHERTRANS_REFNO  = "RefNo";
    public static final String FOTHERTRANS_REFNO1  = "RefNo1";
    public static final String FOTHERTRANS_TXNDATE  = "TxnDate";
    public static final String FOTHERTRANS_TXNTYPE  = "TxnType";

    private static final String CREATE_FOTHERTRANS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FOTHERTRANS + " (" + FOTHERTRANS_AMOUNT + "  TEXT, " + FOTHERTRANS_DEBCODE + " TEXT, " + FOTHERTRANS_REFNO + " TEXT, " + FOTHERTRANS_REFNO1 + " TEXT, " + FOTHERTRANS_TXNDATE + " TEXT, " + FOTHERTRANS_TXNTYPE + " TEXT); ";


    /**
     * ############################ Ffreedeb table Details
     * ################################
     */

    // table
    public static final String TABLE_FFREEDEB = "Ffreedeb";
    // table attributes
    public static final String FFREEDEB_ID = "Ffreedeb_id";
    public static final String FFREEDEB_REFNO = "Refno";
    public static final String FFREEDEB_DEB_CODE = "Debcode";
    public static final String FFREEDEB_ADD_USER = "AddUser";
    public static final String FFREEDEB_ADD_DATE = "AddDate";
    public static final String FFREEDEB_ADD_MACH = "AddMach";
    public static final String FFREEDEB_RECORD_ID = "RecordId";
    public static final String FFREEDEB_TIMESTAMP_COLUMN = "timestamp_column";
    /**
     * ############################ Ffreedet table Details
     * ################################
     */

    // table
    public static final String TABLE_FFREEDET = "Ffreedet";
    // table attributes
    public static final String FFREEDET_ID = "Ffreedet_id";
    public static final String FFREEDET_REFNO = "Refno";
    public static final String FFREEDET_ITEM_CODE = "Itemcode";
    public static final String FFREEDET_RECORD_ID = "RecordId";
    /**
     * ############################ Ffreehed table Details
     * ################################
     */

    // table
    public static final String TABLE_FFREEHED = "Ffreehed";
    // table attributes
    public static final String FFREEHED_ID = "Ffreehed_id";
    public static final String FFREEHED_REFNO = "Refno";
    public static final String FFREEHED_TXNDATE = "txndate";
    public static final String FFREEHED_DISC_DESC = "DiscDesc";
    public static final String FFREEHED_PRIORITY = "Priority";
    public static final String FFREEHED_VDATEF = "Vdatef";
    public static final String FFREEHED_VDATET = "Vdatet";
    public static final String FFREEHED_REMARKS = "Remarks";
    public static final String FFREEHED_RECORD_ID = "RecordId";
    public static final String FFREEHED_ITEM_QTY = "ItemQty";
    public static final String FFREEHED_FREE_IT_QTY = "FreeItQty";
    public static final String FFREEHED_FTYPE = "Ftype";
    public static final String FFREEHED_COSTCODE = "CostCode";
    /**
     * ############################ FfreeSlab table Details
     * ################################
     */

    // table
    public static final String TABLE_FFREESLAB = "Ffreeslab";
    // table attributes
    public static final String FFREESLAB_ID = "Ffreeslab_id";
    public static final String FFREESLAB_REFNO = "Refno";
    public static final String FFREESLAB_QTY_F = "Qtyf";
    public static final String FFREESLAB_QTY_T = "Qtyt";
    public static final String FFREESLAB_FITEM_CODE = "fItemCode";
    public static final String FFREESLAB_FREE_QTY = "freeQty";
    public static final String FFREESLAB_ADD_USER = "AddUser";
    public static final String FFREESLAB_ADD_DATE = "AddDate";
    public static final String FFREESLAB_ADD_MACH = "AddMach";
    public static final String FFREESLAB_RECORD_ID = "RecordId";
    public static final String FFREESLAB_TIMESTAP_COLUMN = "timestamp_column";
    public static final String FFREESLAB_SEQ_NO = "seqno";
    /**
     * ############################ fFreeItem table Details
     * ################################
     */

    // table
    public static final String TABLE_FFREEITEM = "fFreeItem";
    // table attributes
    public static final String FFREEITEM_ID = "fFreeItem_id";
    public static final String FFREEITEM_REFNO = "Refno";
    public static final String FFREEITEM_ITEMCODE = "Itemcode";
    public static final String FFREEITEM_RECORD_ID = "RecordId";
    public static final String TABLE_FITEM = "fItem";
    public static final String FITEM_ID = "fItem_id";
    public static final String FITEM_AVG_PRICE = "AvgPrice";
    public static final String FITEM_BRAND_CODE = "BrandCode";
    public static final String FITEM_GROUP_CODE = "GroupCode";
    public static final String FITEM_ITEM_CODE = "ItemCode";
    public static final String FITEM_ITEM_NAME = "ItemName";
    public static final String FITEM_ITEM_STATUS = "ItemStatus";
    public static final String FITEM_PRIL_CODE = "PrilCode";
    public static final String FITEM_TAX_COM_CODE = "TaxComCode";
    public static final String FITEM_TYPE_CODE = "TypeCode";
    public static final String FITEM_UNIT_CODE = "UnitCode";
    public static final String FITEM_VEN_P_CODE = "VenPcode";
    public static final String FITEM_NOUCASE = "NouCase";
    public static final String FITEM_REORDER_LVL = "ReOrderLvl";
    public static final String FITEM_REORDER_QTY = "ReOrderQty";
    public static final String FITEM_SCAT_CODE = "ScatCode";
    public static final String FITEM_SUBCAT_CODE = "SubCatCode";
    public static final String FITEM_COLOR_CODE = "ColorCode";
    public static final String FITEM_DISCOUNT = "Discount";
    public static final String FITEM_CLASS_CODE = "ClassCode";
    public static final String FITEM_IS_SIZE = "IsSize";
    public static final String FITEM_IS_DISCOUNT = "IsDiscount";
    // table
    public static final String TABLE_FITEMLOC = "fItemLoc";
    // table attributes
    public static final String FITEMLOC_ID = "fItemLoc_id";
    public static final String FITEMLOC_ITEM_CODE = "ItemCode";
    public static final String FITEMLOC_LOC_CODE = "LocCode";
    public static final String FITEMLOC_QOH = "QOH";
    public static final String FITEMLOC_RECORD_ID = "RecordId";
    /**
     * ############################ fItemPri table Details
     * ################################
     */
    // table
    public static final String TABLE_FITEMPRI = "fItemPri";
    // table attributes
    public static final String FITEMPRI_ID = "fItemPri_id";
    public static final String FITEMPRI_ADD_MACH = "AddMach";
    public static final String FITEMPRI_ADD_USER = "AddUser";
    public static final String FITEMPRI_ITEM_CODE = "ItemCode";
    public static final String FITEMPRI_PRICE = "Price";
    public static final String FITEMPRI_PRIL_CODE = "PrilCode";
    public static final String FITEMPRI_SKU_CODE = "SKUCode";
    public static final String FITEMPRI_TXN_MACH = "TxnMach";
    public static final String FITEMPRI_TXN_USER = "Txnuser";
    // table
    public static final String TABLE_FAREA = "fArea";

    //----------------------------------------------Notification Messages---------------------------
    public static final String TABLE_MESSAGE = "Messages";
    public static final String TAG_ID = "Id";
    public static final String TAG_MSG = "message";
    public static final String TAG_SUBJECT = "subject";
    public static final String TAG_FROM = "msgfrom";
    public static final String TAG_DATE_TIME = "date_time";
    public static final String TAG_STATUS = "status";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- fItem table Details -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    // table attributes
    public static final String FAREA_ID = "fArea_id";
    public static final String FAREA_ADD_MACH = "AddMach";
    public static final String FAREA_ADD_USER = "AddUser";
    public static final String FAREA_AREA_CODE = "AreaCode";
    public static final String FAREA_AREA_NAME = "AreaName";
    public static final String FAREA_DEAL_CODE = "DealCode";
    public static final String FAREA_REQ_CODE = "RegCode";
    /**
     * ############################ fLocations table Details
     * ################################
     */
    // table
    public static final String TABLE_FLOCATIONS = "fLocations";
    // table attributes
    public static final String FLOCATIONS_ID = "fLocations_id";
    public static final String FLOCATIONS_ADD_MACH = "AddMach";
    public static final String FLOCATIONS_ADD_USER = "AddUser";
    public static final String FLOCATIONS_LOC_CODE = "LocCode";
    public static final String FLOCATIONS_LOC_NAME = "LocName";
    public static final String FLOCATIONS_LOC_T_CODE = "LoctCode";
    public static final String FLOCATIONS_REP_CODE = "RepCode";
    /**
     * ############################ fDealer table Details
     * ################################
     */
    // table
    public static final String TABLE_FDEALER = "fDealer";
    // table attributes
    public static final String FDEALER_ID = "fDealer_id";
    public static final String FDEALER_ADD_DATE = "AddDate";
    public static final String FDEALER_ADD_MACH = "AddMach";
    public static final String FDEALER_ADD_USER = "AddUser";
    public static final String FDEALER_CUS_TYP_CODE = "CusTypCode";
    public static final String FDEALER_DEAL_ADD1 = "DealAdd1";
    public static final String FDEALER_DEAL_ADD2 = "DealAdd2";
    public static final String FDEALER_DEAL_ADD3 = "DealAdd3";
    public static final String FDEALER_DEAL_CODE = "DealCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- fItemLoc table Details -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FDEALER_DEAL_EMAIL = "DealEMail";
    public static final String FDEALER_DEAL_GD_CODE = "DealGdCode";
    public static final String FDEALER_DEAL_MOB = "DealMob";
    public static final String FDEALER_DEAL_NAME = "DealName";
    public static final String FDEALER_DEAL_TELE = "DealTele";
    public static final String FDEALER_DISTANCE = "Distance";
    public static final String FDEALER_STATUS = "Status";
    public static final String FDEALER_PREFIX = "DealPreFix";
    /**
     * ############################ fMerch table Details
     * ################################
     */
    // table
    public static final String TABLE_FMERCH = "fMerch";
    // table attributes
    public static final String FMERCH_ID = "fMerch_id";
    public static final String FMERCH_CODE = "MerchCode";
    public static final String FMERCH_NAME = "MerchName";
    public static final String FMERCH_ADD_USER = "AddUser";
    public static final String FMERCH_ADD_DATE = "AddDate";
    public static final String FMERCH_ADD_MACH = "AddMach";
    public static final String FMERCH_RECORD_ID = "RecordId";
    public static final String FMERCH_TIMESTAMP_COLUMN = "timestamp_column";
    /**
     * ############################ FfreeMslab table Details
     * ################################
     */
    // table
    public static final String TABLE_FFREEMSLAB = "FfreeMslab";
    // table attributes
    public static final String FFREEMSLAB_ID = "FfreeMslab_id";
    public static final String FFREEMSLAB_REFNO = "Refno";
    public static final String FFREEMSLAB_QTY_F = "Qtyf";
    public static final String FFREEMSLAB_QTY_T = "Qtyt";
    public static final String FFREEMSLAB_ITEM_QTY = "ItemQty";
    public static final String FFREEMSLAB_FREE_IT_QTY = "FreeItQty";
    public static final String FFREEMSLAB_ADD_USER = "AddUser";
    public static final String FFREEMSLAB_ADD_DATE = "AddDate";
    public static final String FFREEMSLAB_ADD_MACH = "AddMach";
    public static final String FFREEMSLAB_RECORD_ID = "RecordId";
    public static final String FFREEMSLAB_TIMESTAMP_COLUMN = "timestamp_column";
    public static final String FFREEMSLAB_SEQ_NO = "seqno";
    /**
     * ############################ fRouteDet table Details
     * ################################
     */
    // table
    public static final String TABLE_FROUTEDET = "fRouteDet";
    // table attributes
    public static final String FROUTEDET_ID = "fRouteDet_id";
    public static final String FROUTEDET_DEB_CODE = "DebCode";
    public static final String FROUTEDET_ROUTE_CODE = "RouteCode";
    /**
     * ############################ FDiscvhed table Details
     * ################################
     */
    // table
    public static final String TABLE_FDISCVHED = "FDiscvhed";
    // table attributes
    public static final String FDISCVHED_ID = "FDiscvhed_id";
    public static final String FDISCVHED_REF_NO = "Refno";
    public static final String FDISCVHED_TXN_DATE = "Txndate";
    public static final String FDISCVHED_DISC_DESC = "DiscDesc";
    public static final String FDISCVHED_PRIORITY = "Priority";
    public static final String FDISCVHED_DIS_TYPE = "DisType";
    public static final String FDISCVHED_V_DATE_F = "Vdatef";
    public static final String FDISCVHED_V_DATE_T = "Vdatet";
    public static final String FDISCVHED_REMARKS = "Remarks";
    /**
     * ############################ Fdiscvdet table Details
     * ################################
     */
    // table
    public static final String TABLE_FDISCVDET = "Fdiscvdet";
    // table attributes
    public static final String FDISCVDET_ID = "FDiscvdet_id";
    public static final String FDISCVDET_REF_NO = "Refno";
    public static final String FDISCVDET_VALUE_F = "Valuef";
    public static final String FDISCVDET_VALUE_T = "Valuet";
    public static final String FDISCVDET_DISPER = "Disper";
    public static final String FDISCVDET_DIS_AMT = "Disamt";
    /**
     * ############################ FDiscvdeb table Details
     * ################################
     */
    // table
    public static final String TABLE_FDISCVDEB = "FDiscvdeb";
    // table attributes
    public static final String FDISCVDEB_ID = "FDiscvdet_id";
    public static final String FDISCVDEB_REF_NO = "Refno";
    public static final String FDISCVDED_DEB_CODE = "Debcode";
    /**
     * ############################ fdisched table Details
     * ################################
     */
    // table
    public static final String TABLE_FDISCHED = "fdisched";
    // table attributes
    public static final String FDISCHED_ID = "fdisched_id";
    public static final String FDISCHED_REF_NO = "RefNo";
    public static final String FDISCHED_TXN_DATE = "TxnDate";
    public static final String FDISCHED_DISC_DESC = "DiscDesc";
    public static final String FDISCHED_PRIORITY = "Priority";
    public static final String FDISCHED_DIS_TYPE = "DisType";
    public static final String FDISCHED_V_DATE_F = "Vdatef";
    public static final String FDISCHED_V_DATE_T = "Vdatet";
    public static final String FDISCHED_REMARK = "Remarks";
    public static final String FDISCHED_ADD_USER = "AddUser";
    public static final String FDISCHED_ADD_DATE = "AddDate";
    public static final String FDISCHED_ADD_MACH = "AddMach";
    public static final String FDISCHED_RECORD_ID = "RecordId";
    public static final String FDISCHED_TIMESTAMP_COLUMN = "timestamp_column";
    /**
     * ############################ FDiscdet table Details
     * ################################
     */

    // table
    public static final String TABLE_FDISCDET = "FDiscdet";
    // table attributes
    public static final String FDISCDET_ID = "FDiscdet_id";
    public static final String FDISCDET_REF_NO = "RefNo";
    public static final String FDISCDET_ITEM_CODE = "itemcode";
    public static final String FDISCDET_RECORD_ID = "RecordId";
    public static final String FDISCHED_TIEMSTAMP_COLUMN = "timestamp_column";
    /**
     * ############################ FDiscdeb table Details
     * ################################
     */

    // table
    public static final String TABLE_FDISCDEB = "FDiscdeb";
    // table attributes
    public static final String FDISCDEB_ID = "FDiscdet_id";
    public static final String FDISCDEB_REF_NO = "RefNo";
    public static final String FDISCDEB_DEB_CODE = "debcode";
    public static final String FDISCDEB_RECORD_ID = "RecordId";
    public static final String FDISCDEB_TIEMSTAMP_COLUMN = "timestamp_column";
    /**
     * ############################ FDiscslab table Details
     * ################################
     */

    // table
    public static final String TABLE_FDISCSLAB = "FDiscslab";
    // table attributes
    public static final String FDISCSLAB_ID = "FDiscdet_id";
    public static final String FDISCSLAB_REF_NO = "RefNo";
    public static final String FDISCSLAB_SEQ_NO = "seqno";
    public static final String FDISCSLAB_QTY_F = "Qtyf";
    public static final String FDISCSLAB_QTY_T = "Qtyt";
    public static final String FDISCSLAB_DIS_PER = "disper";
    public static final String FDISCSLAB_DIS_AMUT = "disamt";
    public static final String FDISCSLAB_RECORD_ID = "RecordId";
    public static final String FDISCSLAB_TIMESTAMP_COLUMN = "timestamp_column";
    /**
     * ############################ FItenrHed table Details
     * ################################
     */

    // table
    public static final String TABLE_FITENRHED = "FItenrHed";
    // table attributes
    public static final String FITENRHED_ID = "FItenrHed_id";
    public static final String FITENRHED_COST_CODE = "CostCode";
    public static final String FITENRHED_DEAL_CODE = "DealCode";
    public static final String FITENRHED_MONTH = "Month";
    public static final String FITENRHED_REF_NO = "RefNo";
    public static final String FITENRHED_REMARKS1 = "Remarks1";
    public static final String FITENRHED_REP_CODE = "RepCode";
    public static final String FITENRHED_YEAR = "Year";
    /**
     * ############################ FItenrDet table Details
     * ################################
     */

    // table
    public static final String TABLE_FITENRDET = "FItenrDet";
    // table attributes
    public static final String FITENRDET_ID = "FItenrDet_id";
    public static final String FITENRDET_NO_OUTLET = "NoOutlet";
    public static final String FITENRDET_NO_SHCUCAL = "NoShcuCal";
    public static final String FITENRDET_RD_TARGET = "RDTarget";
    public static final String FITENRDET_REF_NO = "RefNo";
    public static final String FITENRDET_REMARKS = "Remarks";
    public static final String FITENRDET_ROUTE_CODE = "RouteCode";
    public static final String FITENRDET_TXN_DATE = "TxnDate";
    /**
     * ############################ FIteDebDet table Details
     * ################################
     */

    // table
    public static final String TABLE_FITEDEBDET = "FIteDebDet";
    // table attributes
    public static final String FITEDEBDET_ID = "FItenrDet_id";
    public static final String FITEDEBDET_DEB_CODE = "DebCode";
    public static final String FITEDEBDET_REF_NO = "RefNo";
    public static final String FITEDEBDET_ROUTE_CODE = "RouteCode";
    public static final String FITEDEBDET_TXN_DATE = "TxnDate";
    /**
     * ############################ FinvHedL3 table Details
     * ################################
     */

    // table
    public static final String TABLE_FINVHEDL3 = "FinvHedL3";
    // table attributes
    public static final String FINVHEDL3_ID = "FinvHedL3_id";
    public static final String FINVHEDL3_DEB_CODE = "DebCode";
    public static final String FINVHEDL3_REF_NO = "RefNo";
    public static final String FINVHEDL3_REF_NO1 = "RefNo1";
    public static final String FINVHEDL3_TOTAL_AMT = "TotalAmt";
    public static final String FINVHEDL3_TOTAL_TAX = "TotalTax";
    public static final String FINVHEDL3_TXN_DATE = "TxnDate";
    /**
     * ############################ FinvHedL3 table Details
     * ################################
     */

    // table
    public static final String TABLE_FINVDETL3 = "FinvDetL3";
    // table attributes
    public static final String FINVDETL3_ID = "FinvDetL3_id";
    public static final String FINVDETL3_AMT = "Amt";
    public static final String FINVDETL3_ITEM_CODE = "ItemCode";
    public static final String FINVDETL3_QTY = "Qty";
    public static final String FINVDETL3_REF_NO = "RefNo";
    public static final String FINVDETL3_SEQ_NO = "SeqNo";
    public static final String FINVDETL3_TAX_AMT = "TaxAmt";
    public static final String FINVDETL3_TAX_COM_CODE = "TaxComCode";
    public static final String FINVDETL3_TXN_DATE = "TxnDate";
    /**
     * ############################ FTranDet ################################
     */
    public static final String TABLE_FTRANDET = "FTranDet";
    // table attributes
    public static final String FTRANDET_ID = "FTranDet_id";
    public static final String FTRANDET_REFNO = "RefNo";
    public static final String FTRANDET_TXNDATE = "TxnDate";
    public static final String FTRANDET_LOCCODE = "LocCode";
    public static final String FTRANDET_TXNTYPE = "TxnType";
    public static final String FTRANDET_SEQNO = "SeqNo";
    public static final String FTRANDET_ITEMCODE = "ItemCode";
    public static final String FTRANDET_QTY = "Qty";
    public static final String FTRANDET_AMT = "Amt";
    public static final String FTRANDET_CASEQTY = "CaseQty";
    public static final String FTRANDET_PICEQTY = "PiceQty";
    public static final String REMQTY = "remqty";
    /**
     * ############################ FTranHed ################################
     */

    public static final String TABLE_FTRANHED = "FTranHed";
    // table attributes
    public static final String FTRANHED_ID = "FTranHed_id";
    public static final String FTRANHED_REFNO = "RefNo";
    public static final String FTRANHED_TXNDATE = "TxnDate";
    public static final String FTRANHED_MANUREF = "ManuRef";
    public static final String FTRANHED_COSTCODE = "CostCode";
    public static final String FTRANHED_REMARKS = "Remarks";
    public static final String FTRANHED_TXNTYPE = "TxnType";
    public static final String FTRANHED_TOTALAMT = "TotalAmt";
    public static final String FTRANHED_DELPERSN = "DelPerson";
    public static final String FTRANHED_DELADD1 = "DelAdd1";
    public static final String FTRANHED_DELADD2 = "DelAdd2";
    public static final String FTRANHED_DELADD3 = "DelAdd3";
    public static final String FTRANHED_VEHICALNO = "VehicalNo";
    public static final String FTRANHED_PRTCOPY = "PrtCopy";
    public static final String FTRANHED_GLPOST = "GlPost";
    public static final String FTRANHED_GLBATCH = "GlBatch";
    public static final String FTRANHED_ADDUSER = "AddUser";
    public static final String FTRANHED_ADDDATE = "AddDate";
    public static final String FTRANHED_ADDMACH = "AddMach";
    public static final String FTRANHED_DEALCODE = "DealCode";
    public static final String FTRANHED_LONGITUDE = "Longitude";
    public static final String FTRANHED_LATITUDE = "Latitude";
    public static final String FTRANHED_LOCFROM = "Locfrom";
    public static final String FTRANHED_LOCTO = "Locto";
    public static final String FTRANHED_IS_SYNCED = "issync";
    public static final String FTRANHED_ACTIVE_STATE = "ActiveState";
    /**
     * ############################ FTranIss ################################
     */

    public static final String TABLE_FTRANISS = "FTranIss";
    // table attributes
    public static final String FTRANISS_ID = "Id";
    public static final String FTRANISS_REFNO = "RefNo";
    public static final String FTRANISS_ITEMCODE = "ItemCode";
    public static final String FTRANISS_QTY = "Qty";
    public static final String FTRANISS_AMT = "Amt";
    public static final String FTRANISS_SIZECODE = "SizeCode";
    public static final String FTRANISS_GROUPCODE = "GroupCode";
    public static final String FTRANISS_PRICE = "Price";
    public static final String FTRANISS_QOH = "QOH";
    public static final String FTRANISS_BRAND = "Brand";
    public static final String TABLE_NONPRDHED = "FDaynPrdHed";
    // table attributes
    public static final String NONPRDHED_ID = "FNonprdHed_id";
    public static final String NONPRDHED_REFNO = "RefNo";
    public static final String NONPRDHED_TXNDAET = "TxnDate";
    public static final String NONPRDHED_DEALCODE = "DealCode";
    public static final String NONPRDHED_REPCODE = "RepCode";
    public static final String NONPRDHED_REMARK = "Remarks";
    public static final String NONPRDHED_COSTCODE = "CostCode";
    public static final String NONPRDHED_ADDUSER = "AddUser";
    public static final String NONPRDHED_ADDDATE = "AddDate";
    public static final String NONPRDHED_ADDMACH = "AddMach";
    public static final String NONPRDHED_TRANSBATCH = "TranBatch";
    public static final String NONPRDHED_IS_SYNCED = "ISsync";
    public static final String NONPRDHED_ADDRESS = "Address";

    public static final String NONPRDHED_DEBCODE = "DebCode";
    public static final String NONPRDHED_LONGITUDE = "Longitude";
    public static final String NONPRDHED_LATITUDE = "Latitude";
    public static final String NONPRDHED_IS_ACTIVE = "ISActive";
    /**
     * ############################ FDaynonprdDet
     * ################################
     */
    public static final String TABLE_NONPRDDET = "FDaynPrdDet";
    // table attributes
    public static final String NONPRDDET_ID = "FNonprdDet_id";
    public static final String NONPRDDET_REFNO = "RefNo";
    public static final String NONPRDDET_TXNDATE = "TxnDate";
    public static final String NONPRDDET_REPCODE = "RepCode";
    public static final String NONPRDDET_REASON = "Reason";
//    public static final String NONPRDDET_LONGITUDE = "Longitude";
//    public static final String NONPRDDET_LATITUDE = "Latitude";
    public static final String NONPRDDET_REASON_CODE = "ReasonCode";
    public static final String NONPRDDET_IS_SYNCED = "ISsync";
    /**
     * ############################ FDamHed ################################
     */

    public static final String TABLE_FDAMHED = "FDamHed";
    // table attributes
    public static final String FDAMHED_ID = "FDamHed_id";
    public static final String FDAMHED_REFNO = "RefNo";
    public static final String FDAMHED_TXNDATE = "TxnDate";
    public static final String FDAMHED_MANUREF = "ManuRef";
    public static final String FDAMHED_DEALCODE = "DealCode";
    public static final String FDAMHED_COSTCODE = "CostCode";
    public static final String FDAMHED_REMARKS = "Remarks";
    public static final String FDAMHED_TXNTYPE = "TxnType";
    public static final String FDAMHED_TOTALAMT = "TotalAmt";
    public static final String FDAMHED_REACODE = "ReaCode";
    public static final String FDAMHED_ITEMTYPE = "ItemType";
    public static final String FDAMHED_GLPOST = "GlPost";
    public static final String FDAMHED_GLBATCH = "GlBatch";
    public static final String FDAMHED_ADDUSER = "AddUser";
    public static final String FDAMHED_ADDDATE = "AddDate";
    public static final String FDAMHED_ADDMACH = "AddMach";
    public static final String FDAMHED_LOCFROM = "Locfrom";
    public static final String FDAMHED_LOCTO = "Locto";
    public static final String FDAMHED_IS_SYNCED = "issync";
    public static final String FDAMHED_ACTIVE_STATE = "ActiveState";
    /**
     * ############################ FDamDet ################################
     */
    public static final String TABLE_FDAMDET = "FDamDet";
    // table attributes
    public static final String FDAMDET_ID = "FTranDet_id";
    public static final String FDAMDET_REFNO = "RefNo";
    public static final String FDAMDET_TXNDATE = "TxnDate";
    public static final String FDAMDET_LOCCODE = "LocCode";
    public static final String FDAMDET_TXNTYPE = "TxnType";
    public static final String FDAMDET_SEQNO = "SeqNo";
    public static final String FDAMDET_ITEMCODE = "ItemCode";
    public static final String FDAMDET_REACODE = "ReaCode";
    public static final String FDAMDET_REANAME = "ReaName";
    public static final String FDAMDET_QTY = "Qty";
    public static final String FDAMDET_AMT = "Amt";
    public static final String FDAMDET_CASEQTY = "CaseQty";
    public static final String FDAMDET_PICEQTY = "PiceQty";
    public static final String REMQTYDAM = "remqty";
    /**
     * ############################ FDayExpHed ################################
     */

    public static final String TABLE_FDAYEXPHED = "FDayExpHed";
    // table attributes
    public static final String FDAYEXPHED_ID = "FDayExpHed_id";
    public static final String FDAYEXPHED_REFNO = "RefNo";
    public static final String FDAYEXPHED_TXNDATE = "TxnDate";
    public static final String FDAYEXPHED_REPNAME = "RepName";
    public static final String FDAYEXPHED_DEALCODE = "DealCode";
    public static final String FDAYEXPHED_COSTCODE = "CostCode";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-**-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FDAYEXPHED_REPCODE = "RepCode";
    public static final String FDAYEXPHED_REMARKS = "Remarks";
    public static final String FDAYEXPHED_AREACODE = "AreaCode";
    public static final String FDAYEXPHED_ADDUSER = "AddUser";
    public static final String FDAYEXPHED_ADDDATE = "AddDate";
    public static final String FDAYEXPHED_ADDMATCH = "AddMach";
    public static final String FDAYEXPHED_LONGITUDE = "Longitude";
    public static final String FDAYEXPHED_LATITUDE = "Latitude";
    public static final String FDAYEXPHED_ISSYNC = "issync";
    public static final String FDAYEXPHED_ACTIVESTATE = "ActiveState";
    public static final String FDAYEXPHED_TOTAMT = "TotAmt";
    public static final String FDAYEXPHED_ADDRESS = "Address";
    /**
     * ############################ FDayExpDet ################################
     */
    public static final String TABLE_FDAYEXPDET = "FDayExpDet";
    // table attributes
    public static final String FDAYEXPDET_ID = "FDayExpDet_id";
    public static final String FDAYEXPDET_REFNO = "RefNo";
    public static final String FDAYEXPDET_TXNDATE = "TxnDate";
    public static final String FDAYEXPDET_SEQNO = "SeqNo";
    public static final String FDAYEXPDET_EXPCODE = "ExpCode";
    public static final String FDAYEXPDET_AMT = "Amt";
    public static final String TABLE_FTOURHED = "fTourHed";
    public static final String TOURHED_ID = "Id";
    public static final String TOURHED_REFNO = "Refno";
    public static final String TOURHED_MANUREF = "ManuRef";
    public static final String TOURHED_TXNDATE = "TxnDate";
    public static final String TOURHED_LORRYCODE = "LorryCode";
    public static final String TOURHED_ROUTECODE = "RouteCode";
    public static final String TOURHED_AREACODE = "AreaCode";
    public static final String TOURHED_COSTCODE = "CostCode";
    public static final String TOURHED_REMARKS = "Remarks";
    public static final String TOURHED_LOCCODEF = "LocCodeF";
    public static final String TOURHED_LOCCODE = "LocCode";
    public static final String TOURHED_REPCODE = "RepCode";
    public static final String TOURHED_HELPERCODE = "HelperCode";
    public static final String TOURHED_ADDUSER = "AddUser";
    public static final String TOURHED_ADDMACH = "AddMach";
    public static final String TOURHED_DRIVERCODE = "DriverCode";
    public static final String TOURHED_VANLOADFLG = "VanLoadFlg";
    public static final String TOURHED_CLSFLG = "Clsflg";
    public static final String TOURHED_TOURTYPE = "TourType";
    public static final String TABLE_DEBITEMPRI = "fDebItemPri";
    public static final String DEBITEMPRI_ID = "Id";
    public static final String DEBITEMPRI_BRANDCODE = "BrandCode";
    public static final String DEBITEMPRI_DEBCODE = "DebCode";
    public static final String DEBITEMPRI_DISPER = "Disper";
    public static final String TABLE_FDISPHED = "FDispHed";
    public static final String FDISPHED_ID = "Id";
    public static final String FDISPHED_REFNO = "RefNo";
    public static final String FDISPHED_TXNDATE = "TxnDate";
    public static final String FDISPHED_REFNO1 = "RefNo1";
    public static final String FDISPHED_MANUREF = "ManuRef";
    public static final String FDISPHED_TOTALAMT = "TotalAmt";
    public static final String FDISPHED_LOCCODE = "LocCode";
    public static final String FDISPHED_COSTCODE = "CostCode";
    public static final String FDISPHED_DEBCODE = "DebCode";
    public static final String FDISPHED_REPCODE = "RepCode";
    public static final String FDISPHED_REMARKS = "Remarks";
    public static final String FDISPHED_TXNTYPE = "TxnType";
    public static final String FDISPHED_INVOICE = "Invoice";
    public static final String FDISPHED_CONTACT = "Contact";
    public static final String FDISPHED_CUSADD1 = "CusAdd1";
    public static final String FDISPHED_CUSADD2 = "CusAdd2";
    public static final String FDISPHED_CUSADD3 = "CusAdd3";
    public static final String FDISPHED_CUSTELE = "CusTele";
    public static final String FDISPHED_ADDUSER = "AddUser";
    public static final String FDISPHED_ADDDATE = "AddDate";
    public static final String FDISPHED_ADDMACH = "AddMach";
    public static final String TABLE_FDISPDET = "FDispDet";
    public static final String FDISPDET_ID = "Id";
    public static final String FDISPDET_REFNO = "RefNo";
    public static final String FDISPDET_TXNDATE = "TxnDate";
    public static final String FDISPDET_TXNTYPE = "TxnType";
    public static final String FDISPDET_ITEMCODE = "ItemCode";
    public static final String FDISPDET_QTY = "Qty";
    public static final String FDISPDET_BALQTY = "BalQty";
    public static final String FDISPDET_SAQTY = "SAQty";
    public static final String FDISPDET_BALSAQTY = "BalSAQty";
    public static final String FDISPDET_FIQTY = "FIQty";
    public static final String FDISPDET_BALFIQTY = "BalFIQty";
    public static final String FDISPDET_COSTPRICE = "CostPrice";
    public static final String FDISPDET_AMT = "Amt";
    public static final String FDISPDET_SEQNO = "SeqNo";
    public static final String FDISPDET_REFNO1 = "RefNo1";
    public static final String TABLE_FDISPISS = "FDispIss";
    public static final String FDISPISS_ID = "Id";
    public static final String FDISPISS_REFNO = "RefNo";
    public static final String FDISPISS_TXNDATE = "TxnDate";
    public static final String FDISPISS_LOCCODE = "LocCode";
    public static final String FDISPISS_STKRECNO = "StkRecNo";
    ;
    public static final String FDISPISS_STKRECDATE = "StkRecDate";
    public static final String FDISPISS_STKTXNNO = "StkTxnNo";
    public static final String FDISPISS_STKTXNDATE = "StkTxnDate";
    public static final String FDISPISS_STKTXNTYPE = "StkTxnType";

    // --------------------------------------------------------------------------------------------------------------
    public static final String FDISPISS_ITEMCODE = "ItemCode";
    public static final String FDISPISS_QTY = "Qty";
    public static final String FDISPISS_BALQTY = "BalQty";
    public static final String FDISPISS_COSTPRICE = "CostPrice";
    public static final String FDISPISS_AMT = "Amt";
    public static final String FDISPISS_OTHCOST = "OthCost";
    public static final String FDISPISS_REFNO1 = "Refno1";
    public static final String TABLE_FBRANDTARGET = "fBrandTarget";
    public static final String FBRANDTARGET_ID = "Id";
    public static final String FBRANDTARGET_BRANDCODE = "BrandCode";
    public static final String FBRANDTARGET_COSTCODE = "CostCode";
    public static final String FBRANDTARGET_TARGET = "Target";

    public static final String TABLE_FINVRHED = "FInvRHed";
    public static final String FINVRHED_ID = "id";// 1
    public static final String FINVRHED_REFNO = "RefNo";
    public static final String FINVRHED_MANUREF = "ManuRef";
    public static final String FINVRHED_TXNDATE = "TxnDate";
    public static final String FINVRHED_COSTCODE = "CostCode";
    public static final String FINVRHED_DEBCODE = "DebCode";
    public static final String FINVRHED_REMARKS = "Remarks";
    public static final String FINVRHED_TXNTYPE = "TxnType";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FINVRHED_LOCCODE = "LOCCode";
    public static final String FINVRHED_REPCODE = "RepCode";
    public static final String FINVRHED_REASON_CODE = "ReasonCode";
    public static final String FINVRHED_TOTAL_TAX = "TotalTax";
    public static final String FINVRHED_TOTAL_AMT = "TotalAmt";
    public static final String FINVRHED_TOTAL_DIS = "TotalDis";

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FINVRHED_TAX_REG = "TaxReg";
    public static final String FINVRHED_ADD_DATE = "AddDate";
    public static final String FINVRHED_ADD_MACH = "AddMach";
    public static final String FINVRHED_ADD_USER = "AddUser";
    public static final String FINVRHED_ROUTE_CODE = "RouteCode";
    public static final String FINVRHED_LONGITUDE = "Longitude";
    public static final String FINVRHED_LATITUDE = "Latitude";
    public static final String FINVRHED_IS_ACTIVE = "IsActive";
    public static final String FINVRHED_IS_SYNCED = "IsSync";
    public static final String FINVRHED_ADDRESS = "Address";
    public static final String FINVRHED_START_TIME = "StartTime";
    public static final String FINVRHED_END_TIME = "EndTime";
    public static final String FINVRHED_RETURN_TYPE = "ReturnType";
    public static final String FINVRHED_TOURCODE = "TourCode";
    public static final String FINVRHED_AREACODE = "AreaCode";
    public static final String FINVRHED_DRIVERCODE = "DriverCode";
    public static final String FINVRHED_HELPERCODE = "HelperCode";
    public static final String FINVRHED_LORRYCODE = "LorryCode";

    public static final String TABLE_FINVRDET = "FInvRDet";
    public static final String FINVRDET_ID = "id";
    public static final String FINVRDET_REFNO = "RefNo";
    public static final String FINVRDET_ITEMCODE = "ItemCode";
    public static final String FINVRDET_TAXCOMCODE = "TaxComCode";
    public static final String FINVRDET_PRILCODE = "PrilCode";
    public static final String FINVRDET_TXN_DATE = "TxnDate";
    public static final String FINVRDET_TXN_TYPE = "TxnType";
    public static final String FINVRDET_COST_PRICE = "CostPrice";
    public static final String FINVRDET_SELL_PRICE = "SellPrice";
    public static final String FINVRDET_T_SELL_PRICE = "TSellPrice";
    public static final String FINVRDET_AMT = "Amt";
    public static final String FINVRDET_DIS_AMT = "DisAmt";
    public static final String FINVRDET_DIS_RATE = "DisRate";
    public static final String FINVRDET_TAX_AMT = "TaxAmt";
    public static final String FINVRDET_QTY = "Qty";
    public static final String FINVRDET_BAL_QTY = "BalQty";
    public static final String FINVRDET_IS_ACTIVE = "IsActive";
    public static final String FINVRDET_SEQNO = "SeqNo";
    public static final String FINVRDET_REASON_CODE = "ReasonCode";
    public static final String FINVRDET_REASON_NAME = "ReasonName";
    public static final String FINVRDET_RETURN_TYPE = "ReturnType";
    public static final String FINVRDET_FREE_QTY = "ReturnFreeQty";
    public static final String FINVRDET_PRICE = "price";
    public static final String FINVRDET_CHANGED_PRICE = "ChangedPrice";

    public static final String TABLE_FPRODUCT = "fProducts";
    public static final String FPRODUCT_ID = "id";
    public static final String FPRODUCT_ITEMCODE = "itemcode";
    public static final String FPRODUCT_ITEMNAME = "itemname";
    public static final String FPRODUCT_PRICE = "price";
    public static final String FPRODUCT_QOH = "qoh";
    public static final String FPRODUCT_QTY = "qty";

    //------------------ temp table crate for PreSales detail data saved------------------------------------
    public static final String TABLE_FPRODUCT_PRE = "fProducts_pre";
    public static final String FPRODUCT_ID_PRE = "id";
    public static final String FPRODUCT_ITEMCODE_PRE = "itemcode_pre";
    public static final String FPRODUCT_ITEMNAME_PRE = "itemname_pre";
    public static final String FPRODUCT_PRICE_PRE = "price_pre";
    public static final String FPRODUCT_QOH_PRE = "qoh_pre";
    public static final String FPRODUCT_QTY_PRE = "qty_pre";
    public static final String FPRODUCT_NOUCASE_PRE = "nou_case";

    // --------------------- nuwan 14/08/2018 --------------------------------------------------------------

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- FItTourDiscDs table Details -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    // table attributes
    public static final String TABLE_FITTOURDISC = "fItTourDisc";
    public static final String FITTOURDISC_ID = "_id";
    public static final String FITTOURDISC_PRILCODE = "prilCode";
    public static final String FITTOURDISC_ITEMCODE = "itemCode";
    public static final String FITTOURDISC_QTYFROM = "qtyFrom";
    public static final String FITTOURDISC_QTYTO = "qtyTo";
    public static final String FITTOURDISC_PRILDISC = "prilDisc";
    public static final String FITTOURDISC_RECORDID = "recordId";


    // --------------------------------- 28/08/2018 ----------------------------------------------------------

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*- FDebTaxDS table Details -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    // table attributes
    public static final String TABLE_FDEBTAX = "fDebTax";
    public static final String FDEBTAX_ID = "_id";
    public static final String FDEBTAX_DEBCODE = "debCode";
    public static final String FDEBTAX_TAXCODE = "taxCode";
    public static final String FDEBTAX_TAXREGNO = "taxRegNo";
    public static final String FDEBTAX_RECORDID = "recordId";

    //--------------------------------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGE + " (" + TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAG_MSG + " TEXT," + TAG_STATUS + " TEXT," + TAG_FROM + " TEXT," + TAG_SUBJECT + " TEXT," + TAG_DATE_TIME + " TEXT);";
    private static final String CREATE_SERVER_DB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVER_DB + " (" + SERVER_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SERVER_DB_NAME + " TEXT); ";
    private static final String CREATE_FDEBTOR_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FDEBTOR + " (" + FDEBTOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDEBTOR_CODE + " TEXT, " + FDEBTOR_NAME + " TEXT, " + FDEBTOR_ADD1 + " TEXT, " + FDEBTOR_ADD2 + " TEXT, " + FDEBTOR_ADD3 + " TEXT, " + FDEBTOR_TELE + " TEXT, " + FDEBTOR_MOB + " TEXT, " + FDEBTOR_EMAIL + " TEXT, " + FDEBTOR_CREATEDATE + " TEXT, " + FDEBTOR_REM_DIS + " TEXT, " + FDEBTOR_TOWN_CODE + " TEXT, " + FDEBTOR_AREA_CODE + " TEXT, " + FDEBTOR_DEB_CAT_CODE + " TEXT, " + FDEBTOR_DBGR_CODE + " TEXT, " + FDEBTOR_DEB_CLS_CODE + " TEXT, " + FDEBTOR_STATUS + " TEXT, " + FDEBTOR_LYLTY + " TEXT, " + FDEBTOR_DEAL_CODE + " TEXT, " + FDEBTOR_ADD_USER + " TEXT, " + FDEBTOR_ADD_DATE_DEB + " TEXT, " + FDEBTOR_ADD_MACH + " TEXT, " + FDEBTOR_RECORD_ID + " TEXT, " + FDEBTOR_TIME_STAMP + " TEXT, " + FDEBTOR_CRD_PERIOD + " TEXT, " + FDEBTOR_CHK_CRD_PRD + " TEXT, " + FDEBTOR_CRD_LIMIT + " TEXT, " + FDEBTOR_CHK_CRD_LIMIT + " TEXT, " + FDEBTOR_REP_CODE + " TEXT, " + FDEBTOR_RANK_CODE + " TEXT, " + FDEBTOR_TRAN_DATE + " TEXT, " + FDEBTOR_TRAN_BATCH + " TEXT, " + FDEBTOR_OUT_DIS + " TEXT, " + FDEBTOR_DEB_FAX + " TEXT, " + FDEBTOR_DEB_WEB + " TEXT, " + FDEBTOR_DEBCT_NAM + " TEXT, " + FDEBTOR_DEBCT_ADD1 + " TEXT, " + FDEBTOR_DEBCT_ADD2 + " TEXT, " + FDEBTOR_DEBCT_ADD3 + " TEXT, " + FDEBTOR_DEBCT_TELE + " TEXT, " + FDEBTOR_DEBCT_FAX + " TEXT, " + FDEBTOR_DEBCT_EMAIL + " TEXT, " + FDEBTOR_DEL_PERSN + " TEXT, " + FDEBTOR_DEL_ADD1 + " TEXT, " + FDEBTOR_DEL_ADD2 + " TEXT, " + FDEBTOR_DEL_ADD3 + " TEXT, " + FDEBTOR_DEL_TELE + " TEXT, " + FDEBTOR_DEL_FAX + " TEXT, " + FDEBTOR_DEL_EMAIL + " TEXT, " + FDEBTOR_DATE_OFB + " TEXT, " + FDEBTOR_TAX_REG + " TEXT, " + FDEBTOR_CUSDISPER + " TEXT, " + FDEBTOR_PRILLCODE + " TEXT, " + FDEBTOR_CUSDISSTAT + " TEXT, " + FDEBTOR_BUS_RGNO + " TEXT, " + FDEBTOR_POSTCODE + " TEXT, " + FDEBTOR_GEN_REMARKS + " TEXT, " + FDEBTOR_BRANCODE + " TEXT, " + FDEBTOR_BANK + " TEXT, " + FDEBTOR_BRANCH + " TEXT, " + FDEBTOR_ACCTNO + " TEXT, " + FDEBTOR_LATITUDE + " TEXT, " + FDEBTOR_LONGITUDE + " TEXT, " + FDEBTOR_CUS_VATNO + " TEXT, " + FDEBTOR_NIC + " TEXT, " + FDEBTOR_BIS_REG + " TEXT, "  + FDEBTOR_SUMMARY + " TEXT); ";
    private static final String INDEX_DEBTOR = "CREATE UNIQUE INDEX IF NOT EXISTS ui_debtor ON " + TABLE_FDEBTOR + " (DebCode);";
    private static final String CREATE_FSIZE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FSIZE + " (" + FSIZE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSIZE_CODE + " TEXT, " + FSIZE_DESC + " TEXT); ";
    private static final String CREATE_FGRCOMBDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FGRCOMBDET + " (" + FSIZE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FGRCOMBDET_SIZECODE + " TEXT, " + FGRCOMBDET_GROUPCODE + " TEXT); ";
    private static final String CREATE_FTERMS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTERMS + " (" + FTERMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTERMS_CODE + " TEXT, " + FTERMS_DES + " TEXT, " + FTERMS_DISPER + " TEXT); ";
    private static final String CREATE_FSIZECOMB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FSIZECOMB + " (" + FSIZECOMB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSIZECOMB_ITEMCODE + " TEXT, " + FSIZECOMB_GROUPCODE + " TEXT, " + FSIZECOMB_DEG_PRICE + " TEXT, " + FSIZECOMB_OBS_PRICE + " TEXT, " + FSIZECOMB_PRICE + " TEXT, " + FSIZECOMB_SIZECODE + " TEXT); ";
    private static final String INDEX_FSIZECOMB = "CREATE UNIQUE INDEX IF NOT EXISTS ui_sizecomb ON " + TABLE_FSIZECOMB + " (GroupCode, ItemCode, SizeCode);";
    private static final String CREATE_FCRCOMB_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FCRCOMB + " (" + FCRCOMB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FCRCOMB_CODE + " TEXT, " + FCRCOMB_DEBCODE + " TEXT, " + FCRCOMB_CHKCRDLMT + " TEXT, " + FCRCOMB_CHKCRDPRD + " TEXT, " + FCRCOMB_CRD_PERIOD + " TEXT, " + FCRCOMB_GROUPCODE + " TEXT, " + FCRCOMB_REPCODE + " TEXT, " + FCRCOMB_TERMCODE + " TEXT, " + FCRCOMB_COM_DIS + " TEXT, " + FCRCOMB_CRDLIMIT + " TEXT); ";
    private static final String CREATE_FTRANSOHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTRANSOHED + " (" + FTRANSOHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTRANSOHED_REFNO + " TEXT, " + FTRANSOHED_REFNO1 + " TEXT, " + FTRANSOHED_TXNDATE + " TEXT, " + FTRANSOHED_MANUREF + " TEXT, " + FTRANSOHED_COSTCODE + " TEXT, " + FTRANSOHED_REMARKS + " TEXT, " + FTRANSOHED_TXNTYPE + " TEXT, " + FTRANSOHED_TOTALAMT + " TEXT, " + FTRANSOHED_CURCODE + " TEXT, " + FTRANSOHED_CURRATE + " TEXT, " + FTRANSOHED_DEBCODE + " TEXT, " + FTRANSOHED_REPCODE + " TEXT, " + FTRANSOHED_BTOTALDIS + " TEXT, " + FTRANSOHED_BTOTALTAX + " TEXT, " + FTRANSOHED_BTOTALAMT + " TEXT, " + FTRANSOHED_TAXREG + " TEXT, " + FTRANSOHED_CONTACT + " TEXT, " + FTRANSOHED_CUSADD1 + " TEXT, " + FTRANSOHED_CUSADD2 + " TEXT, " + FTRANSOHED_CUSADD3 + " TEXT, " + FTRANSOHED_CUSTELE + " TEXT, " + FTRANSOHED_ADDMACH + " TEXT, " + FTRANSOHED_IS_SYNCED + " TEXT, " + FTRANSOHED_IS_ACTIVE + " TEXT, " + FTRANSOHED_BPTOTALDIS + " TEXT, " + FTRANSOHED_TOTALDIS + " TEXT, " + FTRANSOHED_TOTALTAX + " TEXT, " + FTRANSOHED_PTOTALDIS + " TEXT, " + FTRANSOHED_LONGITUDE + " TEXT, " + FTRANSOHED_LATITUDE + " TEXT, " + FTRANSOHED_START_TIMESO + " TEXT, " + FTRANSOHED_END_TIMESO + " TEXT, " + FTRANSOHED_TOTQTY + " TEXT, " + FTRANSOHED_TOTFREE + " TEXT DEFAULT '0', " + FTRANSOHED_ADDRESS + " TEXT, " + FTRANSOHED_TOURCODE + " TEXT, " + FTRANSOHED_LOCCODE + " TEXT, " + FTRANSOHED_AREACODE + " TEXT, " + FTRANSOHED_ROUTECODE + " TEXT, " + FTRANSOHED_TXNDELDATE + " TEXT); ";
    private static final String CREATE_FAPPRORDHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FAPPRORDHED + " (" + FAPPRORDHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FAPPRORDHED_REFNO + " TEXT, " + FAPPRORDHED_TOTALAMT + " TEXT, " + FAPPRORDHED_DEBCODE + " TEXT); ";
    private static final String CREATE_FTRANSODET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FTRANSODET + " (" + FTRANSODET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTRANSODET_REFNO + " TEXT, " + FTRANSODET_TXNDATE + " TEXT, " + FTRANSODET_LOCCODE + " TEXT, " + FTRANSODET_TXNTYPE + " TEXT, " + FTRANSODET_SEQNO + " TEXT, " + FTRANSODET_ITEMCODE + " TEXT, " + FTRANSODET_QTY + " TEXT, " + FTRANSODET_COSTPRICE + " TEXT, " + FTRANSODET_AMT + " TEXT, " + FTRANSODET_BALQTY + " TEXT, " + FTRANSODET_TAXCOMCODE + " TEXT, " + FTRANSODET_BSELLPRICE + " TEXT, " + FTRANSODET_SELLPRICE + " TEXT, " + FTRANSODET_BTSELLPRICE + " TEXT, " + FTRANSODET_TSELLPRICE + " TEXT, " + FTRANSODET_PRILCODE + " TEXT, " + FTRANSODET_BDISAMT + " TEXT, " + FTRANSODET_DISAMT + " TEXT, " + FTRANSODET_PDISAMT + " TEXT, " + FTRANSODET_BPDISAMT + " TEXT, " + FTRANSODET_BTAXAMT + " TEXT, " + FTRANSODET_TAXAMT + " TEXT, " + FTRANSODET_BAMT + " TEXT, " + FTRANSODET_PICE_QTY + " TEXT, " + FTRANSODET_IS_ACTIVE + " TEXT, " + FTRANSODET_IS_SYNCED + " TEXT, " + FTRANSODET_QOH + " TEXT, " + FTRANSODET_TYPE + " TEXT, " + FTRANSODET_DISVALAMT + " TEXT DEFAULT '0', " + FTRANSODET_COMP_DISPER + " TEXT, " + FTRANSODET_BRAND_DISPER + " TEXT, " + FTRANSODET_BRAND_DIS + " TEXT, " + FTRANSODET_SCH_DISPER + " TEXT, " + FTRANSODET_PRICE + " TEXT, " + FTRANSODET_DISCTYPE + " TEXT, " + FTRANSODET_COMP_DIS + " TEXT, " + FTRANSODET_ORG_PRICE + " TEXT, " + FTRANSODET_DISFLAG + " TEXT, " + FTRANSODET_QTY_SLAB_DISC + " TEXT ); ";
    // create String
    private static final String CREATE_FCONTROL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FCONTROL + " (" + FCONTROL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FCONTROL_COM_NAME + " TEXT, " + FCONTROL_COM_ADD1 + " TEXT, " + FCONTROL_COM_ADD2 + " TEXT, " + FCONTROL_COM_ADD3 + " TEXT, " + FCONTROL_COM_TEL1 + " TEXT, " + FCONTROL_COM_TEL2 + " TEXT, " + FCONTROL_COM_FAX + " TEXT, " + FCONTROL_COM_EMAIL + " TEXT, " + FCONTROL_COM_WEB + " TEXT, " + FCONTROL_FYEAR + " TEXT, " + FCONTROL_TYEAR + " TEXT, " + FCONTROL_COM_REGNO + " TEXT, " + FCONTROL_FTXN + " TEXT, " + FCONTROL_TTXN + " TEXT, " + FCONTROL_CRYSTALPATH + " TEXT, " + FCONTROL_VATCMTAXNO + " TEXT, " + FCONTROL_NBTCMTAXNO + " TEXT, " + FCONTROL_SYSTYPE + " TEXT, " + FCONTROL_DEALCODE + " TEXT, " + FCONTROL_BASECUR + " TEXT, " + FCONTROL_COMDISPER + " TEXT, " + FCONTROL_BALGCRLM + " TEXT, " + FCONTROL_CONAGE1 + " TEXT, " + FCONTROL_CONAGE2 + " TEXT, " + FCONTROL_CONAGE3 + " TEXT, " + FCONTROL_CONAGE4 + " TEXT, " + FCONTROL_CONAGE5 + " TEXT, " + FCONTROL_CONAGE6 + " TEXT, " + FCONTROL_CONAGE7 + " TEXT, " + FCONTROL_CONAGE8 + " TEXT, " + FCONTROL_CONAGE9 + " TEXT, " + FCONTROL_CONAGE10 + " TEXT, " + FCONTROL_CONAGE11 + " TEXT, " + FCONTROL_CONAGE12 + " TEXT, " + FCONTROL_CONAGE13 + " TEXT, " + FCONTROL_CONAGE14 + " TEXT, " + FCONTROL_SALESACC + " TEXT ); ";
    // create String
    private static final String CREATE_FCOMPANYSETTING_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FCOMPANYSETTING + " (" + FCOMPANYSETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FCOMPANYSETTING_SETTINGS_CODE + " TEXT, " + FCOMPANYSETTING_GRP + " TEXT, " + FCOMPANYSETTING_LOCATION_CHAR + " TEXT, " + FCOMPANYSETTING_CHAR_VAL + " TEXT, " + FCOMPANYSETTING_NUM_VAL + " TEXT, " + FCOMPANYSETTING_REMARKS + " TEXT, " + FCOMPANYSETTING_TYPE + " TEXT, " + FCOMPANYSETTING_COMPANY_CODE + " TEXT); ";
    // create String
    private static final String CREATE_FROUTE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FROUTE + " (" + FROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FROUTE_REPCODE + " TEXT, " + FROUTE_ROUTECODE + " TEXT, " + FROUTE_ROUTE_NAME + " TEXT, " + FROUTE_RECORDID + " TEXT, " + FROUTE_ADDDATE + " TEXT, " + FROUTE_ADD_MACH + " TEXT, " + FROUTE_ADD_USER + " TEXT, " + FROUTE_AREACODE + " TEXT, " + FROUTE_DEALCODE + " TEXT, " + FROUTE_FREQNO + " TEXT, " + FROUTE_KM + " TEXT, " + FROUTE_MINPROCALL + " TEXT, " + FROUTE_RDALORATE + " TEXT, " + FROUTE_RDTARGET + " TEXT, " + FROUTE_REMARKS + " TEXT, " + FROUTE_STATUS + " TEXT, " + FROUTE_TONNAGE + " TEXT); ";
    // create String
    private static final String CREATE_FBANK_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FBANK + " (" + FBANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FBANK_RECORD_ID + " TEXT, " + FBANK_BANK_CODE + " TEXT, " + FBANK_BANK_NAME + " TEXT, " + FBANK_BANK_ACC_NO + " TEXT, " + FBANK_BRANCH + " TEXT, " + FBANK_ADD1 + " TEXT, " + FBANK_ADD2 + " TEXT, " + FBANK_ADD_MACH + " TEXT, " + FBANK_ADD_USER + " TEXT, " + FBANK_ADD_DATE + " TEXT); ";
    // create String
    private static final String CREATE_FREASON_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FREASON + " (" + FREASON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FREASON_ADD_DATE + " TEXT, " + FREASON_ADD_MACH + " TEXT, " + FREASON_ADD_USER + " TEXT, " + FREASON_CODE + " TEXT, " + FREASON_NAME + " TEXT, " + FREASON_REATCODE + " TEXT, " + FREASON_RECORD_ID + " TEXT, " + FREASON_TYPE + " TEXT); ";
    // create String
    private static final String CREATE_FEXPENSE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FEXPENSE + " (" + FEXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FEXPENSE_CODE + " TEXT, " + FEXPENSE_GRP_CODE + " TEXT, " + FEXPENSE_NAME + " TEXT, " + FEXPENSE_RECORDID + " TEXT, " + FEXPENSE_STATUS + " TEXT, " + FEXPENSE_ADD_MACH + " TEXT, " + FEXPENSE_ADD_DATE + " TEXT, " + FEXPENSE_ADD_USER + " TEXT); ";
    // create String
    private static final String CREATE_FTOWN_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTOWN + " (" + FTOWN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTOWN_RECORDID + " TEXT, " + FTOWN_CODE + " TEXT, " + FTOWN_NAME + " TEXT, " + FTOWN_DISTR_CODE + " TEXT, " + FTOWN_ADDDATE + " TEXT, " + FTOWN_ADD_MACH + " TEXT, " + FTOWN_ADD_USER + " TEXT); ";
    // create String
    private static final String CREATE_FTRGCAPUL_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTRGCAPUL + " (" + FTRGCAPUL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTRGCAPUL_ADD_DATE + " TEXT, " + FTRGCAPUL_ADD_MACH + " TEXT, " + FTRGCAPUL_ADD_USER + " TEXT, " + FTRGCAPUL_DEAL_CODE + " TEXT, " + FTRGCAPUL_MONTH + " TEXT, " + FTRGCAPUL_QTY + " TEXT, " + FTRGCAPUL_RECORDID + " TEXT, " + FTRGCAPUL_REP_CODE + " TEXT, " + FTRGCAPUL_SKU_CODE + " TEXT, " + FTRGCAPUL_YEAR + " TEXT); ";
    // create String
    private static final String CREATE_FTYPE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTYPE + " (" + FTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTYPE_ADD_DATE + " TEXT, " + FTYPE_ADD_MACH + " TEXT, " + FTYPE_ADD_USER + " TEXT, " + FTYPE_RECORDID + " TEXT, " + FTYPE_CODE + " TEXT, " + FTYPE_NAME + " TEXT); ";
    // create String
    private static final String CREATE_FSUBBRAND_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSUBBRAND + " (" + FSUBBRAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSUBBRAND_ADD_DATE + " TEXT, " + FSUBBRAND_ADD_MACH + " TEXT, " + FSUBBRAND_ADD_USER + " TEXT, " + FSUBBRAND_RECORDID + " TEXT, " + FSUBBRAND_CODE + " TEXT, " + FSUBBRAND_NAME + " TEXT); ";
    // create String
    private static final String CREATE_FGROUP_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FGROUP + " (" + FGROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FGROUP_ADD_DATE + " TEXT, " + FGROUP_ADD_MACH + " TEXT, " + FGROUP_ADD_USER + " TEXT, " + FGROUP_CODE + " TEXT, " + FGROUP_NAME + " TEXT, " + FGROUP_RECORDID + " TEXT); ";
    // create String
    private static final String CREATE_DOWNLOAD_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_DOWNLOAD + " (" + DOWNLOAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DOWNLOAD_COUNT + " TEXT, " + DOWNLOADED_COUNT + " TEXT, " + DOWNLOAD_TITLE + " TEXT); ";

    // create String
    private static final String CREATE_FSKU_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSKU + " (" + FSKU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSKU_ADD_DATE + " TEXT, " + FSKU_ADD_MACH + " TEXT, " + FSKU_ADD_USER + " TEXT, " + FSKU_BRAND_CODE + " TEXT, " + FSKU_GROUP_CODE + " TEXT, " + FSKU_ITEM_STATUS + " TEXT, " + FSKU_MUST_SALE + " TEXT, " + FSKU_NOUCASE + " TEXT, " + FSKU_ORDSEQ + " TEXT, " + FSKU_RECORDID + " TEXT, " + FSKU_SUB_BRAND_CODE + " TEXT, " + FSKU_CODE + " TEXT, " + FSKU_NAME + " TEXT, " + FSKU_SIZE_CODE + " TEXT, " + FSKU_TONNAGE + " TEXT, " + FSKU_TYPE_CODE + " TEXT, " + FSKU_UNIT + " TEXT); ";
    // create String
    private static final String CREATE_FBRAND_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FBRAND + " (" + FBRAND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FBRAND_ADD_MACH + " TEXT, " + FBRAND_ADD_USER + " TEXT, " + FBRAND_CODE + " TEXT, " + FBRAND_NAME + " TEXT, " + FBRAND_RECORDID + " TEXT); ";
    private static final String CREATE_FORDHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDHED + " (" + FORDHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FORDHED_ADD_MACH + " TEXT, " + FORDHED_ADD_DATE + " TEXT," + FORDHED_ADD_USER + " TEXT, " + FORDHED_APP_DATE + " TEXT, " + FORDHED_ADDRESS + " TEXT, " + FORDHED_APPSTS + " TEXT, " + FORDHED_APP_USER + " TEXT, " + FORDHED_BP_TOTAL_DIS + " TEXT, " + FORDHED_B_TOTAL_AMT + " TEXT, " + FORDHED_B_TOTAL_DIS + " TEXT, " + FORDHED_B_TOTAL_TAX + " TEXT, " + FORDHED_COST_CODE + " TEXT, " + FORDHED_CUR_CODE + " TEXT, " + FORDHED_CUR_RATE + " TEXT, " + FORDHED_DEB_CODE + " TEXT, " + FORDHED_LOC_CODE + " TEXT, " + FORDHED_MANU_REF + " TEXT, " + FORDHED_DIS_PER + " TEXT, " + FORDHED_RECORD_ID + " TEXT, " + FORDHED_REFNO + " TEXT, " + FORDHED_REMARKS + " TEXT, " + FORDHED_REPCODE + " TEXT, " + FORDHED_TAX_REG + " TEXT, " + FORDHED_TIMESTAMP_COLUMN + " TEXT, " + FORDHED_TOTAL_TAX + " TEXT, " + FORDHED_TOTAL_AMT + " TEXT, " + FORDHED_TOTALDIS + " TEXT, " + FORDHED_TOTAL_ITM_DIS + " TEXT, " + FORDHED_TOT_MKR_AMT + " TEXT, " + FORDHED_TXN_TYPE + " TEXT, " + FORDHED_TXN_DATE + " TEXT, " + FORDHED_LONGITUDE + " TEXT, " + FORDHED_LATITUDE + " TEXT, " + FORDHED_START_TIME_SO + " TEXT, " + FORDHED_IS_SYNCED + " TEXT, " + FORDHED_IS_ACTIVE + " TEXT, " + FORDHED_DELV_DATE + " TEXT, " + FORDHED_ROUTE_CODE + " TEXT, " + FORDHED_HED_DIS_VAL + " TEXT, " + FORDHED_HED_DIS_PER_VAL + " TEXT," + FORDHED_PAYMENT_TYPE + " TEXT," + FORDHED_END_TIME_SO + " TEXT," + FORDHED_GROUPCODE + " TEXT, " + FORDHED_ORD_REFNO + " TEXT, " + FORDHED_ORD_TYPE + " TEXT, " + FORDHED_FIPAY_TYPE + " TEXT, " + FORDHED_FISS_TYPE + " TEXT, " + FORDHED_FIPER_CAL + " TEXT, " + FORDHED_FIAMT_CAL + " TEXT, " + FORDHED_FICASH_DISPER + " TEXT, " + FORDHED_FICASH_DISAMT + " TEXT, " + FORDHED_FIPER + " TEXT, " + FORDHED_FIAMT + " TEXT, " + FORDHED_TOTAL_QTY + " TEXT, " + FORDHED_FICAMT + " TEXT, " + FORDHED_FIQTY + " TEXT, " + FORDHED_FICHG_AMT + " TEXT); ";
    private static final String CREATE_FORDDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FORDDET + " (" + FORDDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FORDDET_AMT + " TEXT, " + FORDDET_B_AMT + " TEXT, " + FORDDET_B_SELL_PRICE + " TEXT, " + FORDDET_DIS_AMT + " TEXT, " + FORDDET_DIS_PER + " TEXT, " + FORDDET_ITEM_CODE + " TEXT, " + FORDDET_QTY + " TEXT, " + FORDDET_PICE_QTY + " TEXT, " + FORDDET_TYPE + " TEXT, " + FORDDET_REFNO + " TEXT, " + FORDDET_SELL_PRICE + " TEXT, " + FORDDET_SEQNO + " TEXT, " + " TEXT, " + FORDDET_TXN_DATE + " TEXT, " + FORDDET_IS_ACTIVE + " TEXT, " + FORDDET_TXN_TYPE + " TEXT); ";
    private static final String CREATE_FINVHED_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINVHED + " (" + FINVHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FINVHED_REFNO + " TEXT, " + FINVHED_REFNO1 + " TEXT, " + FINVHED_TXNDATE + " TEXT, " + FINVHED_MANUREF + " TEXT, " + FINVHED_COSTCODE + " TEXT, " + FINVHED_CURCODE + " TEXT, " + FINVHED_CURRATE + " TEXT, " + FINVHED_DEBCODE + " TEXT, " + FINVHED_REMARKS + " TEXT, " + FINVHED_TXNTYPE + " TEXT, " + FINVHED_LOCCODE + " TEXT, " + FINVHED_REPCODE + " TEXT, " + FINVHED_CONTACT + " TEXT, " + FINVHED_CUSADD1 + " TEXT, " + FINVHED_CUSADD2 + " TEXT, " + FINVHED_CUSADD3 + " TEXT, " + FINVHED_CUSTELE + " TEXT, " + FINVHED_TOTALDIS + " TEXT, " + FINVHED_TOTALTAX + " TEXT, " + FINVHED_TAXREG + " TEXT, " + FINVHED_ADDUSER + " TEXT, " + FINVHED_ADDDATE + " TEXT, " + FINVHED_ADDMACH + " TEXT, " + FINVHED_START_TIME_SO + " TEXT, " + FINVHED_END_TIME_SO + " TEXT, " + FINVHED_TOTALAMT + " TEXT, " + FINVHED_LONGITUDE + " TEXT, " + FINVHED_LATITUDE + " TEXT, " + FINVHED_ADDRESS + " TEXT, " + FINVHED_IS_SYNCED + " TEXT, " + FINVHED_AREACODE + " TEXT, " + FINVHED_ROUTECODE + " TEXT, " + FINVHED_TOURCODE + " TEXT, " + FINVHED_IS_ACTIVE + " TEXT); ";
    private static final String CREATE_FINVDET_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_FINVDET + " (" + FINVDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FINVDET_AMT + " TEXT, " + FINVDET_BAL_QTY + " TEXT, " + FINVDET_B_AMT + " TEXT, " + FINVDET_B_SELL_PRICE + " TEXT, " + FINVDET_BT_TAX_AMT + " TEXT, " + FINVDET_BT_SELL_PRICE + " TEXT, " + FINVDET_DIS_AMT + " TEXT, " + FINVDET_DIS_PER + " TEXT, " + FINVDET_ITEM_CODE + " TEXT, " + FINVDET_PRIL_CODE + " TEXT, " + FINVDET_QTY + " TEXT, " + FINVDET_PICE_QTY + " TEXT, " + FINVDET_TYPE + " TEXT, " + FINVDET_RECORD_ID + " TEXT, " + FINVDET_REFNO + " TEXT, " + FINVDET_SELL_PRICE + " TEXT, " + FINVDET_SEQNO + " TEXT, " + FINVDET_TAX_AMT + " TEXT, " + FINVDET_TAX_COM_CODE + " TEXT, " + FINVDET_T_SELL_PRICE + " TEXT, " + FINVDET_TXN_DATE + " TEXT, " + FINVDET_IS_ACTIVE + " TEXT, " + FINVDET_TXN_TYPE + " TEXT," + FINVDET_COMDISPER + " TEXT DEFAULT '0'," + FINVDET_BRAND_DISPER + " TEXT DEFAULT '0'," + FINVDET_DISVALAMT + " TEXT DEFAULT '0'," + FINVDET_BRAND_DISC + " TEXT DEFAULT '0'," + FINVDET_QOH + " TEXT DEFAULT '0'," + FINVDET_FREEQTY + " TEXT DEFAULT '0'," + FINVDET_SCHDISPER + " TEXT DEFAULT '0'," + FINVDET_PRICE + " TEXT," + FINVDET_COMPDISC + " TEXT DEFAULT '0'); ";
    private static final String CREATE_STKIN_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSTKIN + " (" + FSTKIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSTKIN_REFNO + " TEXT," + FSTKIN_TXNDATE + " TEXT," + FSTKIN_STKREC_DATE + " TEXT," + FSTKIN_LOCCODE + " TEXT," + FSTKIN_ITEMCODE + " TEXT," + FSTKIN_INQTY + " TEXT," + FSTKIN_BALQTY + " TEXT," + FSTKIN_COSTPRICE + " TEXT," + FSTKIN_OTHCOST + " TEXT," + FSTKIN_STKRECNO + " TEXT," + FSTKIN_TXNTYPE + " TEXT); ";
    private static final String CREATE_FSIZEIN_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSIZEIN + " (" + FSIZEIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSIZEIN_REFNO + " TEXT," + FSIZEIN_TXNDATE + " TEXT," + FSIZEIN_STKRECDATE + " TEXT," + FSIZEIN_LOCCODE + " TEXT," + FSIZEIN_TXNTYPE + " TEXT," + FSIZEIN_STKRECNO + " TEXT," + FSIZEIN_ITEMCODE + " TEXT," + FSIZEIN_SIZECODE + " TEXT," + FSIZEIN_GROUPCODE + " TEXT," + FSIZEIN_QTY + " TEXT," + FSIZEIN_BALQTY + " TEXT," + FSIZEIN_COSTPRICE + " TEXT," + FSIZEIN_PRICE + " TEXT," + FSIZEIN_OTHCOST + " TEXT," + FSIZEIN_APPSTAT + " TEXT," + FSIZEIN_ORDREFNO + " TEXT," + FSIZEIN_ADDDATE + " TEXT," + FSIZEIN_MRPPRICE + " TEXT); ";
    private static final String CREATE_FSTKISS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSTKISS + " (" + FSTKISS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSIZEIN_REFNO + " TEXT," + FSTKISS_TXNDATE + " TEXT," + FSTKISS_LOCCODE + " TEXT," + FSTKISS_STKRECNO + " TEXT," + FSTKISS_STKTXNNO + " TEXT," + FSTKISS_STKTXNDATE + " TEXT," + FSTKISS_STKRECDATE + " TEXT," + FSTKISS_STKTXNTYPE + " TEXT," + FSTKISS_ITEMCODE + " TEXT," + FSTKISS_COSTPRICE + " TEXT," + FSTKISS_QTY + " TEXT); ";
    // create String
    private static final String CREATE_FCOMPANYBRANCH_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FCOMPANYBRANCH + " (" + FCOMPANYBRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FCOMPANYBRANCH_BRANCH_CODE + " TEXT, " + FCOMPANYBRANCH_RECORD_ID + " TEXT, " + FCOMPANYBRANCH_CSETTINGS_CODE + " TEXT, " + FCOMPANYBRANCH_NNUM_VAL + " TEXT," + FCOMPANYBRANCH_YEAR + " TEXT," + FCOMPANYBRANCH_MONTH + " TEXT);";
    private static final String CREATE_FSALREP_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FSALREP + " (" + FSALREP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FSALREP_ADDMACH + " TEXT, " + FSALREP_ADDUSER + " TEXT, " + FSALREP_PASSWORD + " TEXT, " + FSALREP_RECORDID + " TEXT, " + FSALREP_REPCODE + " TEXT, " + FSALREP_EMAIL + " TEXT, " + FSALREP_REPID + " TEXT, " + FSALREP_MOBILE + " TEXT, " + FSALREP_NAME + " TEXT, " + FSALREP_PREFIX + " TEXT, " + FSALREP_TELE + " TEXT, "
            + FSALREP_STATUS + " TEXT, "
            + FSALREP_LOCCODE + " TEXT, "
            + FSALREP_MACID + " TEXT); ";

    private static final String CREATE_FFREEDEB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEDEB + " (" + FFREEDEB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FFREEDEB_REFNO + " TEXT, " + FFREEDEB_DEB_CODE + " TEXT, " + FFREEDEB_ADD_USER + " TEXT, " + FFREEDEB_ADD_DATE + " TEXT, " + FFREEDEB_ADD_MACH + " TEXT, " + FFREEDEB_RECORD_ID + " TEXT, " + FFREEDEB_TIMESTAMP_COLUMN + " TEXT); ";
    // create String
    private static final String CREATE_FFREEDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEDET + " (" + FFREEDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FFREEDET_REFNO + " TEXT, " + FFREEDET_ITEM_CODE + " TEXT, " + FFREEDET_RECORD_ID + " TEXT); ";
    // create String
    private static final String CREATE_FFREEHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEHED + " (" + FFREEHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FFREEHED_REFNO + " TEXT, " + FFREEHED_TXNDATE + " TEXT, " + FFREEHED_DISC_DESC + " TEXT, " + FFREEHED_PRIORITY + " TEXT, " + FFREEHED_VDATEF + " TEXT, " + FFREEHED_VDATET + " TEXT, " + FFREEHED_REMARKS + " TEXT, " + FFREEHED_RECORD_ID + " TEXT, " + FFREEHED_ITEM_QTY + " TEXT, " + FFREEHED_FREE_IT_QTY + " TEXT, " + FFREEHED_COSTCODE + " TEXT, " + FFREEHED_FTYPE + " TEXT); ";
    // create String
    private static final String CREATE_FFREESLAB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREESLAB + " (" + FFREESLAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FFREESLAB_REFNO + " TEXT, " + FFREESLAB_QTY_F + " TEXT, " + FFREESLAB_QTY_T + " TEXT, " + FFREESLAB_FITEM_CODE + " TEXT, " + FFREESLAB_FREE_QTY + " TEXT, " + FFREESLAB_ADD_USER + " TEXT, " + FFREESLAB_ADD_DATE + " TEXT, " + FFREESLAB_ADD_MACH + " TEXT, " + FFREESLAB_RECORD_ID + " TEXT, " + FFREESLAB_TIMESTAP_COLUMN + " TEXT, " + FFREESLAB_SEQ_NO + " TEXT); ";
    // create String
    private static final String CREATE_FFREEITEM_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEITEM + " (" + FFREEITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FFREEITEM_REFNO + " TEXT, " + FFREEITEM_ITEMCODE + " TEXT, " + FFREEITEM_RECORD_ID + " TEXT); ";
    // create String
    private static final String CREATE_FITEM_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITEM + " (" + FITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITEM_AVG_PRICE + " TEXT, " + FITEM_BRAND_CODE + " TEXT, " + FITEM_GROUP_CODE + " TEXT, " + FITEM_ITEM_CODE + " TEXT, " + FITEM_ITEM_NAME + " TEXT, " + FITEM_ITEM_STATUS + " TEXT, " + FITEM_PRIL_CODE + " TEXT, " + " TEXT, " + FITEM_TAX_COM_CODE + " TEXT, " + FITEM_TYPE_CODE + " TEXT, "

            + FITEM_SCAT_CODE + " TEXT, " + FITEM_SUBCAT_CODE + " TEXT, " + FITEM_COLOR_CODE + " TEXT, " + FITEM_DISCOUNT + " TEXT, " + FITEM_CLASS_CODE + " TEXT, " + FITEM_IS_SIZE + " TEXT, " + FITEM_IS_DISCOUNT + " TEXT, " + FITEM_UNIT_CODE + " TEXT, " + FITEM_NOUCASE + " TEXT, " + FITEM_REORDER_LVL + " TEXT, " + FITEM_REORDER_QTY + " TEXT, " + FITEM_VEN_P_CODE + " TEXT); ";
    private static final String INDEX_FITEMS = "CREATE UNIQUE INDEX IF NOT EXISTS ui_item ON " + TABLE_FITEM + " (ItemCode);";
    // create String
    private static final String CREATE_FITEMLOC_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITEMLOC + " (" + FITEMLOC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITEMLOC_ITEM_CODE + " TEXT, " + FITEMLOC_LOC_CODE + " TEXT, " + FITEMLOC_QOH + " TEXT, " + FITEMLOC_RECORD_ID + " TEXT); ";
    private static final String INDEX_FITEMLOC = "CREATE UNIQUE INDEX IF NOT EXISTS ui_itemloc ON " + TABLE_FITEMLOC + " (ItemCode,LocCode);";
    // create String
    private static final String CREATE_FITEMPRI_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITEMPRI + " (" + FITEMPRI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITEMPRI_ADD_MACH + " TEXT, " + FITEMPRI_ADD_USER + " TEXT, " + FITEMPRI_ITEM_CODE + " TEXT, " + FITEMPRI_PRICE + " TEXT, " + FITEMPRI_PRIL_CODE + " TEXT, " + FITEMPRI_SKU_CODE + " TEXT, " + FITEMPRI_TXN_MACH + " TEXT, " + FITEMPRI_TXN_USER + " TEXT); ";
    private static final String INDEX_FITEMPRI = "CREATE UNIQUE INDEX IF NOT EXISTS ui_itempri ON " + TABLE_FITEMPRI + " (ItemCode,PrilCode);";
    // create String
    private static final String CREATE_FAREA_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FAREA + " (" + FAREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FAREA_ADD_MACH + " TEXT, " + FAREA_ADD_USER + " TEXT, " + FAREA_AREA_CODE + " TEXT, " + FAREA_AREA_NAME + " TEXT, " + FAREA_DEAL_CODE + " TEXT, " + FAREA_REQ_CODE + " TEXT); ";
    // create String
    private static final String CREATE_FLOCATIONS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FLOCATIONS + " (" + FLOCATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FLOCATIONS_ADD_MACH + " TEXT, " + FLOCATIONS_ADD_USER + " TEXT, " + FLOCATIONS_LOC_CODE + " TEXT, " + FLOCATIONS_LOC_NAME + " TEXT, " + FLOCATIONS_LOC_T_CODE + " TEXT, " + FLOCATIONS_REP_CODE + " TEXT); ";
    // create String
    private static final String CREATE_FDEALER_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDEALER + " (" + FDEALER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDEALER_ADD_DATE + " TEXT, " + FDEALER_ADD_MACH + " TEXT, " + FDEALER_ADD_USER + " TEXT, " + FDEALER_CUS_TYP_CODE + " TEXT, " + FDEALER_DEAL_ADD1 + " TEXT, " + FDEALER_DEAL_ADD2 + " TEXT, " + FDEALER_DEAL_ADD3 + " TEXT, " + FDEALER_DEAL_CODE + " TEXT, " + FDEALER_DEAL_EMAIL + " TEXT, " + FDEALER_DEAL_GD_CODE + " TEXT, " + FDEALER_DEAL_MOB + " TEXT, " + FDEALER_DEAL_NAME + " TEXT, " + FDEALER_DEAL_TELE + " TEXT, " + FDEALER_DISTANCE + " TEXT, " + FDEALER_STATUS + " TEXT," + FDEALER_PREFIX + " TEXT); ";
    // create String
    private static final String CREATE_FMERCH_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FMERCH + " (" + FMERCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FMERCH_CODE + " TEXT, " + FMERCH_NAME + " TEXT, " + FMERCH_ADD_USER + " TEXT, " + FMERCH_ADD_DATE + " TEXT, " + FMERCH_ADD_MACH + " TEXT, " + FMERCH_RECORD_ID + " TEXT, " + FMERCH_TIMESTAMP_COLUMN + " TEXT); ";
    // create String
    private static final String CREATE_FFREEMSLAB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FFREEMSLAB + " (" + FFREEMSLAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FFREEMSLAB_REFNO + " TEXT, " + FFREEMSLAB_QTY_F + " TEXT, " + FFREEMSLAB_QTY_T + " TEXT, " + FFREEMSLAB_ITEM_QTY + " TEXT, " + FFREEMSLAB_FREE_IT_QTY + " TEXT, " + FFREEMSLAB_ADD_USER + " TEXT, " + FFREEMSLAB_ADD_DATE + " TEXT, " + FFREEMSLAB_ADD_MACH + " TEXT, " + FFREEMSLAB_RECORD_ID + " TEXT, " + FFREEMSLAB_TIMESTAMP_COLUMN + " TEXT, " + FFREEMSLAB_SEQ_NO + " TEXT); ";
    // create String
    private static final String CREATE_FROUTEDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FROUTEDET + " (" + FROUTEDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FROUTEDET_DEB_CODE + " TEXT, " + FROUTEDET_ROUTE_CODE + " TEXT); ";
    // create Index String
    private static final String INDEX_ROUTEDET = "CREATE UNIQUE INDEX IF NOT EXISTS ui_debtor ON " + TABLE_FROUTEDET + " (DebCode,RouteCode);";
    // create String
    private static final String CREATE_FDISCVHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCVHED + " (" + FDISCVHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCVHED_REF_NO + " TEXT, " + FDISCVHED_TXN_DATE + " TEXT, " + FDISCVHED_DISC_DESC + " TEXT, " + FDISCVHED_PRIORITY + " TEXT, " + FDISCVHED_DIS_TYPE + " TEXT, " + FDISCVHED_V_DATE_F + " TEXT, " + FDISCVHED_V_DATE_T + " TEXT, " + FDISCVHED_REMARKS + " TEXT); ";
    // create String
    private static final String CREATE_FDISCVDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCVDET + " (" + FDISCVDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCVDET_REF_NO + " TEXT, " + FDISCVDET_VALUE_F + " TEXT, " + FDISCVDET_VALUE_T + " TEXT, " + FDISCVDET_DISPER + " TEXT, " + FDISCVDET_DIS_AMT + " TEXT); ";
    // create String
    private static final String CREATE_FDISCVDEB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCVDEB + " (" + FDISCVDEB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCVDEB_REF_NO + " TEXT, " + FDISCVDED_DEB_CODE + " TEXT); ";
    // create String
    private static final String CREATE_FDISCHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCHED + " (" + FDISCHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCHED_REF_NO + " TEXT, " + FDISCHED_TXN_DATE + " TEXT, " + FDISCHED_DISC_DESC + " TEXT, " + FDISCHED_PRIORITY + " TEXT, " + FDISCHED_DIS_TYPE + " TEXT, " + FDISCHED_V_DATE_F + " TEXT, " + FDISCHED_V_DATE_T + " TEXT, " + FDISCHED_REMARK + " TEXT, " + FDISCHED_ADD_USER + " TEXT, " + FDISCHED_ADD_DATE + " TEXT, " + FDISCHED_ADD_MACH + " TEXT, " + FDISCHED_RECORD_ID + " TEXT, " + FDISCHED_TIMESTAMP_COLUMN + " TEXT); ";
    // create String
    private static final String CREATE_FDISCDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCDET + " (" + FDISCDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCDET_REF_NO + " TEXT, " + FDISCDET_ITEM_CODE + " TEXT, " + FDISCDET_RECORD_ID + " TEXT, " + FDISCHED_TIEMSTAMP_COLUMN + " TEXT); ";
    // create String
    private static final String CREATE_FDISCDEB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCDEB + " (" + FDISCDEB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCDEB_REF_NO + " TEXT, " + FDISCDEB_DEB_CODE + " TEXT, " + FDISCDET_RECORD_ID + " TEXT, " + FDISCHED_TIEMSTAMP_COLUMN + " TEXT); ";
    // create String
    private static final String CREATE_FDISCSLAB_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISCSLAB + " (" + FDISCSLAB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISCSLAB_REF_NO + " TEXT, " + FDISCSLAB_SEQ_NO + " TEXT, " + FDISCSLAB_QTY_F + " TEXT, " + FDISCSLAB_QTY_T + " TEXT, " + FDISCSLAB_DIS_PER + " TEXT, " + FDISCSLAB_DIS_AMUT + " TEXT, " + FDISCSLAB_RECORD_ID + " TEXT, " + FDISCSLAB_TIMESTAMP_COLUMN + " TEXT); ";
    // create String
    private static final String CREATE_FITENRHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITENRHED + " (" + FITENRHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITENRHED_COST_CODE + " TEXT, " + FITENRHED_DEAL_CODE + " TEXT, " + FITENRHED_MONTH + " TEXT, " + FITENRHED_REF_NO + " TEXT, " + FITENRHED_REMARKS1 + " TEXT, " + FITENRHED_REP_CODE + " TEXT, " + FITENRHED_YEAR + " TEXT); ";
    // create String
    private static final String CREATE_FITENRDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITENRDET + " (" + FITENRDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITENRDET_NO_OUTLET + " TEXT, " + FITENRDET_NO_SHCUCAL + " TEXT, " + FITENRDET_RD_TARGET + " TEXT, " + FITENRDET_REF_NO + " TEXT, " + FITENRDET_REMARKS + " TEXT, " + FITENRDET_ROUTE_CODE + " TEXT, " + FITENRDET_TXN_DATE + " TEXT); ";
    // create String
    private static final String CREATE_FITEDEBDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITEDEBDET + " (" + FITEDEBDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITEDEBDET_DEB_CODE + " TEXT, " + FITEDEBDET_REF_NO + " TEXT, " + FITEDEBDET_ROUTE_CODE + " TEXT, " + FITEDEBDET_TXN_DATE + " TEXT); ";
    // create String
    private static final String CREATE_FINVHEDL3_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVHEDL3 + " (" + FINVHEDL3_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FINVHEDL3_DEB_CODE + " TEXT, " + FINVHEDL3_REF_NO + " TEXT, " + FINVHEDL3_REF_NO1 + " TEXT, " + FINVHEDL3_TOTAL_AMT + " TEXT, " + FINVHEDL3_TOTAL_TAX + " TEXT, " + FINVHEDL3_TXN_DATE + " TEXT); ";
    // create String
    private static final String CREATE_FINVDETL3_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVDETL3 + " (" + FINVDETL3_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FINVDETL3_AMT + " TEXT, " + FINVDETL3_ITEM_CODE + " TEXT, " + FINVDETL3_QTY + " TEXT, " + FINVDETL3_REF_NO + " TEXT, " + FINVDETL3_SEQ_NO + " TEXT, " + FINVDETL3_TAX_AMT + " TEXT, " + FINVDETL3_TAX_COM_CODE + " TEXT, " + FINVDETL3_TXN_DATE + " TEXT); ";
    // create String
    private static final String CREATE_FTRANDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTRANDET + " (" + FTRANDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTRANDET_REFNO + " TEXT, " + FTRANDET_TXNDATE + " TEXT, " + FTRANDET_LOCCODE + " TEXT, " + FTRANDET_TXNTYPE + " TEXT, " + FTRANDET_SEQNO + " TEXT, " + FTRANDET_ITEMCODE + " TEXT, " + FTRANDET_QTY + " TEXT, " + FTRANDET_AMT + " TEXT," + FTRANDET_CASEQTY + " TEXT," + FTRANDET_PICEQTY + " TEXT," + REMQTY + " TEXT); ";
    // create String
    private static final String CREATE_FTRANHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTRANHED + " (" + FTRANHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTRANHED_REFNO + " TEXT, " + FTRANHED_TXNDATE + " TEXT, " + FTRANHED_MANUREF + " TEXT, " + FTRANHED_COSTCODE + " TEXT, " + FTRANHED_REMARKS + " TEXT, " + FTRANHED_TXNTYPE + " TEXT, " + FTRANHED_TOTALAMT + " TEXT, " + FTRANHED_DELPERSN + " TEXT," + FTRANHED_DELADD1 + " TEXT," + FTRANHED_DELADD2 + " TEXT, " + FTRANHED_DELADD3 + " TEXT, " + FTRANHED_VEHICALNO + " TEXT, " + FTRANHED_PRTCOPY + " TEXT, " + FTRANHED_GLPOST + " TEXT, " + FTRANHED_GLBATCH + " TEXT, " + FTRANHED_ADDUSER + " TEXT," + FTRANHED_ADDDATE + " TEXT," + FTRANHED_ADDMACH + " TEXT," + FTRANHED_DEALCODE + " TEXT," + FTRANHED_LONGITUDE + " TEXT," + FTRANHED_LATITUDE + " TEXT," + FTRANHED_LOCFROM + " TEXT," + FTRANHED_LOCTO + " TEXT," + FTRANHED_IS_SYNCED + " TEXT," + FTRANHED_ACTIVE_STATE + " TEXT); ";

    // ------------------------------------- nuwan 14/08/2018 -----------------------------------------------------------------------

    private static final String CREATE_FITTOURDISC_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FITTOURDISC + " (" + FITTOURDISC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FITTOURDISC_PRILCODE + " TEXT, " + FITTOURDISC_ITEMCODE + " TEXT, " + FITTOURDISC_QTYFROM + " TEXT, " + FITTOURDISC_QTYTO + " TEXT, " + FITTOURDISC_PRILDISC + " TEXT, " + FITTOURDISC_RECORDID + " TEXT); ";

    // -----------------------------------------28/08/2018 --------------------------------------------------------------------------

    private static final String CREATE_FDEBTAX_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDEBTAX + " (" + FDEBTAX_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDEBTAX_DEBCODE + " TEXT, " + FDEBTAX_TAXCODE + " TEXT, " + FDEBTAX_TAXREGNO + " TEXT, " + FDEBTAX_RECORDID + " TEXT); ";

    // -------------------------------------------------------------------------------------------------------------------------------
    private static final String CREATE_FTRANISS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTRANISS + " (" + FTRANISS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FTRANISS_REFNO + " TEXT, " + FTRANISS_ITEMCODE + " TEXT," + FTRANISS_QTY + " TEXT, " + FTRANISS_AMT + " TEXT, "

            + FTRANISS_SIZECODE + " TEXT, " + FTRANISS_GROUPCODE + " TEXT, " + FTRANISS_PRICE + " TEXT, " + FTRANISS_BRAND + " TEXT, " + FTRANISS_QOH + " TEXT); ";
    // create String
    private static final String CREATE_TABLE_NONPRDHED = "CREATE  TABLE IF NOT EXISTS " + TABLE_NONPRDHED + " (" + NONPRDHED_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + NONPRDHED_REFNO +
            " TEXT, " + NONPRDHED_TXNDAET + " TEXT, " +
            NONPRDHED_DEALCODE + " TEXT, " +
            NONPRDHED_REPCODE + " TEXT, " +
            NONPRDHED_REMARK + " TEXT, " +
            NONPRDHED_COSTCODE + " TEXT, " +
            NONPRDHED_LONGITUDE + " TEXT, " +
            NONPRDHED_LATITUDE + " TEXT, " +
            NONPRDHED_IS_ACTIVE + " TEXT, " +
            NONPRDHED_DEBCODE + " TEXT, " +
            NONPRDHED_ADDUSER + " TEXT, " + NONPRDHED_ADDDATE + " TEXT," + NONPRDHED_ADDMACH + " TEXT," + NONPRDHED_TRANSBATCH + " TEXT, " + NONPRDHED_IS_SYNCED + " TEXT," + NONPRDHED_ADDRESS + " TEXT); ";
    // create StringNONPRDHED_DEBCODE =

    private static final String CREATE_TABLE_NONPRDDET = "CREATE  TABLE IF NOT EXISTS "
            + TABLE_NONPRDDET + " (" + NONPRDDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NONPRDDET_REFNO + " TEXT, "
            + NONPRDDET_TXNDATE + " TEXT, "
            + NONPRDDET_REPCODE + " TEXT, "
            + NONPRDDET_REASON_CODE + " TEXT, "
            + NONPRDDET_REASON + " TEXT, "
            + NONPRDDET_IS_SYNCED + " TEXT); ";
    // create String
    private static final String CREATE_FDAMHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDAMHED + " (" + FDAMHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDAMHED_REFNO + " TEXT, " + FDAMHED_TXNDATE + " TEXT, " + FDAMHED_MANUREF + " TEXT, " + FDAMHED_COSTCODE + " TEXT, " + FDAMHED_REMARKS + " TEXT, " + FDAMHED_TXNTYPE + " TEXT, " + FDAMHED_TOTALAMT + " TEXT, " + FDAMHED_REACODE + " TEXT, " + FDAMHED_ITEMTYPE + " TEXT, " + FDAMHED_GLPOST + " TEXT, " + FDAMHED_GLBATCH + " TEXT, " + FDAMHED_ADDUSER + " TEXT," + FDAMHED_ADDDATE + " TEXT," + FDAMHED_ADDMACH + " TEXT," + FDAMHED_DEALCODE + " TEXT," + FDAMHED_LOCFROM + " TEXT," + FDAMHED_LOCTO + " TEXT," + FDAMHED_IS_SYNCED + " TEXT," + FDAMHED_ACTIVE_STATE + " TEXT); ";
    // create String
    private static final String CREATE_FDAMDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDAMDET + " (" + FDAMDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDAMDET_REFNO + " TEXT, " + FDAMDET_TXNDATE + " TEXT, " + FDAMDET_LOCCODE + " TEXT, " + FDAMDET_TXNTYPE + " TEXT, " + FDAMDET_SEQNO + " TEXT, " + FDAMDET_ITEMCODE + " TEXT, " + FDAMDET_REACODE + " TEXT, " + FDAMDET_REANAME + " TEXT, " + FDAMDET_QTY + " TEXT, " + FDAMDET_AMT + " TEXT," + FDAMDET_CASEQTY + " TEXT," + FDAMDET_PICEQTY + " TEXT," + REMQTYDAM + " TEXT); ";
    // create String
    private static final String CREATE_FDAYEXPHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDAYEXPHED + " (" + FDAYEXPHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDAYEXPHED_REFNO + " TEXT, " + FDAYEXPHED_TXNDATE + " TEXT, " + FDAYEXPHED_REPNAME + " TEXT, " + FDAYEXPHED_DEALCODE + " TEXT, " + FDAYEXPHED_COSTCODE + " TEXT, " + FDAYEXPHED_REPCODE + " TEXT, " + FDAYEXPHED_REMARKS + " TEXT, " + FDAYEXPHED_AREACODE + " TEXT, " + FDAYEXPHED_ADDUSER + " TEXT, " + FDAYEXPHED_ADDDATE + " TEXT, " + FDAYEXPHED_ADDMATCH + " TEXT, " + FDAYEXPHED_LONGITUDE + " TEXT," + FDAYEXPHED_LATITUDE + " TEXT," + FDAYEXPHED_ISSYNC + " TEXT," + FDAYEXPHED_ACTIVESTATE + " TEXT," + FDAYEXPHED_TOTAMT + " TEXT," + FDAYEXPHED_ADDRESS + " TEXT); ";
    // create String
    private static final String CREATE_FDAYEXPDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDAYEXPDET + " (" + FDAYEXPDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDAYEXPDET_REFNO + " TEXT, " + FDAYEXPDET_TXNDATE + " TEXT, " + FDAYEXPDET_SEQNO + " TEXT, " + FDAYEXPDET_EXPCODE + " TEXT, " + FDAYEXPDET_AMT + " TEXT); ";
    private static final String CREATE_FTOURHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FTOURHED + " (" + TOURHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TOURHED_REFNO + " TEXT, " + TOURHED_MANUREF + " TEXT, " + TOURHED_TXNDATE + " TEXT, " + TOURHED_LORRYCODE + " TEXT, " + TOURHED_ROUTECODE + " TEXT, " + TOURHED_AREACODE + " TEXT, " + TOURHED_COSTCODE + " TEXT, " + TOURHED_REMARKS + " TEXT, " + TOURHED_LOCCODEF + " TEXT, " + TOURHED_LOCCODE + " TEXT, " + TOURHED_REPCODE + " TEXT, " + TOURHED_HELPERCODE + " TEXT, " + TOURHED_ADDUSER + " TEXT, " + TOURHED_ADDMACH + " TEXT, " + TOURHED_DRIVERCODE + " TEXT, " + TOURHED_VANLOADFLG + " TEXT, " + TOURHED_CLSFLG + " TEXT, " + TOURHED_TOURTYPE + " TEXT); ";
    private static final String CREATE_DEBITEMPRI_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_DEBITEMPRI + " (" + DEBITEMPRI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DEBITEMPRI_BRANDCODE + " TEXT, " + DEBITEMPRI_DEBCODE + " TEXT, " + DEBITEMPRI_DISPER + " TEXT); ";
    private static final String CREATE_FDISPHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISPHED + " (" + FDISPHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISPHED_REFNO + " TEXT," + FDISPHED_TXNDATE + " TEXT," + FDISPHED_REFNO1 + " TEXT," + FDISPHED_MANUREF + " TEXT," + FDISPHED_TOTALAMT + " TEXT," + FDISPHED_LOCCODE + " TEXT," + FDISPHED_COSTCODE + " TEXT," + FDISPHED_DEBCODE + " TEXT," + FDISPHED_REPCODE + " TEXT," + FDISPHED_REMARKS + " TEXT," + FDISPHED_TXNTYPE + " TEXT," + FDISPHED_INVOICE + " TEXT," + FDISPHED_CONTACT + " TEXT," + FDISPHED_CUSADD1 + " TEXT," + FDISPHED_CUSADD2 + " TEXT," + FDISPHED_CUSADD3 + " TEXT," + FDISPHED_CUSTELE + " TEXT," + FDISPHED_ADDUSER + " TEXT," + FDISPHED_ADDDATE + " TEXT," + FDISPHED_ADDMACH + " TEXT);";
    private static final String CREATE_FDISPDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISPDET + " (" + FDISPDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISPDET_REFNO + " TEXT ," + FDISPDET_TXNDATE + " TEXT," + FDISPDET_TXNTYPE + " TEXT," + FDISPDET_ITEMCODE + " TEXT," + FDISPDET_QTY + " TEXT DEFAULT '0'," + FDISPDET_BALQTY + " TEXT DEFAULT '0'," + FDISPDET_SAQTY + " TEXT DEFAULT '0'," + FDISPDET_BALSAQTY + " TEXT DEFAULT '0'," + FDISPDET_FIQTY + " TEXT DEFAULT '0'," + FDISPDET_BALFIQTY + " TEXT DEFAULT '0'," + FDISPDET_COSTPRICE + " TEXT," + FDISPDET_AMT + " TEXT," + FDISPDET_REFNO1 + " TEXT," + FDISPDET_SEQNO + " TEXT);";
    private static final String CREATE_FDISPISS_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISPISS + " (" + FDISPISS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FDISPISS_REFNO + " TEXT," + FDISPISS_TXNDATE + " TEXT," + FDISPISS_LOCCODE + " TEXT," + FDISPISS_STKRECNO + " TEXT," + FDISPISS_STKRECDATE + " TEXT," + FDISPISS_STKTXNNO + " TEXT," + FDISPISS_STKTXNDATE + " TEXT," + FDISPISS_STKTXNTYPE + " TEXT," + FDISPISS_ITEMCODE + " TEXT," + FDISPISS_QTY + " TEXT," + FDISPISS_BALQTY + " TEXT," + FDISPISS_COSTPRICE + " TEXT," + FDISPISS_AMT + " TEXT," + FDISPISS_REFNO1 + " TEXT," + FDISPISS_OTHCOST + " TEXT);";
    private static final String CREATE_FBRANDTARGET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FBRANDTARGET + " (" + FBRANDTARGET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FBRANDTARGET_BRANDCODE + " TEXT," + FBRANDTARGET_COSTCODE + " TEXT," + FBRANDTARGET_TARGET + " TEXT);";
    private static final String CREATE_FINVRHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVRHED + " ("
            + FINVRHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //1
            + FINVRHED_REFNO + " TEXT, "
            + FINVRHED_TXNDATE + " TEXT, "
            + FINVRHED_MANUREF + " TEXT, "
            + FINVRHED_COSTCODE + " TEXT, "
            + FINVRHED_DEBCODE + " TEXT, "
            + FINVRHED_REMARKS + " TEXT, "
            + FINVRHED_TXNTYPE + " TEXT, "
            + FINVRHED_ADD_DATE + " TEXT, "
            + FINVRHED_ADD_MACH + " TEXT, "
            + FINVRHED_ADD_USER + " TEXT, "
            + FINVRHED_LOCCODE + " TEXT, "
            + FINVRHED_IS_ACTIVE + " TEXT, "
            + FINVRHED_IS_SYNCED + " TEXT, "
            + FINVRHED_REPCODE + " TEXT, "
            + FINVRHED_REASON_CODE + " TEXT, "
            + FINVRHED_TAX_REG + " TEXT, "
            + FINVRHED_TOTAL_AMT + " TEXT, "
            + FINVRHED_TOTAL_TAX + " TEXT, "
            + FINVRHED_TOTAL_DIS + " TEXT, "
            + FINVRHED_ROUTE_CODE + " TEXT, "
            + FINVRHED_LONGITUDE + " TEXT, "
            + FINVRHED_LATITUDE + " TEXT, "
            + FINVRHED_ADDRESS + " TEXT, "
            + FINVRHED_START_TIME + " TEXT, "
            + FINVRHED_RETURN_TYPE + " TEXT, "
            + FINVRHED_TOURCODE + " TEXT, "
            + FINVRHED_AREACODE + " TEXT, "
            + FINVRHED_DRIVERCODE + " TEXT, "
            + FINVRHED_HELPERCODE + " TEXT, "
            + FINVRHED_LORRYCODE + " TEXT, "
            + FINVRHED_END_TIME + " TEXT); ";

    private static final String CREATE_FINVRDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FINVRDET + " ("
            + FINVRDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FINVRDET_REFNO + " TEXT, "
            + FINVRDET_ITEMCODE + " TEXT, "
            + FINVRDET_TAXCOMCODE + " TEXT, "
            + FINVRDET_PRILCODE + " TEXT, "
            + FINVRDET_TXN_DATE + " TEXT, "
            + FINVRDET_TXN_TYPE + " TEXT, "
            + FINVRDET_COST_PRICE + " TEXT, "
            + FINVRDET_SELL_PRICE + " TEXT, "
            + FINVRDET_T_SELL_PRICE + " TEXT, "
            + FINVRDET_AMT + " TEXT, "
            + FINVRDET_DIS_AMT + " TEXT, "
            + FINVRDET_DIS_RATE + " TEXT, "
            + FINVRDET_TAX_AMT + " TEXT, "
            + FINVRDET_QTY + " TEXT, "
            + FINVRDET_BAL_QTY + " TEXT, "
            + FINVRDET_IS_ACTIVE + " TEXT, "
            + FINVRDET_REASON_CODE + " TEXT, "
            + FINVRDET_REASON_NAME + " TEXT, "
            + FINVRDET_RETURN_TYPE + " TEXT, "
            + FINVRDET_FREE_QTY + " TEXT, "
            + FINVRDET_PRICE + " TEXT, "
            + FINVRDET_CHANGED_PRICE + " TEXT, "
            + FINVRDET_SEQNO + " TEXT); ";

    private static final String CREATE_FPRODUCT_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRODUCT + " ("
            + FPRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FPRODUCT_ITEMCODE + " TEXT, "
            + FPRODUCT_ITEMNAME + " TEXT, "
            + FPRODUCT_PRICE + " TEXT, "
            + FPRODUCT_QOH + " TEXT, "
            + FPRODUCT_QTY + " TEXT); ";
    private static final String INDEX_PRODUCTS = "CREATE UNIQUE INDEX IF NOT EXISTS ui_product ON " + TABLE_FPRODUCT + " (itemcode);";

//----------------------------------------------------------------------------------------------------------------------------
    private static final String CREATE_FPRODUCT_PRE_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRODUCT_PRE + " ("
            + FPRODUCT_ID_PRE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FPRODUCT_ITEMCODE_PRE + " TEXT, "
            + FPRODUCT_ITEMNAME_PRE + " TEXT, "
            + FPRODUCT_PRICE_PRE + " TEXT, "
            + FPRODUCT_QOH_PRE + " TEXT, "
            + FPRODUCT_NOUCASE_PRE + " TEXT, "
            + FPRODUCT_QTY_PRE + " TEXT); ";
    private static final String INDEX_PRODUCTS_PRE = "CREATE UNIQUE INDEX IF NOT EXISTS pre_product ON " + TABLE_FPRODUCT_PRE + " (itemcode_pre,itemname_pre);";

    private static final String CREATE_FDISTRICT = "CREATE  TABLE IF NOT EXISTS " + TABLE_FDISTRICT + " ("
            + FDISTRICT_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FDISTRICT_NAME + " TEXT, "
            + FDISTRICT_CODE + " TEXT, "
            + FDISTRICT_PROVECODE+ " TEXT); ";
    private static final String INDEX_FDISTRICT= "CREATE UNIQUE INDEX IF NOT EXISTS fDistrictIN ON " + TABLE_FDISTRICT + " (DistrCode,DistrName);";

    public static final String TABLE_FPRECDET = "fpRecDet";

    //l
    public static final String FPRECDET_ID = "Id";
    public static final String FPRECDET_REFNO = "RefNo";
    public static final String FPRECDET_REFNO1 = "RefNo1";
    public static final String FPRECDET_REFNO2 = "RefNo2";
    public static final String FPRECDET_DEBCODE = "DebCode";
    public static final String FPRECDET_SALEREFNO = "SaleRefNo";
    public static final String FPRECDET_MANUREF = "ManuRef";
    public static final String FPRECDET_TXNTYPE = "TxnType";
    public static final String FPRECDET_TXNDATE = "TxnDate";
    public static final String FPRECDET_DTXNDATE = "DtxnDate";
    public static final String FPRECDET_DTXNTYPE = "DtxnType";
    public static final String FPRECDET_DCURCODE = "DCurCode";
    public static final String FPRECDET_DCURRATE = "DCurRate";
    public static final String FPRECDET_OCURRATE = "OCurRate";
    public static final String FPRECDET_REPCODE = "RepCode";
    public static final String FPRECDET_AMT = "Amt";
    public static final String FPRECDET_BAMT = "BAmt";
    public static final String FPRECDET_ALOAMT = "AloAmt";
    public static final String FPRECDET_OVPAYAMT = "OvPayAmt";
    public static final String FPRECDET_OVPAYBAL = "OvPayBal";
    public static final String FPRECDET_RECORDID = "RecordId";
    public static final String FPRECDET_TIMESTAMP = "timestamp_column";
    public static final String FPRECDET_ISDELETE = "IsDelete";
    public static final String FPRECDET_REMARK = "Remark";

    private static final String CREATE_FPRECDET_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRECDET + " (" + FPRECDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FPRECDET_REFNO + " TEXT, " + FPRECDET_REFNO1 + " TEXT, " + FPRECDET_REFNO2 + " TEXT, " + FPRECDET_DEBCODE + " TEXT, " + FPRECDET_SALEREFNO + " TEXT, "

            + FPRECDET_MANUREF + " TEXT, " + FPRECDET_TXNTYPE + " TEXT, " + FPRECDET_TXNDATE + " TEXT, "

            + FPRECDET_DTXNDATE + " TEXT, " + FPRECDET_DTXNTYPE + " TEXT, " + FPRECDET_DCURCODE + " TEXT, " + FPRECDET_DCURRATE + " TEXT, "

            + FPRECDET_OCURRATE + " TEXT, " + FPRECDET_REPCODE + " TEXT, " + FPRECDET_AMT + " TEXT, " + FPRECDET_BAMT + " TEXT, "

            + FPRECDET_ALOAMT + " TEXT, " + FPRECDET_OVPAYAMT + " TEXT, " + FPRECDET_REMARK + " TEXT, " + FPRECDET_OVPAYBAL + " TEXT, " + FPRECDET_RECORDID + " TEXT, " + FPRECDET_ISDELETE + " TEXT, " + FPRECDET_TIMESTAMP + " TEXT );";

	/*-*-*-*-*-*-*-*-*-*-*--*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    public static final String TABLE_FPRECHED = "fpRecHed";

    public static final String FPRECHED_ID = "Id";
    public static final String FPRECHED_REFNO = "RefNo";
    public static final String FPRECHED_REFNO1 = "RefNo1";
    public static final String FPRECHED_MANUREF = "ManuRef";
    public static final String FPRECHED_SALEREFNO = "SaleRefNo";
    public static final String FPRECHED_REPCODE = "RepCode";
    public static final String FPRECHED_TXNTYPE = "TxnType";
    public static final String FPRECHED_CHQNO = "Chqno";
    public static final String FPRECHED_CHQDATE = "ChqDate";
    public static final String FPRECHED_TXNDATE = "TxnDate";
    public static final String FPRECHED_CURCODE = "CurCode";
    public static final String FPRECHED_CURRATE1 = "CurRate1";
    public static final String FPRECHED_DEBCODE = "DebCode";
    public static final String FPRECHED_TOTALAMT = "TotalAmt";
    public static final String FPRECHED_BTOTALAMT = "BTotalAmt";
    public static final String FPRECHED_PAYTYPE = "PayType";
    public static final String FPRECHED_PRTCOPY = "PrtCopy";
    public static final String FPRECHED_REMARKS = "Remarks";
    public static final String FPRECHED_ADDUSER = "AddUser";
    public static final String FPRECHED_ADDMACH = "AddMach";
    public static final String FPRECHED_ADDDATE = "AddDate";
    public static final String FPRECHED_RECORDID = "RecordId";
    public static final String FPRECHED_TIMESTAMP = "timestamp_column";
    public static final String FPRECHED_CURRATE = "CurRate";
    public static final String FPRECHED_CUSBANK = "CusBank";
    public static final String FPRECHED_COST_CODE = "CostCode";
    public static final String FPRECHED_LONGITUDE = "Longitude";
    public static final String FPRECHED_LATITUDE = "Latitude";
    public static final String FPRECHED_ADDRESS = "Address";
    public static final String FPRECHED_START_TIME = "StartTime";
    public static final String FPRECHED_END_TIME = "EndTime";
    public static final String FPRECHED_ISACTIVE = "IsActive";
    public static final String FPRECHED_ISSYNCED = "IsSynced";
    public static final String FPRECHED_ISDELETE = "IsDelete";
    public static final String FPRECHED_BANKCODE = "BankCode";
    public static final String FPRECHED_BRANCHCODE = "BranchCode";
    public static final String FPRECHED_COMMON_REFNO = "MRefNo";
    public static final String FPRECHED_ISDEPOSIT = "IsDeposit";
    private static final String CREATE_FPRECHED_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FPRECHED + " (" + FPRECHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            FPRECHED_REFNO + " TEXT, " + FPRECHED_REFNO1 + " TEXT, " + FPRECHED_MANUREF + " TEXT, " + FPRECHED_SALEREFNO + " TEXT, " +

            FPRECHED_REPCODE + " TEXT, " + FPRECHED_TXNTYPE + " TEXT, " + FPRECHED_CHQNO + " TEXT, " + FPRECHED_CHQDATE + " TEXT, " + FPRECHED_TXNDATE + " TEXT, " + FPRECHED_CURCODE + " TEXT, " +

            FPRECHED_CURRATE1 + " TEXT, " + FPRECHED_DEBCODE + " TEXT, " + FPRECHED_TOTALAMT + " TEXT, " + FPRECHED_BANKCODE + " TEXT, " + FPRECHED_BRANCHCODE + " TEXT, " +

            FPRECHED_BTOTALAMT + " TEXT, " + FPRECHED_PAYTYPE + " TEXT, " + FPRECHED_PRTCOPY + " TEXT, " + FPRECHED_REMARKS + " TEXT, " + FPRECHED_ADDUSER + " TEXT, " + FPRECHED_ADDMACH + " TEXT, " + FPRECHED_ADDDATE + " TEXT, " +

            FPRECHED_RECORDID + " TEXT, " + FPRECHED_TIMESTAMP + " TEXT, " + FPRECHED_ISDELETE + " TEXT, " + FPRECHED_COST_CODE + " TEXT, " +

            FPRECHED_LONGITUDE + " TEXT, " + FPRECHED_LATITUDE + " TEXT, " + FPRECHED_ADDRESS + " TEXT, " + FPRECHED_START_TIME + " TEXT, " + FPRECHED_END_TIME + " TEXT, " + FPRECHED_ISACTIVE + " TEXT, " + FPRECHED_ISSYNCED + " TEXT, " + FPRECHED_CURRATE + " TEXT, " + FPRECHED_COMMON_REFNO + " TEXT, " + FPRECHED_CUSBANK + " TEXT, " + FPRECHED_ISDEPOSIT + " TEXT DEFAULT '0');";



    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FBANKDEPOHED-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FBANKDEPOHED = "fbankdepohed";
    public static final String FBANKDEPOHED_ID = "Id";
    public static final String FBANKDEPOHED_REFNO = "RefNo";
    public static final String FBANKDEPOHED_TXNDATE = "txndate";
    public static final String FBANKDEPOHED_PAYTYPE = "PayType";
    public static final String FBANKDEPOHED_SLIPNO = "Slipno";
    public static final String FBANKDEPOHED_DEPOAMT = "DepoAmt";
    public static final String FBANKDEPOHED_REMARKS = "Remarks";
    public static final String FBANKDEPOHED_ADDDATE = "AddDate";
    public static final String FBANKDEPOHED_ADDUSER = "AddUser";
    public static final String FBANKDEPOHED_ADDMACH = "AddMach";
    public static final String FBANKDEPOHED_ISACTIVE = "IsActive";
    public static final String FBANKDEPOHED_ISSYNCED = "IsSynced";
    public static final String FBANKDEPOHED_ISDELETE = "IsDelete";


    private static final String CREATE_FBANKDEPOHED_TABLE = "CREATE TABLE IF NOT EXISTS " + FBANKDEPOHED + " (" + FBANKDEPOHED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FBANKDEPOHED_REFNO + " TEXT, " + FBANKDEPOHED_TXNDATE + " TEXT, " + FBANKDEPOHED_PAYTYPE + " TEXT, " + FBANKDEPOHED_SLIPNO + " TEXT, " + FBANKDEPOHED_DEPOAMT + " TEXT, " + FBANKDEPOHED_REMARKS + " TEXT,  " + FBANKDEPOHED_ADDDATE + " TEXT,  " + FBANKDEPOHED_ADDUSER + " TEXT,  " + FBANKDEPOHED_ADDMACH + " TEXT,  "+ FBANKDEPOHED_ISDELETE + " TEXT DEFAULT '0', " + FBANKDEPOHED_ISSYNCED + " TEXT, " +FBANKDEPOHED_ISACTIVE+ " TEXT); ";


    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FBANKDEPODET-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/
    public static final String FBANKDEPODET = "fbankdepodet";
    public static final String FBANKDEPODET_ID = "Id";
    public static final String FBANKDEPODET_REFNO = "RefNo";
    public static final String FBANKDEPODET_RECEIPT_REFNO = "Receipt_RefNo";
    public static final String FBANKDEPODET_DEBTOR_CODE = "DebCode";
    public static final String FBANKDEPODET_RECEIPT_TXNDATE = "Receipt_txndate";
    public static final String FBANKDEPODET_RECEIPT_CHEQNO = "Receipt_cheqno";
    public static final String FBANKDEPODET_RECEIPT_AMT = "Receipt_amt";


    private static final String CREATE_FBANKDEPODET_TABLE = "CREATE TABLE IF NOT EXISTS " + FBANKDEPODET + " (" + FBANKDEPODET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FBANKDEPODET_REFNO + " TEXT, " + FBANKDEPODET_RECEIPT_REFNO + " TEXT, " + FBANKDEPODET_DEBTOR_CODE + " TEXT, " + FBANKDEPODET_RECEIPT_TXNDATE + " TEXT, " + FBANKDEPODET_RECEIPT_CHEQNO + " TEXT, " + FBANKDEPODET_RECEIPT_AMT + " TEXT); ";

    //-------------------------kaveesha 29/07/2020------------------------------
    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FTARGETDET-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    //table
    public static final String FTARGETDET = "fTargetDet";
    //attributes
    public static final String FTARGETDET_ID = "Id";
    public static final String FTARGETDET_MONTH = "Monthn";
    public static final String FTARGETDET_REFNO = "RefNo";
    public static final String FTARGETDET_TRAMT = "TrAmt";
    public static final String FTARGETDET_YEART = "YearT";
    public static final String FTARGETDET_TXNDATE = "txndate";

    private static final String CREATE_FTARGETDET_TABLE = "CREATE TABLE IF NOT EXISTS " + FTARGETDET + " (" + FTARGETDET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FTARGETDET_MONTH + " TEXT, " + FTARGETDET_REFNO + " TEXT, " + FTARGETDET_TRAMT + " TEXT, "
            + FTARGETDET_YEART + " TEXT, " + FTARGETDET_TXNDATE + " TEXT); ";

    //-------------------------kaveesha 30/07/2020------------------------------
    /*-*-*-*-*-**-*-*-*-*-*-*-*-TABLE_FACHIEVEMENT-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    //table
    public static final String FACHIEVEMENT = "fAchievement";
    //attributes

    public static final String FACHIEVE_ID = "Id";
    public static final String FACHIEVE_CUMINVCOUNT = "CumInvCount";
    public static final String FACHIEVE_CUMINVVAL = "CumInvVal";
    public static final String FACHIEVE_DAYINVCOUNT = "DayInvCount";
    public static final String FACHIEVE_DAYINVVAL = "DayInvVal";
    public static final String FACHIEVE_REPCODE = "RepCode";
    public static final String FACHIEVE_REPNAME = "RepName";

    private static final String CREATE_FACHIEVEMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + FACHIEVEMENT + " (" + FACHIEVE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FACHIEVE_CUMINVCOUNT + " TEXT, " + FACHIEVE_CUMINVVAL + " TEXT, " + FACHIEVE_DAYINVCOUNT + " TEXT, "
            + FACHIEVE_DAYINVVAL + " TEXT, " + FACHIEVE_REPCODE + " TEXT, " + FACHIEVE_REPNAME + " TEXT); ";


    public DatabaseHelper(Context context) {
        super(context, context.getResources().getString(R.string.DATABASE_NAME), null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {

        arg0.execSQL(CREATE_TABLE_MESSAGE);
        arg0.execSQL(CREATE_SERVER_DB_TABLE);
        arg0.execSQL(CREATE_FDEBTOR_TABLE);
        arg0.execSQL(CREATE_FCONTROL_TABLE);
        arg0.execSQL(CREATE_FCOMPANYSETTING_TABLE);
        arg0.execSQL(CREATE_FINVDET_TABLE);
        arg0.execSQL(CREATE_FBANK_TABLE);
        arg0.execSQL(CREATE_FREASON_TABLE);
        arg0.execSQL(CREATE_FEXPENSE_TABLE);
        arg0.execSQL(CREATE_FTOWN_TABLE);
        arg0.execSQL(CREATE_FTRGCAPUL_TABLE);
        arg0.execSQL(CREATE_FTYPE_TABLE);
        arg0.execSQL(CREATE_FSUBBRAND_TABLE);
        arg0.execSQL(CREATE_FGROUP_TABLE);
        arg0.execSQL(CREATE_DOWNLOAD_TABLE);
        arg0.execSQL(CREATE_FSKU_TABLE);
        arg0.execSQL(CREATE_FBRAND_TABLE);
        arg0.execSQL(CREATE_FCOMPANYBRANCH_TABLE);
        arg0.execSQL(CREATE_FSALREP_TABLE);
        arg0.execSQL(CREATE_FDDBNOTE_TABLE);
        arg0.execSQL(CREATE_FOTHERTRANS_TABLE);
        arg0.execSQL(CREATE_FFREEDEB_TABLE);
        arg0.execSQL(CREATE_FFREEDET_TABLE);
        arg0.execSQL(CREATE_FFREEHED_TABLE);
        arg0.execSQL(CREATE_FFREESLAB_TABLE);
        arg0.execSQL(CREATE_FFREEITEM_TABLE);
        arg0.execSQL(CREATE_FITEM_TABLE);
        arg0.execSQL(CREATE_FITEMLOC_TABLE);
        arg0.execSQL(CREATE_FITEMPRI_TABLE);
        arg0.execSQL(CREATE_FAREA_TABLE);
        arg0.execSQL(CREATE_FLOCATIONS_TABLE);
        arg0.execSQL(CREATE_FDEALER_TABLE);
        arg0.execSQL(CREATE_FFREEMSLAB_TABLE);
        arg0.execSQL(CREATE_FMERCH_TABLE);
        arg0.execSQL(CREATE_FROUTEDET_TABLE);
        arg0.execSQL(CREATE_FDISCVHED_TABLE);
        arg0.execSQL(CREATE_FDISCVDET_TABLE);
        arg0.execSQL(CREATE_FDISCVDEB_TABLE);
        arg0.execSQL(CREATE_FDISCHED_TABLE);
        arg0.execSQL(CREATE_FDISCDET_TABLE);
        arg0.execSQL(CREATE_FDISCDEB_TABLE);
        arg0.execSQL(CREATE_FDISCSLAB_TABLE);
        arg0.execSQL(CREATE_FITENRHED_TABLE);
        arg0.execSQL(CREATE_FITENRDET_TABLE);
        arg0.execSQL(CREATE_FITEDEBDET_TABLE);
        arg0.execSQL(CREATE_FINVHEDL3_TABLE);
        arg0.execSQL(CREATE_FINVDETL3_TABLE);
        arg0.execSQL(CREATE_FINVHED_TABLE);
        arg0.execSQL(CREATE_FTRANDET_TABLE);
        arg0.execSQL(CREATE_FTRANHED_TABLE);
        // arg0.execSQL(CREATE_FTRANISS_TABLE);
        arg0.execSQL(CREATE_TABLE_NONPRDHED);
        arg0.execSQL(CREATE_TABLE_NONPRDDET);
        arg0.execSQL(CREATE_FDAMHED_TABLE);
        arg0.execSQL(CREATE_FDAMDET_TABLE);
        arg0.execSQL(CREATE_FDAYEXPHED_TABLE);
        arg0.execSQL(CREATE_FDAYEXPDET_TABLE);
        arg0.execSQL(CREATE_FORDDISC_TABLE);
        arg0.execSQL(CREATE_FORDFREEISS_TABLE);
        // arg0.execSQL(CREATE_FSIZE_TABLE);
        arg0.execSQL(CREATE_FGRCOMBDET_TABLE);
        // arg0.execSQL(CREATE_FTERMS_TABLE);
        // arg0.execSQL(CREATE_FSIZECOMB_TABLE);
        // arg0.execSQL(CREATE_FCRCOMB_TABLE);
        arg0.execSQL(CREATE_FTRANSOHED_TABLE);
        arg0.execSQL(CREATE_FAPPRORDHED_TABLE);
        arg0.execSQL(CREATE_FTRANSODET_TABLE);
        // arg0.execSQL(CREATE_FSIZEIN_TABLE);
        arg0.execSQL(CREATE_FSTKISS_TABLE);
        arg0.execSQL(CREATE_STKIN_TABLE);
        arg0.execSQL(CREATE_FPRETAXDT_TABLE);
        arg0.execSQL(CREATE_FPRETAXRG_TABLE);
        arg0.execSQL(CREATE_FTAX_TABLE);
        arg0.execSQL(CREATE_FTAXHED_TABLE);
        arg0.execSQL(CREATE_FTAXDET_TABLE);
        arg0.execSQL(CREATE_FINVTAXRG_TABLE);
        arg0.execSQL(CREATE_FINVTAXDT_TABLE);
        arg0.execSQL(CREATE_FROUTE_TABLE);
        arg0.execSQL(CREATE_FTOUR_TABLE);
        arg0.execSQL(CREATE_FTOURHED_TABLE);
        arg0.execSQL(CREATE_DEBITEMPRI_TABLE);

        arg0.execSQL(CREATE_FDISPHED_TABLE);
        arg0.execSQL(CREATE_FDISPDET_TABLE);
        arg0.execSQL(CREATE_FDISPISS_TABLE);
        arg0.execSQL(CREATE_FINVRDET_TABLE);
        arg0.execSQL(CREATE_FINVRHED_TABLE);
        arg0.execSQL(CREATE_FBRANDTARGET_TABLE);
        arg0.execSQL(CREATE_FPRODUCT_TABLE);
        arg0.execSQL(CREATE_FPRECHED_TABLE);
        arg0.execSQL(CREATE_FPRECDET_TABLE);
        arg0.execSQL(CREATE_FPRODUCT_PRE_TABLE);

        arg0.execSQL(CREATE_FORDHEDTEMP);  //last three bill saved in this tbl
        arg0.execSQL(CREATE_FORDDETTEMP); //last three bill saved in this tbl
        arg0.execSQL(CREATE_FINVHEDTEMP);
        arg0.execSQL(CREATE_FINVDETTEMP);
        arg0.execSQL(CREATE_FDISTRICT);
        arg0.execSQL(CREATE_NEW_CUSTOMER);
        arg0.execSQL(CREATE_FTARGETDET_TABLE);
        arg0.execSQL(CREATE_FACHIEVEMENT_TABLE);



		/* Indexes */
        arg0.execSQL(INDEX_PRODUCTS);
        arg0.execSQL(INDEX_PRODUCTS_PRE);
        arg0.execSQL(INDEX_DEBTOR);
        arg0.execSQL(INDEX_FITEMS);
        arg0.execSQL(INDEX_FITEMLOC);
        arg0.execSQL(INDEX_FITEMPRI);
        arg0.execSQL(INDEX_ROUTEDET);
        arg0.execSQL(INDEX_FDISTRICT);

        // ---------------------------------------------- Nuwan ----------------------------------------------------------------------
        arg0.execSQL(CREATE_FITTOURDISC_TABLE);
        arg0.execSQL(CREATE_FDEBTAX_TABLE);
        arg0.execSQL(CREATE_FINVRTAXRG_TABLE);
        arg0.execSQL(CREATE_FINVRTAXDT_TABLE);
        arg0.execSQL(CREATE_TABLE_FPAYMODE);
        arg0.execSQL(CREATE_TABLE_FPAYMENT_ALLOCATE);
        arg0.execSQL(CREATE_FBANKDEPODET_TABLE);
        arg0.execSQL(CREATE_FBANKDEPOHED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

         try {
             arg0.execSQL(CREATE_DOWNLOAD_TABLE);
             arg0.execSQL(CREATE_NEW_CUSTOMER);
             arg0.execSQL(CREATE_FBANKDEPODET_TABLE);
             arg0.execSQL(CREATE_FBANKDEPOHED_TABLE);
             arg0.execSQL(CREATE_FTARGETDET_TABLE);
             arg0.execSQL(CREATE_FACHIEVEMENT_TABLE);


        // arg0.execSQL("ALTER TABLE FDDbNote ADD COLUMN EntRemark TEXT");
         } catch (SQLException e) {
         e.printStackTrace();
         }

        try {
            arg0.execSQL(CREATE_FDISTRICT);
            arg0.execSQL(INDEX_FDISTRICT);

        } catch (SQLiteException e) {
             e.printStackTrace();
        }

        try {
            arg0.execSQL(CREATE_TABLE_NONPRDHED);
            arg0.execSQL(NONPRDHED_LONGITUDE);
            arg0.execSQL(NONPRDHED_LATITUDE);
            arg0.execSQL(NONPRDHED_IS_ACTIVE);
            arg0.execSQL(NONPRDHED_DEBCODE);

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }

        try
        {
            arg0.execSQL(CREATE_FAPPRORDHED_TABLE);
        }
        catch (SQLException se)
        {
            se.printStackTrace();
        }

        try {
            arg0.execSQL("ALTER TABLE fDebtor ADD COLUMN IsSync TEXT DEFAULT 0");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
        try {
            arg0.execSQL("ALTER TABLE fProducts_pre ADD COLUMN nou_case TEXT DEFAULT 0");
        } catch (SQLiteException e) {
            Log.v("SQLiteException", e.toString());
        }
/*
        try {
            arg0.execSQL(CREATE_FINVHEDTEMP);

        } catch (SQLiteException e) {
        }
        try {
            arg0.execSQL(CREATE_FINVDETTEMP);

        } catch (SQLiteException e) {
        }*/
       // arg0.execSQL(INDEX_PRODUCTS_PRE);
    }

}
