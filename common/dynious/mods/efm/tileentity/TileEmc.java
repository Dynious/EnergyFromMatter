package dynious.mods.efm.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEmc extends TileEntity
{
    /**
     * emcCapacitor coordinates
     */
    public int emcCapX, emcCapY, emcCapZ;
    
    public int maxRange;
    
    public TileEmc()
    {
        super();
        maxRange = 25;
    }
    
    /**
     * Sets the EMC Capcitor position where this block drains from/adds to
     * 
     * @param EMC
     *            Capacitor TileEntity
     */
    public void setEmcCapacitor(int x, int y, int z)
    {
        TileEntity tileEmcCap = worldObj.getBlockTileEntity(x, y, z);
        if (tileEmcCap != null && tileEmcCap instanceof TileEmcCapacitor)
        {
            this.emcCapX = x;
            this.emcCapY = y;
            this.emcCapZ = z;
            if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1,
                        2);
            }
        }
    }
    
    public TileEmcCapacitor getEmcCapacitor()
    {
        TileEntity tile = worldObj
                .getBlockTileEntity(emcCapX, emcCapY, emcCapZ);
        if (tile != null && tile instanceof TileEmcCapacitor)
        {
            if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1,
                        2);
            }
            return (TileEmcCapacitor) tile;
        } else
        {
            if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 1)
            {
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0,
                        2);
            }
        }
        return null;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readFromNBT(nbtTagCompound);
        emcCapX = nbtTagCompound.getInteger("emcCapX");
        emcCapY = nbtTagCompound.getInteger("emcCapY");
        emcCapZ = nbtTagCompound.getInteger("emcCapZ");
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("emcCapX", emcCapX);
        nbtTagCompound.setInteger("emcCapY", emcCapY);
        nbtTagCompound.setInteger("emcCapZ", emcCapZ);
    }
}
