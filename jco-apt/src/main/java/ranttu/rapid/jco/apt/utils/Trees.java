/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.utils;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author rapid
 * @version $Id: Trees.java, v 0.1 2018年06月08日 8:50 PM rapid Exp $
 */
@RequiredArgsConstructor
public class Trees extends com.sun.source.util.Trees {
    @Delegate
    private final com.sun.source.util.Trees delegate;
}