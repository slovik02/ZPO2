package MainActivity

import com.example.chad.models.Contact
import com.google.firebase.firestore.FirebaseFirestore

interface FirestoreInterface {
    fun addMedication(userEmail: String, medication: Medication, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun addData(data: FireStoreData, collectionID: String, documentID: String)
    fun addContact(userEmail: String, contact: Contact, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)

    // fun updateData(collectionID: String, documentID: String, newData: FireStoreData)

    // fun getData(documentID: String, callback: (FireStoreData?) -> Unit)

    // fun deleteData(documentID: String)
}
