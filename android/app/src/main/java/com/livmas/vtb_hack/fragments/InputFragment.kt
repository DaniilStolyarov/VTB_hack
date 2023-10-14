package com.livmas.vtb_hack.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.clearFragmentResult
import com.livmas.vtb_hack.MainActivity
import com.livmas.vtb_hack.connection.HttpClient
import com.livmas.vtb_hack.databinding.FragmentInputBinding

class InputFragment : DialogFragment() {

    private lateinit var viewModel: InputViewModel
    private lateinit var binding: FragmentInputBinding
    private lateinit var client: HttpClient

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentInputBinding.inflate(layoutInflater)
        val mainActivity = (activity as MainActivity)
        client = HttpClient(mainActivity)

        binding.apply {
            fabSend.setOnClickListener {
                mainActivity.holder.location?.let {loc ->
                    client.query(loc)
                    dismiss()
                }
            }
        }

        return AlertDialog.Builder(activity)
            .setMessage("Выберете параментры поиска")
            .setView(binding.root)
            .show()
    }

}