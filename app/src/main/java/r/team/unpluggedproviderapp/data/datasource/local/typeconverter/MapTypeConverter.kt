package r.team.unpluggedproviderapp.data.datasource.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromMap(value: Map<String, String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMap(value: String?): Map<String, String>? {
        return if (value == null) null
        else gson.fromJson(value, object : TypeToken<Map<String, String>>() {}.type)
    }
}