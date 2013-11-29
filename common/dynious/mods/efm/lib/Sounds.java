package dynious.mods.efm.lib;

import com.pahimar.ee3.lib.Reference;

public class Sounds
{
    private static final String SOUND_PREFIX = Reference.MOD_ID.toLowerCase()
            + ":";
    
    public static String[] soundFiles = { "saveblocks.ogg", "ambience.ogg",
            "explosion.ogg" };
    
    public static final String SOUND_PORTABLEHOUSE = SOUND_PREFIX
            + "saveBlocks";
}
