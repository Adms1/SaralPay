package com.adms.saralpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.pg.secure.pgsdkv4.PGConstants;
import com.test.pg.secure.pgsdkv4.PaymentGatewayPaymentInitializer;
import com.test.pg.secure.pgsdkv4.PaymentParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class TraknpayNewActivity extends AppCompatActivity {

    ProgressBar pb;
    TextView transactionIdView;
    TextView transactionStatusView;

    String amount, order_id, desc, card_number, card_name, card_exp_month, card_exp_year;

    private Bundle extras = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traknpay_new);

//        pb.setVisibility(View.VISIBLE);

        extras = getIntent().getExtras();
        if (extras != null) {
//            mode = "TEST";
            amount = extras.getString("amount").trim();
            order_id = extras.getString("order_id").trim();
            desc = extras.getString("description").trim();

            if (extras.containsKey("CardDetails")) {

                String cardDetails = extras.getString("CardDetails").trim();
                String[] details = cardDetails.split("\\-");//card.CardNumber + "-" + card.CardHolderName + " - " + card.ValidToDate
                String expiryDate = details[2].trim();
                expiryDate = expiryDate.substring(0, 2) + "/" + expiryDate.substring(2, expiryDate.length());

                card_number = details[0].trim();
                //card_name = details[1].toString().replace("/", "").trim();22-12-2016 old
                card_name = details[1].toString().replaceAll("[;\\/:*?\"<>|&']", "").trim();//22-12-2016 navin

                card_exp_month = expiryDate.split("\\/")[0];
                card_exp_year = expiryDate.split("\\/")[1];
            }

        }

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        SampleAppConstants.PG_ORDER_ID = Integer.toString(n);

        PaymentParams pgPaymentParams = new PaymentParams();
        pgPaymentParams.setAPiKey(SampleAppConstants.PG_API_KEY);
        pgPaymentParams.setAmount(amount);
        pgPaymentParams.setEmail(SampleAppConstants.PG_EMAIL);
        pgPaymentParams.setName(SampleAppConstants.PG_NAME);
        pgPaymentParams.setPhone(SampleAppConstants.PG_PHONE);
        pgPaymentParams.setOrderId(order_id);
        pgPaymentParams.setCurrency(SampleAppConstants.PG_CURRENCY);
        pgPaymentParams.setDescription(desc);
        pgPaymentParams.setCity(SampleAppConstants.PG_CITY);
        pgPaymentParams.setState(SampleAppConstants.PG_STATE);
        pgPaymentParams.setAddressLine1(SampleAppConstants.PG_ADD_1);
        pgPaymentParams.setAddressLine2(SampleAppConstants.PG_ADD_2);
        pgPaymentParams.setZipCode(SampleAppConstants.PG_ZIPCODE);
        pgPaymentParams.setCountry(SampleAppConstants.PG_COUNTRY);
        pgPaymentParams.setReturnUrl(SampleAppConstants.PG_RETURN_URL);
        pgPaymentParams.setMode(SampleAppConstants.PG_MODE);
        pgPaymentParams.setUdf1(SampleAppConstants.PG_UDF1);
        pgPaymentParams.setUdf2(SampleAppConstants.PG_UDF2);
        pgPaymentParams.setUdf3(SampleAppConstants.PG_UDF3);
        pgPaymentParams.setUdf4(SampleAppConstants.PG_UDF4);
        pgPaymentParams.setUdf5(SampleAppConstants.PG_UDF5);
        pgPaymentParams.setEnableAutoRefund("n");
        pgPaymentParams.setOfferCode("testcoupon");
        //pgPaymentParams.setSplitInfo("{\"vendors\":[{\"vendor_code\":\"24VEN985\",\"split_amount_percentage\":\"20\"}]}");

        PaymentGatewayPaymentInitializer pgPaymentInitialzer = new PaymentGatewayPaymentInitializer(pgPaymentParams, TraknpayNewActivity.this);
        pgPaymentInitialzer.initiatePaymentProcess();

    }

    @Override
    public void onStop() {
        super.onStop();
//        pb.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PGConstants.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    String paymentResponse = data.getStringExtra(PGConstants.PAYMENT_RESPONSE);
                    System.out.println("paymentResponse: " + paymentResponse);
                    if (paymentResponse.equals("null")) {
                        System.out.println("Transaction Error!");
//                        transactionIdView.setText("Transaction ID: NIL");
//                        transactionStatusView.setText("Transaction Status: Transaction Error!");
                    } else {
                        JSONObject response = new JSONObject(paymentResponse);
//                        transactionIdView.setText("Transaction ID: "+response.getString("transaction_id"));
//                        transactionStatusView.setText("Transaction Status: "+response.getString("response_message"));

                        Intent intent = new Intent(getApplicationContext(), PaymentSuccessScreen.class);
                        intent.putExtra("transactionId", response.getString("transaction_id"));
                        intent.putExtra("responseCode", response.getString("response_code"));
                        intent.putExtra("amount", response.getString("amount"));
                        intent.putExtra("description", response.getString("description"));
                        intent.putExtra("order_id", response.getString("order_id"));
                        if (extras.containsKey("CardDetails")) {
                            intent.putExtra("Trans_Type", "1");
                        } else {
                            intent.putExtra("Trans_Type", "2");
                        }
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }

        }
    }
}