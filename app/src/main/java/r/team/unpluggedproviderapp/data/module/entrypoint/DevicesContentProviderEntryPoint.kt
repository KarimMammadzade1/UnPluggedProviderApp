package r.team.unpluggedproviderapp.data.module.entrypoint

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import r.team.unpluggedproviderapp.data.datasource.local.dao.DevicesDAO

@EntryPoint
@InstallIn(SingletonComponent::class)
interface DevicesContentProviderEntryPoint {
    fun devicesDao(): DevicesDAO
}