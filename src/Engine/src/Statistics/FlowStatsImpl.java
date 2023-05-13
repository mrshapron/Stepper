package Statistics;

import Flow.Defenition.FlowDefinition;
import Flow.Defenition.StepUsageDeclaration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlowStatsImpl implements FlowStats {
    private Map<FlowDefinition, RuntimeAndCountFlow> flows;

    public FlowStatsImpl() {
        this.flows = new HashMap<>();
    }

    @Override
    public void addFlow(FlowDefinition flow) {
        if(!flows.containsKey(flow))
            flows.put(flow, new RuntimeAndCountFlow());
    }
    public void addFlowRunTime(FlowDefinition flow, long runtime){
        if(!flows.containsKey(flow)) return;
        flows.get(flow).addRuntime(runtime);
    }

    /* @Override
    public void addFlowStats(FlowDefinition flow, long runTimeMS) {
        RuntimeAndCountFlow runtimeAndCountFlow;
        if(flows.containsKey(flow)) {
            runtimeAndCountFlow = flows.get(flow);
        }else{
            runtimeAndCountFlow = new RuntimeAndCountFlow();
            flows.put(flow,runtimeAndCountFlow);
        }
        runtimeAndCountFlow.addRuntime(runTimeMS);
    }*/

    @Override
    public void addStepStats(FlowDefinition flow,StepUsageDeclaration step, long runTimeMS) {
        if(!flows.containsKey(flow)) return;
        RuntimeAndCountFlow runtimeAndCountFlow = flows.get(flow);
        runtimeAndCountFlow.addStepRuntime(step,runTimeMS);
    }

    @Override
    public boolean isFlowRun(FlowDefinition flow) {
        return flows.containsKey(flow);
    }

    @Override
    public int getTimesRunFlow(FlowDefinition flow) {
        if(!flows.containsKey(flow)) return 0;
        RuntimeAndCountFlow runtimeAndCountFlow = flows.get(flow);
        return runtimeAndCountFlow.getCountRun();
    }

    @Override
    public float getAverageRuntimeFlow(FlowDefinition flow) {
        if(!flows.containsKey(flow)) return 0;
        RuntimeAndCountFlow runtimeAndCountFlow = flows.get(flow);
        return runtimeAndCountFlow.getAverageRuntime();
    }

    @Override
    public int getTimesRunStep(FlowDefinition flow, StepUsageDeclaration step) {
        if(!flows.containsKey(flow)) return 0;
        RuntimeAndCountFlow runtimeAndCountFlow = flows.get(flow);
        return runtimeAndCountFlow.getTimesRunStep(step);
    }

    @Override
    public float getAverageRuntimeStep(FlowDefinition flow, StepUsageDeclaration step) {
        if(!flows.containsKey(flow)) return 0;
        RuntimeAndCountFlow runtimeAndCountFlow = flows.get(flow);
        return runtimeAndCountFlow.getAverageRuntimeStep(step);
    }

    private static class RuntimeAndCountFlow {
        private List<Long> runtimes;
        private int countRun;
        private Map<StepUsageDeclaration, RuntimeAndCountStep> stepMap;

        public RuntimeAndCountFlow(){
            runtimes = new ArrayList<>();
            stepMap = new HashMap<>();
        }

        public void addRuntime(long runtime){
            runtimes.add(runtime);
            countRun++;
        }
        public void addStepRuntime(StepUsageDeclaration stepUsageDeclaration, long runTimeMS){
            RuntimeAndCountStep runtimeAndCountStep;
            if(stepMap.containsKey(stepUsageDeclaration)){
                runtimeAndCountStep = stepMap.get(stepUsageDeclaration);
            }else{
                runtimeAndCountStep = new RuntimeAndCountStep();
                stepMap.put(stepUsageDeclaration,runtimeAndCountStep);
            }
            runtimeAndCountStep.addRuntime(runTimeMS);
        }
        public float getAverageRuntimeStep(StepUsageDeclaration stepUsageDeclaration){
            if (!stepMap.containsKey(stepUsageDeclaration)) return 0;
            return stepMap.get(stepUsageDeclaration).getAverageRuntime();
        }
        public int getTimesRunStep(StepUsageDeclaration stepUsageDeclaration){
            if(!stepMap.containsKey(stepUsageDeclaration)) return 0;
            return stepMap.get(stepUsageDeclaration).getCountRun();
        }
        public float getAverageRuntime(){
            return (float)runtimes.stream().mapToInt(Long::intValue).sum() / countRun;
        }

        public int getCountRun(){
            return countRun;
        }
    }

    private static class RuntimeAndCountStep{
        private int countRun;
        private List<Long> runtimes;
        public RuntimeAndCountStep(){
            runtimes = new ArrayList<>();
        }

        public void addRuntime(long runtime){
            runtimes.add(runtime);
            countRun++;
        }
        public float getAverageRuntime(){
            return (float)runtimes.stream().mapToInt(Long::intValue).sum() / countRun;
        }

        public int getCountRun(){
            return countRun;
        }
    }
}
