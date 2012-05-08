package com.gra.rozgrywka;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.gra.R;
 
/**
 * @author Maciej
 * guzik na dole ekranu, moze sluzyc do przelaczania bogow, itd
 * 
 */
public class Switcher {
	private Player player;
	private GameView gameView;	//widok gry
	private boolean godORattack; //true god false = attack
	private Bitmap bmp;
	private int currentAttack;
	private int currentGod;
	private int width = 128;
	private int height = 128;
	private int x;
	private int y;
	
	/*
	 * konstruktor dla gameView
	 */
	public Switcher(Player player,GameView gameView, boolean godORattack, int x, int y){
		this.player = player;
		this.godORattack = godORattack;
		this.gameView = gameView;
		if(this.godORattack){
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.onlythreegods1);
		}
		else if(!this.godORattack){
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.attacksonlythree2);
		}
		this.x = x;
		this.y = y;
	}
	public void onDraw(Canvas canvas){
		int srcX = 0;
		int srcY = 0;
		this.currentGod = this.player.getCurrentGod();
		this.currentAttack = this.player.getCurrentAttack();
		if(this.godORattack){
			srcX = this.width * this.currentGod;
		}
        if(!this.godORattack){
        	srcX = this.width * this.currentAttack;
        	srcY = this.height * this.currentGod;
        }
        Rect src = new Rect(srcX, srcY, srcX + this.width, srcY + this.height);
        Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y +this.height);
        //TEST SKALOWANIA
        //Rect dst = new Rect(this.x, this.y, this.x + (int)(this.width * w_factor), this.y + (int)(this.height * h_factor));
        //canvas.drawB
        canvas.drawBitmap(this.bmp, src, dst, null);
	}
	public void onClick(){
		if(this.godORattack){
			//lec po liscie bogow ktorych player posiada
			player.nextGod();
		}
		if(!this.godORattack){
			//lec po liscie atakow ktore posiada gracz (w ramach danego boga);
			player.nextAttack();
		}
	}
	public boolean collision(int x, int y){

		Rect dst = new Rect(this.x, this.y, this.x + this.width,this.y + this.height);

		if(dst.contains(x, y)){
			onClick();
			return true;
		}
		return false;
	}
}
