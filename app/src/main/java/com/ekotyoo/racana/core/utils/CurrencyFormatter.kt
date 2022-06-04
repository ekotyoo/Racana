package com.ekotyoo.racana.core.utils

import java.text.NumberFormat
import java.util.*

fun currencyFormatter(price: String) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"


fun currencyFormatter(price: Int) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price)}"

fun currencyFormatter(price: Double) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"

fun currencyFormatter(price: Float) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"

fun currencyFormatter(price: Long) =
    "Rp ${NumberFormat.getInstance(Locale.ITALY).format(price.toInt())}"

