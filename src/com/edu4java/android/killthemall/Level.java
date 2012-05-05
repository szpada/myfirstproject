package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;

public class Level {
	private List<Wave> waves = new ArrayList<Wave>();
	
	public Level(level lvl){
			waves.add(new Wave(lvl, army.knight_squad));
		}
}
