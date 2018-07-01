/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.handlers;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;

import com.sun.source.util.Trees;

/**
 * @author rapid
 * @version $Id: Handler.java, v 0.1 2018年06月08日 7:37 PM rapid Exp $
 */
public interface Handler {
    void init(Messager messager, Trees trees, ProcessingEnvironment processingEnv,
              RoundEnvironment roundEnv);

    void handle();
}