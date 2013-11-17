package redmennl.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import redmennl.mods.efm.client.audio.ICulledSoundPlayer;
import redmennl.mods.efm.network.PacketTypeHandler;
import cpw.mods.fml.common.network.Player;

public class PacketSoundCullEvent extends PacketEFM
{
    
    public int x, y, z;
    
    public PacketSoundCullEvent()
    {
        
        super(PacketTypeHandler.SOUND_CULL_EVENT, false);
    }
    
    public PacketSoundCullEvent(int x, int y, int z)
    {
        
        super(PacketTypeHandler.SOUND_CULL_EVENT, false);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        TileEntity tile = ((EntityPlayer) player).worldObj.getBlockTileEntity(
                x, y, z);
        if (tile instanceof ICulledSoundPlayer)
        {
            ((ICulledSoundPlayer) tile).cullSound();
        }
    }
}
