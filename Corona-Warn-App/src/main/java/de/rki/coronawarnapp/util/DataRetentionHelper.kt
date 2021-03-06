/******************************************************************************
 * Corona-Warn-App                                                            *
 *                                                                            *
 * SAP SE and all other contributors /                                        *
 * copyright owners license this file to you under the Apache                 *
 * License, Version 2.0 (the "License"); you may not use this                 *
 * file except in compliance with the License.                                *
 * You may obtain a copy of the License at                                    *
 *                                                                            *
 * http://www.apache.org/licenses/LICENSE-2.0                                 *
 *                                                                            *
 * Unless required by applicable law or agreed to in writing,                 *
 * software distributed under the License is distributed on an                *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY                     *
 * KIND, either express or implied.  See the License for the                  *
 * specific language governing permissions and limitations                    *
 * under the License.                                                         *
 ******************************************************************************/

package de.rki.coronawarnapp.util

import android.content.Context
import android.util.Log
import de.rki.coronawarnapp.storage.AppDatabase
import de.rki.coronawarnapp.storage.FileStorageHelper
import de.rki.coronawarnapp.storage.LocalData
import java.util.UUID

/**
 * Helper for supplying functionality regarding Data Retention
 */
object DataRetentionHelper {
    private val TAG: String? = DataRetentionHelper::class.simpleName

    /**
     * Deletes all data known to the Application
     *
     */
    fun clearAllLocalData(context: Context) {
        Log.w(TAG, "CWA LOCAL DATA DELETION INITIATED.")
        // Database Reset
        AppDatabase.getInstance(context).clearAllTables()
        // Shared Preferences Reset
        LocalData.getSharedPreferenceInstance().edit().clear().apply()
        // Delete Database Instance
        AppDatabase.resetInstance(context)
        LocalData.databasePassword(UUID.randomUUID().toString().toCharArray())
        // Export File Reset
        FileStorageHelper.getAllFilesInKeyExportDirectory().forEach { it.delete() }
        Log.w(TAG, "CWA LOCAL DATA DELETION COMPLETED.")
    }
}
