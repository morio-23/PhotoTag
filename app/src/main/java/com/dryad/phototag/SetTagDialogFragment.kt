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
        var tagStatus: List<String> = mutableListOf("")

        val job = GlobalScope.launch {
            tagArray = AppDatabase.getDatabase_tag(this@SetTagDialogFragment.requireContext()).DataBaseDao().getAllTagName()!!
            tagStatus = AppDatabase.getDatabase_item(this@SetTagDialogFragment.requireContext()).DataBaseDao().returnTagStatus(contentUri)
            var aaa = AppDatabase.getDatabase_item(this@SetTagDialogFragment.requireContext()).DataBaseDao().returnTagStatus(contentUri).toString()
            Log.d("SetTagDialog", aaa)
        }

        runBlocking {
            job.join()
        }

        Log.d("SetTagDialog", tagArray.contentToString())
        Log.d("SetTagDialog", tagStatus.toString())
        Log.d("SetTagDialog", tagStatus[0])


        val checkedItems = returnCheckedTag(tagArray, tagStatus) // 保存されたデータに置き換えることができる
        Log.d("checkedItems", checkedItems.contentToString())

        val mSelectedItems:MutableList<Int> = mutableListOf()

        setupSelectedItems(checkedItems, mSelectedItems)

        val builder = AlertDialog.Builder(activity)

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

    private fun returnCheckedTag(tagArray: Array<String>, tagStatus:List<String>): BooleanArray {

        var mCheckedItems:MutableList<Boolean> = mutableListOf()

        tagArray.forEachIndexed { i, element ->
            if(element in tagStatus[0]){    //なぜかtagStatusが２次元配列で返ってきているのでこうしないと意図した挙動をしない。解決したい。
                mCheckedItems.add(true)
            }else{
                mCheckedItems.add(false)
            }
        }

        Log.d("returnCheckedTag", mCheckedItems.toTypedArray().contentToString())

        val BooleanArray = mCheckedItems.toTypedArray().toBooleanArray()
        return BooleanArray
    }

    private fun returnSelectedTag(tagArray: Array<String>, mSelectedItems: MutableList<Int>): List<String>{

        var string_mSelectedItems = arrayListOf<String>()

        mSelectedItems.forEach{
            string_mSelectedItems.add(tagArray[it])
        }

        Log.d("returnSelectedTag", string_mSelectedItems.toString())
        return string_mSelectedItems
    }

}