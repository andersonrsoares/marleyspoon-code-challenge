package br.com.anderson.marleyspooncodechallenge

import com.google.gson.Gson

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.reflect.KClass


class MockJSONDataSourceRule(private val mGson: Gson) : TestRule {
    private var mValue: Any? = null

    fun <T> getValue(): T {
        return mValue as T
    }


    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {

            @Throws(Throwable::class)
            override fun evaluate() {
                val jsonFileResource = description!!.getAnnotation(
                    MockJSONDataSource::class.java
                )
                if (jsonFileResource != null) {
                    val clazz: KClass<*> = jsonFileResource.clazz
                    val resourceName: String = jsonFileResource.fileName
                    val testClass = description.testClass
                    val inputStream: InputStream = testClass.classLoader?.getResourceAsStream(resourceName)
                        ?: error("Failed to load resource: $resourceName from $testClass")
                    BufferedReader(InputStreamReader(inputStream)).use { reader ->
                        mValue = mGson.fromJson(reader, clazz.java)
                    }
                }
                base.evaluate()
            }
        }
    }

}


