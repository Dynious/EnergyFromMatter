package redmennl.mods.efm.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TilePortableHouseDeployer extends TileEntity
{
    public int[] idArr = new int[9 * 2 * 5];
    public int[] metaArr = new int[9 * 2 * 5];
    public int[] xArr = new int[9 * 2 * 5];
    public int[] yArr = new int[9 * 2 * 5];
    public int[] zArr = new int[9 * 2 * 5];
    public int size;
    public int height;
    public String name;
    public boolean clearArea = false;
    public int[] hasTag = new int[9 * 2 * 5];
    public NBTTagCompound tag;
    
    public boolean noDrop = false;
    
    int x, y, z;
    int currentBlock;
    private boolean startDeploy;
    private boolean deploy;
    
    @Override
    public void updateEntity()
    {
        if (startDeploy)
        {
            if (clearArea)
            {
                int xTrans = xCoord - (size - 1) / 2 + x;
                int yTrans = yCoord - 1 + y;
                int zTrans = zCoord - (size - 1) / 2 + z;
                
                if (xTrans != xCoord || yTrans != yCoord || zTrans != zCoord)
                {
                    worldObj.destroyBlock(xTrans, yTrans, zTrans, true);
                }
                
                if (x < size - 1)
                {
                    x++;
                } else
                {
                    x = 0;
                    if (y < height - 1)
                    {
                        y++;
                    } else
                    {
                        y = 0;
                        if (z < size - 1)
                        {
                            z++;
                        } else
                        {
                            z = 0;
                            startDeploy = false;
                            deploy = true;
                        }
                    }
                }
            } else
            {
                startDeploy = false;
                deploy = true;
            }
        }
        if (deploy)
        {
            int xStart = xCoord - (size - 1) / 2;
            int zStart = zCoord - (size - 1) / 2;
            
            if (idArr[currentBlock] != 0)
            {
                worldObj.destroyBlock(xStart + xArr[currentBlock], yCoord - 1
                        + yArr[currentBlock], zStart + zArr[currentBlock], true);
                worldObj.setBlock(xStart + xArr[currentBlock], yCoord - 1
                        + yArr[currentBlock], zStart + zArr[currentBlock],
                        idArr[currentBlock], metaArr[currentBlock], 2);
                if (hasTag[currentBlock] == 1)
                {
                    TileEntity TE = worldObj.getBlockTileEntity(xStart
                            + xArr[currentBlock], yCoord - 1
                            + yArr[currentBlock], zStart + zArr[currentBlock]);
                    
                    NBTTagList nbttaglist = tag.getTagList(Integer
                            .toString(currentBlock));
                    NBTTagCompound nbt = (NBTTagCompound) nbttaglist.tagAt(0);
                    nbt.setInteger("x", xStart + xArr[currentBlock]);
                    nbt.setInteger("y", yCoord - 1 + yArr[currentBlock]);
                    nbt.setInteger("z", zStart + zArr[currentBlock]);
                    TE.readFromNBT(nbt);
                }
            }
            if (currentBlock < idArr.length - 1)
            {
                currentBlock++;
            } else
            {
                ItemStack stack = new ItemStack(this.getBlockType(), 1, 0);
                EntityItem entityItem = new EntityItem(worldObj, xCoord,
                        yCoord + 1, zCoord, stack);
                entityItem.motionX = 0;
                entityItem.motionY = 0;
                entityItem.motionZ = 0;
                worldObj.spawnEntityInWorld(entityItem);
                
                noDrop = true;
                worldObj.destroyBlock(xCoord, yCoord, zCoord, false);
            }
        }
    }
    
    public void deploy()
    {
        startDeploy = true;
        /*
         * World world = this.getWorldObj();
         * 
         * if (clearArea) { for (byte x = 0; x < size; x++) { int xTrans =
         * xCoord - (size - 1) / 2 + x;
         * 
         * for (byte y = 0; y < height; y++) { int yTrans = yCoord - 1 + y;
         * 
         * for (byte z = 0; z < size; z++) { int zTrans = zCoord - (size - 1) /
         * 2 + z;
         * 
         * if (xTrans == xCoord && yTrans == yCoord && zTrans == zCoord) {
         * 
         * } else { world.destroyBlock(xTrans, yTrans, zTrans, true); } } } } }
         * 
         * int xStart = xCoord - (size - 1) / 2; int zStart = zCoord - (size -
         * 1) / 2; for (int i = 0; i < idArr.length; i++) {
         * 
         * if (idArr[i] != 0) { world.destroyBlock(xStart + xArr[i], yCoord - 1
         * + yArr[i], zStart + zArr[i], true); world.setBlock(xStart + xArr[i],
         * yCoord - 1 + yArr[i], zStart + zArr[i], idArr[i], metaArr[i], 2); if
         * (hasTag[i] == 1) { TileEntity TE = world.getBlockTileEntity(xStart +
         * xArr[i], yCoord - 1 + yArr[i], zStart + zArr[i]);
         * 
         * NBTTagList nbttaglist = tag.getTagList(Integer.toString(i));
         * NBTTagCompound nbt = (NBTTagCompound) nbttaglist.tagAt(0);
         * nbt.setInteger("x", xStart + xArr[i]); nbt.setInteger("y", yCoord - 1
         * + yArr[i]); nbt.setInteger("z", zStart + zArr[i]);
         * TE.readFromNBT(nbt); } } }
         * 
         * ItemStack stack = new ItemStack(this.getBlockType(), 1, 0);
         * EntityItem entityItem = new EntityItem(world, xCoord, yCoord, zCoord,
         * stack); world.spawnEntityInWorld(entityItem);
         * 
         * noDrop = true; world.destroyBlock(xCoord, yCoord, zCoord, false);
         */
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt = tag;
        nbt.setIntArray("idArr", idArr);
        nbt.setIntArray("metaArr", metaArr);
        nbt.setIntArray("xArr", xArr);
        nbt.setIntArray("yArr", yArr);
        nbt.setIntArray("zArr", zArr);
        nbt.setInteger("size", size);
        nbt.setInteger("height", height);
        if (name != null)
        {
            nbt.setString("name", name);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        tag = nbt;
        idArr = nbt.getIntArray("idArr");
        metaArr = nbt.getIntArray("metaArr");
        xArr = nbt.getIntArray("xArr");
        yArr = nbt.getIntArray("yArr");
        zArr = nbt.getIntArray("zArr");
        size = nbt.getInteger("size");
        height = nbt.getInteger("height");
        if (nbt.getString("name") != null)
        {
            name = nbt.getString("name");
        }
    }
}
