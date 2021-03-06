package parsing

import JavaParser
import JavaParserBaseListener
import org.antlr.symtab.ClassSymbol
import org.antlr.symtab.LocalScope
import org.antlr.symtab.Scope
import org.antlr.symtab.Symbol
import org.antlr.symtab.SymbolWithScope
import org.antlr.symtab.Type
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.TokenStream
import parsing.detectors.Detector
import parsing.detectors.Visit
import parsing.symtab.SymtabUtil
import parsing.symtab.symbols.AtomsBaseSymbol
import parsing.symtab.symbols.AtomsClassFieldSymbol
import parsing.symtab.symbols.AtomsConstructorSymbol
import parsing.symtab.symbols.AtomsLocalVariableSymbol
import parsing.symtab.symbols.AtomsMethodSymbol
import parsing.symtab.symbols.AtomsParameterSymbol
import parsing.symtab.types.TypeResolver
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

@SuppressWarnings("TooManyFunctions")
class AtomsListener : JavaParserBaseListener() {

    var currentScope: Scope? = null

    lateinit var fileName: String
    lateinit var tokens: TokenStream
    lateinit var parsedFile: ParsedFile

    /**
     * Set up the listener to traverse a file
     *
     * @pa
     */
    fun setFile(file: ParsedFile) {
        fileName = file.name
        this.tokens = file.tokens
        this.parsedFile = file
    }

    private val callbacksMap = mutableMapOf<KClass<*>, MutableList<Detector>>()

    /**
     * Registers a Detector object to the listener.
     *
     * @param detector the Detector to register.
     */
    fun registerDetector(detector: Detector) {

        val annotation = detector::class.findAnnotation<Visit>() ?: return

        // register the detector for each type
        annotation.types.forEach {
            if (callbacksMap[it] == null) callbacksMap[it] = mutableListOf()
            callbacksMap[it]!!.add(detector)
        }
    }

    // Running detectors

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

