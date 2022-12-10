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
        if (CommonUtil.isForge()) {
            return "no-telemetry-forge-refmap.json";
        }
        return "no-telemetry-refmap.json";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case "de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin" ->
                    CommonUtil.classExists("com.mojang.authlib.yggdrasil.response.UserAttributesResponse$Privileges");
            case "de.kb1000.notelemetry.mixin.Post1193TelemetryManagerMixin", "de.kb1000.notelemetry.mixin.Pre1193TelemetryManagerMixin" ->
                    mixinClassName.equals("de.kb1000.notelemetry.mixin.Post1193TelemetryManagerMixin") == CommonUtil.minecraftNewerThan("1.19.3-alpha.22.46.a");
            default -> true;
        };
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
