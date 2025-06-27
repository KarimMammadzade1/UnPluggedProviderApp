package r.team.unpluggedproviderapp.domain.usecase

import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import r.team.unpluggedproviderapp.core_domain.usecase.CoreUseCase
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO
import r.team.unpluggedproviderapp.domain.repository.DevicesRepository
import javax.inject.Inject

class GetDevicesFromDBUseCase @Inject constructor(
    private val devicesRepository: DevicesRepository
) : CoreUseCase<Unit, List<DeviceResponseDO>>() {
    override suspend fun run(params: Unit): ResultWrapper<List<DeviceResponseDO>> {
        return devicesRepository.getDevicesFromDB()
    }
}

