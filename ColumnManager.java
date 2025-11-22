package four;

import javax.swing.*;
import java.util.Map;

public class ColumnManager {
    private final Map<String, JButton> buttonMap;

    public ColumnManager(Map<String, JButton> buttonMap) {
        this.buttonMap = buttonMap;
    }

    public String getFirstFreeCell(char column) {
        for (int row = 1; row <= 6; row++) {
            String cellLabel = "" + column + row;
            JButton button = buttonMap.get(cellLabel);

            if (button != null && button.getText().trim().isEmpty()) {
                return cellLabel;
            }
        }
        return null;
    }
}
