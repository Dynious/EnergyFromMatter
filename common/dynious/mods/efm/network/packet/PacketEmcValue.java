package dynious.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import cpw.mods.fml.common.network.Player;
import dynious.mods.efm.network.PacketTypeHandler;
import dynious.mods.efm.tileentity.TileEmcCapacitor;

public class PacketEmcValue extends PacketEFM
{
    float[] emcValues;
    int linkedCapacitors;
    int x, y, z;
    
    public PacketEmcValue()
    {
        super(PacketTypeHandler.EMCVALUE, false);
    }
    
    public PacketEmcValue(float[] emcValues, int linkedCapacitors, int x,
            int y, int z)
    {
        super(PacketTypeHandler.EMCVALUE, false);
        this.emcValues = emcValues;
        this.linkedCapacitors = linkedCapacitors;
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
        data.writeInt(linkedCapacitors);
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
        linkedCapacitors = data.readInt();
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        EntityPlayer thePlayer = (EntityPlayer) player;
        TileEmcCapacitor tile = (TileEmcCapacitor) thePlayer.worldObj
                .getBlockTileEntity(x, y, z);
        if (tile != null)
        {
            tile.setEmc(new EmcValue(emcValues));
            tile.linkedCapacitors = linkedCapacitors;
        }
    }
}
