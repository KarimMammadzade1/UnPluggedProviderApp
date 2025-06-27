package r.team.unpluggedproviderapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import r.team.unpluggedproviderapp.data.datasource.local.dao.DeviceEntity
import r.team.unpluggedproviderapp.data.datasource.local.dao.DevicesDAO
import r.team.unpluggedproviderapp.data.datasource.local.typeconverter.MapTypeConverter

@Database(
    entities = [DeviceEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(MapTypeConverter::class)
abstract class DevicesDatabase : RoomDatabase() {

    abstract fun devicesDAO(): DevicesDAO

    companion object {
        internal const val DB_NAME = "DevicesDatabase"
    }
}