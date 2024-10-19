package me.partypronl.opportune.data

import kotlinx.serialization.Serializable

@Serializable
data class Company(val name: String, val location: String)
