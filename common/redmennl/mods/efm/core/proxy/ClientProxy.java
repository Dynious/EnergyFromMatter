package redmennl.mods.efm.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.efm.client.audio.CustomSoundManager;
import redmennl.mods.efm.client.audio.SoundHandler;
import redmennl.mods.efm.client.renderer.item.ItemEnergyCondenserRenderer;
import redmennl.mods.efm.client.renderer.item.ItemPortableHouseRenderer;
import redmennl.mods.efm.client.renderer.tileentity.TilePortableHouseRenderer;
import redmennl.mods.efm.lib.BlockIds;
import redmennl.mods.efm.tileentity.TilePortableHouse;
import redmennl.mods.efm.tileentity.TilePortableHouseDeployer;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
    @Override
    public void initRenderingAndTextures()
    {
        MinecraftForgeClient.registerItemRenderer(BlockIds.ENERGY_CONDENSER,
                new ItemEnergyCondenserRenderer());
        MinecraftForgeClient.registerItemRenderer(BlockIds.PORTABLE_HOUSE,
                new ItemPortableHouseRenderer());
    }
    
    @Override
    public void initTileEntities()
    {
        super.initTileEntities();
        ClientRegistry.bindTileEntitySpecialRenderer(TilePortableHouse.class,
                new TilePortableHouseRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(
                TilePortableHouseDeployer.class,
                new TilePortableHouseRenderer());
    }
    
    @Override
    public void initEntities()
    {
        super.initEntities();
        /*
         * RenderingRegistry.registerEntityRenderingHandler(EntityCompanion.class
         * , new RendererEntityCompanion(new ModelCompanion()));
         */
    }
    
    @Override
    public void registerSoundHandler()
    {
        CustomSoundManager.init();
        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }
}
