package de.kb1000.notelemetry;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public abstract class NoTelemetryAbstractMixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return switch (mixinClassName) {
            case "de.kb1000.notelemetry.mixin.YggdrasilUserApiServiceMixin" ->
                    !this.minecraftNewerThan("1.18-beta.3");
            case "de.kb1000.notelemetry.mixin.NewYggdrasilUserApiServiceMixin" ->
                    this.minecraftNewerThan("1.18-beta.3");
            case "de.kb1000.notelemetry.mixin.Pre1193TelemetryManagerMixin" ->
                    !this.minecraftNewerThan("1.19.3-alpha.22.46.a");
            case "de.kb1000.notelemetry.mixin.MinecraftClientMixin", "de.kb1000.notelemetry.mixin.OptionsScreenMixin",
                 "de.kb1000.notelemetry.mixin.Post1193TelemetryManagerMixin" -> this.minecraftNewerThan("1.19.3-alpha.22.46.a");
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

    protected abstract boolean minecraftNewerThan(String version);
}
