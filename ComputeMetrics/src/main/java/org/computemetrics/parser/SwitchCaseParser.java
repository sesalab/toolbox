package org.computemetrics.parser;

import org.computemetrics.beans.SwitchCaseBean;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.SwitchCase;

import java.util.List;
import java.util.stream.Collectors;

public class SwitchCaseParser {

    public static SwitchCaseBean parse(SwitchCase switchCase) {
        SwitchCaseBean switchCaseBean = new SwitchCaseBean();
        switchCaseBean.setExpressions(((List<Expression>) switchCase.expressions()).stream()
                .map(Object::toString)
                .collect(Collectors.toList())
        );
        switchCaseBean.setNode(switchCase);
        return switchCaseBean;
    }
}
