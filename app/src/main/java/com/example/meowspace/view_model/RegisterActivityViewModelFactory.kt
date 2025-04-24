import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.meowspace.repository.AuthRepository
import com.example.meowspace.view_model.RegisterActivityViewModel
import java.security.InvalidParameterException

class RegisterActivityViewModelFactory(
    private val authRepository: AuthRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (RegisterActivityViewModel::class.java.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterActivityViewModel(authRepository, application) as T
        }
        throw InvalidParameterException("Unable to construct RegisterActivityViewModel")
    }
}
