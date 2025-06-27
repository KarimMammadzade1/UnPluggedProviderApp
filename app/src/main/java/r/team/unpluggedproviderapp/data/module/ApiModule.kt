package r.team.unpluggedproviderapp.data.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import r.team.unpluggedproviderapp.core_data.module.NetworkModule
import r.team.unpluggedproviderapp.core_data.network.GeneralRetrofitClient
import r.team.unpluggedproviderapp.data.datasource.remote.DevicesDataSource
import r.team.unpluggedproviderapp.data.repository.DevicesRepositoryImpl
import r.team.unpluggedproviderapp.domain.repository.DevicesRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    abstract fun provideDevicesRepository(devicesRepositoryImpl: DevicesRepositoryImpl): DevicesRepository

    companion object {

        @Provides
        @Singleton
        internal fun provideSignInDataSource(@GeneralRetrofitClient retrofit: Retrofit) =
            retrofit.create(DevicesDataSource::class.java)


    }

}