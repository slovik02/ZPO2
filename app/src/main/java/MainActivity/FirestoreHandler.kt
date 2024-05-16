package MainActivity

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHandler : FirestoreInterface {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
