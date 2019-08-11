package idd.eventsposter.network

import idd.eventsposter.model.EventResponse
import idd.eventsposter.model.registration.RegistrationResponse
import idd.eventsposter.model.users.UsersResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {

    @POST("events")
    fun getpost(@Body event:EventResponse): Call<EventResponse>

    @POST("api/v1/users")
    fun postuser(@Body registration:RegistrationResponse): Call<RegistrationResponse>


    @GET("api/v1/users")
    fun getuser(): Observable<UsersResponse> }



