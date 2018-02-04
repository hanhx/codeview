package com.code.java;

import com.code.java.listener.JavaDiagnosticListener;
import com.google.common.collect.Lists;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.file.JavacFileManager;
import org.codehaus.plexus.util.FileUtils;
import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * Created by hyson 2018/2/4 下午9:54
 */
public class TestGetJava {

    @Test
    public void testGetJava() throws IOException {
        String srcFile = "/Users/hyson/Documents/ws/workspace-test-demo/demo-testng";

        boolean exists = FileUtils.fileExists(srcFile);
        if (!exists) {
            System.err.println("not exists");
            return;
        }
        if (!FileUtils.fileExists(srcFile + "/pom.xml")) {
            System.err.println("is not a java project!");
            return;
        }
        System.out.println("This is a java project !");
        File file = FileUtils.getFile(srcFile);
        List<File> filelists = FileUtils.getFiles(file, null, null).stream().filter(p -> p.getName().contains(".java")).collect(Collectors.toList());
        filelists.forEach(p -> System.out.println(p.getName()));
        JavacTool javacTool = JavacTool.create();

        JavaDiagnosticListener javaDiagnosticListener = new JavaDiagnosticListener();
        JavacFileManager fileManager = javacTool.getStandardFileManager(javaDiagnosticListener, Locale.SIMPLIFIED_CHINESE, Charset.defaultCharset());

        Iterable<? extends JavaFileObject> javaFiles = fileManager.getJavaFileObjects(filelists.toArray(new File[filelists.size()]));
        JavaCompiler.CompilationTask compilationTask = javacTool.getTask(null, fileManager, null, null, null, javaFiles);
        JavacTask javacTask = (JavacTask) compilationTask;
        javacTask.setLocale(Locale.SIMPLIFIED_CHINESE);

        Iterable<? extends CompilationUnitTree> trees = javacTask.parse();

        List<CompilationUnitTree> allTrees = new ArrayList<>();
        allTrees.addAll(Lists.newArrayList(trees));
        allTrees.forEach(unit ->
                System.out.println(unit.getPackageName() + "||" + unit.getSourceFile().getName())
        );
    }
}
