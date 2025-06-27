package r.team.unpluggedproviderapp.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import r.team.unpluggedproviderapp.core_data.network.handleResult
import r.team.unpluggedproviderapp.core_domain.model.ErrorModelDO
import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import r.team.unpluggedproviderapp.core_domain.model.processResultWrapper
import r.team.unpluggedproviderapp.data.datasource.local.dao.DevicesDAO
import r.team.unpluggedproviderapp.data.datasource.remote.DevicesDataSource
import r.team.unpluggedproviderapp.data.mapper.DeviceMapper
import r.team.unpluggedproviderapp.data.model.DeviceResponse.Companion.toDO
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO
import r.team.unpluggedproviderapp.domain.repository.DevicesRepository
import javax.inject.Inject

class DevicesRepositoryImpl @Inject constructor(
    private val devicesDataSource: DevicesDataSource,
    private val devicesDAO: DevicesDAO
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

    override suspend fun saveDevices(items: List<DeviceResponseDO>): ResultWrapper<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val mapper = DeviceMapper()
                val entities = items.map { item -> mapper.toEntity(item) }
                devicesDAO.upsertDevices(entities)
                ResultWrapper.Success(Unit)
            } catch (e: Exception) {
                ResultWrapper.Error(
                    exception = e,
                    message = e.message,
                    error = ErrorModelDO(message = e.message)
                )
            }
        }

    override suspend fun getDevicesFromDB(): ResultWrapper<List<DeviceResponseDO>> =
        withContext(Dispatchers.IO) {
            try {
                val entities = devicesDAO.getAllDevices()
                val mapper = DeviceMapper()
                val items = entities.map { item -> mapper.toDO(item) }
                ResultWrapper.Success(items)
            } catch (e: Exception) {
                ResultWrapper.Error(
                    exception = e,
                    message = e.message,
                    error = ErrorModelDO(message = e.message)
                )
            }
        }


}