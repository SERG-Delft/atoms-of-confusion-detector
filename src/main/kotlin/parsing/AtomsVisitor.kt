package parsing

import JavaParser
import JavaParserBaseVisitor
import parsing.detectors.Detector
import kotlin.reflect.KClass

class AtomsVisitor : JavaParserBaseVisitor<Unit>() {

    lateinit var fileName: String

    private val callbacksMap = mutableMapOf<KClass<*>, MutableList<Detector>>()

    fun registerDetector(detector: Detector, type: KClass<*>) {
        if (callbacksMap[type] == null) callbacksMap[type] = mutableListOf()
        callbacksMap[type]!!.add(detector)
    }

    override fun visitExprPostfix(ctx: JavaParser.ExprPostfixContext) =
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }

    override fun visitExprPrefix(ctx: JavaParser.ExprPrefixContext) =
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }

    override fun visitExprInfix(ctx: JavaParser.ExprInfixContext) = callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
}
