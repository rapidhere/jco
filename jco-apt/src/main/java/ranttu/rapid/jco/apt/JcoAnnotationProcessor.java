/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import com.sun.source.util.Trees;

import lombok.experimental.var;
import ranttu.rapid.jco.apt.handlers.AsyncAnnotationHandler;
import ranttu.rapid.jco.apt.handlers.Handler;

/**
 * apt processor entry
 *
 * @author rapid
 * @version $Id: JcoAnnotationProcessor.java, v 0.1 2018年06月08日 6:40 PM rapid Exp $
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class JcoAnnotationProcessor extends AbstractProcessor {
    /** log messager */
    private Messager                                    messager;

    /** processing env */
    private ProcessingEnvironment                       processingEnv;

    /** ast trees */
    private Trees                                       trees;

    /** handlers */
    private static final List<Class<? extends Handler>> handlers;

    /**
     * @see Processor#init(ProcessingEnvironment)
     */
    @Override
    public void init(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
        this.processingEnv = processingEnv;
        this.trees = Trees.instance(processingEnv);
    }

    /**
     * @see javax.annotation.processing.Processor#process(Set, RoundEnvironment)
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }

        for (var handlerClass : handlers) {
            newHandler(handlerClass, roundEnv).handle();
        }

        return true;
    }

    private Handler newHandler(Class<? extends Handler> handlerClz, RoundEnvironment roundEnv) {
        try {
            Handler handler = handlerClz.newInstance();

            handler.init(messager, trees, processingEnv, roundEnv);
            return handler;
        } catch (Exception e) {
            messager.printMessage(Diagnostic.Kind.ERROR, "failed to init handler");
            throw new RuntimeException(e);
        }
    }

    //~~ handler init
    static {
        handlers = Collections.unmodifiableList(new ArrayList<Class<? extends Handler>>() {
            {
                add(AsyncAnnotationHandler.class);
            }
        });
    }
}