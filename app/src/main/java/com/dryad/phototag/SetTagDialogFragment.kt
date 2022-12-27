package com.dryad.phototag

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SetTagDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val checkedItems = booleanArrayOf(false, false ,false) // 保存されたデータに置き換えることができる
        val mSelectedItems:MutableList<Int> = mutableListOf()
        setupSelectedItems(checkedItems, mSelectedItems)
        var tagArray: Array<String> = arrayOf("")

        val job = GlobalScope.launch {
            tagArray = AppDatabase.getDatabase_tag(this@SetTagDialogFragment.requireContext()).DataBaseDao().getAllTagName()!!
        }

        runBlocking {
            job.join()
        }

        val builder = AlertDialog.Builder(activity)

        Log.d("SetTagDialog", tagArray.toString())

        if(tagArray.isNullOrEmpty()){
            builder.setTitle("タグが存在しません")
                // .setMessage("Here Message") // setMessageは使うとリスト表示されないので注意！
                .setMultiChoiceItems(tagArray, checkedItems) { dialog, which, isChecked ->
                    if (isChecked) {
                        mSelectedItems.add(which)
                    } else {
                        mSelectedItems.remove(which)
                    }
                }
                .setPositiveButton("OK") { dialog, id ->

                }
                .setNegativeButton("Cancel") { dialog, id ->

                }
        }else {
            builder.setTitle("Here Title")
                // .setMessage("Here Message") // setMessageは使うとリスト表示されないので注意！
                .setMultiChoiceItems(tagArray, checkedItems) { dialog, which, isChecked ->
                    if (isChecked) {
                        mSelectedItems.add(which)
                    } else {
                        mSelectedItems.remove(which)
                    }
                }
                .setPositiveButton("OK") { dialog, id ->

                }
                .setNegativeButton("Cancel") { dialog, id ->

                }
        }

        return builder.create()
    }

    private fun setupSelectedItems(
        checkedItems: BooleanArray,
        mSelectedItems: MutableList<Int>
    ) {
        var index = 0
        checkedItems.forEach {
            if (it) {
                mSelectedItems.add(index)
            }
            index++
        }
    }

}