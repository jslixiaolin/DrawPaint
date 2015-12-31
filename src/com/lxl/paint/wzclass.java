package com.lxl.paint;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;


public class wzclass implements Serializable {
	String wcstring;
	Color wccolor;
	int wcx;
	int wcy;
	Font wcfont;
	 public wzclass(String wcstring,Font wcfont,Color wccolor,int wcx,int wcy)
	 {
		 this.wcstring=wcstring;
		 this.wcfont=wcfont;
		 this.wccolor=wccolor;
		 this.wcx=wcx;
		 this.wcy=wcy;
	 }
}