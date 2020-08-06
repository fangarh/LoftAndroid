package com.dds.core.faces;

import com.dds.core.DTC.AuthResponseDTC;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWebAuthorization {
    @GET("./auth")
    Single<AuthResponseDTC>  performLogin(@Query("social_user_id") String uid);

}
