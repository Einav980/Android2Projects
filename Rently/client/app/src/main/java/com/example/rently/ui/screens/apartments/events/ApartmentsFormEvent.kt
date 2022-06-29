package com.example.rently.ui.screens.apartments.events
sealed class ApartmentsFormEvent{
    data class AddToWatchlist(val apartmentId: String): ApartmentsFormEvent()
    data class RemoveFromWatchList(val apartmentId: String): ApartmentsFormEvent()
    data class CitiesChanged(val cities: ArrayList<String>): ApartmentsFormEvent()
    data class PriceRangeChanged(val priceRange: ClosedFloatingPointRange<Float>): ApartmentsFormEvent()
    data class SizeChanged(val size: Float): ApartmentsFormEvent()
    data class PropertyTypesChanged(val types: ArrayList<Int>): ApartmentsFormEvent()
    data class NumberOfBedroomsChanged(val amount: Int): ApartmentsFormEvent()
    data class NumberOfBathroomsChanged(val amount: Int): ApartmentsFormEvent()
    data class IsAnimalFriendlyChanged(val checkState: Boolean): ApartmentsFormEvent()
    data class IsFurnishedChanged(val checkState: Boolean): ApartmentsFormEvent()
    data class HasBalconyChanged(val checkState: Boolean): ApartmentsFormEvent()
    data class HasParkingChanged(val checkState: Boolean): ApartmentsFormEvent()
    object ApplyFilter: ApartmentsFormEvent()
    object ClearFilter: ApartmentsFormEvent()
}
