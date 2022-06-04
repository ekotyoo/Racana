package com.ekotyoo.racana.ui.main.createtourplan.model

data class DestinationCategory(
    val id: Int,
    val title: String,
    val imageUrl: String,
)

fun getCategories(): List<DestinationCategory> {
    return listOf(
        DestinationCategory(
            1,
            "Agrowisata",
            "https://cdn.pixabay.com/photo/2018/02/18/09/33/landscape-3161800_960_720.jpg"
        ),
        DestinationCategory(
            2,
            "Alam",
            "https://cdn.pixabay.com/photo/2014/08/05/04/19/hawaii-410128_960_720.jpg"
        ),
        DestinationCategory(
            3,
            "Belanja",
            "https://cdn.pixabay.com/photo/2014/01/12/17/20/bali-242946_960_720.jpg"
        ),
        DestinationCategory(
            4,
            "Budaya",
            "https://cdn.pixabay.com/photo/2017/11/15/13/53/bali-2952056_960_720.jpg"
        ),
        DestinationCategory(
            5,
            "Cagar Alam",
            "https://cdn.pixabay.com/photo/2016/11/14/04/45/elephant-1822636_960_720.jpg"
        ),
        DestinationCategory(
            6,
            "Pantai",
            "https://cdn.pixabay.com/photo/2019/03/05/06/31/fleet-4035655_960_720.jpg"
        ),
        DestinationCategory(
            7,
            "Rekreasi",
            "https://cdn.pixabay.com/photo/2016/07/29/14/25/roller-coaster-1553336_960_720.jpg"
        ),
        DestinationCategory(
            8,
            "Religius",
            "https://cdn.pixabay.com/photo/2014/12/16/19/59/indonesia-570661_960_720.jpg"
        )
    )
}