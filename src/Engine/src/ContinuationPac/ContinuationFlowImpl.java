package ContinuationPac;

import java.util.ArrayList;
import java.util.List;

public class ContinuationFlowImpl implements ContinuationFlow {
    List<Continuation> continuations;

    public ContinuationFlowImpl() {
        continuations = new ArrayList<>();
    }


    @Override
    public void addContinuation(Continuation continuation) {
        this.continuations.add((continuation));
    }

    @Override
    public List<Continuation> continuations() {
        return null;
    }
}
