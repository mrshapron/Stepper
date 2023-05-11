package Step.Declaration;


import DataDefinitionPack.DataDefinition;
import Step.DataNecessity;

public interface DataDefinitionDeclaration {
    String getAliasName();
    String getName();
    DataNecessity necessity();
    String userString();
    DataDefinition dataDefinition();
    void setAliasName(String name);
}
