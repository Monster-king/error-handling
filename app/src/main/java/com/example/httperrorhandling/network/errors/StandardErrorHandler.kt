package com.example.httperrorhandling.network.errors

import com.example.httperrorhandling.message.MessageController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StandardErrorHandler @Inject constructor(
    private val messageController: MessageController
) : ErrorHandler {

    override fun handleError(error: Throwable) {
        when (error) {
            is NetworkException -> messageController.show("Internet bilan bog'lanishni tekshiring")
            // buyerda login ekranga navigatsiya qilish ham mumkin
            is UnauthorizedException -> messageController.show("Iltimos qaytadan login qiling")
            is UnknownException -> messageController.show("Nimadir xato, birozdan keyin qayta urunib ko'ring")
            is HttpResponseException -> messageController.show(error.message)
            else -> messageController.show("Kutilmagan xatolik")
        }
    }
}