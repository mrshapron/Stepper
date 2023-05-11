package DataDefinitionPack.Implemantion;


import DataDefinitionPack.AbstractDataDefinition;

import java.io.File;

public class FileDataDefinition extends AbstractDataDefinition {
    public FileDataDefinition(){
        super("File", false, File.class);
    }
}
