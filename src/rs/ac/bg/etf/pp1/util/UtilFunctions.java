package rs.ac.bg.etf.pp1.util;

import rs.etf.pp1.symboltable.concepts.Struct;

public final class UtilFunctions {

    
    public static String StructToTypeName(Struct structKind)
    {
        switch(structKind.getKind())
        {
            case 0:
            return "None";
            case 1:
            return "Int";
            case 2:
            return "Char";
            case 3:
            return "Array";
            case 4:
            return "Class";
            case 5:
            return "Bool";
            default:
            return "No such kind";
        }
    }

}
