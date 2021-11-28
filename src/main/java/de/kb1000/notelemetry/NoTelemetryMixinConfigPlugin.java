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
        if (NoTelemetryMixinConfigPlugin.class.getClassLoader().getResource("net/minecraftforge/fml/common/Mod.class") != null) {
            return "no-telemetry-forge-refmap.json";
        }
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equals("de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin")) {
            return NoTelemetryMixinConfigPlugin.class.getClassLoader().getResource("com/mojang/authlib/yggdrasil/response/UserAttributesResponse$Privileges.class") != null;
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
