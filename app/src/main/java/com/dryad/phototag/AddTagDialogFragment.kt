package com.dryad.phototag

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.dryad.phototag.databinding.ActivityTagSettingBinding
import com.dryad.phototag.databinding.FragmentAddTagDialogBinding
import kotlinx.android.synthetic.main.fragment_add_tag_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddTagDialogFragment : DialogFragment() {
    public interface DialogListener{
        public fun onTagAddedListener()
    }

    var listener:DialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as DialogListener
        }catch (e: Exception){
            Log.e("ERROR","CANNOT FIND LISTENER")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_add_tag_dialog, null)
        val edit_tagname = view.findViewById<EditText>(R.id.edit_tagname)

        builder.setView(view)
            .setTitle("タグの作成")
            .setPositiveButton("OK") { dialog, id ->
                //データベースにタグを追加
                val tagname = edit_tagname.text.toString()
                Log.d("addTag", "tagName:$tagname")
                if(!tagname.isNullOrBlank()){ //空白であれば追加しない
                    GlobalScope.launch {
                        AppDatabase.getDatabase_tag(requireActivity().applicationContext).DataBaseDao().addTag(TagDatabase(0,tagname,""))
                    }
                }
                listener?.onTagAddedListener()
            }
            .setNegativeButton("Cancel") { dialog, id ->

            }

        return builder.create()
    }

}