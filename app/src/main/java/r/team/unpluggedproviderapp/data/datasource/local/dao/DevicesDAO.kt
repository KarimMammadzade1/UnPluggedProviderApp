package r.team.unpluggedproviderapp.data.datasource.local.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface DevicesDAO {
    @Query("SELECT * FROM DeviceEntity")
    suspend fun getAllDevices(): List<DeviceEntity>

    @Query("SELECT * FROM DeviceEntity")
    fun getDevicesCursor(): Cursor

    @Query("SELECT * FROM DeviceEntity WHERE name LIKE '%' || :searchQuery || '%'")
    fun getDevicesCursorByName(searchQuery: String): Cursor

    @Delete
    suspend fun deleteDevice(item: DeviceEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAccount(item: DeviceEntity): Long

    @Upsert
    suspend fun upsertAccount(item: DeviceEntity): Long

    @Upsert
    suspend fun upsertDevices(items: List<DeviceEntity>): List<Long>

    @Update()
    suspend fun updateAccount(item: DeviceEntity): Int
}