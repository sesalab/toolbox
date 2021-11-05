package org.computemetrics.beans;

import org.eclipse.jdt.core.dom.ASTNode;

public abstract class NodeBean<T extends ASTNode> {
    private T node;

    public NodeBean() {
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }
}
