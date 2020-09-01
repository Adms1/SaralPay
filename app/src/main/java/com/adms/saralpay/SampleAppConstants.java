package com.adms.saralpay;

import com.adms.saralpay.Utility.AppConfiguration;

public class SampleAppConstants {

    //API_KEY is given by the Payment Gateway. Please Copy Paste Here.
    public static final String PG_API_KEY = AppConfiguration.api_key.trim();

    //URL to Accept Payment Response After Payment. This needs to be done at the client's web server.
    public static final String PG_RETURN_URL = "https://biz.traknpay.in/tnp/return_page_android.php";

    //Enter the Mode of Payment Here . Allowed Values are "LIVE" or "TEST".
    public static final String PG_MODE = "LIVE";

    //PG_CURRENCY is given by the Payment Gateway. Only "INR" Allowed.
    public static final String PG_CURRENCY = "INR";

    //PG_COUNTRY is given by the Payment Gateway. Only "IND" Allowed.
    public static final String PG_COUNTRY = "IND";

    public static final String PG_AMOUNT = "";
    public static final String PG_EMAIL = "saralpayonline@gmail.com";
    public static final String PG_NAME = AppConfiguration.CustomerDetail.get("CustomerName").trim();
    public static final String PG_PHONE = "7575809733";
    public static final String PG_DESCRIPTION = "";
    public static final String PG_CITY = "Ahmedabad";
    public static final String PG_STATE = "Gujarat";
    public static final String PG_ADD_1 = "";
    public static final String PG_ADD_2 = "";
    public static final String PG_ZIPCODE = "380015";
    public static final String PG_UDF1 = "";
    public static final String PG_UDF2 = "";
    public static final String PG_UDF3 = "";
    public static final String PG_UDF4 = "";
    public static final String PG_UDF5 = "";
    public static String PG_ORDER_ID = "";

}
