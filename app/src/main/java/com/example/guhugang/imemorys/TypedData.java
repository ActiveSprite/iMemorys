package com.example.guhugang.imemorys;

import android.content.Context;
import android.view.View;

/**
 * Created by GuHuGang on 2018/1/24.
 */

public class TypedData extends PhotoUpImageItem{
  public final static int TYPE_BASE=1;
  public final static int TYPE_AI=2;
  public final static int TYPE_PERSON=3;
  public final static int TYPE_LIST=4;
  private int type;
  public TypedData(int type){
      this.type=type;
  }

    public int getType() {
        return type;
    }
}
