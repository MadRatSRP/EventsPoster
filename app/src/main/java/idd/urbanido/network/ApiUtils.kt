package idd.urbanido.network

object ApiUtils {
    private const val BASE_URL = "https://hacaton2018app.herokuapp.com/"

    //private context

    val apiService: APIService
        get() = RetrofitClient.getClient(BASE_URL)!!.create(APIService::class.java)
}