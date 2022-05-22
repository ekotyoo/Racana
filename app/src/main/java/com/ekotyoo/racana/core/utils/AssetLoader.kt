package com.ekotyoo.racana.core.utils

import android.content.Context
import com.ekotyoo.racana.data.datasource.local.CityWithProvince
import com.ekotyoo.racana.data.datasource.local.CityWithProvinceItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object AssetLoader {
    fun getCityProvince(context: Context): List<Pair<String, String>> {
        val jsonString = context.assets.open("citiesprovince.json").bufferedReader()
            .use { it.readText() }
        val cityProvinceType = object : TypeToken<List<CityWithProvinceItem>>() {}.type
        val cityWithProvince = Gson().fromJson(jsonString, cityProvinceType) as List<CityWithProvinceItem>
        val cities = cityWithProvince.map { province ->
            province.kota.map { city ->
                city to province.provinsi
            }
        }.flatten()
        return cities
    }
}