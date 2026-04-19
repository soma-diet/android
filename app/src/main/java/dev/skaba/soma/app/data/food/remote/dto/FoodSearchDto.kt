package dev.skaba.soma.app.data.food.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PagedFoodResponse(
  val content: List<FoodResponseDto>,
  val empty: Boolean,
  val first: Boolean,
  val last: Boolean,
  val number: Int,
  val numberOfElements: Int,
  val pageable: Pageable,
  val size: Int,
  val sort: Sort,
  val totalElements: Int,
  val totalPages: Int
)

@Serializable
data class Pageable(
  val offset: Int,
  val pageNumber: Int,
  val pageSize: Int,
  val paged: Boolean,
  val sort: Sort,
  val unpaged: Boolean
)

@Serializable
data class Sort(
  val empty: Boolean,
  val sorted: Boolean,
  val unsorted: Boolean
)