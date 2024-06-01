package org.bukkit.craftbukkit.v1_20_R5.legacy.reroute;

import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Type;

import java.util.List;

public record RerouteMethodData(String source, Type sourceDesc, Type sourceOwner, String sourceName,
                                boolean staticReroute, Type targetType, String targetOwner, String targetName,
                                List<RerouteArgument> arguments, RerouteReturn rerouteReturn, boolean isInBukkit,
                                @Nullable String requiredCompatibility) {
}
