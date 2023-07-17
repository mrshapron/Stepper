package BusinessLogic;

@FunctionalInterface
public interface ProgressCallback {
    void updateProgress(double progress);
}
