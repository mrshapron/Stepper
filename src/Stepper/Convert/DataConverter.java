package Stepper.Convert;

import Stepper.DataDefinition.DataDefinition;

public interface DataConverter {
    <T> DataDefinition dataTypeToDefinition(Class<T> dataType);
}
