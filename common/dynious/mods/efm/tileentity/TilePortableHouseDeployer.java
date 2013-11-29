package dynious.mods.efm.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;
import dynious.mods.efm.client.audio.CustomSoundManager;
import dynious.mods.efm.client.audio.ICulledSoundPlayer;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.network.PacketTypeHandler;
import dynious.mods.efm.network.packet.PacketSetBlockContainer;
import dynious.mods.efm.network.packet.PacketSetTileNBT;
import dynious.mods.efm.network.packet.PacketSoundCullEvent;
import dynious.mods.efm.network.packet.PacketSoundEvent;

public class TilePortableHouseDeployer extends TileEntity implements
        ICulledSoundPlayer
{
    public int[] idArr;
    public byte[] metaArr;
    public int[] xArr;
    public int[] yArr;
    public int[] zArr;
    public byte size;
    public byte height;
    public String name;
    public boolean clearArea = false;
    public NBTTagCompound tag;
    
    public boolean noDrop = false;
    
    int x, y, z;
    int currentBlock;
    private boolean startDeploy;
    private boolean deploy;
    
    private String soundsource;
    
    @Override
    public void updateEntity()
    {
        if (!this.getWorldObj().isRemote && startDeploy)
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
        if (!this.getWorldObj().isRemote && deploy)
        {
            int xStart = xCoord - (size - 1) / 2;
            int zStart = zCoord - (size - 1) / 2;
            
            if (idArr[currentBlock] != 0
                    && Block.blocksList[idArr[currentBlock]].canPlaceBlockAt(
                            worldObj, 0, 255, 0))
            {
                worldObj.destroyBlock(xStart + xArr[currentBlock], yCoord - 1
                        + yArr[currentBlock], zStart + zArr[currentBlock], true);
                worldObj.setBlock(xStart + xArr[currentBlock], yCoord - 1
                        + yArr[currentBlock], zStart + zArr[currentBlock],
                        idArr[currentBlock], metaArr[currentBlock], 2);
                
                TileEntity TE = worldObj.getBlockTileEntity(xStart
                        + xArr[currentBlock], yCoord - 1 + yArr[currentBlock],
                        zStart + zArr[currentBlock]);
                if (TE != null)
                {
                    NBTTagList nbttaglist = tag.getTagList(Integer
                            .toString(currentBlock));
                    if (nbttaglist != null)
                    {
                        NBTTagCompound nbt = (NBTTagCompound) nbttaglist
                                .tagAt(0);
                        nbt.setInteger("x", xStart + xArr[currentBlock]);
                        nbt.setInteger("y", yCoord - 1 + yArr[currentBlock]);
                        nbt.setInteger("z", zStart + zArr[currentBlock]);
                        TE.readFromNBT(nbt);
                        PacketDispatcher
                                .sendPacketToAllAround(
                                        TE.xCoord,
                                        TE.yCoord,
                                        TE.zCoord,
                                        64D,
                                        worldObj.provider.dimensionId,
                                        PacketTypeHandler
                                                .populatePacket(new PacketSetBlockContainer(
                                                        TE.xCoord,
                                                        TE.yCoord,
                                                        TE.zCoord,
                                                        TE.getBlockType().blockID,
                                                        TE.getBlockMetadata(),
                                                        nbt)));
                    }
                }
            }
            if (currentBlock < idArr.length - 1)
            {
                currentBlock++;
            } else
            {
                for (int i = 0; i < idArr.length; i++)
                {
                    Block block = Block.blocksList[idArr[i]];
                    if (block != null
                            && !block.canPlaceBlockAt(worldObj, 0, 255, 0))
                    {
                        worldObj.destroyBlock(xStart + xArr[i], yCoord - 1
                                + yArr[i], zStart + zArr[i], true);
                        worldObj.setBlock(xStart + xArr[i], yCoord - 1
                                + yArr[i], zStart + zArr[i], idArr[i],
                                metaArr[i], 2);
                    }
                }
                for (int i = 0; i < idArr.length; i++)
                {
                    TileEntity TE = worldObj.getBlockTileEntity(xStart
                            + xArr[i], yCoord - 1 + yArr[i],
                            zStart + zArr[i]);
                    if (TE != null)
                    {
                        NBTTagList nbttaglist = tag.getTagList(Integer
                                .toString(i));
                        if (nbttaglist != null)
                        {
                            if (nbttaglist != null)
                            {
                                NBTTagCompound nbt = (NBTTagCompound) nbttaglist
                                        .tagAt(0);
                                nbt.setInteger("x", xStart + xArr[currentBlock]);
                                nbt.setInteger("y", yCoord - 1 + yArr[currentBlock]);
                                nbt.setInteger("z", zStart + zArr[currentBlock]);
                                TE.readFromNBT(nbt);
                                PacketDispatcher
                                        .sendPacketToAllAround(
                                                TE.xCoord,
                                                TE.yCoord,
                                                TE.zCoord,
                                                64D,
                                                worldObj.provider.dimensionId,
                                                PacketTypeHandler
                                                        .populatePacket(new PacketSetTileNBT(
                                                                TE.xCoord,
                                                                TE.yCoord,
                                                                TE.zCoord,
                                                                nbt)));
                            }
                        }
                    }
                }
                ItemStack stack = new ItemStack(this.getBlockType(), 1, 0);
                EntityItem entityItem = new EntityItem(worldObj, xCoord + 0.5,
                        yCoord + 1, zCoord + 0.5, stack);
                entityItem.motionX = 0;
                entityItem.motionY = 0;
                entityItem.motionZ = 0;
                worldObj.spawnEntityInWorld(entityItem);
                
                noDrop = true;
                PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord,
                        64D, worldObj.provider.dimensionId, PacketTypeHandler
                                .populatePacket(new PacketSoundCullEvent(
                                        xCoord, yCoord, zCoord)));
                worldObj.destroyBlock(xCoord, yCoord, zCoord, false);
            }
        }
    }
    
    public void deploy()
    {
        if (worldObj.isRemote)
        {
            return;
        }
        PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 64D,
                worldObj.provider.dimensionId, PacketTypeHandler
                        .populatePacket(new PacketSoundEvent(Resources.MOD_ID
                                + ":ambience", xCoord, yCoord, zCoord, 1.0F,
                                1.0F)));
        startDeploy = true;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt = tag;
        nbt.setIntArray("idArr", idArr);
        nbt.setByteArray("metaArr", metaArr);
        nbt.setIntArray("xArr", xArr);
        nbt.setIntArray("yArr", yArr);
        nbt.setIntArray("zArr", zArr);
        nbt.setByte("size", size);
        nbt.setByte("height", height);
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
        metaArr = nbt.getByteArray("metaArr");
        xArr = nbt.getIntArray("xArr");
        yArr = nbt.getIntArray("yArr");
        zArr = nbt.getIntArray("zArr");
        size = nbt.getByte("size");
        height = nbt.getByte("height");
        if (nbt.getString("name") != null)
        {
            name = nbt.getString("name");
        }
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
