/**
 * 
 */
package com.gra.rozgrywka;

import com.gra.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * @author Maciej
 *
 *	I jego gowna
 *
 *
 *
//	 * @param context
//	 * @param attrs
//	 */
//	public TreeView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		// TODO Auto-generated constructor stub
//	}
//
//	/**
//	 * @param context
//	 * @param attrs
//	 * @param defStyle
//	 */
//	public TreeView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		// TODO Auto-generated constructor stub
//	}
/**
 *
 *
 *
 *
 *
 */
public class TreeView extends SurfaceView {
	private float h_factor;
	private float w_factor;
	private TreeLoopThread treeLoopThread;
	private Sprite backGround;
	/**
	 * @param context
	 */
	public TreeView(Context context,double w_factor, double h_factor) {
		super(context);
        this.h_factor = (float)h_factor;
 	   	this.w_factor = (float)w_factor;
 	   	 
        treeLoopThread = new TreeLoopThread(this);
        getHolder().addCallback(new SurfaceHolder.Callback() {
               //@Override
               public void surfaceDestroyed(SurfaceHolder holder) {
               Log.d("GameView", "odpalam surfaceDestroyed");
                      boolean retry = true;
                      treeLoopThread.setRunning(false);
                      while (retry) {
                             try {
                                   treeLoopThread.join();
                                   retry = false;
                             } catch (InterruptedException e) {}
                      }
               }
               //@Override
               public void surfaceCreated(SurfaceHolder holder) {
                      createSprites();
                      treeLoopThread.setRunning(true);
                      treeLoopThread.start();
               }
               //@Override
               public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
               	
               }
        }); 
	}
	
	public void createSprites(){
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.panel3);
		this.backGround = new Sprite(this,100, 20, bmp, "olymp", 1);
	}
	public void onDraw(Canvas canvas){
		this.backGround.onDraw(canvas);
	}

}
