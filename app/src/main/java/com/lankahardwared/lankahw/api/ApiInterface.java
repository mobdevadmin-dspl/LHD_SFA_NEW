package com.lankahardwared.lankahw.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Rashmi on 24/01/2019.
 */

public interface ApiInterface {

    @POST("insertFOrdHed")
    Call<String> uploadOrder(@Body JsonArray orderlist, @Header("Content-Type") String cont_type);
    //Call<String> uploadOrder(@Body String orderlist, @Header("Content-Type") String cont_type);

    @POST("insertFInvHed")
    Call<String> uploadInvoice(@Body JsonArray orderlist, @Header("Content-Type") String cont_type);
    //******rashmi 29-07-2020********
    @POST("insertReturn")
    Call<String> uploadReturns(@Body JsonArray orderlist, @Header("Content-Type") String cont_type);
    @POST("insertDeletedFInvHed")
    Call<String> uploadDeletedInvoice(@Body JsonArray orderlist, @Header("Content-Type") String cont_type);
    @POST("insertFDaynPrdHed")
    Call<String> uploadNonProd(@Body JsonArray nonpList, @Header("Content-Type") String cont_type);
    @POST("insertFrecHed")
    Call<String> uploadReceipt(@Body JsonArray nonpList, @Header("Content-Type") String cont_type);

    @POST("updateDebtorCordinates")
    Call<String> uploadDebtorCordinates(@Body JsonArray debtorCordinateList, @Header("Content_Type") String cont_type);

    @POST("updateDebtorImageURL")
    Call<String> uploadDebtorImg(@Body JsonArray debtorImgList, @Header("Content_Type") String cont_type);
    @POST("updateEditedDebtors")
    Call<String> uploadEditedDebtors(@Body JsonArray debtorList, @Header("Content_Type") String cont_type);
    @POST("updateEmailUpdatedSalRep")
    Call<String> uploadRepEmail(@Body JsonArray repList, @Header("Content_Type") String cont_type);

    @POST("insertCustomer")
    Call<String> uploadNCustomer(@Body JsonArray customerList, @Header("Content_Type") String cont_type);

    @POST("insertTourInfo")
    Call<String> uploadAttendence(@Body JsonArray attendenceList, @Header("Content_Type") String cont_type);

    @POST("insertDayExpense")
    Call<String> uploadExpense(@Body JsonArray expenseList, @Header("Content_Type") String cont_type);

    @POST("updateFirebaseTokenID")
    Call<String> uploadfTokenID(@Body JsonArray tokenIdList, @Header("Content_Type") String cont_type);
}
