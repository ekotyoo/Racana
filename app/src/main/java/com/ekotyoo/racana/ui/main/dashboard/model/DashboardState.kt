package com.ekotyoo.racana.ui.main.dashboard.model

import com.ekotyoo.racana.data.model.ArticlePreview
import com.ekotyoo.racana.data.model.TourPlan
import com.ekotyoo.racana.data.model.TravelDestination
import com.ekotyoo.racana.data.model.UserModel

data class DashboardState(
    val user: UserModel? = null,
    val activeTourPlan: TourPlan? = null,
    val searchTextFieldValue: String = "",
    val destinations: List<TravelDestination> = listOf(),
    val articles: List<ArticlePreview> = emptyList(),
    val isLoading: Boolean = false
)