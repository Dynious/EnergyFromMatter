package dynious.mods.efm.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.client.gui.inventory.GuiEmcCapacitor;
import dynious.mods.efm.client.gui.inventory.GuiMatterCreator;
import dynious.mods.efm.client.gui.inventory.GuiMatterDistillery;
import dynious.mods.efm.client.gui.inventory.GuiPortableHouse;
import dynious.mods.efm.inventory.ContainerEmcCapacitor;
import dynious.mods.efm.inventory.ContainerMatterCreator;
import dynious.mods.efm.inventory.ContainerMatterDistillery;
import dynious.mods.efm.lib.GuiIds;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.tileentity.TileCropRipener;
import dynious.mods.efm.tileentity.TileEmcCapacitor;
import dynious.mods.efm.tileentity.TileEnergyCreator;
import dynious.mods.efm.tileentity.TileEnergyDistillery;
import dynious.mods.efm.tileentity.TileFluidCreator;
import dynious.mods.efm.tileentity.TileFluidDistillery;
import dynious.mods.efm.tileentity.TileMatterCondenser;
import dynious.mods.efm.tileentity.TileMatterCreator;
import dynious.mods.efm.tileentity.TileMatterDistillery;
import dynious.mods.efm.tileentity.TileMatterSuperheater;
import dynious.mods.efm.tileentity.TilePortableHouse;
import dynious.mods.efm.tileentity.TilePortableHouseDeployer;

public class CommonProxy implements IGuiHandler
{
    public void initRenderingAndTextures()
    {
    }
    
    public void initTileEntities()
    {
        GameRegistry.registerTileEntity(TileMatterDistillery.class,
                Strings.MATTER_DISTILLERY_NAME);
        GameRegistry.registerTileEntity(TileEmcCapacitor.class,
                Strings.EMC_CAPACITOR_NAME);
        GameRegistry.registerTileEntity(TileCropRipener.class,
                Strings.CROP_RIPENER_NAME);
        GameRegistry.registerTileEntity(TileMatterCreator.class,
                Strings.MATTER_CREATOR_NAME);
        GameRegistry.registerTileEntity(TilePortableHouse.class,
                Strings.PORTABLE_HOUSE_NAME);
        GameRegistry.registerTileEntity(TilePortableHouseDeployer.class,
                Strings.PORTABLE_HOUSE_NAME + "Deployer");
        GameRegistry.registerTileEntity(TileFluidDistillery.class,
                Strings.FLUID_DISTILLERY_NAME);
        GameRegistry.registerTileEntity(TileFluidCreator.class,
                Strings.FLUID_CREATOR_NAME);
        GameRegistry.registerTileEntity(TileMatterSuperheater.class,
                Strings.MATTER_SUPERHEATER_NAME);
        GameRegistry.registerTileEntity(TileMatterCondenser.class,
                Strings.MATTER_CONDENSER_NAME);
        
        if (EnergyFromMatter.hasBC || EnergyFromMatter.hasIC2)
        {
            GameRegistry.registerTileEntity(TileEnergyCreator.class,
                    Strings.ENERGY_CREATOR_NAME);
            GameRegistry.registerTileEntity(TileEnergyDistillery.class,
                    Strings.ENERGY_DISTILLERY_NAME);
        }
        if (EnergyFromMatter.hasAE2)
        {
            //GameRegistry.registerTileEntity(TileCreatorInterface.class,
            //        Strings.CREATOR_INTERFACE_NAME);
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
