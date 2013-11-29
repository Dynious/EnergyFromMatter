package dynious.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.Player;
import dynious.mods.efm.client.audio.CustomSoundManager;
import dynious.mods.efm.client.audio.ICulledSoundPlayer;
import dynious.mods.efm.network.PacketTypeHandler;

public class PacketSoundEvent extends PacketEFM
{
    
    public String soundName;
    public int x, y, z;
    public float volume, pitch;
    
    public PacketSoundEvent()
    {
        
        super(PacketTypeHandler.SOUND_EVENT, false);
    }
    
    public PacketSoundEvent(String soundName, int x, int y, int z,
            float volume, float pitch)
    {
        
        super(PacketTypeHandler.SOUND_EVENT, false);
        this.soundName = soundName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        
        data.writeUTF(soundName);
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeFloat(volume);
        data.writeFloat(pitch);
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        
        soundName = data.readUTF();
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        volume = data.readFloat();
        pitch = data.readFloat();
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(
                x, y, z);
        if (tile instanceof ICulledSoundPlayer)
        {
            ((ICulledSoundPlayer) tile).setCullSoundSource(CustomSoundManager
                    .playSound(soundName, x + 0.5F, y + 0.5F, z + 0.5F, volume,
                            pitch));
        }
    }
}
