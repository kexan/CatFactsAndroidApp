import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kexan.catfactsandroidapp.R
import com.kexan.catfactsandroidapp.databinding.CardFactBinding
import com.kexan.catfactsandroidapp.viewmodel.FactViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class FullFactFragment : Fragment() {
    private val factViewModel: FactViewModel by viewModels(ownerProducer = ::requireParentFragment)
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = CardFactBinding.inflate(inflater, container, false)
        val fact = factViewModel.currentFact
        binding.apply {
            when (fact.type) {
                "cat" -> {
                    author.text = "Cat fact №" + fact.id.toString()
                }
                "dog" -> {
                    author.text = "Dog fact №" + fact.id.toString()
                }
            }
            published.text = "Published: " + fact.updatedAt
            content.text = fact.text
            factId.text = "FactID " + fact._id
            userId.text = "UserID " + fact.user
            sentCounter.text = fact.type
            additionalInfoGroup.visibility = View.VISIBLE
            share.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, fact.text)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_fact))
                startActivity(shareIntent)
            }
        }
        return binding.root
    }
}