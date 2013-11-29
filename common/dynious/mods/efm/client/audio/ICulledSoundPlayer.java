package dynious.mods.efm.client.audio;

public interface ICulledSoundPlayer
{
    public void setCullSoundSource(String cullSoundSource);
    
    public void cullSound();
}
