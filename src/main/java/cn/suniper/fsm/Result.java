package cn.suniper.fsm;

/**
 * @author Rao Mengnan
 * on 2019-06-03.
 */
public class Result<O> {

    private O output;
    private State next;

    public O getOutput() {
        return output;
    }

    public void setOutput(O output) {
        this.output = output;
    }

    public State getNext() {
        return next;
    }

    public void setNext(State next) {
        this.next = next;
    }
}
