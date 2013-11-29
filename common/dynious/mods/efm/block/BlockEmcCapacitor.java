package dynious.mods.efm.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dynious.mods.efm.EnergyFromMatter;
import dynious.mods.efm.item.ModItems;
import dynious.mods.efm.lib.GuiIds;
import dynious.mods.efm.lib.Resources;
import dynious.mods.efm.lib.Strings;
import dynious.mods.efm.tileentity.TileEmcCapacitor;

public class BlockEmcCapacitor extends BlockContainer
{
    protected BlockEmcCapacitor(int id)
    {
        super(id, Material.iron);
        this.setUnlocalizedName(Strings.RESOURCE_PREFIX
                + Strings.EMC_CAPACITOR_NAME);
        this.setCreativeTab(EnergyFromMatter.tabEFM);
        this.setHardness(5.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEmcCapacitor();
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {
        if (world.isRemote)
            return true;
        else
        {
            TileEmcCapacitor tile = (TileEmcCapacitor) world
                    .getBlockTileEntity(x, y, z);
            if (tile != null)
            {
                ItemStack stack = player.getCurrentEquippedItem();
                if (stack != null && stack.getItem() == ModItems.linker)
                {
                    if (!stack.hasTagCompound())
                    {
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    if (!(stack.getTagCompound().getInteger("tileX") == tile
                            .getCapacitor().xCoord
                            && stack.getTagCompound().getInteger("tileY") == tile
                                    .getCapacitor().yCoord && stack
                            .getTagCompound().getInteger("tileZ") == tile
                            .getCapacitor().zCoord))
                    {
                        stack.getTagCompound().setInteger("tileX",
                                tile.getCapacitor().xCoord);
                        stack.getTagCompound().setInteger("tileY",
                                tile.getCapacitor().yCoord);
                        stack.getTagCompound().setInteger("tileZ",
                                tile.getCapacitor().zCoord);
                        player.sendChatToPlayer(new ChatMessageComponent()
                                .addText("Linker set to EMC Capacitor at: "
                                        + tile.getCapacitor().xCoord + ", "
                                        + tile.getCapacitor().yCoord + ", "
                                        + tile.getCapacitor().zCoord));
                        return true;
                    }
                }
                player.openGui(EnergyFromMatter.instance, GuiIds.EMC_CAPACITOR,
                        world, tile.getCapacitor().xCoord,
                        tile.getCapacitor().yCoord, tile.getCapacitor().zCoord);
            }
            return true;
        }
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
        TileEmcCapacitor tile = (TileEmcCapacitor) world.getBlockTileEntity(x,
                y, z);
        tile.setLinkedCapacitor(tile);
        tile.scanNeighbors();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon(Resources.MOD_ID + ":"
                + this.getUnlocalizedName().substring(9));
    }
}
