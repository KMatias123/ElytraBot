package me.bebeli555.ElytraBot.Settings;

import me.bebeli555.ElytraBot.Highway.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoEat {
	//AutoEAT Setting
	static Minecraft mc = Minecraft.getMinecraft();
	int delay = 0;
	static boolean Stop = false;

	@SubscribeEvent
	public void onUpdate(ClientTickEvent e) {
		try {
			if (IsElytrabotEnabled()) {
				if (Settings.getBoolean("AutoEat")) {
					if (ShouldActivate()) {
						if (GetFood() != -1) {
							delay++;
							if (delay > 150) {
								mc.player.inventory.currentItem = GetFood();
								KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
								mc.playerController.processRightClick(mc.player, mc.world, EnumHand.MAIN_HAND);
								delay = 0;
								Stop = true;
							}
						}
					} else {
						// Just a thing that if the event messes up it wont eat all ur food
						if (Stop) {
							KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
							Stop = false;
						}
					}
				}
			}
		} catch (NullPointerException ignored) {
			
		}
	}
	
	//Check if elytrabot is activated
	public static boolean IsElytrabotEnabled() {
		if (Main.isEnabled) {
			return true;
		} else if (Diagonal.isEnabled) {
			return true;
		} else if (Main.baritoneToggle) {
			return true;
		} else if (Diagonal.baritonetoggle) {
			return true;
		}
		return false;
	}
	
	//Get food location in hotbar
	public static int GetFood() {
		//Check for gaps gaps are preferred over other food
		for (int i = 0; i < 9; ++i) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack.getItem() == Items.GOLDEN_APPLE) {
				return i;
			}
			
		}
		
		//Check for other food
		for (int i = 0; i < 9; ++i) {
			final ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack.getItem() instanceof ItemFood && stack.getItem() != Items.CHORUS_FRUIT) {
				return i;
			}
			
		}
		
		return -1;
	}
	
	public static boolean ShouldActivate() {
		float Health = mc.player.getHealth();
		FoodStats Hunger = mc.player.getFoodStats();
		
		if (Health < 16) {
			return true;
		}
		
		if (Hunger.getFoodLevel() < 15) {
			return true;
		}
		return false;
	}
	
	//Stop eating when food is aten
	@SubscribeEvent
	public void StopEating(LivingEntityUseItemEvent.Finish e) {
		if (IsElytrabotEnabled()) {
			if (Settings.getBoolean("AutoEat")) {
				if (Stop) {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
				}
			}
		}
	}
}
