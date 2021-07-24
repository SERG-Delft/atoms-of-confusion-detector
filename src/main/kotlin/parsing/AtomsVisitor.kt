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

    override fun visitExprPostfix(ctx: JavaParser.ExprPostfixContext) {
        val detectors = callbacksMap[ctx::class]
        if (detectors == null) {
            visitChildren(ctx)
        } else {
            detectors.forEach { it.detect(ctx) }
        }
    }

    override fun visitExprPrefix(ctx: JavaParser.ExprPrefixContext) {
        val detectors = callbacksMap[ctx::class]
        if (detectors == null) {
            visitChildren(ctx)
        } else {
            detectors.forEach { it.detect(ctx) }
        }
    }

    override fun visitExprInfix(ctx: JavaParser.ExprInfixContext) {
        val detectors = callbacksMap[ctx::class]
        if (detectors == null) {
            visitChildren(ctx)
        } else {
            detectors.forEach { it.detect(ctx) }
        }
    }

    override fun visitExprTernary(ctx: JavaParser.ExprTernaryContext) {
        val detectors = callbacksMap[ctx::class]
        if (detectors == null) {
            visitChildren(ctx)
        } else {
            detectors.forEach { it.detect(ctx) }
        }
    }
}
