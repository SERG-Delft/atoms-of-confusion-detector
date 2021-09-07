// Generated from JavaParser.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JavaParser}.
 */
public interface JavaParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JavaParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(JavaParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(JavaParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterPackageDeclaration(JavaParser.PackageDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#packageDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitPackageDeclaration(JavaParser.PackageDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterImportDeclaration(JavaParser.ImportDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#importDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitImportDeclaration(JavaParser.ImportDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTypeDeclaration(JavaParser.TypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTypeDeclaration(JavaParser.TypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void enterModifier(JavaParser.ModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#modifier}.
	 * @param ctx the parse tree
	 */
	void exitModifier(JavaParser.ModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classOrInterfaceModifier}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classOrInterfaceModifier}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(JavaParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(JavaParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameters(JavaParser.TypeParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeParameters}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameters(JavaParser.TypeParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeParameter(JavaParser.TypeParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeParameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeParameter(JavaParser.TypeParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeBound}.
	 * @param ctx the parse tree
	 */
	void enterTypeBound(JavaParser.TypeBoundContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeBound}.
	 * @param ctx the parse tree
	 */
	void exitTypeBound(JavaParser.TypeBoundContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEnumDeclaration(JavaParser.EnumDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#enumDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEnumDeclaration(JavaParser.EnumDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstants(JavaParser.EnumConstantsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#enumConstants}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstants(JavaParser.EnumConstantsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void enterEnumConstant(JavaParser.EnumConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#enumConstant}.
	 * @param ctx the parse tree
	 */
	void exitEnumConstant(JavaParser.EnumConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void enterEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 */
	void exitEnumBodyDeclarations(JavaParser.EnumBodyDeclarationsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDeclaration(JavaParser.InterfaceDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(JavaParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(JavaParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBody(JavaParser.InterfaceBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#interfaceBody}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBody(JavaParser.InterfaceBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(JavaParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(JavaParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(JavaParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void enterMethodBody(JavaParser.MethodBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#methodBody}.
	 * @param ctx the parse tree
	 */
	void exitMethodBody(JavaParser.MethodBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(JavaParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(JavaParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#genericMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericMethodDeclaration(JavaParser.GenericMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#genericMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericMethodDeclaration(JavaParser.GenericMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#genericConstructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#genericConstructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(JavaParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(JavaParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#interfaceBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceBodyDeclaration(JavaParser.InterfaceBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMemberDeclaration(JavaParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMemberDeclaration(JavaParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstDeclaration(JavaParser.ConstDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#constDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstDeclaration(JavaParser.ConstDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#constantDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#constantDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitConstantDeclarator(JavaParser.ConstantDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodDeclaration(JavaParser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMethodModifier(JavaParser.InterfaceMethodModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMethodModifier(JavaParser.InterfaceMethodModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#genericInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterGenericInterfaceMethodDeclaration(JavaParser.GenericInterfaceMethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#genericInterfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitGenericInterfaceMethodDeclaration(JavaParser.GenericInterfaceMethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(JavaParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(JavaParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(JavaParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(JavaParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(JavaParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(JavaParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(JavaParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(JavaParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(JavaParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(JavaParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(JavaParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(JavaParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(JavaParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(JavaParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(JavaParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(JavaParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(JavaParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(JavaParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(JavaParser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(JavaParser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(JavaParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(JavaParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(JavaParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(JavaParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitDecimal}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntLitDecimal(JavaParser.IntLitDecimalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitDecimal}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntLitDecimal(JavaParser.IntLitDecimalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitHex}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntLitHex(JavaParser.IntLitHexContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitHex}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntLitHex(JavaParser.IntLitHexContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitOctal}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntLitOctal(JavaParser.IntLitOctalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitOctal}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntLitOctal(JavaParser.IntLitOctalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intLitBin}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntLitBin(JavaParser.IntLitBinContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intLitBin}
	 * labeled alternative in {@link JavaParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntLitBin(JavaParser.IntLitBinContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(JavaParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(JavaParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#altAnnotationQualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterAltAnnotationQualifiedName(JavaParser.AltAnnotationQualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#altAnnotationQualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitAltAnnotationQualifiedName(JavaParser.AltAnnotationQualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotation}.
	 * @param ctx the parse tree
	 */
	void enterAnnotation(JavaParser.AnnotationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotation}.
	 * @param ctx the parse tree
	 */
	void exitAnnotation(JavaParser.AnnotationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePairs(JavaParser.ElementValuePairsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elementValuePairs}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePairs(JavaParser.ElementValuePairsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void enterElementValuePair(JavaParser.ElementValuePairContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elementValuePair}.
	 * @param ctx the parse tree
	 */
	void exitElementValuePair(JavaParser.ElementValuePairContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void enterElementValue(JavaParser.ElementValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elementValue}.
	 * @param ctx the parse tree
	 */
	void exitElementValue(JavaParser.ElementValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitElementValueArrayInitializer(JavaParser.ElementValueArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeDeclaration(JavaParser.AnnotationTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationTypeBody}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeBody(JavaParser.AnnotationTypeBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementDeclaration(JavaParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationTypeElementDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementDeclaration(JavaParser.AnnotationTypeElementDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationTypeElementRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationTypeElementRest(JavaParser.AnnotationTypeElementRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationMethodOrConstantRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodOrConstantRest(JavaParser.AnnotationMethodOrConstantRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationMethodOrConstantRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodOrConstantRest(JavaParser.AnnotationMethodOrConstantRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationMethodRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationMethodRest(JavaParser.AnnotationMethodRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 */
	void enterAnnotationConstantRest(JavaParser.AnnotationConstantRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#annotationConstantRest}.
	 * @param ctx the parse tree
	 */
	void exitAnnotationConstantRest(JavaParser.AnnotationConstantRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void enterDefaultValue(JavaParser.DefaultValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#defaultValue}.
	 * @param ctx the parse tree
	 */
	void exitDefaultValue(JavaParser.DefaultValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(JavaParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(JavaParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(JavaParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(JavaParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalVariableDeclaration(JavaParser.LocalVariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#localTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterLocalTypeDeclaration(JavaParser.LocalTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#localTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitLocalTypeDeclaration(JavaParser.LocalTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statBlock}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatBlock(JavaParser.StatBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statBlock}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatBlock(JavaParser.StatBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statAssert}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatAssert(JavaParser.StatAssertContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statAssert}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatAssert(JavaParser.StatAssertContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statIfElse}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatIfElse(JavaParser.StatIfElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statIfElse}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatIfElse(JavaParser.StatIfElseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statFor}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatFor(JavaParser.StatForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statFor}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatFor(JavaParser.StatForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statWhile}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatWhile(JavaParser.StatWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statWhile}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatWhile(JavaParser.StatWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statDoWhile}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatDoWhile(JavaParser.StatDoWhileContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statDoWhile}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatDoWhile(JavaParser.StatDoWhileContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statTry}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatTry(JavaParser.StatTryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statTry}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatTry(JavaParser.StatTryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statTry2}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatTry2(JavaParser.StatTry2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code statTry2}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatTry2(JavaParser.StatTry2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code statSwitch}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatSwitch(JavaParser.StatSwitchContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statSwitch}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatSwitch(JavaParser.StatSwitchContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statSync}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatSync(JavaParser.StatSyncContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statSync}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatSync(JavaParser.StatSyncContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statReturn}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatReturn(JavaParser.StatReturnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statReturn}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatReturn(JavaParser.StatReturnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statThrow}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatThrow(JavaParser.StatThrowContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statThrow}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatThrow(JavaParser.StatThrowContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statBreak}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatBreak(JavaParser.StatBreakContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statBreak}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatBreak(JavaParser.StatBreakContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statContinue}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatContinue(JavaParser.StatContinueContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statContinue}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatContinue(JavaParser.StatContinueContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statSemicolon}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatSemicolon(JavaParser.StatSemicolonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statSemicolon}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatSemicolon(JavaParser.StatSemicolonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statExpression}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatExpression(JavaParser.StatExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statExpression}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatExpression(JavaParser.StatExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code statColon}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatColon(JavaParser.StatColonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code statColon}
	 * labeled alternative in {@link JavaParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatColon(JavaParser.StatColonContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void enterCatchClause(JavaParser.CatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#catchClause}.
	 * @param ctx the parse tree
	 */
	void exitCatchClause(JavaParser.CatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#catchType}.
	 * @param ctx the parse tree
	 */
	void enterCatchType(JavaParser.CatchTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#catchType}.
	 * @param ctx the parse tree
	 */
	void exitCatchType(JavaParser.CatchTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void enterFinallyBlock(JavaParser.FinallyBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#finallyBlock}.
	 * @param ctx the parse tree
	 */
	void exitFinallyBlock(JavaParser.FinallyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void enterResourceSpecification(JavaParser.ResourceSpecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#resourceSpecification}.
	 * @param ctx the parse tree
	 */
	void exitResourceSpecification(JavaParser.ResourceSpecificationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#resources}.
	 * @param ctx the parse tree
	 */
	void enterResources(JavaParser.ResourcesContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#resources}.
	 * @param ctx the parse tree
	 */
	void exitResources(JavaParser.ResourcesContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#resource}.
	 * @param ctx the parse tree
	 */
	void enterResource(JavaParser.ResourceContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#resource}.
	 * @param ctx the parse tree
	 */
	void exitResource(JavaParser.ResourceContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(JavaParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(JavaParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(JavaParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forCtrlEnhanced}
	 * labeled alternative in {@link JavaParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForCtrlEnhanced(JavaParser.ForCtrlEnhancedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forCtrlEnhanced}
	 * labeled alternative in {@link JavaParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForCtrlEnhanced(JavaParser.ForCtrlEnhancedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forCtrlStandard}
	 * labeled alternative in {@link JavaParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForCtrlStandard(JavaParser.ForCtrlStandardContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forCtrlStandard}
	 * labeled alternative in {@link JavaParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForCtrlStandard(JavaParser.ForCtrlStandardContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(JavaParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(JavaParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(JavaParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(JavaParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(JavaParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(JavaParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void enterMethodCall(JavaParser.MethodCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#methodCall}.
	 * @param ctx the parse tree
	 */
	void exitMethodCall(JavaParser.MethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStaticFuncAccesor2}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprStaticFuncAccesor2(JavaParser.ExprStaticFuncAccesor2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStaticFuncAccesor2}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprStaticFuncAccesor2(JavaParser.ExprStaticFuncAccesor2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStaticFuncAccessor1}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprStaticFuncAccessor1(JavaParser.ExprStaticFuncAccessor1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStaticFuncAccessor1}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprStaticFuncAccessor1(JavaParser.ExprStaticFuncAccessor1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code exprPostfix}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprPostfix(JavaParser.ExprPostfixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprPostfix}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprPostfix(JavaParser.ExprPostfixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprNewExpression}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprNewExpression(JavaParser.ExprNewExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprNewExpression}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprNewExpression(JavaParser.ExprNewExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprIdentifier(JavaParser.ExprIdentifierContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprIdentifier}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprIdentifier(JavaParser.ExprIdentifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprTypeCast}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprTypeCast(JavaParser.ExprTypeCastContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprTypeCast}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprTypeCast(JavaParser.ExprTypeCastContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprFuncAccesor}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprFuncAccesor(JavaParser.ExprFuncAccesorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprFuncAccesor}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprFuncAccesor(JavaParser.ExprFuncAccesorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprLiteral}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprLiteral(JavaParser.ExprLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprLiteral}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprLiteral(JavaParser.ExprLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprLambda}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprLambda(JavaParser.ExprLambdaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprLambda}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprLambda(JavaParser.ExprLambdaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprArrayAccess}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprArrayAccess(JavaParser.ExprArrayAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprArrayAccess}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprArrayAccess(JavaParser.ExprArrayAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprInfixBitshift}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprInfixBitshift(JavaParser.ExprInfixBitshiftContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprInfixBitshift}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprInfixBitshift(JavaParser.ExprInfixBitshiftContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprInstanceof}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprInstanceof(JavaParser.ExprInstanceofContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprInstanceof}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprInstanceof(JavaParser.ExprInstanceofContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprSuper}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprSuper(JavaParser.ExprSuperContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprSuper}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprSuper(JavaParser.ExprSuperContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprDotAccess}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprDotAccess(JavaParser.ExprDotAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprDotAccess}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprDotAccess(JavaParser.ExprDotAccessContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprParenthesized}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprParenthesized(JavaParser.ExprParenthesizedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprParenthesized}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprParenthesized(JavaParser.ExprParenthesizedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprPrefix}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprPrefix(JavaParser.ExprPrefixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprPrefix}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprPrefix(JavaParser.ExprPrefixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code typedMethodCall}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterTypedMethodCall(JavaParser.TypedMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code typedMethodCall}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitTypedMethodCall(JavaParser.TypedMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprMethodCall}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprMethodCall(JavaParser.ExprMethodCallContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprMethodCall}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprMethodCall(JavaParser.ExprMethodCallContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprTernary}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprTernary(JavaParser.ExprTernaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprTernary}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprTernary(JavaParser.ExprTernaryContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprAssignment}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprAssignment(JavaParser.ExprAssignmentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprAssignment}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprAssignment(JavaParser.ExprAssignmentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprThis}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprThis(JavaParser.ExprThisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprThis}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprThis(JavaParser.ExprThisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprInfix}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprInfix(JavaParser.ExprInfixContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprInfix}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprInfix(JavaParser.ExprInfixContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprClassAccess}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExprClassAccess(JavaParser.ExprClassAccessContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprClassAccess}
	 * labeled alternative in {@link JavaParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExprClassAccess(JavaParser.ExprClassAccessContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaExpression(JavaParser.LambdaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#lambdaExpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaExpression(JavaParser.LambdaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void enterLambdaParameters(JavaParser.LambdaParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#lambdaParameters}.
	 * @param ctx the parse tree
	 */
	void exitLambdaParameters(JavaParser.LambdaParametersContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambdaBodyExpr}
	 * labeled alternative in {@link JavaParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void enterLambdaBodyExpr(JavaParser.LambdaBodyExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambdaBodyExpr}
	 * labeled alternative in {@link JavaParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void exitLambdaBodyExpr(JavaParser.LambdaBodyExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code lambdaBodyBlock}
	 * labeled alternative in {@link JavaParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void enterLambdaBodyBlock(JavaParser.LambdaBodyBlockContext ctx);
	/**
	 * Exit a parse tree produced by the {@code lambdaBodyBlock}
	 * labeled alternative in {@link JavaParser#lambdaBody}.
	 * @param ctx the parse tree
	 */
	void exitLambdaBodyBlock(JavaParser.LambdaBodyBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classType}.
	 * @param ctx the parse tree
	 */
	void enterClassType(JavaParser.ClassTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classType}.
	 * @param ctx the parse tree
	 */
	void exitClassType(JavaParser.ClassTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(JavaParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(JavaParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#createdName}.
	 * @param ctx the parse tree
	 */
	void enterCreatedName(JavaParser.CreatedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#createdName}.
	 * @param ctx the parse tree
	 */
	void exitCreatedName(JavaParser.CreatedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void enterInnerCreator(JavaParser.InnerCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#innerCreator}.
	 * @param ctx the parse tree
	 */
	void exitInnerCreator(JavaParser.InnerCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#arrayCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreatorRest(JavaParser.ArrayCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void enterClassCreatorRest(JavaParser.ClassCreatorRestContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#classCreatorRest}.
	 * @param ctx the parse tree
	 */
	void exitClassCreatorRest(JavaParser.ClassCreatorRestContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#explicitGenericInvocation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitGenericInvocation(JavaParser.ExplicitGenericInvocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#explicitGenericInvocation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitGenericInvocation(JavaParser.ExplicitGenericInvocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgumentsOrDiamond(JavaParser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#nonWildcardTypeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArgumentsOrDiamond(JavaParser.NonWildcardTypeArgumentsOrDiamondContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void enterNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#nonWildcardTypeArguments}.
	 * @param ctx the parse tree
	 */
	void exitNonWildcardTypeArguments(JavaParser.NonWildcardTypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(JavaParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(JavaParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterTypeType(JavaParser.TypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitTypeType(JavaParser.TypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(JavaParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(JavaParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void enterTypeArguments(JavaParser.TypeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#typeArguments}.
	 * @param ctx the parse tree
	 */
	void exitTypeArguments(JavaParser.TypeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(JavaParser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(JavaParser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#explicitGenericInvocationSuffix}.
	 * @param ctx the parse tree
	 */
	void enterExplicitGenericInvocationSuffix(JavaParser.ExplicitGenericInvocationSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#explicitGenericInvocationSuffix}.
	 * @param ctx the parse tree
	 */
	void exitExplicitGenericInvocationSuffix(JavaParser.ExplicitGenericInvocationSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link JavaParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(JavaParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JavaParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(JavaParser.ArgumentsContext ctx);
}