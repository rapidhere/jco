/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranttu.rapid.jco.apt.compile.scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

import com.sun.source.tree.VariableTree;

import lombok.SneakyThrows;
import lombok.experimental.var;

/**
 * @author rapid
 * @version $Id: EnvContext.java, v 0.1 2018年06月29日 10:09 PM rapid Exp $
 */
public class EnvContext {
    private Stack<Map<String, Named>> namingStack = new Stack<>();

    @SneakyThrows
    public <T> T enter(Callable<T> callable) {
        try {
            namingStack.push(new HashMap<>());
            return callable.call();
        } finally {
            namingStack.pop();
        }
    }

    public Map<String, VariableNamed> resolveVairables() {
        return resolve(named -> named instanceof VariableNamed);
    }

    public Map<String, Named> resolveAll() {
        return resolve(named -> true);
    }

    public <T extends Named> Map<String, T> resolve(Predicate<Named> predicate) {
        Map<String, T> res = new HashMap<>();
        namingStack.forEach(map -> map.forEach((k, v) -> {
            if (predicate.test(v)) {
                @SuppressWarnings("unchecked")
                T t = (T) v;
                res.put(k, t);
            }
        }));

        return Collections.unmodifiableMap(res);
    }

    public <T extends Named> T search(String name) {
        for (var map : namingStack) {
            if (map.containsKey(name)) {
                @SuppressWarnings("unchecked")
                T res = (T) map.get(name);
                return res;
            }
        }

        return null;
    }

    public void setValued(String name) {
        VariableNamed variableNamed = search(name);
        variableNamed.setValued(true);
    }

    public void add(VariableTree variable, boolean valued) {
        var map = namingStack.peek();
        VariableNamed oldNamed = (VariableNamed) map.get(variable.getName().toString());
        if (oldNamed != null) {
            oldNamed.setValued(oldNamed.isValued() || valued);
        } else {
            VariableNamed named = VariableNamed.of(variable, valued);
            map.put(named.getName(), named);
        }
    }

    public void add(VariableTree variable) {
        add(variable, variable.getInitializer() != null);
    }
}