package redmennl.mods.efm.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.FakePlayerFactory;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

public class TileCropRipener extends TileEmc
{
    /**
     * Time in ticks to finish one ripening
     */
    private int timePerRipening;
    /**
     * Time worked
     */
    private int workTime;
    
    public TileCropRipener()
    {
        super();
        timePerRipening = 100;
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (worldObj.isRemote)
        {
            return;
        }
        TileEmcCapacitor emcCap = getEmcCapacitor();
        if (emcCap != null)
        {
            if (workTime < timePerRipening)
            {
                workTime++;
            } else if (workTime >= timePerRipening)
            {
                doWork(emcCap);
                workTime = 0;
            }
        }
    }
    
    private void doWork(TileEmcCapacitor emcCap)
    {
        for (int x = -4; x <= 4; x++)
        {
            for (int z = -4; z <= 4; z++)
            {
                if (!(Block.blocksList[worldObj.getBlockId(xCoord + x, yCoord,
                        zCoord + z)] == Block.grass))
                {
                    if (ItemDye.applyBonemeal(new ItemStack(Item.dyePowder),
                            worldObj, xCoord + x, yCoord, zCoord + z,
                            FakePlayerFactory.getMinecraft(worldObj)))
                    {
                        emcCap.useEmc(new EmcValue(1.0F, EmcType.ESSENTIA),
                                xCoord, yCoord, zCoord);
                    }
                }
            }
        }
    }
    
    /**
     * Gets the amount of work finished
     * 
     * @return Fraction of time to finish working
     */
    public float getWork()
    {
        return workTime == 0 ? 0F : (float) workTime / (float) timePerRipening;
    }
}
