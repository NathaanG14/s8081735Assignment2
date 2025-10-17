package com.example.s8081735assignment2.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.s8081735assignment2.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, c: ViewGroup?, s: Bundle?): View? =
        inflater.inflate(R.layout.fragment_details, c, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val entity = DetailsFragmentArgs.fromBundle(requireArguments()).entity
        view.findViewById<TextView>(R.id.tvTechnique).text = entity.technique ?: ""
        view.findViewById<TextView>(R.id.tvEquipment).text = "Equipment: ${entity.equipment ?: ""}"
        view.findViewById<TextView>(R.id.tvSubject).text = "Subject: ${entity.subject ?: ""}"
        view.findViewById<TextView>(R.id.tvPhotographer).text = "Pioneer: ${entity.pioneeringPhotographer ?: ""}"
        view.findViewById<TextView>(R.id.tvYear).text = "Year: ${entity.yearIntroduced ?: ""}"
        view.findViewById<TextView>(R.id.tvDescription).text = entity.description ?: ""
    }
}
