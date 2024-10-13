package ir.hmb72.forexuser.ui.analeys.journal.statement

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import ir.hmb72.forexuser.R
import ir.hmb72.forexuser.databinding.FragmentStatementBinding
import ir.hmb72.forexuser.utils.MOVE
import ir.hmb72.forexuser.utils.setupRecyclerView
import ir.hmb72.forexuser.viewmodel.journal.statement.StatementViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class StatementFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    //Binding
    private lateinit var binding: FragmentStatementBinding

    private val viewModel: StatementViewModel by viewModels()

    @Inject
    lateinit var adapter: StatementAdapter



    private var savedHour = 0
    private var savedMinute = 0

    private var selectedDate = 0
    private var one = ""
    private var two = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStatementBinding.inflate(layoutInflater)


        //Active
        val toolbar = binding.ToolbarStatement
        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(toolbar)


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_statement, menu)

            }

            //Views Visible/GONE
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.cCalendar -> {
                        var startDate: Long?
                        var endDate: Long?

                        val builder = MaterialDatePicker.Builder.dateRangePicker()
                            .setTitleText("Select a date range")
                        val picker = builder.build()
                        picker.show(childFragmentManager, picker.toString())
                     /*   picker.addOnPositiveButtonClickListener { selection: Pair<Long, Long>? ->
                            // Save the selected dates to variables
                            if (selection != null) {
                                startDate = selection.first
                                endDate = selection.second
                                // Print the selected dates for debugging
                                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//                                    txt1.text = sdf.format(startDate)
//                                    txt2.text = sdf.format(endDate)

                                one = sdf.format(startDate)
                                two = sdf.format(endDate)
                                if (one.isNotEmpty() && two.isNotEmpty()) {
                                    viewModel.searchBetweenDate(one, two)
                                    viewModel.resultSearchTwoDate.observe(viewLifecycleOwner) {
                                        adapter.setData(it)
                                        binding.recyclerCalendar.setupRecyclerView(
                                            LinearLayoutManager(requireContext()),
                                            adapter
                                        )
                                    }
//                    Toast.makeText(activity, one + two, Toast.LENGTH_SHORT).show()
                                    //Chart
                                    binding.chartCalendar.apply {
                                        viewModel.getXyWithRangeDate(one, two)
                                        viewModel.xyWithDate.observe(viewLifecycleOwner) { chartXY ->
                                            val entries = chartXY.map { Entry(it.x, it.y) }
                                            val dataSet = LineDataSet(entries, "Chart Label")
                                            val lineData = LineData(dataSet)
                                            dataSet.circleRadius = 4f
                                            dataSet.setCircleColor(Color.RED)
                                            dataSet.valueTextSize = 12f
                                            dataSet.lineWidth = 2.5f
                                            dataSet.cubicIntensity = 0.2f //Softening the line in chart
                                            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                                            dataSet.color = Color.RED
                                            dataSet.valueTextColor = Color.WHITE
                                            binding.chartCalendar.data = lineData

                                            binding.chartCalendar.invalidate()
                                        }

                                        //X axis settings
                                        xAxis.position = XAxis.XAxisPosition.BOTTOM
                                        xAxis.setDrawGridLines(false)
                                        xAxis.labelRotationAngle = 1f
                                        xAxis.axisMinimum = 0f
                                        xAxis.granularity = 1f
                                        xAxis.textColor = Color.WHITE


                                        //Y axis settings
                                        axisLeft.setDrawGridLines(false)
                                        val yAxis = binding.chartCalendar.axisLeft
                                        yAxis.textColor = Color.WHITE


                                        // All chart setting
                                        axisRight.isEnabled = false
                                        description.isEnabled = false
                                        legend.isEnabled = false
                                        animateY(1000)

                                    }


                                }
                            }
                        }*/

                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {

            //Calendar
            val inflater = LayoutInflater.from(activity)
//            val customHeader = inflater.inflate(R.layout.calendar_layout,null, false) as ViewGroup
//            calendar.addView(customHeader, 0)
            calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val c = Calendar.getInstance()
                c.set(year, month, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val selectedDates = dateFormat.format(c.timeInMillis)
//                Toast.makeText(activity, selectedDates.toString(), Toast.LENGTH_SHORT).show()

                //Show list item
                viewModel.searchDate(selectedDates)
                viewModel.resultSearch.observe(viewLifecycleOwner) {
                    adapter.setData(it)
                    recyclerCalendar.setupRecyclerView(
                        LinearLayoutManager(requireContext()),
                        adapter
                    )
                }

                //Show chart
                chartCalendar.apply {
                    //update data
                    root.setOnClickListener {
                        notifyDataSetChanged()
                    }


                    viewModel.getXyDaily(selectedDates)
                    viewModel.xyDaily.observe(viewLifecycleOwner) { chartXY ->

                        val entries = chartXY.map { Entry(it.x, it.y) }
                        val dataSet = LineDataSet(entries, "Chart Label")
                        val lineData = LineData(dataSet)
                        dataSet.circleRadius = 4f
                        dataSet.setCircleColor(Color.RED)
                        dataSet.valueTextSize = 12f
                        dataSet.lineWidth = 2.5f
                        dataSet.cubicIntensity = 0.2f //Softening the line in chart
                        dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                        dataSet.color = Color.RED
                        dataSet.valueTextColor = Color.WHITE
                        chartCalendar.data = lineData
                        chartCalendar.invalidate()
                    }

                    //X axis settings


                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.setDrawGridLines(false)
                    xAxis.labelRotationAngle = 1f
                    xAxis.axisMinimum = 0f
                    xAxis.granularity = 1f
//                    xAxis.axisMinimum =1f
                    xAxis.textColor = Color.WHITE


                    //Y axis settings
                    axisLeft.setDrawGridLines(false)
                    val yAxis = chartCalendar.axisLeft
                    yAxis.textColor = Color.WHITE


                    // All chart setting
                    axisRight.isEnabled = false
                    description.isEnabled = false
                    legend.isEnabled = false
                    animateY(1000)


                }

                //Click item
                adapter.clickItem { entity, s ->
                    if (s == MOVE) {
//                        val direction =
//                            StatementFragmentDirections.actionResultFragmentToDetailFragment(entity.noTransaction.toString())
//                        findNavController().navigate(direction)
                    }
                }
            }


            /*  // Open the MaterialDatePicker dialog and select a date range
              btnRange.setOnClickListener {
                  var startDate: Long?
                  var endDate: Long?

                  val builder = MaterialDatePicker.Builder.dateRangePicker()
                      .setTitleText("Select a date range")
                  val picker = builder.build()
                  picker.show(childFragmentManager, picker.toString())
                  picker.addOnPositiveButtonClickListener { selection: Pair<Long, Long>? ->
                      // Save the selected dates to variables
                      if (selection != null) {
                          startDate = selection.first
                          endDate = selection.second
                          // Print the selected dates for debugging
                          val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                          txt1.text = sdf.format(startDate)
                          txt2.text = sdf.format(endDate)

                          one = sdf.format(startDate)
                          two = sdf.format(endDate)
                      }
                  }
              }

              //show range list and chart
              btnSearch.setOnClickListener {
                  if (one.isNotEmpty() && two.isNotEmpty()) {
                      viewModel.searchBetweenDate(one, two)
                      viewModel.resultSearchTwoDate.observe(viewLifecycleOwner) {
                          adapter.setData(it)
                          recyclerCalendar.setupRecyclerView(
                              LinearLayoutManager(requireContext()),
                              adapter
                          )
                      }
  //                    Toast.makeText(activity, one + two, Toast.LENGTH_SHORT).show()
                      //Chart
                      chartCalendar.apply {
                          //update data
                          root.setOnClickListener {
                              notifyDataSetChanged()
                          }


                          viewModel.getXyWithRangeDate(one, two)
                          viewModel.xyWithDate.observe(viewLifecycleOwner) { chartXY ->
                              val entries = chartXY.map { Entry(it.x, it.y) }
                              val dataSet = LineDataSet(entries, "Chart Label")
                              val lineData = LineData(dataSet)
                              dataSet.circleRadius = 4f
                              dataSet.setCircleColor(Color.RED)
                              dataSet.valueTextSize = 12f
                              dataSet.lineWidth = 2.5f
                              dataSet.cubicIntensity = 0.2f //Softening the line in chart
                              dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
                              dataSet.color = Color.RED
                              dataSet.valueTextColor = Color.WHITE
                              chartCalendar.data = lineData

                              chartCalendar.invalidate()
                          }

                          //X axis settings
                          xAxis.position = XAxis.XAxisPosition.BOTTOM
                          xAxis.setDrawGridLines(false)
                          xAxis.labelRotationAngle = 1f
                          xAxis.axisMinimum = 0f
                          xAxis.granularity = 1f
                          xAxis.textColor = Color.WHITE


                          //Y axis settings
                          axisLeft.setDrawGridLines(false)
                          val yAxis = chartCalendar.axisLeft
                          yAxis.textColor = Color.WHITE


                          // All chart setting
                          axisRight.isEnabled = false
                          description.isEnabled = false
                          legend.isEnabled = false
                          animateY(1000)


                      }

                  }

              }
  */


        }
    }


    /*private fun getDateTimeCalendar() {
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                onDateSet(
                    view,
                    year,
                    month,
                    dayOfMonth
                )
            }, year, month, day
        )
        datePickerDialog.show()

    }*/

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = formatDate(year, month, dayOfMonth)


//        getDateTimeCalendar()


        if (selectedDate == 1) {
//            binding.txt1.text = date
            if (date != null) {
                one = date
            }

        } else if (selectedDate == 2) {
//            binding.txt2.text = date
            if (date != null) {
                two = date
            }
        }


    }

    private fun formatDate(year: Int, month: Int, day: Int): String? {
        // Format the date as a string, e.g. "2023-03-02"
        return String.format("%d-%02d-%02d", year, month + 1, day)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute


    }

}