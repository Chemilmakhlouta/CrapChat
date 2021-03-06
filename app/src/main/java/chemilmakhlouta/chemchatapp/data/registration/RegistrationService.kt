package chemilmakhlouta.chemchatapp.data.registration

import android.net.Uri
import android.util.Log
import chemilmakhlouta.chemchatapp.data.registration.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.Single.create
import java.util.*
import javax.inject.Inject

/**
 * Created by Chemil Makhlouta on 15/01/19.
 */
class RegistrationService @Inject constructor() : RegistrationRepository {
    override fun register(email: String, password: String): Completable {
        return Completable.create { subscriber ->
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        subscriber.onComplete()
                    }
                    .addOnFailureListener {
                        subscriber.onError(it)
                    }
        }
    }

    override fun uploadImage(selectedPhotoUri: Uri): Single<String> {
        return create { subscriber ->
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

            ref.putFile(selectedPhotoUri)
                    .addOnSuccessListener {
                        ref.downloadUrl.addOnSuccessListener {
                            subscriber.onSuccess(it.toString())
                        }
                    }
                    .addOnFailureListener {
                        subscriber.onError(it)
                    }
        }
    }

    override fun saveUser(username: String, profileImageUrl: String): Completable {
        return Completable.create { subscriber ->
            val uid = FirebaseAuth.getInstance().uid ?: ""
            val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

            val user = User(uid, username, profileImageUrl)
            ref.setValue(user)
                    .addOnSuccessListener {
                        Log.e("REGISTRATION_SERVICE", "Finally we saved the user to Firebase Database")
                        subscriber.onComplete()
                    }
                    .addOnFailureListener {
                        Log.e("REGISTRATION_SERVICE", "Failed to set value to database: ${it.message}")
                        subscriber.onError(it)
                    }
        }
    }
}