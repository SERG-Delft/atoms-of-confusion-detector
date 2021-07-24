package parsing.detectors

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
annotation class Visit(vararg val types: KClass<*>)
