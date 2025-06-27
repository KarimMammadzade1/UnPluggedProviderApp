package r.team.unpluggedproviderapp.data.mapper

import r.team.unpluggedproviderapp.data.datasource.local.dao.DeviceEntity
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO

class DeviceMapper {

    fun toEntity(item: DeviceResponseDO): DeviceEntity = DeviceEntity(
        id = item.id.orEmpty(),
        name = item.name.orEmpty(),
        deviceDetails = item.deviceDetails
    )

    fun toDO(item: DeviceEntity): DeviceResponseDO = DeviceResponseDO(
        id = item.id,
        name = item.name,
        deviceDetails = item.deviceDetails
    )
}