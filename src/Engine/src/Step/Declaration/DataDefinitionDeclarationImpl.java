package Step.Declaration;


import DataDefinitionPack.DataDefinition;
import Step.DataNecessity;

public class DataDefinitionDeclarationImpl implements DataDefinitionDeclaration {

    private String name;
    private String aliasName;
    private final DataNecessity necessity;
    private final String userString;
    private final DataDefinition dataDefinition;

    public DataDefinitionDeclarationImpl(String name, DataNecessity necessity, String userString, DataDefinition dataDefinition) {
        this.name = name;
        this.necessity = necessity;
        this.userString = userString;
        this.dataDefinition = dataDefinition;
        this.aliasName = "";
    }
    @Override
    public String getName() {
        return name;
    }
    public String getAliasName(){
        if(aliasName.isEmpty())
            return name;
        return aliasName;
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
        this.aliasName = name;
    }
}
