package dev.skaba.soma.app.di

import android.content.Context
import androidx.room.Room
import dev.skaba.soma.app.data.AppDatabase
import dev.skaba.soma.app.data.food.FoodRepositoryImpl
import dev.skaba.soma.app.domain.food.FoodRepository
import dev.skaba.soma.app.util.ImageProcessor

class AppContainer(private val context: Context) {

  val imageProcessor = ImageProcessor(context)

  private val database = Room.databaseBuilder(
    context,
    AppDatabase::class.java,
    "app_database"
  ).build()

  private val foodDao = database.foodDao()

  val foodRepository: FoodRepository = FoodRepositoryImpl(foodDao)
}