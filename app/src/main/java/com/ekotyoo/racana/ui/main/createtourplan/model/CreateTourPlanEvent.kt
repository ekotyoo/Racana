package com.ekotyoo.racana.ui.main.createtourplan.model

sealed class CreateTourPlanEvent {
    object NavigateToTourPlanResult : CreateTourPlanEvent()
    object SomeFieldsAreEmpty : CreateTourPlanEvent()
}