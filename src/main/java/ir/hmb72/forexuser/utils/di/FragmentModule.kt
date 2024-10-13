package ir.hmb72.forexuser.utils.di

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import ir.hmb72.forexuser.data.model.network.login.BodyLogin
import ir.hmb72.forexuser.data.model.network.login.BodyRegister

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun bodyRegister() = BodyRegister()

    @Provides
    fun bodyLogin() = BodyLogin()
}