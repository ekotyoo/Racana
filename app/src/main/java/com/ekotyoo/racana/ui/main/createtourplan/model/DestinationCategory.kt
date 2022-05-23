package com.ekotyoo.racana.ui.main.createtourplan.model

data class DestinationCategory(
    val id: String,
    val title: String,
    val imageUrl: String,
)

fun getCategories(): List<DestinationCategory> {
    return listOf(
        DestinationCategory(
            "1",
            "Pantai",
            "https://cdn.pixabay.com/photo/2014/08/15/11/29/beach-418742_960_720.jpg"
        ),
        DestinationCategory(
            "2",
            "Lembah",
            "https://cdn.pixabay.com/photo/2019/07/14/10/48/vineyards-4336787_960_720.jpg"
        ),
        DestinationCategory(
            "3",
            "Gunung",
            "https://cdn.pixabay.com/photo/2021/08/08/20/37/mountains-6531903_960_720.jpg"
        ),
        DestinationCategory(
            "4",
            "Hutan",
            "https://cdn.pixabay.com/photo/2019/02/17/22/50/jungle-4003374_960_720.jpg"
        )
    )
}