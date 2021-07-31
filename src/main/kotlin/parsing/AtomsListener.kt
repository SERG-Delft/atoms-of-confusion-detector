package parsing

import JavaParser
import JavaParserBaseListener
import parsing.detectors.Detector
import parsing.detectors.Visit
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

@Suppress("TooManyFunctions")
class AtomsListener : JavaParserBaseListener() {

    lateinit var fileName: String
    lateinit var file: ParsedFile

    fun traverseFile(file: ParsedFile) {
        fileName = file.stream.sourceName
        this.file = file
    }

    private val callbacksMap = mutableMapOf<KClass<*>, MutableList<Detector>>()

    fun registerDetector(detector: Detector) {

        val annotation = detector::class.findAnnotation<Visit>() ?: return

        // register the detector for each type
        annotation.types.forEach {
            if (callbacksMap[it] == null) callbacksMap[it] = mutableListOf()
            callbacksMap[it]!!.add(detector)
        }
    }

    override fun enterExprPostfix(ctx: JavaParser.ExprPostfixContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterExprPrefix(ctx: JavaParser.ExprPrefixContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterExprInstanceof(ctx: JavaParser.ExprInstanceofContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterExprInfixBitshift(ctx: JavaParser.ExprInfixBitshiftContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterExprInfix(ctx: JavaParser.ExprInfixContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterExprTernary(ctx: JavaParser.ExprTernaryContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatIfElse(ctx: JavaParser.StatIfElseContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatFor(ctx: JavaParser.StatForContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatWhile(ctx: JavaParser.StatWhileContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatDoWhile(ctx: JavaParser.StatDoWhileContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatBlock(ctx: JavaParser.StatBlockContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatExpression(ctx: JavaParser.StatExpressionContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }
}
