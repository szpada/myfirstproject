package com.gra.rozgrywka;

import com.gra.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class InfoSprite {
	private TreeView treeView;
	private int x;
	private int y;
	private Bitmap windowBitmap;
	private Bitmap animationBitmap;
	private attackType attack;
	
	public InfoSprite(TreeView treeView, int x, int y, attackType attack){
		this.treeView = treeView;
		this.x = x;
		this.y = y;
		this.attack = attack;
		this.windowBitmap  = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.electriccircle);
		switch(attack){
		case charge_defence:
			this.animationBitmap = BitmapFactory.decodeResource(treeView.getResources(), R.drawable.electriccircle);
			break;
		}
		
	}
	public void onDraw(Canvas canvas){
		
	}
}
