package com.code.java.listener;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import java.util.Locale;

/**
 * Created by hyson 2018/2/4 下午10:41
 */
public class JavaDiagnosticListener implements DiagnosticListener {
    /**
     * nothing to do ?
     * @param diagnostic
     */
    @Override
    public void report(Diagnostic diagnostic) {
        System.out.println("JavaDiagnosticListener" + diagnostic.getMessage(Locale.SIMPLIFIED_CHINESE));
    }
}
