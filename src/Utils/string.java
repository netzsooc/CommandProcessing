/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author vieyra
 */
public class string {

    public static double JaroWinklerDistanceM(String string1, String string2, int ElementsC) {
        double jwd = JaroWinklerDistance(string1, string2);
        return (jwd + (1.0 / (double) string2.length()))/(jwd + ((1.0 / (double) string2.length()) * (double) ElementsC));
    }
    public static double JaroWinklerDistance(String string1, String string2) {
        return JaroWinklerDistance(string1, string2, 0.1);
    }

    public static double JaroWinklerDistance(String string1, String string2, double p) {
        double JD = JaroDistance(string1, string2);
        return JD + (getIndexofCommonPrefix(string1, string2) * p * (1 - JD));
    }

    private static int getIndexofCommonPrefix(String string1, String string2) {
        int n = ((string1.length() > string2.length()) ? (string2.length()) : string1.length()) > 4 ? 4
                : (string1.length() > string2.length()) ? (string2.length()) : string1.length();
        for (int i = 0; i < n; i++) {
            if (string1.charAt(i) != string2.charAt(i)) {
                return i;
            }
        }
        return n;
    }

    public static double JaroDistance(String string1, String string2) {
        int len1 = string1.length();
        int len2 = string2.length();
        if (len1 == 0) {
            return len2 == 0 ? 1.0 : 0.0;
        }

        int searchRange = Math.max(0, Math.max(len1, len2) / 2 - 1);

        boolean[] matched1 = new boolean[len1];
        Arrays.fill(matched1, false);
        boolean[] matched2 = new boolean[len2];
        Arrays.fill(matched2, false);

        int numCommon = 0;
        for (int i = 0; i < len1; ++i) {
            int start = Math.max(0, i - searchRange);
            int end = Math.min(i + searchRange + 1, len2);
            for (int j = start; j < end; ++j) {
                if (matched2[j]) {
                    continue;
                }
                if (string1.charAt(i) != string2.charAt(j)) {
                    continue;
                }
                matched1[i] = true;
                matched2[j] = true;
                ++numCommon;
                break;
            }
        }
        if (numCommon == 0) {
            return 0.0;
        }

        int numHalfTransposed = 0;
        int j = 0;
        for (int i = 0; i < len1; ++i) {
            if (!matched1[i]) {
                continue;
            }
            while (!matched2[j]) {
                ++j;
            }
            if (string1.charAt(i) != string2.charAt(j)) {
                ++numHalfTransposed;
            }
            ++j;
        }
        int numTransposed = numHalfTransposed / 2;

        double numCommonD = numCommon;
        return (numCommonD / len1
                + numCommonD / len2
                + (numCommon - numTransposed) / numCommonD) / 3.0;
    }

    public static float LevenshteinDistance(String string1, String string2) {
        int[][] matriz = new int[string1.length() + 1][string2.length() + 1];
        for (int i = 0; i < string1.length() + 1; i++) {
            matriz[i][0] = i;
        }
        for (int j = 0; j < string1.length() + 1; j++) {
            matriz[0][j] = j;
        }
        for (int i = 1; i < string1.length() + 1; i++) {
            for (int j = 1; j < string1.length() + 1; j++) {
                if (string1.charAt(i - 1) == string2.charAt(i - 1)) {
                    matriz[i][j] = matriz[i - 1][j - 1];
                } else {
                    matriz[i][j] = Math.min(matriz[i][j - 1] + 1, matriz[i - 1][j] + 1);
                    matriz[i][j] = Math.min(matriz[i][j], matriz[i - 1][j - 1] + 1);
                }
            }
        }
        return matriz[string1.length()][string2.length()];
    }

    public static String normalizeEntityName(String EntityName) {
        String normalizedEntityName = "";

        EntityName = EntityName.replace("-", " ");
        EntityName = EntityName.replace("_", " ");

        while (EntityName.contains("  ")) {
            EntityName = EntityName.replace("  ", " ");
        }
        
        if(EntityName.startsWith(" ")){
            EntityName = EntityName.substring(1);
        }
        if(EntityName.endsWith(" ")){
            EntityName = EntityName.substring(0,EntityName.length()-1);
        }

        if (EntityName.matches("[A-Z][a-z0-9 ]+([A-Z][a-z0-9 ]+)+")) {
            String[] splitedEntityName = EntityName.split("[A-Z ]");
            for (int i = 0; i < splitedEntityName.length; i++) {
                String splited = splitedEntityName[i];
                if (!splited.equals("")) {
                    String toAppend = "";
                    try {
                        toAppend = EntityName.substring(EntityName.indexOf(splited) - 1, (EntityName.indexOf(splited) + splited.length()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    normalizedEntityName += toAppend + ((i < splitedEntityName.length - 1) ? " " : "");
                }
            }
            EntityName = normalizedEntityName;
        }

        return EntityName.toLowerCase();
    }

    public static String[] getEntityNameSegments(String EntityName) {
        ArrayList<String> Segments = new ArrayList<String>();

        if (EntityName.matches("[A-Z][a-z0-9]+([A-Z][a-z0-9]+)+")) {
            String[] splitedEntityName = EntityName.split("[A-Z]");
            for (String splited : splitedEntityName) {
                if (!splited.equals("")) {
                    String toAppend;
                    try {
                        toAppend = EntityName.substring(EntityName.indexOf(splited) - 1, (EntityName.indexOf(splited) + splited.length())).toLowerCase();
                    } catch (Exception ex) {
                        break;
                    }
                    Segments.add(toAppend);
                    EntityName = EntityName.replace(toAppend, "");
                }
            }
        }
        if (EntityName.matches("[A-Za-z0-9]+([ _ -][A-Za-z0-9]+)+")) {
            return EntityName.split("[ _-]");
        }
        if (Segments.size() == 0) {
            Segments.add(EntityName);
        }
        return Segments.toArray(new String[]{});
    }
    public static void main(String[] args){
        System.out.println(JaroWinklerDistance("foco", "foco") + "\t" + "foco".length());
        System.out.println(JaroWinklerDistance("foco de la habitacion", "foco de lahabitacion") + "\t" + "foco de lahabitacion".length());
        System.out.println(JaroWinklerDistance("foco de la habitacion de samuel", "foco de lahabitacion samuel") + "\t" + "foco de lahabitacion samuel".length());
    }
}
