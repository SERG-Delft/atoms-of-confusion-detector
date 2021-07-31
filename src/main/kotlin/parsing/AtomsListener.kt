package parsing

import JavaParserBaseListener
import org.antlr.symtab.ClassSymbol
import org.antlr.symtab.LocalScope
import org.antlr.symtab.Scope
import org.antlr.symtab.SymbolWithScope
import org.antlr.symtab.Type
import parsing.detectors.Detector
import parsing.detectors.Visit
import parsing.symtab.symbols.AtomsBaseSymbol
import parsing.symtab.symbols.AtomsClassFieldSymbol
import parsing.symtab.symbols.AtomsLocalVariableSymbol
import parsing.symtab.symbols.AtomsMethodSymbol
import parsing.symtab.types.TypeResolver
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

@SuppressWarnings("TooManyFunctions")
class AtomsListener : JavaParserBaseListener() {

    var currentScope: Scope? = null

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

    override fun enterClassDeclaration(ctx: JavaParser.ClassDeclarationContext) {
        val classSymbol = ClassSymbol(ctx.IDENTIFIER().toString())
        setupNewSymbol(classSymbol)
    }

    override fun exitClassDeclaration(ctx: JavaParser.ClassDeclarationContext?) {
        popScope()
    }

    override fun enterMethodDeclaration(ctx: JavaParser.MethodDeclarationContext) {
        // ctx.IDENTIFIER() is not constant time we should find a better way to get it
        val type = TypeResolver.resolveType(ctx.typeTypeOrVoid().text)
        val function = AtomsMethodSymbol(ctx.IDENTIFIER().toString(), type)
        setupNewSymbol(function)
    }

    override fun exitMethodDeclaration(ctx: JavaParser.MethodDeclarationContext) {
        popScope()
    }

    override fun enterBlockStatement(ctx: JavaParser.BlockStatementContext) {
        val localScope = LocalScope(currentScope)
        pushScope(localScope)
    }

    override fun exitBlockStatement(ctx: JavaParser.BlockStatementContext) {
        popScope()
    }

    private fun pushScope(scope: Scope) {
        currentScope = scope
    }

    private fun popScope() {
        currentScope = currentScope?.enclosingScope
    }

    private fun setupNewSymbol(newSymbol: SymbolWithScope) {
        newSymbol.enclosingScope = currentScope
        currentScope?.define(newSymbol)
        pushScope(newSymbol)
    }

    override fun enterFieldDeclaration(ctx: JavaParser.FieldDeclarationContext) {
        val type = TypeResolver.resolveType(ctx.typeType().text)
        val declarators = ctx.variableDeclarators()
        updateScopeDueToClassFieldDeclaration(declarators, type)
    }

    override fun enterLocalVariableDeclaration(ctx: JavaParser.LocalVariableDeclarationContext) {
        val type = TypeResolver.resolveType(ctx.typeType().text)
        val declarators = ctx.variableDeclarators()
        updateScopeDueToLocalVariableDeclaration(declarators, type)
    }

    override fun exitLocalVariableDeclaration(ctx: JavaParser.LocalVariableDeclarationContext?) {
        currentScope?.allSymbols?.forEach {
            println(it)
        }
    }

    override fun enterExprAssignment(ctx: JavaParser.ExprAssignmentContext) {
        val assignee = ctx.assignee.text
        val assignedValue = ctx.assigned.text
        val symbol = currentScope?.resolve(assignee)
        if (symbol != null && symbol is AtomsBaseSymbol) {
            symbol.value = assignedValue
        }
    }

    override fun exitExprAssignment(ctx: JavaParser.ExprAssignmentContext) {
        currentScope?.allSymbols?.forEach {
            println(it)
        }
    }

    fun updateScopeDueToClassFieldDeclaration(declarators: JavaParser.VariableDeclaratorsContext, type: Type) {
        val lastChildIndex = declarators.childCount - 1
        var assignmentValue: String? = null
        // walk backwards for efficiency since we know that the last declaration will contain a value
        for (i in lastChildIndex downTo 0) {
            val child = declarators.children[i]
            if (child is JavaParser.VariableDeclaratorContext) {
                val identifier = child.variableDeclaratorId().text
                val value = child.variableInitializer()
                if (value != null) {
                    assignmentValue = value.text
                }
                currentScope?.define(AtomsClassFieldSymbol(identifier, type, assignmentValue))
            }
        }
    }

    fun updateScopeDueToLocalVariableDeclaration(declarators: JavaParser.VariableDeclaratorsContext, type: Type) {
        val lastChildIndex = declarators.childCount - 1
        var assignmentValue: String? = null
        // walk backwards for efficiency since we know that the last declaration will contain a value
        for (i in lastChildIndex downTo 0) {
            val child = declarators.children[i]
            if (child is JavaParser.VariableDeclaratorContext) {
                val identifier = child.variableDeclaratorId().text
                val value = child.variableInitializer()
                if (value != null) {
                    assignmentValue = value.text
                }
                currentScope?.define(AtomsLocalVariableSymbol(identifier, type, assignmentValue))
            }
        }
    }
}
