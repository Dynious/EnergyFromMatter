package dynious.mods.efm.client.audio;

import dynious.mods.efm.core.helper.LogHelper;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.lib.Sounds;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler
{
    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event)
    {
        // For each custom sound file we have defined in Sounds
        for (String soundFile : Sounds.soundFiles)
        {
            // Try to add the custom sound file to the pool of sounds
            try
            {
                event.manager.addSound(Resources.MOD_ID + ":" + soundFile);
            }
            // If we cannot add the custom sound file to the pool, log the
            // exception
            catch (Exception e)
            {
                LogHelper.warning("Failed loading sound file: " + soundFile);
            }
        }
    }
}
