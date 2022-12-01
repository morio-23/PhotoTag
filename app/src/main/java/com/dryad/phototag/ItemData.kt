package com.dryad.phototag

import android.net.Uri

data class ItemData(val displayName: String, val contentUri: Uri, val tag:ArrayList<String>)

data class ItemDeta_register(val displayName: String, val contentUri: String)
