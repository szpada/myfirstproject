package com.edu4java.android.killthemall;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MenuView extends SurfaceView {
	private MenuThread menuThread;
	private List<MenuButton> buttons = new ArrayList<MenuButton>();
	private String TAG = "MenuView";
	private long lastClick;
	
	public MenuView(Context context){
		super(context);
		menuThread = new MenuThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
               //@Override
               public void surfaceDestroyed(SurfaceHolder holder) {
                      boolean retry = true;
                      menuThread.setRunning(false);
                      while (retry) {
                             try {
                                   menuThread.join();
                                   retry = false;
                             } catch (InterruptedException e) {}
                      }
               }
               //@Override
               public void surfaceCreated(SurfaceHolder holder) {
                      createMenu();
                      menuThread.setRunning(true);
                      menuThread.start();
               }
               //@Override
               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
               	
               }
        });  
	}
	public void createMenu(){
		buttons.add(new MenuButton(buttons, this,100,200,buttonFlag.achievements));
	}
	public void onDraw(Canvas canvas){
		for(int i = buttons.size()-1; i>=0; i--){
			buttons.get(i).onDraw(canvas);
		}
	}
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if(System.currentTimeMillis() - lastClick > 300) {
			lastClick = System.currentTimeMillis();
			for(int i = buttons.size()-1; i>=0; i--){
				if(buttons.get(i).collision(x, y)){
					switch(buttons.get(i).getFlag()){
					case achievements:
						Log.d("MenuView","kliknalem kurwo");
						Intent intnt = new Intent(this.getContext(), GameActivity.class);
						getContext().startActivity(intnt);
						break;
					}
				}
			}
		}
		return true;
	}
}
