package r.team.unpluggedproviderapp.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.util.Log
import dagger.hilt.android.EntryPointAccessors
import r.team.unpluggedproviderapp.BuildConfig
import r.team.unpluggedproviderapp.data.datasource.local.dao.DevicesDAO
import r.team.unpluggedproviderapp.data.module.entrypoint.DevicesContentProviderEntryPoint

class DevicesContentProvider : ContentProvider() {

    private lateinit var devicesDao: DevicesDAO

    override fun onCreate(): Boolean {
        val appContext = context?.applicationContext ?: return false

        val entryPoint = EntryPointAccessors.fromApplication(
            appContext,
            DevicesContentProviderEntryPoint::class.java
        )

        devicesDao = entryPoint.devicesDao()

        return true
    }


    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        if (!isCallerAllowed()) throw SecurityException("Access denied")

        return when (uri.pathSegments.firstOrNull()) {
            "devices" -> {
                val searchQuery = selectionArgs?.getOrNull(0) ?: ""
                if (searchQuery.isEmpty()) {
                    devicesDao.getDevicesCursor()
                } else {
                    devicesDao.getDevicesCursorByName(searchQuery)
                }
            }

            else -> null
        }
    }


    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0

    private fun isCallerAllowed(): Boolean {
        val context = context ?: return false
        val callingPackage = callingPackage ?: return false
        val allowedPackageName = BuildConfig.ALLOWED_CONSUMER_PACKAGE


        if (callingPackage != allowedPackageName) {
            Log.e("DevicesContentProvider", "Unauthorized caller: $callingPackage")

            return false
        }

        val packageManager = context.packageManager

        return try {

            val callerSignatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(
                    callingPackage,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
                    .signingInfo
                    ?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(callingPackage, PackageManager.GET_SIGNATURES)
                    .signatures
            }

            val providerSignatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
                    .signingInfo
                    ?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
                    .signatures
            }

            callerSignatures ?: return false
            providerSignatures ?: return false

            callerSignatures.any { callerSig ->
                providerSignatures.any { providerSig ->
                    callerSig.toCharsString() == providerSig.toCharsString()
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

}