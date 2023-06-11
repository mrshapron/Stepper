package src.DataDefinitionPack.Implemantion.CustomType;

public enum EnumZip {
    ZIP("zip"),
    UNZIP("unzip");

    private final String zipLabel;

    private EnumZip(String zipLabel) {
        this.zipLabel = zipLabel;
    }

    public String getValue() {
        return this.zipLabel;
    }
}
