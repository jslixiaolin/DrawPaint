package com.lxl.paint;
import java.awt.Color;
import java.awt.Shape;
import java.io.Serializable;


public class shapeclass implements Serializable {
	Shape scshape;
	Color sccolor;
	float stork;
	int scms;
	 public shapeclass(Shape scshape,Color sccolor,int scms,float stork)
	 {
		 this.scshape=scshape;
		 this.sccolor=sccolor;
		 this.scms=scms;
		 this.stork=stork;
		 }
}
