package com.example.guhugang.imemorys.com.example.guhugang.imemorys.fragment;

/**
 * Created by GuHuGang on 2017/11/9.
 */

public class ConstantState {
    public  boolean isMulChoice=false;
    public  onStateChangedListener listener;
    public  onEditModeChangeListener modeChangeListener;
    public boolean editMode=false;
    public static ConstantState Instance=new ConstantState();
    private ConstantState(){

    }
    public static ConstantState getInstance(){
          return Instance;
    }
    public  void setState(boolean choice){
        isMulChoice=choice;
        listener.onStateChanged(isMulChoice);
    }
    public void setEditMode(boolean Mode,String path){
        this.editMode=Mode;
        modeChangeListener.onModeChanged(Mode,path);;
    }
    public boolean getEditMode(){
        return editMode;
    }
    public void setonStateChangedListener(onStateChangedListener listener){
        this.listener=listener;
    }
    public interface onStateChangedListener{
        public void onStateChanged(boolean state);

    }
    public void setonEditModeListener(onEditModeChangeListener listener){
        this.modeChangeListener=listener;
    }
    public interface onEditModeChangeListener{
        public void onModeChanged(boolean mode,String path);
    }
}
