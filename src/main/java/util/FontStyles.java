package util;

import java.awt.Font;

public class FontStyles {
    public static final String JETBRAINS_MONO_FONT = "JetBrains Mono";
    public static final String JETBRAINS_MONO_BOLD_FONT = "JetBrains Mono Bold";
    public static final String JETBRAINS_MONO_ITALIC_FONT = "JetBrains Mono Italic";
    public static final String JETBRAINS_MONO_BOLD_ITALIC_FONT = "JetBrains Mono Bold Italic";

    public static final int FONT_SIZE_LARGE = 36;
    public static final int FONT_SIZE_MEDIUM = 24;
    public static final int FONT_SIZE_SMALL = 18;

    private FontStyles() { }

    public static Font getTitleFont() {
        return new Font(JETBRAINS_MONO_FONT, Font.BOLD, FONT_SIZE_LARGE);
    }
}
