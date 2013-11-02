package redmennl.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.tileentity.TileEmcCapacitor;

import com.pahimar.ee3.emc.EmcType;

import cpw.mods.fml.common.network.Player;

public class PacketEmcValue extends PacketEFM
{
    float[] emcValues;
    int x, y, z;
    
    public PacketEmcValue()
    {
        super(PacketTypeHandler.EMCVALUE, false);
    }
    
    public PacketEmcValue(float[] emcValues, int x, int y, int z)
    {
        super(PacketTypeHandler.EMCVALUE, false);
        this.emcValues = emcValues;
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
        for (int i = 0; i < EmcType.values().length; i++)
        {
            data.writeFloat(emcValues[i]);
        }
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        emcValues = new float[EmcType.values().length];
        for (int i = 0; i < EmcType.values().length; i++)
        {
            emcValues[i] = data.readFloat();
        }
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        EntityPlayer thePlayer = (EntityPlayer) player;
        TileEmcCapacitor tile = (TileEmcCapacitor) thePlayer.worldObj
                .getBlockTileEntity(x, y, z);
        if (tile != null)
        {
            tile.storedEmc = emcValues;
        }
    }
}