    override fun enterIntLitOctal(ctx: JavaParser.IntLitOctalContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterStatIfElse(ctx: JavaParser.StatIfElseContext) {
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

    override fun enterExprTypeCast(ctx: JavaParser.ExprTypeCastContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterClassDeclaration(ctx: JavaParser.ClassDeclarationContext) {
        val classSymbol = ClassSymbol(ctx.IDENTIFIER().toString())
        setupNewSymbol(classSymbol)
    }

    override fun exitClassDeclaration(ctx: JavaParser.ClassDeclarationContext?) {
        popScope()
    }

    override fun enterClassCreatorRest(ctx: JavaParser.ClassCreatorRestContext) {
        if (ctx.classBody() == null) {
            return
        }
        val newSymbol = ClassSymbol("Anonymous-Class@${ctx.start.line}:${ctx.start.charPositionInLine}")
        setupNewSymbol(newSymbol)
    }

    override fun exitClassCreatorRest(ctx: JavaParser.ClassCreatorRestContext) {
        if (ctx.classBody() == null) {
            return
        }
        popScope()
    }

    override fun enterInterfaceMethodDeclaration(ctx: JavaParser.InterfaceMethodDeclarationContext) {
        val type = TypeResolver.resolveType(ctx.typeTypeOrVoid().text)
        val symbolName = SymtabUtil.getInterfaceMethodSymbolId(ctx)
        val function = AtomsMethodSymbol(symbolName, type, mutableSetOf())
        setupNewSymbol(function)
    }

    override fun exitInterfaceDeclaration(ctx: JavaParser.InterfaceDeclarationContext) {
        popScope()
    }

    override fun enterConstructorDeclaration(ctx: JavaParser.ConstructorDeclarationContext) {
        val constructor = AtomsConstructorSymbol(ctx.text, mutableSetOf())
        setupNewSymbol(constructor)
    }

    override fun enterFormalParameter(ctx: JavaParser.FormalParameterContext) {
        val parameter = AtomsParameterSymbol(
            ctx.variableDeclaratorId().text,
            TypeResolver.resolveType(ctx.typeType().text)
        )

        when (currentScope) {
            is AtomsMethodSymbol -> (currentScope as AtomsMethodSymbol).parameters.add(parameter)
            is AtomsConstructorSymbol -> (currentScope as AtomsConstructorSymbol).parameters.add(parameter)
        }

        currentScope?.define(parameter)
    }

    override fun enterMethodDeclaration(ctx: JavaParser.MethodDeclarationContext) {
        val type = TypeResolver.resolveType(ctx.typeTypeOrVoid().text)
        val symbolName = SymtabUtil.getMethodSymbolId(ctx)
        val function = AtomsMethodSymbol(symbolName, type, mutableSetOf())
        setupNewSymbol(function)
    }

    override fun exitMethodDeclaration(ctx: JavaParser.MethodDeclarationContext) {
        popScope()
    }

    override fun enterVariableDeclarator(ctx: JavaParser.VariableDeclaratorContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    // Scoping

    override fun enterBlock(ctx: JavaParser.BlockContext) {
        val localScope = LocalScope(currentScope)
        pushScope(localScope)

        val parent = ctx.parent ?: return
        val grandpa = parent.parent ?: return

        if (grandpa is JavaParser.StatForContext) {

            val forControl = grandpa.forCtrl

            // enhanced for loop
            if (forControl is JavaParser.ForCtrlEnhancedContext) {
                val identifier = forControl.id.text
                val type = TypeResolver.resolveType(forControl.type.text)
                val localVar = AtomsLocalVariableSymbol(identifier, type, null)
                currentScope?.define(localVar)
            }

            // standard for control
            if (forControl is JavaParser.ForCtrlStandardContext) {

                val initializer = forControl.init ?: return

                if (initializer.children.size > 0 &&
                    initializer.children[0] is JavaParser.LocalVariableDeclarationContext
                ) {
                    val localDecl = initializer.children[0] as JavaParser.LocalVariableDeclarationContext
                    val type = TypeResolver.resolveType(localDecl.typeType().text)
                    val declarators = localDecl.variableDeclarators()
                    val constructor = { i: String, t: Type, v: String? -> AtomsLocalVariableSymbol(i, t, v) }
                    updateScope(declarators, type, constructor)
                }
            }
        }
    }

    override fun enterStatFor(ctx: JavaParser.StatForContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterForCtrlStandard(ctx: JavaParser.ForCtrlStandardContext) {
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun exitBlock(ctx: JavaParser.BlockContext) {
        popScope()
    }

    private fun pushScope(scope: Scope) {
        currentScope = scope
    }

    private fun popScope() {
        currentScope = currentScope?.enclosingScope
    }

    /**
     * Sets up a new symbol in the scope.
     *
     * @param newSymbol the symbol to add.
     */
    private fun setupNewSymbol(newSymbol: SymbolWithScope) {
        newSymbol.enclosingScope = currentScope
        currentScope?.define(newSymbol)
        pushScope(newSymbol)
    }

    override fun enterFieldDeclaration(ctx: JavaParser.FieldDeclarationContext) {
        val type = TypeResolver.resolveType(ctx.typeType().text)
        val declarators = ctx.variableDeclarators()
        val constructor = { i: String, t: Type, v: String? -> AtomsClassFieldSymbol(i, t, v) }
        updateScope(declarators, type, constructor)
    }

    override fun enterLocalVariableDeclaration(ctx: JavaParser.LocalVariableDeclarationContext) {
        // scoping logic
        if (ctx.parent.parent !is JavaParser.ForControlContext) {
            val type = TypeResolver.resolveType(ctx.typeType().text)
            val declarators = ctx.variableDeclarators()
            val constructor = { i: String, t: Type, v: String? -> AtomsLocalVariableSymbol(i, t, v) }
            updateScope(declarators, type, constructor)
        }

        // detecting logic
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    override fun enterExprAssignment(ctx: JavaParser.ExprAssignmentContext) {
        val assignee = ctx.assignee.text
        val symbol = currentScope?.resolve(assignee)
        if (symbol != null && symbol is AtomsBaseSymbol) {
            symbol.value = ctx.assigned.text
            symbol.parseTreeNodeValue = ctx.assigned
        }
        callbacksMap[ctx::class]?.forEach { it.detect(ctx) }
    }

    /**
     * Updates the current scope based on (a series of) declaration(s).
     *
     * @param declarators the declarations.
     * @param type the type of the variables being declared.
     * @param constructor the constructor (lambda) to be used to create the new symbols.
     */
    private fun updateScope(
        declarators: JavaParser.VariableDeclaratorsContext,
        type: Type,
        constructor: (String, Type, String?) -> Symbol,
    ) {
        val lastChildIndex = declarators.childCount - 1
        var assignmentValueTextual: String? = null
        var assignmentValueParseTree: ParserRuleContext? = null
        // walk backwards for efficiency since we know that the last declaration will contain a value
        for (i in lastChildIndex downTo 0) {
            val child = declarators.children[i]
            if (child is JavaParser.VariableDeclaratorContext) {
                val identifier = child.variableDeclaratorId().text
                val value = child.variableInitializer()
                if (value != null) {
                    assignmentValueTextual = value.text
                    assignmentValueParseTree = value
                }
                val symbol = constructor(identifier, type, assignmentValueTextual)
                if (symbol is AtomsBaseSymbol) {
                    symbol.parseTreeNodeValue = assignmentValueParseTree
                }
                currentScope?.define(symbol)
            }
        }
    }

    // clear the scope when leaving a compilation unit
    override fun exitCompilationUnit(ctx: JavaParser.CompilationUnitContext) {
        currentScope = null
    }
}
