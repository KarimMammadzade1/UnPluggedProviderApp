package r.team.unpluggedproviderapp.domain.model

data class DeviceResponseDO(
    val id: String? = null,
    val name: String? = null,
    val deviceDetails: Map<String, String>? = null
)