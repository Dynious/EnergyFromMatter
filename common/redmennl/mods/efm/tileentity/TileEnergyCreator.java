package redmennl.mods.efm.tileentity;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.efm.EnergyFromMatter;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;

import com.pahimar.ee3.emc.EmcType;
import com.pahimar.ee3.emc.EmcValue;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.InterfaceList;
import cpw.mods.fml.common.Optional.Method;

@InterfaceList(value = {
        @Interface(iface = "buildcraft.api.power.IPowerEmitter", modid = "BuildCraft|Energy"),
        @Interface(iface = "ic2.api.energy.tile.IEnergyEmitter", modid = "IC2") })
public class TileEnergyCreator extends TileEmc implements IPowerEmitter,
        IEnergyEmitter
{
    public static final int emcMjConversionSize = 250;
    public static final int emcEuConversionSize = 625;
    private TileEntity[] tiles;
    private boolean firstUpdate = true;
    private float usedEMC = 0F;
    private int spawParticleTime = 0;
    
    public TileEnergyCreator()
    {
        super();
        tiles = new TileEntity[ForgeDirection.values().length];
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (worldObj.isRemote)
        {
            return;
        }
        if (firstUpdate)
        {
            scanNeighbors();
            firstUpdate = false;
        }
        
        TileEmcCapacitor emcCap = getEmcCapacitor();
        
        spawParticleTime++;
        if (spawParticleTime >= 20 && emcCap != null)
        {
            emcCap.spawnEmcPartcle(new EmcValue(usedEMC, EmcType.KINETIC),
                    this.xCoord, this.yCoord, this.zCoord, false);
            usedEMC = 0F;
            spawParticleTime = 0;
        }
        
        if (emcCap != null)
        {
            for (int i = 0; i < ForgeDirection.values().length; i++)
            {
                TileEntity tile = tiles[i];
                if (tile != null)
                {
                    if (EnergyFromMatter.hasBC
                            && tile instanceof IPowerReceptor)
                    {
                        PowerReceiver reciever = ((IPowerReceptor) tile)
                                .getPowerReceiver(ForgeDirection
                                        .getOrientation(i).getOpposite());
                        if (reciever != null)
                        {
                            float usedMJ;
                            if (emcCap.getStoredEmc(EmcType.KINETIC)
                                    * emcMjConversionSize < reciever
                                        .powerRequest())
                            {
                                usedMJ = emcCap.getStoredEmc(EmcType.KINETIC)
                                        * emcMjConversionSize;
                            } else
                            {
                                usedMJ = reciever.powerRequest();
                            }
                            if (usedMJ != 0.0F)
                            {
                                if (emcCap
                                        .useEmc(new EmcValue(usedMJ
                                                / emcMjConversionSize,
                                                EmcType.KINETIC)))
                                {
                                    reciever.receiveEnergy(Type.STORAGE,
                                            usedMJ,
                                            ForgeDirection.getOrientation(i));
                                    usedEMC += usedMJ / emcMjConversionSize;
                                }
                            }
                        }
                    } else if (EnergyFromMatter.hasIC2
                            && tile instanceof IEnergySink)
                    {
                        IEnergySink sink = (IEnergySink) tile;
                        if (sink != null
                                & sink.acceptsEnergyFrom(this, ForgeDirection
                                        .getOrientation(i).getOpposite()))
                        {
                            double usedEU;
                            if (emcCap.getStoredEmc(EmcType.KINETIC)
                                    * emcEuConversionSize < sink
                                        .demandedEnergyUnits())
                            {
                                usedEU = emcCap.getStoredEmc(EmcType.KINETIC)
                                        * emcEuConversionSize;
                            } else
                            {
                                usedEU = (float) sink.demandedEnergyUnits();
                            }
                            if (usedEU > sink.getMaxSafeInput())
                            {
                                usedEU = sink.getMaxSafeInput();
                            }
                            if (usedEU != 0.0F)
                            {
                                if (emcCap
                                        .useEmc(new EmcValue((float) usedEU
                                                / emcEuConversionSize,
                                                EmcType.KINETIC)))
                                {
                                    sink.injectEnergyUnits(ForgeDirection
                                            .getOrientation(i).getOpposite(),
                                            usedEU);
                                    usedEMC += usedEU / emcMjConversionSize;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void scanNeighbors()
    {
        for (int i = 0; i < ForgeDirection.values().length; i++)
        {
            ForgeDirection direction = ForgeDirection.getOrientation(i);
            scanNeighbor(direction);
        }
    }
    
    public void scanNeighbor(ForgeDirection direction)
    {
        TileEntity tile;
        switch (direction)
        {
            case EAST:
                tile = worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case WEST:
                tile = worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case UP:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case DOWN:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
                tiles[direction.ordinal()] = tile;
                break;
            case SOUTH:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1);
                tiles[direction.ordinal()] = tile;
                break;
            case NORTH:
                tile = worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1);
                tiles[direction.ordinal()] = tile;
                break;
            default:
                break;
        }
    }
    
    @Method(modid = "BuildCraft|Energy")
    @Override
    public boolean canEmitPowerFrom(ForgeDirection side)
    {
        return true;
    }
    
    @Method(modid = "IC2")
    @Override
    public boolean emitsEnergyTo(TileEntity tile, ForgeDirection direction)
    {
        return tiles[direction.ordinal()] == tile ? true : false;
    }
    
    @Override
    public void validate()
    {
        super.validate();
        if (!worldObj.isRemote && EnergyFromMatter.hasIC2)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        }
    }
    
    @Override
    public void invalidate()
    {
        if (!worldObj.isRemote && EnergyFromMatter.hasIC2)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        }
        
        super.invalidate();
    }
    
    @Override
    public void onChunkUnload()
    {
        if (!worldObj.isRemote && EnergyFromMatter.hasIC2)
        {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        }
        
        super.onChunkUnload();
    }
}
