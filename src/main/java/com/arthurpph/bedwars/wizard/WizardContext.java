package com.arthurpph.bedwars.wizard;

import com.arthurpph.bedwars.generator.GeneratorType;
import org.bukkit.entity.Player;

public record WizardContext(Player player, GeneratorType generatorType) {}
