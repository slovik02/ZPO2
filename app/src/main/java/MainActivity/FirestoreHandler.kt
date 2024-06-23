package MainActivity

import com.example.chad.models.Contact
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHandler : FirestoreInterface {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun addMedication(
        userEmail: String,
        medication: Medication,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userCollection = firestore.collection("users")
            .document(userEmail)
            .collection("medications")
            .document("med")
            .collection("meds")

        userCollection.add(medication)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    override fun addContact(
        userEmail: String,
        contact: Contact,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val userCollection = firestore.collection("users")
            .document(userEmail)
            .collection("contacts")

        userCollection.add(contact)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    override fun addData(data: FireStoreData, collectionID: String, documentID: String){
        firestore.collection(collectionID)
            .document(documentID)
            .set(data)
            .addOnSuccessListener {
                println("DocumentSnapshot added")
            }
            .addOnFailureListener{e ->
                println("Error adding document $e")
            }
    }
}
