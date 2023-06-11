package src.Mapping;


import Step.StepDefinition;

public interface CustomMappingDefinition {
    StepDefinition getSourceStep();
    StepDefinition getTargetStep();
    String getSourceDataName();
    String getTargetDataName();

}
