package cn.suniper.fsm;

import java.util.function.Function;

/**
 * @author Rao Mengnan
 * on 2019-06-03.
 */
public interface  State<I, O> extends Function<I, Result> {
    Result<O> apply(I input);
}
