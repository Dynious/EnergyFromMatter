package redmennl.mods.efm.lib;

import net.minecraft.util.ResourceLocation;

public class Resources
{
    public static final String MOD_ID = Reference.MOD_ID.toLowerCase();
    public static final String MODEL_LOCATION = "/assets/efm/models/";
    public static final String GUI_SHEET_LOCATION = "textures/gui/";
    public static final String ENTITY_SHEET_LOCATION = "textures/entities/";
    public static final String MODEL_SHEET_LOCATION = "textures/models/";
    public static final String ARMOR_SHEET_LOCATION = "textures/armor/";
    
    public static final ResourceLocation GUI_ENERGY_CONDENSER = new ResourceLocation(
            MOD_ID, GUI_SHEET_LOCATION + "energyCondenser.png");
    public static final ResourceLocation GUI_MATTER_DISTILLERY = new ResourceLocation(
            MOD_ID, GUI_SHEET_LOCATION + "matterDistillery.png");
    public static final ResourceLocation GUI_EMC_CAPACITOR = new ResourceLocation(
            MOD_ID, GUI_SHEET_LOCATION + "emcCapacitor.png");
    public static final ResourceLocation GUI_PORTABLEHOUSE = new ResourceLocation(
            MOD_ID, GUI_SHEET_LOCATION + "portableHouse.png");
    
    public static final ResourceLocation MODEL_ENERGY_CONDENSER = new ResourceLocation(
            MOD_ID, MODEL_SHEET_LOCATION + "energyCondenser.png");
    public static final ResourceLocation MODEL_PORTABLEHOUSE = new ResourceLocation(
            MOD_ID, MODEL_SHEET_LOCATION + "portableHouse.png");
}