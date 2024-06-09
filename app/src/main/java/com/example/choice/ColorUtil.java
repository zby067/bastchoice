package com.example.choice;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ColorUtil {
    private static ArrayList<String> colors = new ArrayList<String>() {

    };

    private static ArrayList<String> colorsSelected = new ArrayList<String>();

    public static String getOneColor() {
        if (colors.size() == 0)
            return "#ff0000";
        int i = (new Random()).nextInt(colors.size());
        String str = colors.get(i);
        colorsSelected.add(str);
        colors.remove(str);
        return str;
    }

    public static void reset() {
        colors.addAll(colorsSelected);
        colorsSelected.clear();
    }

    public static void restore(String paramString) {
        colors.add(paramString);
        Iterator<String> iterator = colorsSelected.iterator();
        while (iterator.hasNext()) {
            if (((String)iterator.next()).equals(paramString)) {
                colorsSelected.remove(paramString);
                break;
            }
        }
    }
}
