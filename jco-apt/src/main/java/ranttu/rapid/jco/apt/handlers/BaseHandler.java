/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.handlers;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

import ranttu.rapid.jco.apt.utils.Elements;
import ranttu.rapid.jco.apt.utils.Trees;

/**
 * @author rapid
 * @version $Id: BaseHandler.java, v 0.1 2018年06月08日 7:38 PM rapid Exp $
 */
abstract public class BaseHandler implements Handler {
    private Messager                messager;

    protected ProcessingEnvironment procEnv;

    protected RoundEnvironment      roundEnv;

    protected Elements              eleUtil;

    protected Trees                 trees;

    @Override
    public void init(Messager messager, com.sun.source.util.Trees trees,
                     ProcessingEnvironment processingEnv, RoundEnvironment roundEnv) {
        this.messager = messager;
        this.procEnv = processingEnv;
        this.roundEnv = roundEnv;

        this.eleUtil = new Elements(processingEnv.getElementUtils());
        this.trees = new Trees(trees);
    }

    protected void error(String msg, Element ele) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, ele);
    }

    protected void info(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}