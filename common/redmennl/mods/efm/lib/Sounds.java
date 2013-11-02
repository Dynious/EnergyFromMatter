package redmennl.mods.efm.lib;

import com.pahimar.ee3.lib.Reference;

public class Sounds
{
    private static final String SOUND_RESOURCE_LOCATION = Reference.MOD_ID
            .toLowerCase() + ":";
    
    private static final String SOUND_PREFIX = Reference.MOD_ID.toLowerCase()
            + ":";
    
    public static String[] soundFiles = { SOUND_RESOURCE_LOCATION
            + "soundName.ogg" };
}
