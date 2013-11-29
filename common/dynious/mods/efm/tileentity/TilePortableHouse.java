package dynious.mods.efm.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import dynious.mods.efm.client.audio.CustomSoundManager;
import dynious.mods.efm.client.audio.ICulledSoundPlayer;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.network.PacketTypeHandler;
import dynious.mods.efm.network.packet.PacketSoundCullEvent;
import dynious.mods.efm.network.packet.PacketSoundEvent;

public class TilePortableHouse extends TileEntity implements ICulledSoundPlayer
{
    public int size = 3;
    public int height = 2;
    public String name = "";
    public boolean noDrop = false;
    private boolean breakBlocks;
    private int x, y, z;
    
    private int[] idArr;
    private byte[] metaArr;
    private int[] xArr;
    private int[] yArr;
    private int[] zArr;
    private int idNum;
    private ItemStack stack;
    
    private String soundsource;
    
    @Override
    public void updateEntity()
    {
        if (!this.getWorldObj().isRemote && breakBlocks)
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
                                xCoord + 0.5, yCoord + 1, zCoord + 0.5, stack);
                        entityItem.motionX = 0;
                        entityItem.motionY = 0;
                        entityItem.motionZ = 0;
                        worldObj.spawnEntityInWorld(entityItem);
                        
                        noDrop = true;
                        PacketDispatcher
                                .sendPacketToAllAround(
                                        xCoord,
                                        yCoord,
                                        zCoord,
                                        64D,
                                        worldObj.provider.dimensionId,
                                        PacketTypeHandler
                                                .populatePacket(new PacketSoundCullEvent(
                                                        xCoord, yCoord, zCoord)));
                        worldObj.destroyBlock(xCoord, yCoord, zCoord, false);
                    }
                }
            }
        }
    }
    
    public void saveBlocks()
    {
        World world = this.getWorldObj();
        if (world.isRemote)
        {
            return;
        }
        PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 64D,
                worldObj.provider.dimensionId, PacketTypeHandler
                        .populatePacket(new PacketSoundEvent(Resources.MOD_ID
                                + ":ambience", xCoord, yCoord, zCoord, 1.0F,
                                1.0F)));
        
        stack = new ItemStack(this.getBlockType(), 1, 1);
        stack.setTagCompound(new NBTTagCompound());
        
        idNum = 0;
        idArr = new int[size * size * height];
        metaArr = new byte[size * size * height];
        xArr = new int[size * size * height];
        yArr = new int[size * size * height];
        zArr = new int[size * size * height];
        
        for (byte x = 0; x < size; x++)
        {
            int xTrans = xCoord - (size - 1) / 2 + x;
            
            for (byte y = 0; y < height; y++)
            {
                int yTrans = yCoord - 1 + y;
                
                for (byte z = 0; z < size; z++)
                {
                    int zTrans = zCoord - (size - 1) / 2 + z;
                    
                    if (xTrans != xCoord || yTrans != yCoord
                            || zTrans != zCoord)
                    {
                        Block block = Block.blocksList[worldObj.getBlockId(
                                xTrans, yTrans, zTrans)];
                        if (block != null
                                && !block.canPlaceBlockAt(world, 0, 255, 0))
                        {
                            writeBlock(xTrans, yTrans, zTrans, x, y, z);
                        }
                    }
                }
            }
        }
        
        breakBlocks = true;
    }
    
    private void writeBlock(int xTrans, int yTrans, int zTrans, int x, int y,
            int z)
    {
        int id = worldObj.getBlockId(xTrans, yTrans, zTrans);
        byte meta = (byte) worldObj.getBlockMetadata(xTrans, yTrans, zTrans);
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
                NBTTagCompound nbt = new NBTTagCompound();
                TE.writeToNBT(nbt);
                nbt.removeTag("x");
                nbt.removeTag("y");
                nbt.removeTag("z");
                nbt.removeTag("id");
                if (!nbt.hasNoTags())
                {
                    NBTTagList nbttaglist = new NBTTagList();
                    
                    nbttaglist.appendTag(nbt);
                    stack.getTagCompound().setTag(Integer.toString(idNum),
                            nbttaglist);
                }
                TE.invalidate();
            }
            
            idNum++;
            
            worldObj.destroyBlock(xTrans, yTrans, zTrans, false);
        }
    }
    
    private void saveStackInfo()
    {
        stack.getTagCompound().setIntArray("idArr", idArr);
        stack.getTagCompound().setByteArray("metaArr", metaArr);
        stack.getTagCompound().setIntArray("xArr", xArr);
        stack.getTagCompound().setIntArray("yArr", yArr);
        stack.getTagCompound().setIntArray("zArr", zArr);
        stack.getTagCompound().setByte("size", (byte) size);
        stack.getTagCompound().setByte("height", (byte) height);
        if (name != "")
        {
            stack.getTagCompound().setString("name", name);
        }
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
    
    @Override
    public void cullSound()
    {
        CustomSoundManager.playSound(Resources.MOD_ID + ":explosion", xCoord,
                yCoord, zCoord, 1.0F, 1.0F);
        CustomSoundManager.cullSound(soundsource);
    }
    
    @Override
    public void setCullSoundSource(String cullSoundSource)
    {
        soundsource = cullSoundSource;
    }
}
