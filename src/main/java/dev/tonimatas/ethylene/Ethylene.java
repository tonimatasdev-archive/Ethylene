package dev.tonimatas.ethylene;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(Ethylene.MODID)
public class Ethylene {
    public static final String MODID = "ethylene";
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public Ethylene(IEventBus modEventBus) {
        NeoForge.EVENT_BUS.register(this);
    }
}
