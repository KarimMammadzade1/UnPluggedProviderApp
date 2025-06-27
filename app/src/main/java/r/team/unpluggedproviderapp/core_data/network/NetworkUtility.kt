package r.team.unpluggedproviderapp.core_data.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import okio.IOException
import org.json.JSONObject
import r.team.unpluggedproviderapp.core_domain.model.ErrorModelDO
import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper
import retrofit2.Response
import java.io.Serializable
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException
import r.team.unpluggedproviderapp.core_data.model.Result

/**
 * Handles the result of an API call and returns a [ResultWrapper] representing the outcome.
 *
 * @param globalErrorHandler The global error handler to handle specific error cases.
 * @param apiCall The lambda function representing the API call, returning a Retrofit [Response] object.
 * @return A [ResultWrapper] containing either the successful result or an error.
 *
 */
inline fun <T : Serializable> handleResult(
    apiCall: () -> Response<Result<T>>?
): ResultWrapper<T?> {
    return try {
        val result = apiCall()
        if (result?.isSuccessful == true) {
            ResultWrapper.Success(result.body()?.data)
        } else {
            val errorMessage = result?.errorBody()?.getErrorMessage()
            ResultWrapper.Error(
                exception = Exception(errorMessage),
                message = errorMessage,
                error = ErrorModelDO(message = errorMessage)
            )
        }
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        when (throwable) {
            is UnknownHostException,
            is SSLHandshakeException,
            is SocketTimeoutException,
            is SocketException -> {
                val error = ResultWrapper.Error(
                    message = throwable.message,
                    error = ErrorModelDO(
                        code = Integer.MAX_VALUE - 2
                    )
                )
                error
            }

            is IOException -> {
                val error = ResultWrapper.Error(
                    message = throwable.message,
                    error = ErrorModelDO(
                        code = 1
                    )
                )
                error
            }

            else -> {
                val error = ResultWrapper.Error(
                    message = throwable.message,
                    error = ErrorModelDO(code = Integer.MAX_VALUE, message = throwable.message)
                )
                error
            }
        }

    }
}

/**
 * Parses the error response body and deserializes it into the specified type [T].
 *
 * @return An object of type [T] representing the error details.
 * @throws JsonSyntaxException If the error response body cannot be deserialized into [T].
 */
inline fun <reified T> ResponseBody.getErrorObject(): T {
    val gson = Gson()
    val jsonObject = JSONObject(charStream().readText())
    return gson.fromJson(jsonObject.toString(), T::class.java)
}

fun ResponseBody.getErrorMessage(errorFieldKey: String = "error"): String {
    val jsonObject = JSONObject(charStream().readText())
    return jsonObject.getString(errorFieldKey)
}

/**
 * Constant representing the HTTP status code for unauthorized access.
 */
const val UNAUTHORIZED_CODE = 401
const val SOMETHING_WENT_WRONG = "Something went wrong"
