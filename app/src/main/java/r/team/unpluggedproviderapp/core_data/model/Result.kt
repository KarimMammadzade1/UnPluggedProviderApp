package r.team.unpluggedproviderapp.core_data.model

import com.google.gson.annotations.SerializedName
import r.team.unpluggedproviderapp.core_domain.model.ErrorModelDO
import java.io.Serializable

data class Result<T : Serializable>(
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("error")
    val error: String? = null,
) : Serializable
//
//data class Error(
//    @SerializedName("errorCode")
//    val errorCode: String? = null,
//    @SerializedName("errorMessage")
//    val errorMessage: String? = null,
//    @SerializedName("errorType")
//    val errorType: String? = null,
//    @SerializedName("isValid")
//    val isValid: Boolean = false,
//) : Serializable {
//    companion object {
//        fun toDo(error: Error?): ErrorModelDO {
//            return ErrorModelDO(
//                type = error?.errorType?.toIntOrNull(),
//                code = error?.errorCode?.toIntOrNull(),
//                message = error?.errorMessage
//            )
//        }
//    }
//}