package dev.skaba.soma.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.skaba.soma.app.BuildConfig
import dev.skaba.soma.app.auth.AuthRepository
import dev.skaba.soma.app.data.AppDatabase
import dev.skaba.soma.app.data.food.FoodRepositoryImpl
import dev.skaba.soma.app.data.food.remote.FoodApi
import dev.skaba.soma.app.data.log_entry.LogEntryRepositoryImpl
import dev.skaba.soma.app.data.targets.TargetsRepositoryImpl
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.domain.log_entry.LogEntryRepository
import dev.skaba.soma.app.util.ImageProcessor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class AppContainer(context: Context) {

  // utils
  val imageProcessor = ImageProcessor(context)

  // db
  private val database = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "soma_db",
  )
    .addCallback(
      object : RoomDatabase.Callback() {
        // overridnuti creatuni databaze
        override fun onCreate(db: SupportSQLiteDatabase) {
          super.onCreate(db)
          db.execSQL("INSERT INTO targets (id) VALUES (1)")
        }
      },
    )
    .build()

  // local daos
  private val foodDao = database.foodDao()
  private val targetsDao = database.targetsDao()
  private val logEntryDao = database.logEntryDao()

  // network
  private val networkJson = Json { ignoreUnknownKeys = true }
  private val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BACKEND_URL)
    .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
    .build()

  // apis
  val foodApi: FoodApi by lazy {
    retrofit.create(FoodApi::class.java)
  }

  // repositories
  val authRepository: AuthRepository = AuthRepository()
  val foodRepository: FoodRepository = FoodRepositoryImpl(foodDao, foodApi, authRepository)
  val targetsRepository = TargetsRepositoryImpl(targetsDao)
  val logEntryRepository: LogEntryRepository = LogEntryRepositoryImpl(logEntryDao, foodRepository)
}
