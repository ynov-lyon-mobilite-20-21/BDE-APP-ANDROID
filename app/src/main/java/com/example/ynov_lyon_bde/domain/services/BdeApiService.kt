package com.example.ynov_lyon_bde.domain.services


import com.example.ynov_lyon_bde.data.model.LoginDTO
import com.example.ynov_lyon_bde.data.model.User
import com.example.ynov_lyon_bde.data.model.UserDTO
import com.example.ynov_lyon_bde.domain.utils.RetrofitServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BdeApiService {
    fun createUser(userData: UserDTO, onResult: (Response<UserDTO>) -> Unit){
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)
        retrofit.createUser(userData).enqueue(
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
        val retrofit = RetrofitServiceBuilder.buildService(BdeApiInterface::class.java)
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



