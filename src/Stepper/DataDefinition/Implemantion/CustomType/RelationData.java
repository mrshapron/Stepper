package Stepper.DataDefinition.Implemantion.CustomType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class RelationData {
    private List<String> columns;
    private List<SingleRow> rows;

    public RelationData(List<String> columns) {
        this.columns = columns;
        rows = new ArrayList<>();
    }
    public List<String> getRowDataByColumnsOrder(int rowId) {
        List<String> rowData = new ArrayList<>();
        columns.forEach(column -> rowData.add(rows.get(rowId).getValue(column)));

        return rowData;
    }

    public int getNumOfLines(){
        return rows.size();
    }
    public int getNumOfColumns(){
        return columns.size();
    }
    public List<String> getColumns(){
        return columns;
    }
    public void addRow(Map<String,String> data){
        SingleRow row = new SingleRow();
        data.forEach((key,value)-> row.addData(key,value));
        rows.add(row);
    }

    private static class SingleRow {
        private Map<String, String> data;
        private SingleRow() {
            data = new HashMap<>();
        }

        private String getValue(String columnName) { return data.get(columnName); }
        private void addData(String columnName, String value) {
            data.put(columnName, value);
        }
    }
}