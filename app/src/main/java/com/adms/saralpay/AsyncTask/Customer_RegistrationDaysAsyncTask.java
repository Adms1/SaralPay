package com.adms.saralpay.AsyncTask;

import android.os.AsyncTask;

import com.adms.saralpay.Interface.OnCompletionListner;
import com.adms.saralpay.Utility.AppConfiguration;
import com.adms.saralpay.Utility.ParseJSON;
import com.adms.saralpay.WebServicesCall.WebServicesCall;

import java.util.HashMap;

public class Customer_RegistrationDaysAsyncTask extends AsyncTask<Void, Void, HashMap<String, String>> {
    HashMap<String, String> param = new HashMap<String, String>();
    OnCompletionListner onCompletionListner = null;

    public Customer_RegistrationDaysAsyncTask(HashMap<String, String> param, OnCompletionListner onCompletionListner) {
        this.param = param;
        this.onCompletionListner = onCompletionListner;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected HashMap<String, String> doInBackground(Void... params) {
            String responseString = null;
            HashMap<String, String> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.Customer_RegistrationDays), param);
                result = ParseJSON.parseCustomer_RegistrationDaysJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            super.onPostExecute(result);
            if(result.size() > 0){
                onCompletionListner.OnResponseSuccess(result);
            }else {
                onCompletionListner.OnResponseFail(result);
            }
        }
    }