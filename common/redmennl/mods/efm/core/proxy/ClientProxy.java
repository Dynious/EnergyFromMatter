package redmennl.mods.efm.core.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import redmennl.mods.efm.client.audio.SoundHandler;
import redmennl.mods.efm.client.renderer.item.ItemEnergyCondenserRenderer;
import redmennl.mods.efm.client.renderer.tileentity.TileEntityEnergyCondenserRenderer;
import redmennl.mods.efm.lib.BlockIds;
import redmennl.mods.efm.tileentity.TileEnergyCondenser;

public class ClientProxy extends CommonProxy
{
    @Override
    public void initRenderingAndTextures()
    {
        MinecraftForgeClient.registerItemRenderer(BlockIds.ENERGY_CONDENSER,
                new ItemEnergyCondenserRenderer());
    }
    
    @Override
    public void initTileEntities()
    {
        super.initTileEntities();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEnergyCondenser.class,
                new TileEntityEnergyCondenserRenderer());
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
        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }
}
