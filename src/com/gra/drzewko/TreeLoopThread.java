package com.gra.drzewko;

import android.graphics.Canvas;
 
/**
 * @author Maciej
 * watek generujacy drzewko rozwoju
 */

public class TreeLoopThread extends Thread {
       static final long FPS = 5;
       private TreeView view;
       private boolean running = false;

       public TreeLoopThread(TreeView view) {
             this.view = view;
       }

       public void setRunning(boolean run) {
             running = run;
       }
       @Override
       public void run() {
             long ticksPS = 1000 / FPS;
             long startTime;
             long sleepTime;
             while (running) {
                    Canvas c = null;
                    startTime = System.currentTimeMillis();
                    try {
                           c = view.getHolder().lockCanvas();
                           synchronized (view.getHolder()) {
                                  view.onDraw(c);
                           }
                    }
                    catch (Exception e) {
                    	e.printStackTrace();
                    }
                    finally {
                           if (c != null) {
                                  view.getHolder().unlockCanvasAndPost(c);
                           }
                    }
                    sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
                    try {
                           if (sleepTime > 0)
                                  sleep(sleepTime);
                           else
                                  sleep(10);
                    } catch (Exception e) {
                    	e.printStackTrace();
                    }
             }
       }
}