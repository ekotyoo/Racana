package com.ekotyoo.racana.ui.main.dashboard.model

import com.ekotyoo.racana.data.model.TourPlan

sealed class DashboardEvent {
    data class NavigateToDetailDestination(val id: Int): DashboardEvent()
    data class NavigateToTourPlanDetail(val tourPlan: TourPlan) : DashboardEvent()
    data class NavigateToDetailArticle(val id: Int) : DashboardEvent()
    object AllDestinationClicked: DashboardEvent()
    object AllArticleClicked: DashboardEvent()
}