package com.dryad.phototag

import android.net.Uri

data class ItemData(val contentUri: Uri, val displayName: String, val tag:ArrayList<String>)

data class ItemData_register(val contentUri: String, val displayName: String)
