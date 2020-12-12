package com.example.ynov_lyon_bde.api


import com.example.ynov_lyon_bde.model.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiManager {
    fun addUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    val addedUser = response.body()
                    onResult(addedUser)
                }
            }
        )
    }

    fun loginUser(userDatas: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.loginUser(userDatas).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<UserInfo>,
                    response: Response<UserInfo>
                ) {
                    val connectedUser = response.body()
                    onResult(connectedUser)
                }
            }
        )
    }

}



