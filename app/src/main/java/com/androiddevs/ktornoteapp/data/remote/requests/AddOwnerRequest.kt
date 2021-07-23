package com.androiddevs.ktornoteapp.data.remote.requests

/**
 *Veli-Matti Tikkanen, 22.6.2021
 */
data class AddOwnerRequest(
    val owner: String,
    val noteID: String
)