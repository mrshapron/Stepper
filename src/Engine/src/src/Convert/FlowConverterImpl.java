package src.Convert;


import Convert.FlowConverter;
import Flow.Defenition.FlowDefinition;
import Flow.Defenition.FlowDefinitionImpl;
import Flow.Defenition.StepUsageDeclaration;
import Flow.Defenition.StepUsageDeclarationImpl;
import JAXB.Generated.STCustomMapping;
import JAXB.Generated.STFlow;
import JAXB.Generated.STFlowLevelAlias;
import JAXB.Generated.STStepInFlow;
import Log.Logger;
import Log.LoggerImpl;
import Mapping.MappingDataDefinitionImpl;
import Step.Declaration.DataDefinitionDeclaration;
import Step.StepDefinition;
import Step.StepFactory;
import Step.StepFactoryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class FlowConverterImpl implements FlowConverter {
    private Logger logger;

    public FlowConverterImpl() {
        logger = LoggerImpl.getInstance();
    }

    @Override
    public FlowDefinition Convert(STFlow stFlow) {
        AtomicBoolean isFlowValid = new AtomicBoolean(true);
        FlowDefinition flow = new FlowDefinitionImpl(stFlow.getName(),stFlow.getSTFlowDescription());
        StepFactory factory = new StepFactoryImpl();
        //Add Steps..
        for (STStepInFlow stepST: stFlow.getSTStepsInFlow().getSTStepInFlow()) {
            boolean isSucceeded = addStepToFlow(flow, factory, stepST);
            if (!isSucceeded){
                logger.addLog(String.format("There was a problem initialize step %s to the flow %s\n", stepST.getName(), stFlow.getName()));
                return null;
            }
        }
        //Add Formal outputs..
        Arrays.stream(stFlow.getSTFlowOutput().split(",")).collect(Collectors.toList())
                .forEach(s -> flow.addFlowOutput(s));
        if(stFlow.getSTFlowLevelAliasing() != null){
            for (STFlowLevelAlias stFlowLevelAliasing :
                    stFlow.getSTFlowLevelAliasing().getSTFlowLevelAlias()) {
                boolean isSucceeded = InitializeFlowLevelAlias(stFlowLevelAliasing, flow);
                if(!isSucceeded) {
                    return null;
                }
            }
        }
        if (stFlow.getSTCustomMappings() != null) {
            for (STCustomMapping stCustomMapping : stFlow.getSTCustomMappings().getSTCustomMapping()){
                boolean isSucceeded = addCustomMapping(stCustomMapping, flow);
                if(!isSucceeded)
                    return null;
            }
        }
        return flow;
    }

    private boolean InitializeFlowLevelAlias(STFlowLevelAlias stFlowLevelAliasing, FlowDefinition flow) {
        List<StepUsageDeclaration> stepUsageDecsList = flow.getFlowSteps().stream()
                .filter(stepUsageDec -> stepUsageDec.getFinalStepName()
                        .equals(stFlowLevelAliasing.getStep())).collect(Collectors.toList());
        if(stepUsageDecsList.size() != 1){
            logger.addLog("There is a problem in Flow Level Alias in the name of the step called " +
                    stFlowLevelAliasing.getStep());
            return false;
        }
        StepUsageDeclaration stepUsageDec = stepUsageDecsList.get(0);
        List<DataDefinitionDeclaration> inputDataDefDecs = stepUsageDec.getStepDefinition().inputs().stream().filter(
                inputDataDefDec -> inputDataDefDec.getName().equals(stFlowLevelAliasing.getSourceDataName())
        ).collect(Collectors.toList());
        List<DataDefinitionDeclaration> outputDataDefDecs = stepUsageDec.getStepDefinition().outputs().stream().filter(
                inputDataDefDec -> inputDataDefDec.getName().equals(stFlowLevelAliasing.getSourceDataName())
        ).collect(Collectors.toList());

        if(inputDataDefDecs.size() == 1){
            DataDefinitionDeclaration dataDefinitionDec = inputDataDefDecs.get(0);
            dataDefinitionDec.setAliasName(stFlowLevelAliasing.getAlias());
        }else if(outputDataDefDecs.size() == 1){
            DataDefinitionDeclaration dataDefinitionDec = outputDataDefDecs.get(0);
            dataDefinitionDec.setAliasName(stFlowLevelAliasing.getAlias());
        }else{
            logger.addLog("There is a problem in flow level alias in source data name " +
                    stFlowLevelAliasing.getSourceDataName());
            return false;
        }
        return true;

    }


    private boolean addCustomMapping(STCustomMapping stCustomMapping, FlowDefinition flow) {

        List<StepUsageDeclaration> stepUsageDecSource = flow.getFlowSteps().stream()
                .filter(step -> step.getFinalStepName().equals(stCustomMapping.getSourceStep()))
                .collect(Collectors.toList());
        List<StepUsageDeclaration> stepUsageDecTarget = flow.getFlowSteps().stream()
                .filter(step -> step.getFinalStepName().equals(stCustomMapping.getTargetStep()))
                .collect(Collectors.toList());
        if (stepUsageDecSource.size() > 1 || stepUsageDecTarget.size() > 1) {
            logger.addLog("There is a problem in custom Mapping, the step called "
                    + stCustomMapping.getSourceStep() + "or " + stCustomMapping.getTargetStep()
                    + "is found few times");
            return false;
        } else if (stepUsageDecSource.size() == 0 || stepUsageDecTarget.size() == 0) {
            logger.addLog("There is a problem in custom Mapping, the step " + stCustomMapping.getSourceStep() +
                    "or " + stCustomMapping.getTargetStep() +
                    "haven't found in the step System");
            return false;
        }
        StepUsageDeclaration stepSource = stepUsageDecSource.get(0);
        StepUsageDeclaration stepTarget = stepUsageDecTarget.get(0);
        List<DataDefinitionDeclaration> sourceDataList = stepSource.getStepDefinition().outputs().stream().filter(inputDataDefDec -> inputDataDefDec.getAliasName().equals(
                stCustomMapping.getSourceData()
        )).collect(Collectors.toList());
        List<DataDefinitionDeclaration> targetDataList = stepTarget.getStepDefinition().inputs().stream().filter(inputDataDefDec -> inputDataDefDec.getAliasName().equals(
                stCustomMapping.getTargetData()
        )).collect(Collectors.toList());
        if (sourceDataList.size() != 1 || targetDataList.size() != 1) {
            logger.addLog("There is problem in Custom Mapping at Data called " + stCustomMapping.getSourceData()
                    + "or " + stCustomMapping.getTargetData());
        }
        DataDefinitionDeclaration sourceData = sourceDataList.get(0);
        DataDefinitionDeclaration targetData = targetDataList.get(0);
        if(!sourceData.dataDefinition().getType().equals(targetData.dataDefinition().getType())){
            logger.addLog("There is problem in Custom Mapping at Data called " + stCustomMapping.getSourceData()
                    + "or " + stCustomMapping.getTargetData() + " They are defined as different types");
            return false;
        }
        flow.addCustomMapping(new MappingDataDefinitionImpl(stepSource, stepTarget, sourceData, targetData));
        return true;
    }

    private boolean addStepToFlow(FlowDefinition flow, StepFactory factory, STStepInFlow stepST) {
        StepDefinition stepDef = factory.MakeStep(stepST.getName());
        if(stepDef == null) return false;
        StepUsageDeclaration stepUsageDec;
        if(stepST.getAlias() != null && stepST.getAlias() != "")
            if(stepST.isContinueIfFailing() != null)
                stepUsageDec = new StepUsageDeclarationImpl(stepDef, stepST.isContinueIfFailing(), stepST.getAlias());
            else
                stepUsageDec = new StepUsageDeclarationImpl(stepDef, stepST.getAlias());
        else
            stepUsageDec = new StepUsageDeclarationImpl(stepDef);
        flow.addStepUsageDec(stepUsageDec);
        return true;
    }
}
