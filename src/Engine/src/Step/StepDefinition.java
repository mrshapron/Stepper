package Step;


import Flow.Execution.Context.StepExecutionContext;
import Step.Declaration.DataDefinitionDeclaration;

import java.util.List;

public interface StepDefinition {
    String name();
    boolean isReadonly();
    List<DataDefinitionDeclaration> inputs();
    List<DataDefinitionDeclaration> outputs() ;
    StepResult invoke(StepExecutionContext context);
}
