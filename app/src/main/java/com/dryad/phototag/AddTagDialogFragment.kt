package com.dryad.phototag

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class AddTagDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_add_tag_dialog, null)

        builder.setView(view)
            .setTitle("タグの作成")
            .setPositiveButton("OK") { dialog, id ->
                //データベースにタグを追加
            }
            .setNegativeButton("Cancel") { dialog, id ->

            }

        return builder.create()
    }
}