package redmennl.mods.efm.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.lib.Resources;

public class TilePortableHouse extends TileEntity
{
    public int size = 3;
    public int height = 2;
    public String name = "";
    public boolean noDrop = false;
    private boolean breakBlocks;
    int x, y, z;
    
    int[] idArr;
    int[] metaArr;
    int[] xArr;
    int[] yArr;
    int[] zArr;
    int[] hasTag;
    int idNum;
    ItemStack stack;
    
    @Override
    public void updateEntity()
    {
        if (breakBlocks)
        {
            int xTrans = xCoord - (size - 1) / 2 + x;
            int yTrans = yCoord - 1 + y;
            int zTrans = zCoord - (size - 1) / 2 + z;
            
            if (xTrans != xCoord || yTrans != yCoord || zTrans != zCoord)
            {
                writeBlock(xTrans, yTrans, zTrans, x, y, z);
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
                        saveStackInfo();
                        
                        EntityItem entityItem = new EntityItem(worldObj,
                                xCoord, yCoord + 1, zCoord, stack);
                        entityItem.motionX = 0;
                        entityItem.motionY = 0;
                        entityItem.motionZ = 0;
                        worldObj.spawnEntityInWorld(entityItem);
                        
                        noDrop = true;
                        worldObj.destroyBlock(xCoord, yCoord, zCoord, false);
                    }
                }
            }
        }
    }
    
    public void saveBlocks()
    {
        World world = this.getWorldObj();
        
        stack = new ItemStack(this.getBlockType(), 1, 1);
        stack.setTagCompound(new NBTTagCompound());
        
        idNum = 0;
        idArr = new int[size * size * height];
        metaArr = new int[size * size * height];
        xArr = new int[size * size * height];
        yArr = new int[size * size * height];
        zArr = new int[size * size * height];
        hasTag = new int[size * size * height];
        
        world.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
                Resources.MOD_ID + ":saveblocks", 1.0F, 1.0F);
        
        breakBlocks = true;
        /*
         * for (byte x = 0; x < size; x++) { int xTrans = xCoord - (size - 1) /
         * 2 + x;
         * 
         * for (byte y = 0; y < height; y++) { int yTrans = yCoord - 1 + y;
         * 
         * for (byte z = 0; z < size; z++) { int zTrans = zCoord - (size - 1) /
         * 2 + z;
         * 
         * if (xTrans != xCoord || yTrans != yCoord || zTrans != zCoord) {
         * writeBlock(xTrans, yTrans, zTrans, x, y, z); } } } }
         * 
         * saveStackInfo();
         * 
         * world.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D,
         * Resources.MOD_ID + ":saveblocks", 1.0F, 1.0F);
         * 
         * Random rand = new Random(); float rx = rand.nextFloat() * 0.4F +
         * 0.1F; float ry = rand.nextFloat() * 0.4F + 0.1F; float rz =
         * rand.nextFloat() * 0.4F + 0.1F;
         * 
         * EntityItem entityItem = new EntityItem(world, xCoord + rx, yCoord +
         * ry, zCoord + rz, stack); world.spawnEntityInWorld(entityItem);
         * 
         * noDrop = true; world.setBlockToAir(xCoord, yCoord, zCoord);
         */
    }
    
    private void writeBlock(int xTrans, int yTrans, int zTrans, int x, int y,
            int z)
    {
        int id = worldObj.getBlockId(xTrans, yTrans, zTrans);
        int meta = worldObj.getBlockMetadata(xTrans, yTrans, zTrans);
        if (id != 0)
        {
            idArr[idNum] = id;
            metaArr[idNum] = meta;
            xArr[idNum] = x;
            yArr[idNum] = y;
            zArr[idNum] = z;
            TileEntity TE = worldObj.getBlockTileEntity(xTrans, yTrans, zTrans);
            if (TE != null)
            {
                try
                {
                    TE.getClass().getDeclaredMethod("writeToNBT",
                            NBTTagCompound.class);
                    NBTTagList nbttaglist = new NBTTagList();
                    
                    NBTTagCompound nbt = new NBTTagCompound();
                    TE.writeToNBT(nbt);
                    nbttaglist.appendTag(nbt);
                    stack.getTagCompound().setTag(Integer.toString(idNum),
                            nbttaglist);
                    hasTag[idNum] = 1;
                } catch (Exception e)
                {
                    hasTag[idNum] = 0;
                }
                TE.invalidate();
            } else
            {
                hasTag[idNum] = 0;
            }
            
            idNum++;
            
            worldObj.destroyBlock(xTrans, yTrans, zTrans, false);
            
            /*
             * try { Block.blocksList[id].getClass() .getDeclaredMethod(
             * "canPlaceBlockAt", new Class[] { World.class, int.class,
             * int.class, int.class }); world.destroyBlock(xTrans, yTrans,
             * zTrans, false); } catch (Exception e) { }
             */
        }
    }
    
    private void saveStackInfo()
    {
        stack.getTagCompound().setIntArray("idArr", idArr);
        stack.getTagCompound().setIntArray("metaArr", metaArr);
        stack.getTagCompound().setIntArray("xArr", xArr);
        stack.getTagCompound().setIntArray("yArr", yArr);
        stack.getTagCompound().setIntArray("zArr", zArr);
        stack.getTagCompound().setInteger("size", size);
        stack.getTagCompound().setInteger("height", height);
        if (name != "")
        {
            stack.getTagCompound().setString("name", name);
        }
        stack.getTagCompound().setIntArray("hasTag", hasTag);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if (name != "")
        {
            nbt.setString("name", name);
        }
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        name = nbt.getString("name");
    }
}
