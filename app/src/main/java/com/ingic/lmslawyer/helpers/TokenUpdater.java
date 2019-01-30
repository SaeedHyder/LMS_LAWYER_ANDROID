package com.ingic.lmslawyer.helpers;

import android.content.Context;
import android.util.Log;

import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.ResponseWrapper;
import com.ingic.lmslawyer.retrofit.WebService;
import com.ingic.lmslawyer.retrofit.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TokenUpdater {
    private static final TokenUpdater tokenUpdater = new TokenUpdater();
    private WebService webservice;

    private TokenUpdater() {
    }

    public static TokenUpdater getInstance() {
        return tokenUpdater;
    }

    public void UpdateToken(Context context, final String userToken, String DeviceType, String Token) {
        if (Token.isEmpty()) {
            Log.e("Token Updater", "Token is Empty");
        }
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptor(context,
                WebServiceConstant.SERVICE_URL);

        Call<ResponseWrapper> call = webservice.updateToken(userToken,Token, DeviceType );
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                if (response.body()!=null)
                Log.i("UPDATETOKEN", response.body().getResponse() + "" + userToken);
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                Log.e("UPDATETOKEN", t.toString());
            }
        });
    }

}
