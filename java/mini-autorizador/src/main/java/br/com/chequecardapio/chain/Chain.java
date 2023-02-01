package br.com.chequecardapio.chain;

public abstract class Chain {
    private Chain next;

    public Chain() {
    }

    public void setNext(Chain next) {
        this.next = next;
    }

    protected abstract boolean isValid(Context context);

    public void execute(Context context) {
        if (isValid(context) && next != null) {
            next.execute(context);
        }
    }
}
