package r.team.unpluggedproviderapp.core_domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import r.team.unpluggedproviderapp.core_domain.model.ResultWrapper


/**
 * Abstract base class representing a use case in the domain layer.
 *
 * @param Params The input parameters type for the use case.
 * @param Type The result type produced by the use case.
 *
 */
abstract class CoreUseCase<in Params, Type> {

    /**
     * Executes the use case logic synchronously and returns a ResultWrapper containing the result.
     *
     * @param params The input parameters for the use case.
     * @return A ResultWrapper containing the result or an error.
     */
    abstract suspend fun run(params: Params): ResultWrapper<Type>

    /**
     * Invokes the use case, providing a convenient way to execute it.
     *
     * @param params The input parameters for the use case.
     * @return A ResultWrapper containing the result or an error.
     */
    suspend operator fun invoke(params: Params): ResultWrapper<Type> {
        return run(params)
    }

    /**
     * Executes the use case logic asynchronously and emits the ResultWrapper as a Flow.
     *
     * @param params The input parameters for the use case.
     * @return A Flow emitting a ResultWrapper containing the result or an error.
     */
    suspend fun runAsFlow(params: Params): Flow<ResultWrapper<Type>> = flow {
        emit(run(params))
    }
}

/**
 * Extension function to execute a use case as a Flow and apply a reducer function to the emitted ResultWrapper.
 *
 * @param params The input parameters for the use case.
 * @param reducer A suspend function to handle the emitted ResultWrapper.
 */
suspend fun <P, T> CoreUseCase<P, T>.execute(params: P, reducer: suspend (ResultWrapper<T>) -> Unit) {
    this.runAsFlow(params).collect {
        reducer.invoke(it)
    }
}
