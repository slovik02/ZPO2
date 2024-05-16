package MainActivity

interface FirestoreInterface {

    fun addData(data: FireStoreData, collectionID: String, documentID: String)

   // fun updateData(collectionID: String, documentID: String, newData: FireStoreData)

    // pobieranie danych z firestore
  //  fun getData(documentID: String, callback: (FireStoreData?) -> Unit)

  //  fun deleteData(documentID: String)

}

