package com.dds.loftmoney.utils.faces;

import com.dds.loftmoney.domain.dtc.AnswerDTC;
import com.dds.loftmoney.domain.dtc.BudgetDTC;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IWebMoneyApi {
    @GET("./items")
    Single<List<BudgetDTC>> getBudget(@Query("auth-token") String token, @Query("type") String type);

    @POST("./items/add")
    @FormUrlEncoded
    Single<AnswerDTC> addBudget(@Field("auth-token") String token,
                                @Field("price") String price,
                                @Field("name") String name,
                                @Field("type") String type);
}
