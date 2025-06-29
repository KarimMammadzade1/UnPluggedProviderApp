package r.team.unpluggedproviderapp.domain.usecase

import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import r.team.unpluggedproviderapp.core_domain.usecase.CoreUseCase
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO
import r.team.unpluggedproviderapp.domain.repository.DevicesRepository
import javax.inject.Inject

class SaveDevicesUseCase @Inject constructor(
    private val devicesRepository: DevicesRepository
) : CoreUseCase<Unit, Unit>() {

    override suspend fun run(params: Unit): ResultWrapper<Unit> {
        return devicesRepository.saveDevices()
    }
}