package com.code.java;

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
import java.util.Collection;
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
        filelists.stream().forEach(p -> System.out.println(p.getName()));
        JavacTool javacTool = JavacTool.create();

        JavacFileManager fileManager = javacTool.getStandardFileManager(null, Locale.SIMPLIFIED_CHINESE, Charset.defaultCharset());

        Collection<? extends JavaFileObject> javaFiles = (Collection<? extends JavaFileObject>) fileManager
                .getJavaFileObjects(filelists.toArray(new File[filelists.size()]));
        JavaCompiler.CompilationTask compilationTask = javacTool.getTask(null, fileManager, null, null, null, javaFiles);
        JavacTask javacTask = (JavacTask) compilationTask;
        javacTask.setLocale(Locale.SIMPLIFIED_CHINESE);

        List<CompilationUnitTree> trees = (List<CompilationUnitTree>) javacTask.parse();
        trees.stream().forEach(unit -> {
            System.out.println(unit.getPackageName() + "||" + unit.getSourceFile().getName());
        });
    }
}
