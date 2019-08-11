package idd.eventsposter.repositories

import android.content.Context
import idd.eventsposter.interfaces.fragments.UsersMVP
import idd.eventsposter.model.registered_user.RegisteredUsers
import idd.eventsposter.network.NetworkClient
import idd.eventsposter.network.NetworkInterface
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsersRepository: UsersMVP.Repository {
    override fun getUsersObservable(context: Context): Observable<List<RegisteredUsers>>? {
        return NetworkClient.getRetrofit(context)?.create<NetworkInterface>(NetworkInterface::class.java)
                ?.getusers()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }
    override fun getUserObservable(context: Context): Observable<RegisteredUsers>? {
        return NetworkClient.getRetrofit(context)?.create<NetworkInterface>(NetworkInterface::class.java)
                ?.getuser("1")
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }
}