/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.compile.visitors;

import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;

import lombok.RequiredArgsConstructor;
import ranttu.rapid.jco.apt.compile.scope.EnvContext;

/**
 * @author rapid
 * @version $Id: ScopeAwaredVisitor.java, v 0.1 2018年06月29日 10:05 PM rapid Exp $
 */
@RequiredArgsConstructor
public class ScopeAwaredVisitor<R, P> extends TreeScanner<R, P> {
    protected EnvContext currentCtx;

    @Override
    public R visitMethod(MethodTree node, P p) {
        currentCtx = new EnvContext();

        return currentCtx.enter(() -> {
            node.getParameters().forEach(par -> currentCtx.add(par, true));
            return ScopeAwaredVisitor.super.visitMethod(node, p);
        });
    }

    @Override
    public R visitVariable(VariableTree node, P p) {
        currentCtx.add(node);
        return super.visitVariable(node, p);
    }

    @Override
    public R visitBlock(BlockTree node, P p) {
        return currentCtx.enter(() -> ScopeAwaredVisitor.super.visitBlock(node, p));
    }

    @Override
    public R visitAssignment(AssignmentTree node, P p) {
        ExpressionTree exp = node.getVariable();
        if (exp instanceof IdentifierTree) {
            currentCtx.setValued(((IdentifierTree) exp).getName().toString());
        }
        return super.visitAssignment(node, p);
    }
}