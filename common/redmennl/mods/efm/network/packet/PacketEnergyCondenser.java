package redmennl.mods.efm.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import redmennl.mods.efm.network.PacketTypeHandler;
import redmennl.mods.efm.tileentity.TileEnergyCondenser;
import cpw.mods.fml.common.network.Player;

public class PacketEnergyCondenser extends PacketEFM
{
    float emcValue;
    int x, y, z;
    
    public PacketEnergyCondenser()
    {
        super(PacketTypeHandler.EMCVALUE, false);
    }
    
    public PacketEnergyCondenser(float emcValue, int x, int y, int z)
    {
        super(PacketTypeHandler.EMCVALUE, false);
        this.emcValue = emcValue;
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
        data.writeFloat(emcValue);
    }
    
    @Override
    public void readData(DataInputStream data) throws IOException
    {
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        emcValue = data.readFloat();
    }
    
    @Override
    public void execute(INetworkManager manager, Player player)
    {
        EntityPlayer thePlayer = (EntityPlayer) player;
        TileEnergyCondenser tile = (TileEnergyCondenser) thePlayer.worldObj
                .getBlockTileEntity(x, y, z);
        if (tile != null)
        {
            tile.storedEMC = emcValue;
        }
    }
}
