package MainActivity

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

interface FirestoreInterface {

    fun addMedication(userEmail: String, medication: Medication, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun addData(data: FireStoreData, collectionID: String, documentID: String)

    //fun updateData(collectionID: String, documentID: String, newData: FireStoreData)

    // pobieranie danych z firestore
  //  fun getData(documentID: String, callback: (FireStoreData?) -> Unit)

  //  fun deleteData(documentID: String)

}

