package com.ekotyoo.racana.ui.main.createtourplan.model

sealed class CreateTourPlanEvent {
    object CreateTourPlanSuccess : CreateTourPlanEvent()
    object SomeFieldsAreEmpty : CreateTourPlanEvent()
}