package r.team.unpluggedproviderapp.core_domain.model

import java.io.Serializable

data class ErrorModelDO(
    val type: Int? = null,
    val code: Int? = null,
    val message: String? = null
): Serializable

const val EMPTY = ""
