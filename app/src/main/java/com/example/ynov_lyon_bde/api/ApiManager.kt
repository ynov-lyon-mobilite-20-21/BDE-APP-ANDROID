package com.example.ynov_lyon_bde.api


import com.example.ynov_lyon_bde.model.LoginDTO
import com.example.ynov_lyon_bde.model.User
import com.example.ynov_lyon_bde.model.UserDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiManager {
    fun addUser(userData: UserDTO, onResult: (Response<UserDTO>) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<UserDTO> {
                override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                    print("THROWABLE : $t")
                    return
                }

                override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                    val addedUser = response.body()
                    print("ADDED USER : $addedUser")
                    onResult(response)
                }
            }
        )
    }

    fun loginUser(userData: LoginDTO, onResult: (User?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.loginUser(userData).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    onResult(null)
                }

                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {
                    val connectedUser = response.body()
                    onResult(connectedUser)
                }
            }
        )
    }

}



