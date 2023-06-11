package src.DataDefinitionPack;

public interface DataDefinition {
    String getName();
    boolean isUserFriendly();
    Class<?> getType();
}
