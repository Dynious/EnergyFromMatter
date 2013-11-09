package redmennl.mods.efm.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import redmennl.mods.efm.EnergyFromMatter;
import redmennl.mods.efm.client.gui.inventory.GuiMatterCreator;
import redmennl.mods.efm.client.gui.inventory.GuiCropRipener;
import redmennl.mods.efm.client.gui.inventory.GuiEmcCapacitor;
import redmennl.mods.efm.client.gui.inventory.GuiMatterDistillery;
import redmennl.mods.efm.client.gui.inventory.GuiPortableHouse;
import redmennl.mods.efm.inventory.ContainerMatterCreator;
import redmennl.mods.efm.inventory.ContainerCropRipener;
import redmennl.mods.efm.inventory.ContainerEmcCapacitor;
import redmennl.mods.efm.inventory.ContainerMatterDistillery;
import redmennl.mods.efm.lib.GuiIds;
import redmennl.mods.efm.tileentity.TileMatterCreator;
import redmennl.mods.efm.tileentity.TileCropRipener;
import redmennl.mods.efm.tileentity.TileEmcCapacitor;
import redmennl.mods.efm.tileentity.TileFluidCondenser;
import redmennl.mods.efm.tileentity.TileFluidDistillery;
import redmennl.mods.efm.tileentity.TileMatterDistillery;
import redmennl.mods.efm.tileentity.TileMatterSuperheater;
import redmennl.mods.efm.tileentity.TilePortableHouse;
import redmennl.mods.efm.tileentity.TilePortableHouseDeployer;
import redmennl.mods.efm.tileentity.TilePowerLink;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler
{
    public void initRenderingAndTextures()
    {
    }
    
    public void initTileEntities()
    {
        GameRegistry.registerTileEntity(TileMatterDistillery.class,
                "matterDistillery");
        GameRegistry.registerTileEntity(TileEmcCapacitor.class, "emcCapacitor");
        GameRegistry.registerTileEntity(TileCropRipener.class, "cropRipener");
        GameRegistry.registerTileEntity(TileMatterCreator.class, "condenser");
        GameRegistry.registerTileEntity(TilePortableHouse.class,
                "portableHouser");
        GameRegistry.registerTileEntity(TilePortableHouseDeployer.class,
                "portableHouserDeployer");
        GameRegistry.registerTileEntity(TileFluidDistillery.class,
                "fluidDistillery");
        GameRegistry.registerTileEntity(TileFluidCondenser.class,
                "fluidCondenser");
        GameRegistry.registerTileEntity(TileMatterSuperheater.class,
                "matterSuperHeater");
        
        if (EnergyFromMatter.hasBC || EnergyFromMatter.hasIC2)
        {
            GameRegistry.registerTileEntity(TilePowerLink.class, "powerLink");
        }
    }
    
    public void initEntities()
    {
        /*
         * EntityRegistry.registerModEntity(EntityCompanion.class, "companion",
         * 0, MiscTools.instance, 80, 1, true);
         */
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        if (ID == GuiIds.MATTER_DISTILLERY)
        {
            TileMatterDistillery tile = (TileMatterDistillery) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerMatterDistillery(player.inventory, tile);
        } else if (ID == GuiIds.EMC_CAPACITOR)
        {
            TileEmcCapacitor tile = (TileEmcCapacitor) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerEmcCapacitor(player.inventory, tile);
        } else if (ID == GuiIds.CROP_RIPENER)
        {
            TileCropRipener tile = (TileCropRipener) world.getBlockTileEntity(
                    x, y, z);
            return new ContainerCropRipener(player.inventory, tile);
        } else if (ID == GuiIds.MATTER_CREATOR)
        {
            TileMatterCreator tile = (TileMatterCreator) world
                    .getBlockTileEntity(x, y, z);
            return new ContainerMatterCreator(player.inventory, tile);
        }
        return null;
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        if (ID == GuiIds.MATTER_DISTILLERY)
        {
            TileMatterDistillery tile = (TileMatterDistillery) world
                    .getBlockTileEntity(x, y, z);
            return new GuiMatterDistillery(player.inventory, tile);
        } else if (ID == GuiIds.EMC_CAPACITOR)
        {
            TileEmcCapacitor tile = (TileEmcCapacitor) world
                    .getBlockTileEntity(x, y, z);
            return new GuiEmcCapacitor(player.inventory, tile);
        } else if (ID == GuiIds.CROP_RIPENER)
        {
            TileCropRipener tile = (TileCropRipener) world.getBlockTileEntity(
                    x, y, z);
            return new GuiCropRipener(player.inventory, tile);
        } else if (ID == GuiIds.MATTER_CREATOR)
        {
            TileMatterCreator tile = (TileMatterCreator) world
                    .getBlockTileEntity(x, y, z);
            return new GuiMatterCreator(player.inventory, tile);
        } else if (ID == GuiIds.PORTABLE_HOUSE)
        {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            return new GuiPortableHouse(tile);
        }
        return null;
    }
    
    public void registerSoundHandler()
    {
    }
    
    public void findModels()
    {
    }
    
    public void registerKeyBindingHandler()
    {
    }
}
