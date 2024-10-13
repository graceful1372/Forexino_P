package ir.hmb72.forexuser.data.stored

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.hmb72.forexuser.data.model.network.login.RegisterStoreModel
import ir.hmb72.forexuser.utils.FOREXINO_USERNAME
import ir.hmb72.forexuser.utils.FOREXINO_USER_DATA_STORE
import ir.hmb72.forexuser.utils.WELCOME_CHECK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(FOREXINO_USER_DATA_STORE)

class SessionManager @Inject constructor(@ApplicationContext private val context :Context) {

    private val appContext = context.applicationContext

    private object StoredKeys {
        val username = stringPreferencesKey(FOREXINO_USERNAME)
        val welcome = stringPreferencesKey(WELCOME_CHECK)
    }

    


    suspend fun saveRegisterData(username: String) {

        context.dataStore.edit {

            it[StoredKeys.username] = username
            it[StoredKeys.welcome] = "welcome"

        }
    }

    //Clear dataStore
    suspend fun clearRegisterData() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    //Read register user name
    val readRegisterData: Flow<RegisterStoreModel> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            val username = it[StoredKeys.username] ?: ""
            RegisterStoreModel(username)
        }

    //Read welcome
    val readWelcome: Flow<String> = context.dataStore.data
        .catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            val welcome = it[StoredKeys.welcome] ?: ""
            welcome
        }



}