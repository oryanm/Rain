package net.oryanmat.rain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Arrays;
import java.util.List;

/**
 * Colors are taken from <a href="https://www.google.com/design/spec/style/color.html">Android's Material Design guide</a>
 */
public class Colors {
    enum PrimaryColors {
        BLUE,
        LIGHT_BLUE,
        CYAN,
        TEAL,
        GREEN,
        LIGHT_GREEN,
        LIME,
        YELLOW,
        AMBER,
        ORANGE,
        DEEP_ORANGE,
        RED,
        PINK,
        PURPLE,
        DEEP_PURPLE,
        INDIGO
    }

    static final ObjectMap<PrimaryColors, List<Color>> COLORS = new ObjectMap<PrimaryColors, List<Color>>() {{
        put(PrimaryColors.BLUE, Arrays.asList(Blues.A200, Blues.A400, Blues.A700));
        put(PrimaryColors.LIGHT_BLUE, Arrays.asList(LightBlues.A200, LightBlues.A400, LightBlues.A700));
        put(PrimaryColors.CYAN, Arrays.asList(Cyans.A200, Cyans.A400, Cyans.A700));
        put(PrimaryColors.TEAL, Arrays.asList(Teals.A200, Teals.A400, Teals.A700));
        put(PrimaryColors.GREEN, Arrays.asList(Greens.A200, Greens.A400, Greens.A700));
        put(PrimaryColors.LIGHT_GREEN, Arrays.asList(LightGreens.A200, LightGreens.A400, LightGreens.A700));
        put(PrimaryColors.LIME, Arrays.asList(Limes.A200, Limes.A400, Limes.A700));
        put(PrimaryColors.YELLOW, Arrays.asList(Yellows.A200, Yellows.A400, Yellows.A700));
        put(PrimaryColors.AMBER, Arrays.asList(Ambers.A200, Ambers.A400, Ambers.A700));
        put(PrimaryColors.ORANGE, Arrays.asList(Oranges.A200, Oranges.A400, Oranges.A700));
        put(PrimaryColors.DEEP_ORANGE, Arrays.asList(DeepOranges.A200, DeepOranges.A400, DeepOranges.A700));
        put(PrimaryColors.RED, Arrays.asList(Reds.A200, Reds.A400, Reds.A700));
        put(PrimaryColors.PINK, Arrays.asList(Pinks.A200, Pinks.A400, Pinks.A700));
        put(PrimaryColors.PURPLE, Arrays.asList(Purples.A200, Purples.A400, Purples.A700));
        put(PrimaryColors.DEEP_PURPLE, Arrays.asList(DeepPurples.A200, DeepPurples.A400, DeepPurples.A700));
        put(PrimaryColors.INDIGO, Arrays.asList(Indigos.A200, Indigos.A400, Indigos.A700));
    }};

    /**
     * the primary color is selected according to the level
     * secondary shade is randomly selected out of 3
     */
    public static Color getColor(int level) {
        PrimaryColors[] values = PrimaryColors.values();
        PrimaryColors primary = values[(level - 1) % values.length];
        int random = MathUtils.random(0, 2);

        return COLORS.get(primary).get(random);
    }

    static class Blues {
        public static final Color A200 = new Color(0x448AFFFF);
        public static final Color A400 = new Color(0x2979FFFF);
        public static final Color A700 = new Color(0x2962FFFF);
    }

    static class LightBlues {
        public static final Color A200 = new Color(0x40C4FFFF);
        public static final Color A400 = new Color(0x00B0FFFF);
        public static final Color A700 = new Color(0x0091EAFF);
    }

    static class Cyans {
        public static final Color A200 = new Color(0x18FFFFFF);
        public static final Color A400 = new Color(0x00E5FFFF);
        public static final Color A700 = new Color(0x00B8D4FF);
    }

    static class Teals {
        public static final Color A200 = new Color(0x64FFDAFF);
        public static final Color A400 = new Color(0x1DE9B6FF);
        public static final Color A700 = new Color(0x00BFA5FF);
    }

    static class Greens {
        public static final Color A200 = new Color(0x69F0AEFF);
        public static final Color A400 = new Color(0x00E676FF);
        public static final Color A700 = new Color(0x00C853FF);
    }

    static class LightGreens {
        public static final Color A200 = new Color(0xB2FF59FF);
        public static final Color A400 = new Color(0x76FF03FF);
        public static final Color A700 = new Color(0x64DD17FF);
    }

    static class Limes {
        public static final Color A200 = new Color(0xEEFF41FF);
        public static final Color A400 = new Color(0xC6FF00FF);
        public static final Color A700 = new Color(0xAEEA00FF);
    }

    static class Yellows {
        public static final Color A200 = new Color(0xFFFF00FF);
        public static final Color A400 = new Color(0xFFEA00FF);
        public static final Color A700 = new Color(0xFFD600FF);
    }

    static class Ambers {
        public static final Color A200 = new Color(0xFFD740FF);
        public static final Color A400 = new Color(0xFFC400FF);
        public static final Color A700 = new Color(0xFFAB00FF);
    }

    static class Oranges {
        public static final Color A200 = new Color(0xFFAB40FF);
        public static final Color A400 = new Color(0xFF9100FF);
        public static final Color A700 = new Color(0xFF6D00FF);
    }

    static class DeepOranges {
        public static final Color A200 = new Color(0xFF6E40FF);
        public static final Color A400 = new Color(0xFF3D00FF);
        public static final Color A700 = new Color(0xDD2C00FF);
    }

    static class Reds {
        public static final Color A200 = new Color(0xFF5252FF);
        public static final Color A400 = new Color(0xFF1744FF);
        public static final Color A700 = new Color(0xD50000FF);
    }

    static class Pinks {
        public static final Color A200 = new Color(0xFF4081FF);
        public static final Color A400 = new Color(0xF50057FF);
        public static final Color A700 = new Color(0xC51162FF);
    }

    static class Purples {
        public static final Color A200 = new Color(0xE040FBFF);
        public static final Color A400 = new Color(0xD500F9FF);
        public static final Color A700 = new Color(0xAA00FFFF);
    }

    static class DeepPurples {
        public static final Color A200 = new Color(0x7C4DFFFF);
        public static final Color A400 = new Color(0x651FFFFF);
        public static final Color A700 = new Color(0x6200EAFF);
    }

    static class Indigos {
        public static final Color A200 = new Color(0x536DFEFF);
        public static final Color A400 = new Color(0x3D5AFEFF);
        public static final Color A700 = new Color(0x304FFEFF);
    }
}