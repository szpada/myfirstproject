package com.edu4java.android.killthemall;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;


/**
 * @author Maciej
 * guzik na dole ekranu, moze sluzyc do przelaczania bogow, itd
 */
public class Switcher {
	private Player player;
	private GameView gameView;
	private boolean godORattack; //true god false = attack
	private Bitmap bmp;
	private int currentAttack;	//klatki na szerokosc
	private int currentGod;		//klatki na wysokosc
	private int width = 64;
	private int height = 64;
	private int x;
	private int y;
	
	public Switcher(Player player,GameView gameView, boolean godORattack, int x, int y){
		this.player = player;
		this.godORattack = godORattack;
		this.gameView = gameView;
		if(this.godORattack){
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.gods);
		}
		else if(!this.godORattack){
			this.bmp = BitmapFactory.decodeResource(this.gameView.getResources(), R.drawable.attacks);
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
        Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
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
		Rect dst = new Rect(this.x, this.y, this.x + this.width, this.y + this.height);
		if(dst.contains(x, y)){
			onClick();
			return true;
		}
		return false;
	}
}
