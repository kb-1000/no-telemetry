package de.kb1000.notelemetry;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class NoTelemetryMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        if (classExists("net.minecraftforge.fml.common.Mod")) {
            return "no-telemetry-forge-refmap.json";
        }
        return "no-telemetry-refmap.json";
    }

    private static boolean classExists(String name) {
        try {
            return NoTelemetryMixinConfigPlugin.class.getClassLoader().loadClass(name) != null;
        } catch (Exception | LinkageError e) {
            return false;
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin")) {
            return classExists("com.mojang.authlib.yggdrasil.response.UserAttributesResponse$Privileges");
        }
        if (mixinClassName.equals("de.kb1000.notelemetry.mixin.TelemetrySenderMixin")) {
            return classExists("net.minecraftforge.fml.common.Mod") == targetClassName.equals("net.minecraft.client.ClientTelemetryManager");
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
