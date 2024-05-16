package MainActivity

data class FireStoreData(
    var email: String = "",
    var previousMeds: List<String>?=null,
    var actualMeds: List<String>?=null
)
