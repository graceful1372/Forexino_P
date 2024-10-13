package ir.hmb72.forexuser.ui.analeys.journal


import android.graphics.Color
import android.graphics.Color.WHITE
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.data.model.local.room.NoteEntity
import ir.hmb72.forexuser.databinding.FragmentJournalBinding
import ir.hmb72.forexuser.ui.analeys.journal.journal_add.AddJournalFragment
import ir.hmb72.forexuser.utils.BUNDLE_ID
import ir.hmb72.forexuser.utils.DELETE
import ir.hmb72.forexuser.utils.EDIT
import ir.hmb72.forexuser.utils.MOVE
import ir.hmb72.forexuser.utils.setupRecyclerView
import ir.hmb72.forexuser.viewmodel.journal.JournalViewModel
import javax.inject.Inject


@AndroidEntryPoint
class JournalFragment : Fragment() {
    //Binding
    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!

    //Injects
    @Inject
    lateinit var journalAdapter: JournalAdapter

    private val viewModel: JournalViewModel by viewModels()

    @Inject
    lateinit var entity: NoteEntity

    //Other
    private var revers: Int = 0

    //Line Chart
    private var number = 0
    var xVals = mutableListOf<String>()
    private var itemList = listOf<NoteEntity>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentJournalBinding.inflate(layoutInflater)

        val toolbar = binding.ToolbarJournal
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        //Show back arrow on Toolbar
//        val actionBar = activity.supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBar?.setHomeAsUpIndicator(R.drawable.ic_back)


        //Active toolbar item
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    // Add menu items here
                    menuInflater.inflate(R.menu.menu_journal, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    // Handle the menu selection
                    return when (menuItem.itemId) {
                        R.id.statement -> {
                            findNavController().navigate(R.id.statementFragment)

                            true
                        }

                        R.id.points -> {
                            findNavController().navigate(R.id.pointFragment)

                            true
                        }

                        else -> false
                    }
                }

                /*If this line of code is removed, the item toolbar will be displayed repeatedly for each fragment.
                    Actually, these items toolbar show only this fragment.*/
            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {

            //show list
            viewModel.allNote()
            viewModel.noteData.observe(viewLifecycleOwner) {
                journalAdapter.setData(it.data!!)
                recycler.setupRecyclerView(LinearLayoutManager(requireContext()), journalAdapter)
            }

            //   Hide fab and bottom navigation
            /*      recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        if (dy > 0) {
                            fabAdd.setupVisible(false)
                            (parentFragment!!.requireActivity() as MainActivity).hideBottomNavigation()


                        } else {
                            fabAdd.setupVisible(true)
                            (parentFragment!!.requireActivity() as MainActivity).showBottomNavigation()

                        }
                    }
                })*/

            //open add Fragment
            fabAdd.setOnClickListener {
                AddJournalFragment().show(childFragmentManager, AddJournalFragment().tag)
            }
            //Click item
            journalAdapter.clickItem { entity, type ->
                when (type) {
                    MOVE -> {
                        val direction = JournalFragmentDirections.actionJournalFragmentToJournalDetailFragment(entity.id)
                        findNavController().navigate(direction)
                    }

                    EDIT -> {
                        //Send data to Fragment
                        val addFragment = AddJournalFragment()
                        val bundle = Bundle()
                        bundle.putInt(BUNDLE_ID, entity.id)
                        addFragment.arguments = bundle
                        addFragment.show(childFragmentManager, AddJournalFragment().tag)
                    }

                    DELETE -> {
                        val dialog = AlertDialog.Builder(requireContext())
                        dialog.setView(R.layout.dialog)
                        dialog.setPositiveButton("Yes") { _, _ ->

                            viewModel.deleteAll(entity)
                        }
                        dialog.setNegativeButton("No") { _, _ ->

                        }
                        dialog.create()
                        dialog.show()

                    }
                }

            }


        }

        chart()
    }

    private fun chart() {
        binding.apply {

            chart.apply {
                //update data
                root.setOnClickListener {
                    notifyDataSetChanged()
                }

                viewModel.getXY()
                viewModel.chartData.observe(viewLifecycleOwner) { chartXY ->
                    val entries = chartXY.map { Entry(it.x, it.y) }
                    val dataSet = LineDataSet(entries, "Chart Label")
                    val lineData = LineData(dataSet)
                    dataSet.circleRadius = 4f
                    dataSet.setCircleColor(Color.parseColor("#d73c6c"))
                    dataSet.valueTextSize = 12f
                    dataSet.lineWidth = 2.5f
                    dataSet.cubicIntensity = 0.2f //Softening the line in chart
                    dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                    dataSet.color = Color.parseColor("#d73c6c")
                    dataSet.valueTextColor = WHITE
                    chart.data = lineData
                    chart.invalidate()
                }

                //X axis settings
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.labelRotationAngle = 1f
                xAxis.axisMinimum = 0f
                xAxis.granularity = 1f
                xAxis.textColor = WHITE

                //Y axis settings
                axisLeft.setDrawGridLines(false)
                val yAxis = chart.axisLeft
                yAxis.textColor = WHITE


                // All chart setting
                axisRight.isEnabled = false
                description.isEnabled = false
                legend.isEnabled = false
                animateY(1000)

            }


        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /*   override fun onResume() {
           super.onResume()
           (activity as AppCompatActivity?)!!.supportActionBar!!.show()
       }
   */

}
