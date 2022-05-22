package com.ekotyoo.racana.data.datasource.local

import com.google.gson.annotations.SerializedName

data class CityWithProvince(
	@field:SerializedName("CityWithProvince")
	val items: List<CityWithProvinceItem>
)

data class CityWithProvinceItem(
	@field:SerializedName("provinsi")
	val provinsi: String,

	@field:SerializedName("kota")
	val kota: List<String>
)
