package chemilmakhlouta.chemchatapp.domain.chats.usecase

import chemilmakhlouta.chemchatapp.application.injection.annotation.Mockable
import chemilmakhlouta.chemchatapp.data.chats.UserRepository
import chemilmakhlouta.chemchatapp.data.registration.Model.User
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Chemil Makhlouta on 24/7/19.
 */
@Mockable
class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    fun getUsers(): Single<ArrayList<User>> = userRepository.getUsers()
}