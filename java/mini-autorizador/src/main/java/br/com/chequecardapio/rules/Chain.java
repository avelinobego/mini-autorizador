package br.com.chequecardapio.rules;

import java.util.Map;

public abstract class Chain {
    private Chain next;

    public Chain() {
    }

    public void setNext(Chain next) {
        this.next = next;
    }

    protected abstract boolean isValid(Map<String, Object> context);

    public void execute(Map<String, Object> context) {
        if (isValid(context) && next != null) {
            next.execute(context);
        }
    }
}
