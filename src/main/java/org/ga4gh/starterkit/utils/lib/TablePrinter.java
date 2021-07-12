package org.ga4gh.starterkit.utils.lib;

import java.util.ArrayList;
import java.util.List;

public class TablePrinter {

    public static void printTable(List<String[]> table) {
        List<Integer> longestElem = new ArrayList<>();

        // get the longest elements of each column
        for (String[] row: table) {
            for (int i = 0; i < row.length; i++) {
                String cell = row[i];

                if (longestElem.size() <= i) {
                    longestElem.add(0);
                }

                if (cell.length() > longestElem.get(i)) {
                    longestElem.set(i, cell.length());
                }
            }
        }

        // print each row
        StringBuffer sb = new StringBuffer();
        for (String[] row: table) {
            for (int i = 0; i < row.length; i++) {
                String cell = row[i];
                int longest = longestElem.get(i);
                int paddingLength = longest - cell.length();
                String padding = " ".repeat(paddingLength);
                
                sb.append(cell);
                sb.append(padding);
                sb.append("\t");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }

        System.out.print(sb.toString());
    }
}
