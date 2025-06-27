package r.team.unpluggedproviderapp.data.repository

import r.team.unpluggedproviderapp.core_data.network.handleResult
import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import r.team.unpluggedproviderapp.core_domain.model.processResultWrapper
import r.team.unpluggedproviderapp.data.datasource.remote.DevicesDataSource
import r.team.unpluggedproviderapp.data.model.DeviceResponse.Companion.toDO
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO
import r.team.unpluggedproviderapp.domain.repository.DevicesRepository
import javax.inject.Inject

class DevicesRepositoryImpl @Inject constructor(
    private val devicesDataSource: DevicesDataSource
) : DevicesRepository {
    override suspend fun getDevices(): ResultWrapper<List<DeviceResponseDO>> {
        return processResultWrapper(handleResult {
            devicesDataSource.getDevices()
        }) { result ->
            result?.let { devicesResponse ->
                devicesResponse.map { it.toDO() }
            } ?: emptyList()
        }
    }
}