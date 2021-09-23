package de.kb1000.notelemetry.mixin;

import com.google.gson.Gson;
import com.mojang.authlib.minecraft.TelemetrySession;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import com.mojang.authlib.yggdrasil.YggdrassilTelemetrySession;
import com.mojang.authlib.yggdrasil.response.PrivilegesResponse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.concurrent.Executor;

@Mixin(YggdrasilUserApiService.class)
public class YggdrasilUserApiServiceMixin {
    @Unique
    private static final PrivilegesResponse.Privileges.Privilege PRIVILEGE_DISABLED = new Gson().fromJson("{\"enabled\": false}", PrivilegesResponse.Privileges.Privilege.class);

    @Redirect(method = "checkPrivileges", at = @At(value = "INVOKE", target = "Lcom/mojang/authlib/yggdrasil/response/PrivilegesResponse$Privileges;getTelemetry()Ljava/util/Optional;"))
    private static Optional<PrivilegesResponse.Privileges.Privilege> getTelemetry(PrivilegesResponse.Privileges privileges) {
        return Optional.of(PRIVILEGE_DISABLED);
    }

    /**
     * @author kb1000
     */
    @Overwrite
    public boolean telemetryAllowed() {
        return false;
    }

    /**
     * @author kb1000
     */
    @Overwrite
    public TelemetrySession newTelemetrySession(final Executor executor) {
        return TelemetrySession.DISABLED;
    }
}
