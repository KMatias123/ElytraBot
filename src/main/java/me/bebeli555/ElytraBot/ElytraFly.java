package me.bebeli555.ElytraBot;

import me.bebeli555.ElytraBot.Highway.Main;
import me.bebeli555.ElytraBot.Settings.Diagonal;
import me.bebeli555.ElytraBot.Settings.Settings;

import net.minecraft.client.Minecraft;

public class ElytraFly {
	static Minecraft mc = Minecraft.getMinecraft();
	public static double FlyMinus = 0;
	public static boolean YCenter = false;
	public static double YMinus = 0;
	
	
	//This controls the elytraflight.
	public static void ElytraFlight() {
		double flySpeed = Settings.getDouble("Speed");
		double glideSpeed = Settings.getDouble("GlideSpeed");
		
		if (Main.isEnabled) {
			if (Main.MoveOn) {
				Main.Flight((flySpeed) - FlyMinus, Main.MoveRight, Main.MoveStraight, -(glideSpeed / 10000f));
			}
		}
		
		if (Diagonal.isEnabled) {
			if (Diagonal.MoveOn) {
				Diagonal.Flight(Diagonal.MoveRight, Diagonal.MoveStraight, -(glideSpeed / 10000f));
			}
		}
		
		if (me.bebeli555.ElytraBot.Overworld.Main.toggle) {
			if (me.bebeli555.ElytraBot.Overworld.Main.MoveOn) {
				me.bebeli555.ElytraBot.Overworld.Main.Flight((flySpeed) - FlyMinus, -(glideSpeed / 10000f), me.bebeli555.ElytraBot.Overworld.Main.MoveDirection);
			}
		}
	}
	
	public static boolean IsElytrabotOn() {
		if (Main.isEnabled) {
			return true;
		}
		
		if (Diagonal.isEnabled) {
			return true;
		}
		
		if (me.bebeli555.ElytraBot.Overworld.Main.toggle) {
			return true;
		}
		
		return false;
	}
}
