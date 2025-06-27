package r.team.unpluggedproviderapp.data.model

import com.google.gson.annotations.SerializedName
import r.team.unpluggedproviderapp.domain.model.DeviceResponseDO
import java.io.Serializable

data class DeviceResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("data")
    val deviceDetails: Map<String, String>?
) : Serializable {
    companion object {
        fun DeviceResponse.toDO() = DeviceResponseDO(
            id, name, deviceDetails
        )
    }
}
