/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.compile.scope;

import com.sun.source.tree.Tree;
import lombok.ToString;

/**
 * @author rapid
 * @version $Id: AbstractNamed.java, v 0.1 2018年06月29日 10:28 PM rapid Exp $
 */
@ToString
abstract public class AbstractNamed<T extends Tree> implements Named<T> {
    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Named && getName().equals(((Named) o).getName());
    }
}