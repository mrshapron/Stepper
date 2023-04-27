package Stepper.Step;


import Stepper.Flow.Execution.Context.StepExecutionContext;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface StepDefinition {
    String name();
    boolean isReadonly();
    List<DataDefinitionDeclaration> inputs();
    List<DataDefinitionDeclaration> outputs() ;
    StepResult invoke(StepExecutionContext context);
}
