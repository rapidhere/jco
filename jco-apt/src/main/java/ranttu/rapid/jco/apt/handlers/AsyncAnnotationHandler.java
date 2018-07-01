/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.handlers;

import java.util.Set;
import java.util.stream.Collectors;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.util.TreePath;

import lombok.RequiredArgsConstructor;
import lombok.experimental.var;
import ranttu.rapid.jco.apt.compile.scope.VariableNamed;
import ranttu.rapid.jco.apt.compile.visitors.ScopeAwaredVisitor;
import ranttu.rapid.jco.core.annotations.async;

/**
 * @author rapid
 * @version $Id: AsyncAnnotationHandler.java, v 0.1 2018年06月08日 7:38 PM rapid Exp $
 */
public class AsyncAnnotationHandler extends BaseHandler {
    /**
     * @see Handler#handle() 
     */
    @Override
    public void handle() {
        for (var ele : roundEnv.getElementsAnnotatedWith(async.class)) {
            if (!(ele instanceof ExecutableElement)) {
                error("@async must mark on a function", ele);
                return;
            }

            ExecutableElement executableEle = (ExecutableElement) ele;
            if (!eleUtil.isNormalMethod(executableEle)) {
                error("@async can only mark on a normal function", ele);
                return;
            }

            innerProcess(executableEle);
        }
    }

    private void innerProcess(ExecutableElement ele) {
        TreePath path = trees.getPath(ele);
        CompilationUnitTree compilationUnit = path.getCompilationUnit();
        trees.getTree(ele).accept(new MethodVisitor(compilationUnit), null);
    }

    @RequiredArgsConstructor
    private class MethodVisitor extends ScopeAwaredVisitor<Void, Void> {
        private final CompilationUnitTree compilationUnit;

        @Override
        public Void visitMethodInvocation(MethodInvocationTree node, Void v) {
            Set<VariableNamed> vars = currentCtx.resolveVairables().values().stream()
                .filter(VariableNamed::isValued).collect(Collectors.toSet());

            trees.printMessage(Diagnostic.Kind.NOTE, "@method invocation: ", node, compilationUnit);
            info("find local variables: " + vars);

            return super.visitMethodInvocation(node, v);
        }
    }
}