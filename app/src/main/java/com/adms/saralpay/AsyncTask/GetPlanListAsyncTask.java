package com.adms.saralpay.AsyncTask;

import android.os.AsyncTask;

import com.adms.saralpay.Interface.OnCompletionListner;
import com.adms.saralpay.Utility.AppConfiguration;
import com.adms.saralpay.Utility.ParseJSON;
import com.adms.saralpay.WebServicesCall.WebServicesCall;

import java.util.ArrayList;
import java.util.HashMap;

public class GetPlanListAsyncTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
    HashMap<String, String> param = new HashMap<String, String>();
    OnCompletionListner onCompletionListner = null;

    public GetPlanListAsyncTask(HashMap<String, String> param, OnCompletionListner onCompletionListner) {
        this.param = param;
        this.onCompletionListner = onCompletionListner;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
            String responseString = null;
        ArrayList<HashMap<String, String>> result = null;
            try {
                responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetPlanList), param);
                result = ParseJSON.parseGetPlanListJson(responseString);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
            super.onPostExecute(result);
            if(result.size() > 0){
                onCompletionListner.OnResponseSuccess(result);
            }else {
                onCompletionListner.OnResponseFail(result);
            }
        }
    }