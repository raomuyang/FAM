package cn.suniper.fsm.example;

import cn.suniper.fsm.Result;
import cn.suniper.fsm.State;
import cn.suniper.fsm.StateMachine;


/**
 * @author Rao Mengnan
 * on 2019-06-03.
 */
public class FreeFall {
    private static final double G = 9.8;
    private static final int HEIGHT = 1000;  // 1km

    public static void main(String[] args) {
        FreeFall example = new FreeFall();

        final double interval = 1;
        StateMachine machine = new StateMachine(
                example.getState(StateEnum.BEFORE_RELEASE),
                (state, v)-> {
                    if (v == null) return 0D;  // init
                    return (Double) v + interval * G;
                }
        );

        // input/output: v (speed)
        while (true) {
            State state = machine.step().getState();
            if (state instanceof Landed) {
                break;
            }
        }
        // final state
        Double v = machine.step().getLastOutput();
        System.out.printf("\nfinal speed: %f\n", v);
    }


    enum StateEnum {
        BEFORE_RELEASE,
        FALLING,
        LANDED
    }

    private State<Double, Double> getState(StateEnum stateName) {
        switch (stateName) {
            case BEFORE_RELEASE: return new BeforeReleaseRelease();
            case FALLING: return new Falling();
            case LANDED: return new Landed();
            default: return null;
        }
    }

    class BeforeReleaseRelease implements State<Double, Double> {

        @Override
        public Result<Double> apply(Double v) {
            assert v == 0;
            System.out.println("before release");
            Result<Double> result = new Result<>();
            result.setNext(getState(StateEnum.FALLING));
            result.setOutput(0.);
            return result;
        }
    }

    class Falling implements State<Double, Double> {

        @Override
        public Result<Double> apply(Double v) {
            double t = v / G;
            double s = 0.5 * G * Math.pow(t, 2);

            Result<Double> result = new Result<>();
            result.setOutput(v);

            if (s - HEIGHT > 0.000001) {
                result.setNext(getState(StateEnum.LANDED));
                System.out.printf("falling  t: %.5fs, S: %.5f, v: %.5f\n", t, s, 0D);
            } else {
                result.setNext(this);
                System.out.printf("falling  t: %.5fs, S: %.5f, v: %.5f\n", t, s, v);
            }
            return result;
        }
    }

    class Landed implements State<Double, Double> {

        @Override
        public Result<Double> apply(Double v) {
            Result<Double> result = new Result<>();
            result.setOutput(0D);
            result.setNext(this);
            System.out.println("landed");
            return result;
        }
    }

}
