package Stepper.Step.Declaration;

import Stepper.DataDefinition.DataDefinition;
import Stepper.Step.DataNecessity;
import Stepper.Step.Declaration.DataDefinitionDeclaration;

public class DataDefinitionDeclarationImpl implements DataDefinitionDeclaration {

    private String name;
    private final DataNecessity necessity;
    private final String userString;
    private final DataDefinition dataDefinition;

    public DataDefinitionDeclarationImpl(String name, DataNecessity necessity, String userString, DataDefinition dataDefinition) {
        this.name = name;
        this.necessity = necessity;
        this.userString = userString;
        this.dataDefinition = dataDefinition;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataNecessity necessity() {
        return necessity;
    }

    @Override
    public String userString() {
        return userString;
    }

    @Override
    public DataDefinition dataDefinition() {
        return dataDefinition;
    }

    @Override
    public void setAliasName(String name) {
        this.name = name;
    }
}
