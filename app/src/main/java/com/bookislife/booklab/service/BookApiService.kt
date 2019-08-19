package com.bookislife.booklab.service

import com.bookislife.booklab.model.BaseResponse
import com.bookislife.booklab.model.Book
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BookApiService {

    @GET("books")
    fun list(): Observable<BaseResponse<List<Book>>>

    companion object {
        fun create(): BookApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/api/")
                .build()
            return retrofit.create(BookApiService::class.java)
        }
    }
}