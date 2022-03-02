package com.proj.newer_daang

import java.io.Serializable

data class ItemData (
    val name : String,
    val meaning : String,
    val category : String,
    val hashT : String,
    val article : String,
    val link : String
) : Serializable
