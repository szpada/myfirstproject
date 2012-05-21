package com.gra.zapisy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import android.app.Activity;
import android.content.Context;



public class SaveService {

	private static final String SAVE_FILENAME = "SaveFile.ser";

	private SavedState saveData = null;

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

	
	public String[] existing() {
		return context.fileList();
		
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
	
	public  void saveNum(SavedState object, int num1, int num2){
		try {
			String name = "SaveN" + Integer.toString(num1) + "L" + Integer.toString(num2)+".ser";
			fOut = context.openFileOutput(name, Activity.MODE_PRIVATE);
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
	
	
	public SavedState readNumState(int num1, int num2){
		try {
			String name = "SaveN" + Integer.toString(num1) + "L" + Integer.toString(num2)+".ser";
			fin = context.openFileInput(name);
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

