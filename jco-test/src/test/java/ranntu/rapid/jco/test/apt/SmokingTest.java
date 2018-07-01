/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package ranntu.rapid.jco.test.apt;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;
import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaFileObjects.forSourceString;

import org.testng.annotations.Test;

import com.google.testing.compile.Compilation;

import ranttu.rapid.jco.apt.JcoAnnotationProcessor;

/**
 * @author rapid
 * @version $Id: SmokingTest.java, v 0.1 2018年06月29日 9:23 PM rapid Exp $
 */
public class SmokingTest {
    @Test
    public void test() {
        Compilation compilation = javac().compile(
            forSourceString("HelloWorld", "final class HelloWorld{}"));
        assertThat(compilation).succeeded();

        compilation = javac().withProcessors(new JcoAnnotationProcessor()).compile(
            forSourceString("HelloWorld", "final class HelloWorld{}"));
        assertThat(compilation).succeeded();

        compilation = javac().withProcessors(new JcoAnnotationProcessor()).compile(
            forResource("sourceFiles/CommonAsyncUsage.java"));
        assertThat(compilation).succeeded();

        System.out.println(compilation.notes());
    }
}