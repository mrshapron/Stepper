package ContinuationPac;

import java.util.List;

public interface ContinuationFlow {
    void addContinuation(Continuation continuation);
    List<Continuation> continuations();
}
