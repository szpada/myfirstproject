package com.gra.rozgrywka;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.gra.R;

enum buttonFlag{continue_game, new_game, tree, achievements, quit}

public class MenuButton {
	private MenuView view;
	private	int x;
	private int y;
	private buttonFlag flag;
	private int width;
	private int height;
	private Bitmap bmp;
	private List<MenuButton> buttons;
	private String TAG = "MenuButton";
	
	public MenuButton(List<MenuButton> buttons, MenuView view, int x, int y, buttonFlag flag){
		this.buttons = buttons;
		this.view = view;
		this.x = x;
		this.y = y;
		this.flag = flag;
		switch(flag){
		case achievements:
			this.bmp = BitmapFactory.decodeResource(this.view.getResources(), R.drawable.firewall);
			this.width = bmp.getWidth();
			this.height = bmp.getHeight();
			break;
		}
	}
	public void onDraw(Canvas canvas){
		Rect src = new Rect(0, 0, this.width, this.height);
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		canvas.drawBitmap(this.bmp, src, dst, null);
	}
	public buttonFlag getFlag(){
		return this.flag;
	}
	public boolean collision(int x, int y){
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		if(dst.contains(x, y)){
			return true;
		}
		return false;
	}
}
