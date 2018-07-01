/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.utils;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author rapid
 * @version $Id: Types.java, v 0.1 2018年06月08日 7:54 PM rapid Exp $
 */
@RequiredArgsConstructor
public class Elements implements javax.lang.model.util.Elements {
    /** delegate type utils */
    @Delegate
    final private javax.lang.model.util.Elements delegate;


    public boolean isNormalMethod(ExecutableElement ele) {
        return ele.getKind() == ElementKind.METHOD;
    }
}