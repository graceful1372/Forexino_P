package ir.hmb72.forexuser.utils

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun Spinner.setupListAdapter(list: MutableList<String>, callback: (String) -> Unit) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback(list[position])
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}

/*fun Spinner.setupListAdapter2(list: MutableList<String>,img:MutableList<Int>, callback: (String) -> Unit) {
    val adapter = ArrayAdapter(context, R.layout.spinner_item, list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            callback(list[position])
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}*/

fun RecyclerView.setupRecyclerView(
    layoutManager: RecyclerView.LayoutManager,
    adapter: RecyclerView.Adapter<*>,
) {
    this.layoutManager = layoutManager
    this.setHasFixedSize(true)
    this.adapter = adapter
//    this.isNestedScrollingEnabled = false
}


fun MutableList<out Any>.getIndexFromList(item: Any): Int {
    var index = 0
    for (i in this.indices) {
        if (this[i] == item) {
            index = i
            break
        }
    }
    return index
}

fun View.setupVisible(isShown: Boolean) {
    if (isShown) {
        this.animate().translationY(0f).setInterpolator(DecelerateInterpolator(2f)).setDuration(200).withEndAction {
                this.visibility = View.VISIBLE
            }
    } else {
        this.animate().translationY(height.toFloat()).setInterpolator(AccelerateInterpolator(2f)).setDuration(200)
            .withEndAction {
                this.visibility = View.INVISIBLE
            }
    }
}

fun View.setupLoading(isShown: Boolean, container: View) {
    if (isShown) {
        this.isVisible = true
        container.isVisible = false
    } else {
        this.isVisible = false
        container.isVisible = true
    }
}

fun View.showSnackBar(message: String) {
  Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

//fun Chart<T:ChartData<out IDataSet<out MyChartData>>!>>.setupChart(){}
