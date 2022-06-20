package com.example.rently.validation.presentation

sealed class AddApartmentFormEvent{
    data class AddressChanged(val address: String): AddApartmentFormEvent()
    data class AddressClicked(val address: String): AddApartmentFormEvent()
    data class DescriptionChanged(val description: String): AddApartmentFormEvent()
    data class PriceChanged(val price: Int): AddApartmentFormEvent()
    data class SizeChanged(val size: Int): AddApartmentFormEvent()
    data class IsFurnishedChanged(val checkState: Boolean): AddApartmentFormEvent()
    data class IsAnimalFriendlyChanged(val checkState: Boolean): AddApartmentFormEvent()
    data class HasBalconyChanged(val checkState: Boolean): AddApartmentFormEvent()
    data class HasParkingChanged(val checkState: Boolean): AddApartmentFormEvent()
    data class CityChanged(val city: String): AddApartmentFormEvent()
    object ShowPredictions: AddApartmentFormEvent()
    object HidePredictions: AddApartmentFormEvent()
    object Submit: AddApartmentFormEvent()
}
