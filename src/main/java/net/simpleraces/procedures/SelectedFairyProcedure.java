package net.simpleraces.procedures;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.simpleraces.configuration.SimpleRPGRacesConfiguration;
import net.simpleraces.network.SimpleracesModVariables;

public class SelectedFairyProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Player _player)
			_player.closeContainer();
		if (!(entity.getCapability(SimpleracesModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new SimpleracesModVariables.PlayerVariables())).selected) {
			{
				boolean _setval = true;
				entity.getCapability(SimpleracesModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.fairy = _setval;
					capability.syncPlayerVariables(entity);
				});
				entity.getPersistentData().putInt("fairy_flight_ticks", 0);
			}
			{
				boolean _setval = true;
				entity.getCapability(SimpleracesModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.selected = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			AttributeInstance maxHealthAttr = ((Player) entity).getAttribute(Attributes.MAX_HEALTH);
			if (maxHealthAttr != null) {
				double newMax = SimpleRPGRacesConfiguration.FAIRY_MAX_HEALTH.get();
				maxHealthAttr.setBaseValue(newMax);
				((Player) entity).setHealth((float) newMax);
			}
			if (entity instanceof Player _player && !_player.level().isClientSide()) {
				_player.displayClientMessage(Component.literal("\u00A7e Selected Fairy"), true);
			}
		} else {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal("\u00A74 Class Previously Set"), true);
		}
	}
}
