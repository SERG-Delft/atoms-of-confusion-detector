package parsing

import JavaParser
import JavaParserBaseVisitor
import parsing.detectors.Detector
import parsing.detectors.Visit
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

class AtomsVisitor : JavaParserBaseVisitor<Unit>() {

    lateinit var fileName: String

    private val callbacksMap = mutableMapOf<KClass<*>, MutableList<Detector>>()

    fun registerDetector(detector: Detector) {

        val annotation = detector::class.findAnnotation<Visit>() ?: return

        // register the detector for each type
        annotation.types.forEach {
            if (callbacksMap[it] == null) callbacksMap[it] = mutableListOf()
            callbacksMap[it]!!.add(detector)
        }
    }

    override fun visitExprPostfix(ctx: JavaParser.ExprPostfixContext) =
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }

    override fun visitExprPrefix(ctx: JavaParser.ExprPrefixContext) =
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }

    override fun visitExprInfix(ctx: JavaParser.ExprInfixContext) = callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
}
