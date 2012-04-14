package com.edu4java.android.killthemall;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MenuView extends SurfaceView {
	private MenuThread menuThread;
	
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
                      //createSprites();
                      menuThread.setRunning(true);
                      menuThread.start();
               }
               //@Override
               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
               	
               }
        });  
	}
	public void onDraw(Canvas canvas){
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.firemenu);
		canvas.drawBitmap(bmp, 0, 0, null);
	}
}
