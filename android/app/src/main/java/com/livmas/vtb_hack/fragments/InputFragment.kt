package com.livmas.vtb_hack.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.CheckedTextView
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.clearFragmentResult
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.connection.HttpClient
import com.livmas.vtb_hack.databinding.FragmentInputBinding
import com.livmas.vtb_hack.toInt

class InputFragment : DialogFragment() {

    private lateinit var viewModel: InputViewModel
    private lateinit var binding: FragmentInputBinding
    private lateinit var client: HttpClient

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentInputBinding.inflate(layoutInflater)
        val mainActivity = (activity as MainActivity)
        client = HttpClient(mainActivity)

        binding.apply {
            for (i in 0 until llCriterias.childCount) {
                llCriterias[i].setOnClickListener {
                    val content = it as CheckedTextView
                    content.isChecked = !content.isChecked
                }
            }
        }

        return AlertDialog.Builder(activity)
            .setMessage("Выберете параментры поиска")
            .setView(binding.root)
            .setPositiveButton("Подтвердить") { _, _ ->
                mainActivity.holder.location?.let {loc ->

                    var criteria = ""
                    for (i in binding.llCriterias.children) {
                        criteria += (i as CheckedTextView).isChecked.toInt().toString()
                    }
                    client.query(loc, criteria)
                    Log.d("http", criteria.length.toString())
                }
            }
            .show()

    }
}