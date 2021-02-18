import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.covid19.R
import com.example.covid19.entity.Country
import kotlinx.android.synthetic.main.row.view.*

/**
 * adapter for loading info to list view
 */
class MyListAdapter(private val context: Activity, private val images: MutableList<Country>) :
    ArrayAdapter<Country>(context, R.layout.row, images) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.row, null, false)

        val titleText = rowView.title as TextView
        val imageView = rowView.icon as ImageView



        return rowView
    }
}