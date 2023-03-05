package com.example.chinesehelpchinese

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface UserService {

    @POST("user/login")
    fun login(@Body data:HttpLogin):Call<LoginReturn>


    @POST("user/enroll")
    fun enroll(@Body data:HttpEnroll):Call<EnrollReturn>

    @PUT("user")
    fun changeInformation(@Body data:HttpChange):Call<ChangeInformationReturn>
    //怎么检测错误token呢？随便发一个project相关请求，检测到错误token强制退出即可，反正错的，请求也不会实现


   @GET("project")
    fun getTargetProject (@Query("Title") title:String,@Query("Token") token:String  ):Call<GetTargetProjectReturn>//获得单独一个

    @HTTP(method = "GET", path = "project/all", hasBody = true)
    fun getAllProject (@Query("Token") token: String):Call<GetAllProjectReturn>

    @GET("admin")
    fun getProjectForAdmin(@Query("Token") token: String):Call<GetAllProjectReturn>

    @PUT("project")
    fun giveMoney(@Body data: HttpGiveMoney,@Query("Token") token: String):Call<ErrorReturn>//捐钱,实际修改项目

    @POST("project")
    fun createProject(@Body data :HttpCreateProject,@Query("Token") token: String):Call<ErrorReturn>

    @PUT("project")
    fun adminWork(@Body data :HttpAdminWork,@Query("Token") token: String):Call<ErrorReturn>

    @DELETE("project")
    fun deleteProject(@Body data:HttpDeleteProject,@Query("Token") token: String):Call<ErrorReturn>//实际用于发现该项目钱筹集完毕发送该请求
}