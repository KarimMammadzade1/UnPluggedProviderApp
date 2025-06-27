package r.team.unpluggedproviderapp.data.model

import java.io.Serializable

data class DevicesResponse(
    val devices: List<DeviceResponse>
) : Serializable
