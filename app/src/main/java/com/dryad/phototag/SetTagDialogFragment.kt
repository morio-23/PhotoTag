package com.dryad.phototag

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class SetTagDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val bundle = arguments
        var contentUri = ""

        if(bundle != null){
            contentUri = bundle.getString("contentUri").toString()
        }

        var tagArray: Array<String> = arrayOf("")
        var tagStatus: ArrayList<String> = arrayListOf("")

        val job = GlobalScope.launch {
            tagArray = AppDatabase.getDatabase_tag(this@SetTagDialogFragment.requireContext()).DataBaseDao().getAllTagName()!!
            tagStatus = AppDatabase.getDatabase_item(this@SetTagDialogFragment.requireContext()).DataBaseDao().returnTagStatus(contentUri) as ArrayList<String>
        }

        runBlocking {
            job.join()
        }

        val checkedItems = returnCheckdTag(tagArray, tagStatus) // 保存されたデータに置き換えることができる

        val mSelectedItems:MutableList<Int> = mutableListOf()

        setupSelectedItems(checkedItems, mSelectedItems)

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
                    val job = GlobalScope.launch {
                        AppDatabase.getDatabase_item(this@SetTagDialogFragment.requireContext()).DataBaseDao().updateTag(contentUri, returnSelectedTag(tagArray, mSelectedItems))
                    }
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

    private fun returnCheckdTag(tagArray: Array<String>, tagStatus:ArrayList<String>): BooleanArray {

        var mCheckedItems:MutableList<Boolean> = mutableListOf()

        tagArray.forEachIndexed { i, element ->
            if(element in tagStatus){
                mCheckedItems.add(true)
            }else{
                mCheckedItems.add(false)
            }
        }

        val BooleanArray = mCheckedItems.toTypedArray().toBooleanArray()
        return BooleanArray
    }

    private fun returnSelectedTag(tagArray: Array<String>, mSelectedItems: MutableList<Int>): ArrayList<String>{

        var string_mSelectedItems = arrayListOf<String>()

        mSelectedItems.forEach{
            string_mSelectedItems.add(tagArray[it])
        }

        return string_mSelectedItems
    }

}