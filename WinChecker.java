package four;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class WinChecker {
    private final Map<String, JButton> buttonMap;
    private final char[] cols = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    private final int rows = 6;

    public WinChecker(Map<String, JButton> buttonMap) {
        this.buttonMap = buttonMap;
    }

    public List<String> getWinningCells(String piece) {
        List<String> winningCells;

        winningCells = checkHorizontal(piece);
        if (winningCells != null) return winningCells;

        winningCells = checkVertical(piece);
        if (winningCells != null) return winningCells;

        winningCells = checkDiagonalDown(piece);
        if (winningCells != null) return winningCells;

        winningCells = checkDiagonalUp(piece);
        if (winningCells != null) return winningCells;

        return null;
    }

    private List<String> checkHorizontal(String piece) {
        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col <= cols.length - 4; col++) {
                String cell1 = cols[col] + "" + row;
                String cell2 = cols[col + 1] + "" + row;
                String cell3 = cols[col + 2] + "" + row;
                String cell4 = cols[col + 3] + "" + row;

                if (checkLine(piece, cell1, cell2, cell3, cell4)) {
                    return List.of(cell1, cell2, cell3, cell4);
                }
            }
        }
        return null;
    }

    private List<String> checkVertical(String piece) {
        for (int col = 0; col < cols.length; col++) {
            for (int row = 1; row <= rows - 3; row++) {
                String cell1 = cols[col] + "" + row;
                String cell2 = cols[col] + "" + (row + 1);
                String cell3 = cols[col] + "" + (row + 2);
                String cell4 = cols[col] + "" + (row + 3);

                if (checkLine(piece, cell1, cell2, cell3, cell4)) {
                    return List.of(cell1, cell2, cell3, cell4);
                }
            }
        }
        return null;
    }

    private List<String> checkDiagonalUp(String piece) {
        for (int col = 0; col <= cols.length - 4; col++) {
            for (int row = 1; row <= rows - 3; row++) {
                String cell1 = cols[col] + "" + row;
                String cell2 = cols[col + 1] + "" + (row + 1);
                String cell3 = cols[col + 2] + "" + (row + 2);
                String cell4 = cols[col + 3] + "" + (row + 3);

                if (checkLine(piece, cell1, cell2, cell3, cell4)) {
                    return List.of(cell1, cell2, cell3, cell4);
                }
            }
        }
        return null;
    }

    private List<String> checkDiagonalDown(String piece) {
        for (int col = 0; col <= cols.length - 4; col++) {
            for (int row = 4; row <= rows; row++) {
                String cell1 = cols[col] + "" + row;
                String cell2 = cols[col + 1] + "" + (row - 1);
                String cell3 = cols[col + 2] + "" + (row - 2);
                String cell4 = cols[col + 3] + "" + (row - 3);

                if (checkLine(piece, cell1, cell2, cell3, cell4)) {
                    return List.of(cell1, cell2, cell3, cell4);
                }
            }
        }
        return null;
    }

    private boolean checkLine(String piece, String cell1, String cell2, String cell3, String cell4) {
        return getCellValue(cell1).equals(piece) &&
                getCellValue(cell2).equals(piece) &&
                getCellValue(cell3).equals(piece) &&
                getCellValue(cell4).equals(piece);
    }

    private String getCellValue(String cellLabel) {
        JButton button = buttonMap.get(cellLabel);
        if (button != null) {
            return button.getText().trim();
        }
        return "";
    }

    public boolean isBoardFull() {
        for (JButton button : buttonMap.values()) {
            if (button.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
