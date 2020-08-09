package com.dds.loftmoney.utils.faces.web;


import com.dds.loftmoney.domain.dtc.AuthResponseDTC;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWebAuthorization {
    @GET("./auth")
    Single<AuthResponseDTC> performLogin(@Query("social_user_id") String uid);

}
