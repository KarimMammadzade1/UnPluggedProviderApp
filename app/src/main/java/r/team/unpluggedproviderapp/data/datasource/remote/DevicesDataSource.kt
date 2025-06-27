package r.team.unpluggedproviderapp.data.datasource.remote

import r.team.unpluggedproviderapp.data.model.DeviceResponse
import r.team.unpluggedproviderapp.data.model.DevicesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DevicesDataSource {
    private companion object {
        private const val DEVICES = "objects"

    }

    @GET(DEVICES)
    suspend fun getDevices(): Response<List<DeviceResponse>>

}