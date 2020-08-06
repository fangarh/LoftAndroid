package com.dds.core.faces;

import com.dds.core.DTC.AnswerDTC;
import com.dds.core.DTC.MoneyDTC;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IWebMoneyApi {
    @GET("./items")
    Single<MoneyDTC> getBudget(@Query("type") String type);

    @POST("./items/add")
    @FormUrlEncoded
    Single<AnswerDTC> addBudget(@Field("price") String price,
                                @Field("name") String name,
                                @Field("type") String type);
}
