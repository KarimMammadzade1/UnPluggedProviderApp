package r.team.unpluggedproviderapp.domain.repository

import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO

interface DevicesRepository {

    suspend fun getDevices(): ResultWrapper<List<DeviceResponseDO>>
    suspend fun saveDevices(items: List<DeviceResponseDO>): ResultWrapper<Unit>
    suspend fun getDevicesFromDB(): ResultWrapper<List<DeviceResponseDO>>
}