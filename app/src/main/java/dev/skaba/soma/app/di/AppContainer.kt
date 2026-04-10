package dev.skaba.soma.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.skaba.soma.app.data.AppDatabase
import dev.skaba.soma.app.data.food.FoodRepositoryImpl
import dev.skaba.soma.app.data.targets.TargetsRepositoryImpl
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.util.ImageProcessor

class AppContainer(private val context: Context) {

  val imageProcessor = ImageProcessor(context)

  private val database = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "app_database",
  )
    .addCallback(
      object : RoomDatabase.Callback() {
        // overridnuti creatuni databaze
        override fun onCreate(db: SupportSQLiteDatabase) {
          super.onCreate(db)
          db.execSQL("INSERT INTO targets (id) VALUES (1)") // potrebuju inicializovat prazdne targets
        }
      },
    )
    .build()

  private val foodDao = database.foodDao()
  private val targetsDao = database.targetsDao()

  val foodRepository: FoodRepository = FoodRepositoryImpl(foodDao)
  val targetsRepository = TargetsRepositoryImpl(targetsDao)
}