package r.team.unpluggedproviderapp.data.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import r.team.unpluggedproviderapp.data.datasource.local.DevicesDatabase
import r.team.unpluggedproviderapp.data.datasource.local.dao.DevicesDAO
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    internal fun provideRoomDatabase(@ApplicationContext context: Context): DevicesDatabase {
        return Room.databaseBuilder(
            context,
            DevicesDatabase::class.java,
            DevicesDatabase.DB_NAME
        )
            .build()
    }

    @Provides
    internal fun provideDevicesDao(database: DevicesDatabase): DevicesDAO = database.devicesDAO()

}