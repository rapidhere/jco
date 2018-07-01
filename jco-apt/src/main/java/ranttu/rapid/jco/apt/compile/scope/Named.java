/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.compile.scope;

import com.sun.source.tree.Tree;

/**
 *
 * @author rapid
 * @version $Id: Named.java, v 0.1 2018年06月29日 10:22 PM rapid Exp $
 */
public interface Named<T extends Tree> {
    String getName();

    T getTree();
}