package com.lankahardwared.lankahw.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.IResponseListener;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.model.Debtor;
import com.lankahardwared.lankahw.model.FDayExpHed;
import com.lankahardwared.lankahw.model.FDaynPrdHed;
import com.lankahardwared.lankahw.model.FInvRHed;
import com.lankahardwared.lankahw.model.InvHed;
import com.lankahardwared.lankahw.model.RecHed;
import com.lankahardwared.lankahw.model.TranSOHed;
import com.lankahardwared.lankahw.view.non_productive.NonProductiveManage;
import com.lankahardwared.lankahw.view.presale.PreSales;
import com.lankahardwared.lankahw.view.receipt.Receipt;
import com.lankahardwared.lankahw.view.sales_return.SalesReturn;
import com.lankahardwared.lankahw.view.sales_return.SalesReturnSummary;
import com.lankahardwared.lankahw.view.vansale.VanSales;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;


@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity implements IResponseListener {

    public Debtor selectedDebtor = null;
    public Debtor selectedRetDebtor = null;
    public TranSOHed selectedSOHed = null;
    public InvHed selectedInvHed = null;
    public FDaynPrdHed selectednonHed = null;
    public FDayExpHed selectedexpHed = null;
    public FInvRHed selectedReturnHed = null;
    public RecHed selectedRecHed = null;
    public int cusPosition = 0;
    boolean synced;
    public double ReceivedAmt = 0;


    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    byte[] arrayBytes;
    private String myEncryptionKey;
    private String myEncryptionScheme;
    SecretKey key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        synced = getIntent().getBooleanExtra("Synced", true);
        UtilityContainer.mLoadFragment(new IconPallet(), this);

        //======================================================================================================


        try {

            myEncryptionKey = "ThisIsSpartaThisIsSparta";
            myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
            arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
            ks = new DESedeKeySpec(arrayBytes);


        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String target = "hello 123";
        String encrypted = null;
        try {
            encrypted = encrypt(target);
        } catch (Exception e) {
            e.printStackTrace();
        }


 //       String decrypted = decrypt(encrypted);

//        Log.d("String to Encrypt: ", target);
//        Log.d("Encrypted String :", encrypted);
//        Log.d("decrypted String :", decrypted);


//=================================================================================================================

    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    protected void onStart() {
        super.onStart();
        if (!synced) {
            IconPallet fragment = (IconPallet) getSupportFragmentManager().findFragmentByTag(IconPallet.class.getSimpleName());
            fragment.mSyncDialogbox();
        }
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void onBackPressed() {
        return;
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void moveNextFragment_Van() {
        FragmentManager manager = getSupportFragmentManager();
        VanSales frag = (VanSales) manager.findFragmentByTag(VanSales.class.getSimpleName());
        frag.mMoveToHeader();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

//    @Override
//    public void moveNextFragment_Pre() {
//        FragmentManager manager = getSupportFragmentManager();
//        PreSales frag = (PreSales) manager.findFragmentByTag(PreSales.class.getSimpleName());
//        frag.mMoveToHeader();
//    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void moveNextFragment_NonProd() {
        FragmentManager manager = getSupportFragmentManager();
        NonProductiveManage frag = (NonProductiveManage) manager.findFragmentByTag(NonProductiveManage.class.getSimpleName());
        frag.mMoveToHeader();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void whenDiscardClicked_Ret() {
        FragmentManager manager = getSupportFragmentManager();
        SalesReturnSummary frag = (SalesReturnSummary) manager.findFragmentByTag(SalesReturnSummary.class.getSimpleName());
        frag.undoEditingData();
    }

    @Override
    public void moveBackToCustomer_pre(int index) {
        FragmentManager manager = getSupportFragmentManager();
        PreSales frag = (PreSales) manager.findFragmentByTag(PreSales.class.getSimpleName());

        if (index == 0)
        {
            frag.backToCustomer();
        }

        if (index == 2)
        {
            frag.backToOrdDet();
        }

        if (index == 1)
        {
            frag.moveToHeader();
        }


    }


//    @Override
//    public void moveBackToOrdDet_pre() {
//        FragmentManager manager = getSupportFragmentManager();
//        PreSales frag = (PreSales) manager.findFragmentByTag(PreSales.class.getSimpleName());
//
//
//    }
    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void whenSaveClicked_Ret() {

        FragmentManager manager = getSupportFragmentManager();
        SalesReturnSummary frag = (SalesReturnSummary) manager.findFragmentByTag(SalesReturnSummary.class.getSimpleName());
        frag.saveSummaryDialog();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void moveNextFragment_Ret() {
        FragmentManager manager = getSupportFragmentManager();
        SalesReturn frag = (SalesReturn) manager.findFragmentByTag(SalesReturn.class.getSimpleName());
        frag.moveNextTab();
    }

    /*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/

    @Override
    public void whenPauseClicked_Ret() {
        FragmentManager manager = getSupportFragmentManager();
        SalesReturnSummary frag = (SalesReturnSummary) manager.findFragmentByTag(SalesReturnSummary.class.getSimpleName());
        frag.mPauseinvoice();
    }

    @Override
    public void moveNextFragmentRece() {
        FragmentManager manager = getSupportFragmentManager();
        Receipt frag = (Receipt) manager.findFragmentByTag(Receipt.class.getSimpleName());
        frag.mMoveToHeader();
    }

    @Override
    public void moveToDetailsRece() {
        FragmentManager manager = getSupportFragmentManager();
        Receipt frag = (Receipt) manager.findFragmentByTag(Receipt.class.getSimpleName());
        frag.mMoveToHDetails();
    }

    @Override
    public void moveToSummaryRece() {
        FragmentManager manager = getSupportFragmentManager();
        Receipt frag = (Receipt) manager.findFragmentByTag(Receipt.class.getSimpleName());
        frag.mMoveToSummary();
    }

    //=======================================================================================================================


    public String encrypt(String unencryptedString) throws Exception {
        String encryptedString = null;
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString.getBytes());
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }
}








    