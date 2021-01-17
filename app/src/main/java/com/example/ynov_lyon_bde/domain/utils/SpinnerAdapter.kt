package com.example.ynov_lyon_bde.domain.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class SpinnerService {
    fun initAdapter(context: Context, list: MutableList<String>, spinner: Spinner): ArrayAdapter<String> {
        // initialize an array adapter for spinner
        val adapter: ArrayAdapter<String> = object: ArrayAdapter<String>(
            context,
            android.R.layout.simple_spinner_dropdown_item,
            list
        ){
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup,
            ): View {
                val view: TextView = super.getDropDownView(
                    position,
                    convertView,
                    parent
                ) as TextView

                // set item text bold and sans serif font
                view.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)

                if (position == 0){
                    // set the spinner disabled item text color
                    view.setTextColor(Color.LTGRAY)
                    // set the spinner title not visible
                    //view.setVisibility(View.GONE)
                }

                // set selected item style
                if (position == spinner.selectedItemPosition){
                    view.background = ColorDrawable(Color.parseColor("#F5F5F5"))
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable the third item of spinner
                return position != 0
            }

        }
        return adapter
    }
}
