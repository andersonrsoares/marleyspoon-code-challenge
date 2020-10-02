package br.com.anderson.marleyspooncodechallenge

import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class JsonFileResource(val fileName: String, val clazz: KClass<*>)