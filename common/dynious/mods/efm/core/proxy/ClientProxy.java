package dynious.mods.efm.core.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import dynious.mods.efm.client.audio.CustomSoundManager;
import dynious.mods.efm.client.audio.SoundHandler;
import dynious.mods.efm.client.renderer.item.ItemPortableEmcCapacitorRenderer;
import dynious.mods.efm.client.renderer.item.ItemPortableHouseRenderer;
import dynious.mods.efm.client.renderer.tileentity.TilePortableHouseRenderer;
import dynious.mods.efm.lib.BlockIds;
import dynious.mods.efm.lib.ItemIds;
import dynious.mods.efm.tileentity.TilePortableHouse;
import dynious.mods.efm.tileentity.TilePortableHouseDeployer;

public class ClientProxy extends CommonProxy
{
    @Override
    public void initRenderingAndTextures()
    {
        MinecraftForgeClient.registerItemRenderer(BlockIds.PORTABLE_HOUSE,
                new ItemPortableHouseRenderer());
        MinecraftForgeClient.registerItemRenderer(ItemIds.PORTABLE_EMC_CAPACITOR,
                new ItemPortableEmcCapacitorRenderer());
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
