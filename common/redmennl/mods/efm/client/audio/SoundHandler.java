package redmennl.mods.efm.client.audio;

import redmennl.mods.efm.core.helper.LogHelper;
import redmennl.mods.efm.lib.Sounds;
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
                event.manager.addSound(soundFile);
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
