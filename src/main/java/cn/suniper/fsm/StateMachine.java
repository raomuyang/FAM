package cn.suniper.fsm;

import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * @author Rao Mengnan
 * on 2019-06-03.
 */
public class StateMachine {

    private State state;
    private Object lastOutput;
    private BiFunction<State, Object, Object> inputMapper;
    private InputDataSupplier inputDataSupplier;

    public StateMachine(State initState, BiFunction<State, Object, Object> inputMapper) {
        this.state = initState;
        this.inputMapper = inputMapper;
        this.inputDataSupplier = new InputDataSupplier();
    }

    public StateMachine step() {
        Object input = this.inputDataSupplier.get();
        //noinspection unchecked:
        Result res = this.state.apply(input);
        this.state = res.getNext();
        this.lastOutput = res.getOutput();
        return this;
    }

    @SuppressWarnings({"unchecked"})
    public <T> T getLastOutput() {
        return (T) lastOutput;
    }

    public State getState() {
        return this.state;
    }

    class InputDataSupplier implements Supplier {
        @Override
        public Object get() {
            return inputMapper.apply(state, lastOutput);
        }
    }

}
