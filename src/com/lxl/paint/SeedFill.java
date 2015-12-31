package com.lxl.paint;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;


public class SeedFill {
	Robot rt;
	Stack<Point> stk = new Stack<Point>();
	int dx;
	int dy;

	public void scanSeedFill(JPanel jp, List<Shape> shapes,int x, int y, Color fillColor, List<Color> colors,
	List<Float> storks,List<Integer> msal) {
		try {
			rt = new Robot();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		Point d=jp.getLocationOnScreen();
		dx =d.x;
		dy =d.y;
		Graphics2D g=(Graphics2D) jp.getGraphics();
		g.setColor(fillColor);
		g.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		stk.push(new Point(x, y));
		while (!stk.empty()) {
			Point seed = stk.pop(); // 取当前种子点
			int seedx =seed.x;
			int seedy =seed.y;
			colors.add(fillColor);
			storks.add(1.0f);
			msal.add(0);
			if(!rt.getPixelColor(seedx+dx-1, seedy+dy).equals(fillColor)){
				g.drawLine(seedx-1, seedy, seedx, seedy);
				shapes.add(new Line2D.Double(seedx-1, seedy, seedx, seedy));
				jp.repaint();
				stk.push(new Point(seedx-1,seedy));
			}
			if(!rt.getPixelColor(seedx+dx+1, seedy+dy).equals(fillColor)){
				g.drawLine(seedx+1, seedy, seedx, seedy);
				shapes.add(new Line2D.Double(seedx+1, seedy, seedx, seedy));
				jp.repaint();
				stk.push(new Point(seedx+1,seedy));
			}
			if(!rt.getPixelColor(seedx+dx, seedy+dy-1).equals(fillColor)){
				g.drawLine(seedx, seedy-1, seedx, seedy);
				shapes.add(new Line2D.Double(seedx, seedy-1, seedx, seedy-1));
				jp.repaint();
				stk.push(new Point(seedx,seedy-1));
			}
			if(!rt.getPixelColor(seedx+dx, seedy+dy+1).equals(fillColor)){
				g.drawLine(seedx, seedy+1, seedx, seedy);
				shapes.add(new Line2D.Double(seedx, seedy+1, seedx, seedy));
				jp.repaint();
				stk.push(new Point(seedx,seedy+1));
			}
			
		}
	}

		}
