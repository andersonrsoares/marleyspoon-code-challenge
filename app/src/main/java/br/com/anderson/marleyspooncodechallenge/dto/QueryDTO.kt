package br.com.anderson.marleyspooncodechallenge.dto

import com.google.gson.annotations.Expose
import java.util.*

open class QueryDTO(@field:Expose private val query: String) {

    @Expose
    private val variables: MutableMap<String, Any>
    fun setVariable(name: String, value: Any) {
        variables[name] = value
    }

    init {
        variables = HashMap()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QueryDTO

        if (query != other.query) return false

        return true
    }

    override fun hashCode(): Int {
        return query.hashCode()
    }
}