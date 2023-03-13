package com.dryad.phototag


data class ItemData(val id: Int, val contentUri: String, val displayName: String, val tag:List<String>)

data class ItemData_register(val contentUri: String, val displayName: String)
