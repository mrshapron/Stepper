package Stepper.Step.Declaration;

import Stepper.DataDefinition.DataDefinition;
import Stepper.Step.DataNecessity;

public interface DataDefinitionDeclaration {
    String getAliasName();
    String getName();
    DataNecessity necessity();
    String userString();
    DataDefinition dataDefinition();
    void setAliasName(String name);
}
