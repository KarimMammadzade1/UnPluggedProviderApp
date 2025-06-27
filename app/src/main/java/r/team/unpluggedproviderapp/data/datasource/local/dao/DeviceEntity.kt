package r.team.unpluggedproviderapp.data.datasource.local.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import r.team.unpluggedproviderapp.data.datasource.local.dao.DeviceEntity.Companion.DEVICE_ENTITY_TABLE_NAME
import r.team.unpluggedproviderapp.data.datasource.local.typeconverter.MapTypeConverter


@Entity(tableName = DEVICE_ENTITY_TABLE_NAME)
data class DeviceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    @TypeConverters(MapTypeConverter::class)
    val deviceDetails: Map<String, String>?
) {

    companion object {
        internal const val DEVICE_ENTITY_TABLE_NAME = "DeviceEntity"
    }
}