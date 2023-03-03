
package com.example.testing.interfaces;

import com.example.testing.classes.APIRawData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIEndPoint
{
    @GET("reports?appname=rwint-user-0&profile=list&preset=latest&slim=1&query%5Bvalue%5D=primary_country.id%3A182&query%5Boperator%5D=AND")
    Call<APIRawData> getRawData();

}
