package com.ingic.lmslawyer.helpers;

import android.util.Log;

import com.ingic.lmslawyer.activities.DockActivity;
import com.ingic.lmslawyer.constants.WebServiceConstant;
import com.ingic.lmslawyer.entities.ResponseWrapper;
import com.ingic.lmslawyer.interfaces.webServiceResponseLisener;
import com.ingic.lmslawyer.retrofit.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;
    private WebService webService;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response.body() == null || response.body().getResponse() == null) return;
                    if (response.body().getResponse() == (WebServiceConstant.SUCCESS_RESPONSE_CODE)) {

                        serviceResponseLisener.ResponseSuccess(response.body().getResult(), tag, response.body().getMessage());
                    } else {
                        UIHelper.showShortToastInCenter(context, response.body().getMessage());
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    UIHelper.showShortToastInCenter(context, t.getMessage());
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

}
