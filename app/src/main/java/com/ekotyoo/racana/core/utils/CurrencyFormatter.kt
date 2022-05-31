package com.ekotyoo.racana.core.utils

import java.text.NumberFormat
import java.util.*

fun CurrencyFormatter(price: String) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"


fun CurrencyFormatter(price: Int) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price)}"

fun CurrencyFormatter(price: Double) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"

fun CurrencyFormatter(price: Float) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"

fun CurrencyFormatter(price: Long) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"

