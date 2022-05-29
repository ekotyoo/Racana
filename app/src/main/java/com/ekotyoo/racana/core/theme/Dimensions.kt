package com.ekotyoo.racana.core.theme

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

class Dimensions(
    val sp10: TextUnit,
    val sp12: TextUnit,
    val sp14: TextUnit,
    val sp16: TextUnit,
    val sp20: TextUnit,
    val sp24: TextUnit
)

val mdpi = Dimensions(
    sp10 = 10.sp,
    sp12 = 12.sp,
    sp14 = 14.sp,
    sp16 = 16.sp,
    sp20 = 20.sp,
    sp24 = 24.sp
)

val hdpi = Dimensions(
    sp10 = 12.sp,
    sp12 = 16.sp,
    sp14 = 18.sp,
    sp16 = 21.sp,
    sp20 = 26.sp,
    sp24 = 32.sp
)