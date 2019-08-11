package idd.eventsposter.model.users

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import idd.eventsposter.model.Users

data class UsersResponse(@field:SerializedName("users")
                         @field:Expose
                         val users: List<Users>)