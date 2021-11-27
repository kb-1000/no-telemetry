# No Telemetry

Download from [Curseforge](https://curseforge.com/minecraft/mc-mods/no-telemetry/) or [Modrinth](https://github.com/kb-1000/no-telemetry).

Disables the pseudonymous diagnostics data collection, aka telemetry, introduced in Minecraft 1.18 [(snapshot 21w38a)](https://www.minecraft.net/en-us/article/minecraft-snapshot-21w38a#main-content).

The feature previously existed from Minecraft 1.3.1 to 1.12 as [Snooper](https://minecraft.fandom.com/wiki/Snooper), but was removed in 1.13 [(snapshot 18w21a)](https://bugs.mojang.com/browse/MC-130179) as it did not comply with European Union's General Data Protection Regulations. 

In 1.18 it was re-added in a compliant and presumably more private way, and it would now send the following data on loading singleplayer world or connecting to multiplayer server:

* launcher identifier
* user identitifer (XUID)
* client session id (changes on restart)
* world session id (changes per world load, to be reused for later events)
* game version
* operating system name and version
* Java runtime version
* if client or server is modded (same information as on crash logs)
* server type (single player, Realms or other)
* game mode

This time [Mojang did not add a toggle](https://bugs.mojang.com/browse/MC-237493) for interested users to turn it off, though, so this mod fixes that by disabling the telemetry entirely. 

## License

This mod is available under the CC0 license.
