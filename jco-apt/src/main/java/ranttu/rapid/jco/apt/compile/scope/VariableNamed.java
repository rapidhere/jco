/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.compile.scope;

import com.sun.source.tree.VariableTree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author rapid
 * @version $Id: VariableNamed.java, v 0.1 2018年06月29日 10:24 PM rapid Exp $
 */
@AllArgsConstructor(staticName = "of")
@ToString
public class VariableNamed extends AbstractNamed<VariableTree> {
    private final VariableTree variable;

    @Setter
    @Getter
    private boolean            valued;

    @Override
    public String getName() {
        return variable.getName().toString();
    }

    @Override
    public VariableTree getTree() {
        return variable;
    }

}