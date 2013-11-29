package dynious.mods.efm.lib;

public class Reference
{
    /* Debug Mode On-Off */
    public static final boolean DEBUG_MODE = false;
    /* General Mod related constants */
    public static final String MOD_ID = "EFM";
    public static final String MOD_NAME = "Energy From Matter";
    public static final String VERSION_NUMBER = "@VERSION@ (build @BUILD_NUMBER@)";
    public static final String CHANNEL_NAME = MOD_ID;
    public static final String DEPENDENCIES = "required-after:Forge@[9.10.1.849,);required-after:EE3";
    public static final String FINGERPRINT = "@FINGERPRINT@";
    public static final int SECOND_IN_TICKS = 20;
    public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
    public static final String SERVER_PROXY_CLASS = "dynious.mods.efm.core.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "dynious.mods.efm.core.proxy.ClientProxy";
}