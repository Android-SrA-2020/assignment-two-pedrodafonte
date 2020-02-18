package work.nbcc.assignment2

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.fragment_main.*
import work.nbcc.assignment2.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var binding: FragmentMainBinding

    private val questionBank = listOf(
        Question(R.string.question_1, false),
        Question(R.string.question_2, true),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, false),
        Question(R.string.question_6, true),
        Question(R.string.question_7, false),
        Question(R.string.question_8, true),
        Question(R.string.question_9, false),
        Question(R.string.question_10, false),
        Question(R.string.question_11, false),
        Question(R.string.question_12, true),
        Question(R.string.question_13, false),
        Question(R.string.question_14, true),
        Question(R.string.question_15, false),
        Question(R.string.question_16, false),
        Question(R.string.question_17, true),
        Question(R.string.question_18, false),
        Question(R.string.question_19, false),
        Question(R.string.question_20, true)
    )

    private var questionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_main, container, false
        )

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        questionIndex = savedInstanceState?.getInt("questionId") ?: 0
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item!!,
            view!!.findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        binding.apply {
            questionText.setText(questionBank[questionIndex].resourceId)
        }

        binding.apply {
            button_false.setOnClickListener { answer(false) }
            button_true.setOnClickListener { answer(true) }

            nextButton.setOnClickListener {
                questionIndex++
                if (questionIndex % questionBank.size == 0) {
                    questionIndex = 0
                }

                binding.apply {
                    questionText.setText(questionBank[questionIndex].resourceId)
                }
            }

            buttonPrevious.setOnClickListener {
                questionIndex--
                if (questionIndex == -1) {
                    questionIndex = questionBank.size - 1
                }

                binding.apply {
                    questionText.setText(questionBank[questionIndex].resourceId)
                }
            }

            binding.apply {
                buttonCheat.setOnClickListener {
                    view!!.findNavController().navigate(MainFragmentDirections.actionMainFragmentToCheatFragment(questionBank[questionIndex].answer.toString(), questionBank[questionIndex].resourceId))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        binding.apply {
            questionText.setText(questionBank[questionIndex].resourceId)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("questionId", questionIndex)
    }

    private fun answer(answer: Boolean) {
        if (answer == questionBank[questionIndex].answer) {
            Toast.makeText(requireContext(),"You are correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(),"Try again", Toast.LENGTH_SHORT).show()
        }
    }
}
