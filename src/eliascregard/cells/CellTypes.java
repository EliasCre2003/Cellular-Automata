package eliascregard.cells;

public class CellTypes {
    public static final Class<? extends Cell> AIR = Air.class;
    public static final Class<? extends Cell> WATER = Water.class;
    public static final Class<? extends Cell> SAND = Sand.class;
    public static final Class<? extends Cell> EDGE = Edge.class;
    public static final Class<? extends Cell> IRON = Iron.class;
    public static final Class<? extends Cell> WOOD = Wood.class;
    public static final Class<? extends Cell> THERMITE = Thermite.class;
    public static final Class<? extends Cell> SMOKE = Smoke.class;
    public static final Class<? extends Cell> LAVA = Lava.class;

    private static final Class<? extends Cell>[] TYPES = new Class[] {
            AIR,
            WATER,
            SAND,
            EDGE,
            IRON,
            WOOD,
            THERMITE,
            SMOKE,
            LAVA
    };

    private static final Class<? extends Cell>[] SOLIDS = new Class[] {
            SAND,
            EDGE,
            IRON,
            WOOD,
            THERMITE,
            LAVA
    };

    private static final Class<? extends Cell>[] LIQUIDS = new Class[] {
            WATER,
    };

    private static final Class<? extends Cell>[] GASES = new Class[] {
            AIR,
            SMOKE
    };

    public static final Class<? extends Cell>[] POWDERS = new Class[] {
            SAND,
            THERMITE
    };

    public static Class<? extends Cell>[] getTypes() {
        return TYPES;
    }

    public static Class<? extends Cell>[] getSolids() {
        return SOLIDS;
    }

    public static Class<? extends Cell>[] getLiquids() {
        return LIQUIDS;
    }

    public static Class<? extends Cell>[] getGases() {
        return GASES;
    }


    public static boolean isSolid(Class<? extends Cell> cellType) {
        for (Class<? extends Cell> solidType : SOLIDS) {
            if (cellType == solidType) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLiquid(Class<? extends Cell> cellType) {
        for (Class<? extends Cell> liquidType : LIQUIDS) {
            if (cellType == liquidType) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGas(Class<? extends Cell> cellType) {
        for (Class<? extends Cell> gasType : GASES) {
            if (cellType == gasType) {
                return true;
            }
        }
        return false;
    }
}
