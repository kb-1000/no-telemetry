package de.kb1000.notelemetry;

class CommonUtil {
    static boolean isForge() {
        return classExists("net.minecraftforge.fml.common.Mod") && !classExists("net.fabricmc.loader.api.FabricLoader");
    }

    static boolean classExists(String name) {
        try {
            return NoTelemetryMixinConfigPlugin.class.getClassLoader().loadClass(name) != null;
        } catch (Exception | LinkageError e) {
            return false;
        }
    }

    static boolean minecraftNewerThan(String version) {
        return isForge() ? ForgeUtil.minecraftNewerThan(version) : FabricUtil.minecraftNewerThan(version);
    }
}
