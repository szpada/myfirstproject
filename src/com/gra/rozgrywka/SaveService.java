package com.gra.rozgrywka;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;
import android.util.Log;



public class SaveService {

	private static final String SAVE_FILENAME = "TheSaveFile.ser";

	private SavedState saveData;

	private static SaveService saveService;
	private Activity context;

	private FileOutputStream fOut = null;
	private ObjectOutputStream  osw = null;

	private FileInputStream fin = null;
	private ObjectInputStream sin = null;

	public SaveService(Context context){
		this.context = (Activity)context;
	}

	public static SaveService getInstance(Context context){
		if(saveService == null){
			saveService = new SaveService(context);

		}
		return saveService;
	}

	
	public boolean exists() {
		Log.d("SAVER", context.fileList().toString());
		
		
		return true;
	}
	
	
	
	public  void save(SavedState object){
		try {
			fOut = context.openFileOutput(SAVE_FILENAME, Activity.MODE_PRIVATE);
			osw = new ObjectOutputStream(fOut);
			osw.writeObject(object);
			osw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	
	public SavedState readLastState(){
			try {
				fin = context.openFileInput(SAVE_FILENAME);
				sin = new ObjectInputStream(fin);
				saveData = (SavedState) sin.readObject();
				sin.close();
			} catch (StreamCorruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return saveData;
	}
}

