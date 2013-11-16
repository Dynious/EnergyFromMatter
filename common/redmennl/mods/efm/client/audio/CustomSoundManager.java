package redmennl.mods.efm.client.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;

public class CustomSoundManager
{
    private static SoundManager sndManager = null;
    private static int latestSoundID = 255;
    
    public static void init()
    {
        sndManager = Minecraft.getMinecraft().sndManager;
    }
    /**
     * Plays a sound. Args: soundName, x, y, z, volume, pitch, fadeOutTime
     */
    public static String playSound(String par1Str, float par2, float par3, float par4, float par5, float par6)
    {
        SoundPoolEntry soundpoolentry = sndManager.soundPoolSounds.getRandomSoundFromSoundPool(par1Str);
        soundpoolentry = SoundEvent.getResult(new PlaySoundEvent(sndManager, soundpoolentry, par1Str, par2, par3, par4, par5, par6));
        
        if (soundpoolentry != null && par5 > 0.0F)
        {
            latestSoundID = 256 + ((latestSoundID + 1) % 256);
            String s1 = "sound_" + latestSoundID;
            float f5 = 16.0F;

            if (par5 > 1.0F)
            {
                f5 *= par5;
            }

            sndManager.sndSystem.newSource(par5 > 1.0F, s1, soundpoolentry.getSoundUrl(), soundpoolentry.getSoundName(), false, par2, par3, par4, 2, f5);

            if (par5 > 1.0F)
            {
                par5 = 1.0F;
            }

            sndManager.sndSystem.setPitch(s1, par6);
            sndManager.sndSystem.setVolume(s1, par5 * Minecraft.getMinecraft().gameSettings.soundVolume);
            MinecraftForge.EVENT_BUS.post(new PlaySoundSourceEvent(sndManager, s1, par2, par3, par4));
            sndManager.sndSystem.play(s1);
            return s1;
        }
        return null;
    }
    
    public static void cullSound(String sourcename)
    {
        sndManager.sndSystem.cull(sourcename);
    }
}
