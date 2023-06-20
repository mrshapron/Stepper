package ContinuationPac;

public class FlowSourceTargetImpl implements FlowSourceTarget {
    private String source;
    private String target;

    public FlowSourceTargetImpl(String source, String target){
        this.source = source;
        this.target = target;
    }
    @Override
    public String source() {
        return source;
    }
    @Override
    public String target() {
        return target;
    }
}
