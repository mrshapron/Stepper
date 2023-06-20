package ContinuationPac;

public class CustomContinuationMappingImpl implements CustomContinuationMapping {
    private String sourceDataName;
    private String targetDataName;

    public CustomContinuationMappingImpl(String sourceDataName, String targetDataName) {
        this.sourceDataName = sourceDataName;
        this.targetDataName = targetDataName;
    }

    @Override
    public String sourceDataName() {
        return sourceDataName;
    }

    @Override
    public String targetDataName() {
        return targetDataName;
    }

}
