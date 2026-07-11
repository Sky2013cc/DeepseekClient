package com.deepseek.client.module;

import com.deepseek.client.module.impl.*;
import com.deepseek.client.module.impl.combat.*;
import com.deepseek.client.module.impl.movement.*;
import com.deepseek.client.module.impl.visual.*;
import com.deepseek.client.module.impl.player.*;
import com.deepseek.client.module.impl.world.*;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Combat
        add(new KillAura());
        add(new AimAssist());
        add(new AutoClicker());
        add(new AntiBot());
        add(new AntiKnockback());
        add(new HitBox());
        add(new MoreKB());
        add(new AutoSoup());
        add(new BowAimBot());
        add(new AutoRod());
        add(new BedAura());

        // Movement
        add(new Speed());
        add(new Fly());
        add(new Sprint());
        add(new NoSlowdown());
        add(new NoFall());
        add(new KeepSprint());
        add(new TargetStrafe());
        add(new AntiWeb());
        add(new Eagle());
        add(new AirStuck());

        // Visual
        add(new ESP());
        add(new NameTags());
        add(new FullBright());
        add(new XRay());
        add(new BlockOverlay());
        add(new ItemPhysical());
        add(new StorageESP());
        add(new Tracers());
        add(new Chams());
        add(new CameraNoClip());

        // Player
        add(new Scaffold());
        add(new NoJumpDelay());
        add(new FreeLook());
        add(new AutoTools());
        add(new FastPlace());
        add(new AutoPlace());
        add(new AutoFish());
        add(new ChestSteal());
        add(new Refill());
        add(new InvManager());
        add(new WaterBucket());
        add(new MLG());
        add(new HitSense());
        add(new Auto3rdPerson());

        // World
        add(new Timer());
        add(new AntiFireball());
        add(new BlockAnimation());
        add(new Projectiles());
        add(new FPSBooster());
        add(new TimeChanger());
        add(new Privacy());
        add(new Disabler());
        add(new Blink());
        add(new FakeLag());
        add(new KBBalance());

        // Misc
        add(new HUD());
        add(new GuiModule());
        add(new Rotations());
        add(new IRC());
        add(new Friend());
        add(new HealthBypass());
        add(new Spotify());
        add(new CommandModule());
        add(new LagDetector());
    }

    private void add(Module m) { modules.add(m); }

    public List<Module> getModules() { return modules; }

    public List<Module> getModulesByCategory(Category c) {
        return modules.stream().filter(m -> m.getCategory() == c).toList();
    }

    public Module getModule(String name) {
        return modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void onTick(MinecraftClient client) {
        modules.stream().filter(Module::isEnabled).forEach(Module::onTick);
    }

    public int getEnabledCount() {
        return (int) modules.stream().filter(Module::isEnabled).count();
    }
}
