package r.team.unpluggedproviderapp.core_domain.model

sealed class ResultWrapper<out T> {
    data class Success<T>(val success: T) : ResultWrapper<T>()
    data class Error(
        val exception: Exception? = null,
        val message: String? = null,
        val error: ErrorModelDO = ErrorModelDO(code = 0),
    ) : ResultWrapper<Nothing>()
}


inline fun <reified T, reified R> processResultWrapper(
    result: ResultWrapper<T>,
    transform: (T?) -> R
): ResultWrapper<R> {
    return when (result) {
        is ResultWrapper.Error -> ResultWrapper.Error(
            exception = result.exception,
            message = result.message,
            error = result.error,
        )

        is ResultWrapper.Success -> {
            try {
                ResultWrapper.Success(transform(result.success))
            } catch (e: Exception) {
                e.printStackTrace()
                ResultWrapper.Error(e)
            }
        }
    }
}

