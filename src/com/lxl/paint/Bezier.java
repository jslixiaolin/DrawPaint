package com.lxl.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Bezier {
	double b03;
	double b13;
	double b23;
	double b33;

	public void drawBezierLine(JPanel jp, List<Shape> shapes,
			List<Point> points, Color fillColor, List<Color> colors,float stork, List<Float> storks,
			List<Integer> msal) {
		Point p0 = points.get(0);
		Point p1 = points.get(1);
		Point p2 = points.get(2);
		Point p3 = points.get(3);
		Graphics2D g = (Graphics2D) jp.getGraphics();
		g.setColor(fillColor);
		g.setStroke(new BasicStroke(stork,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		for (int i = 0; i < 3; i++) {
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x,
					points.get(i + 1).y);
			shapes.add(new Line2D.Double(points.get(i).x, points.get(i).y,
					points.get(i + 1).x, points.get(i + 1).y));
			colors.add(fillColor);
			storks.add(1.0f);
			msal.add(0);
			jp.repaint();
		}
		for (double t = 0.01; t < 1.0001; t += 0.01) {
			b03 = (1 - t) * (1 - t) * (1 - t);
			b13 = 3 * t * (1 - t) * (1 - t);
			b23 = 3 * t * t * (1 - t);
			b33 = t * t * t;
			int x = (int) (b03 * p0.x + b13 * p1.x + b23 * p2.x + b33 * p3.x + 0.5);
			int y = (int) (b03 * p0.y + b13 * p1.y + b23 * p2.y + b33 * p3.y + 0.5);
			g.drawLine(p0.x, p0.y, x, y);
			shapes.add(new Line2D.Double(p0.x, p0.y, x, y));
			colors.add(Color.red);
			storks.add(1.0f);
			msal.add(0);
			jp.repaint();
			p0.move(x, y);
		}
	}

	public void deCasteljau(JPanel jp, List<Shape> shapes, List<Point> points,
			Color fillColor, List<Color> colors,float stork,List<Float> storks, List<Integer> msal) {
		Graphics2D g = (Graphics2D) jp.getGraphics();
		g.setColor(fillColor);
		g.setStroke(new BasicStroke(stork,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		int n = points.size();
		Point p0 = points.get(0);
		ArrayList<Integer> xx = new ArrayList<Integer>();
		ArrayList<Integer> yy = new ArrayList<Integer>();
		// for(int i=0;i<n-1;i++){
		// g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x,
		// points.get(i+1).y);
		// shapes.add(new Line2D.Double(points.get(i).x, points.get(i).y,
		// points.get(i+1).x, points.get(i+1).y));
		// colors.add(fillColor);
		// msal.add(0);
		// jp.repaint();
		// }
		for (double t = 0.01; t < 1.001; t += 0.01) {
			for (int i = 0; i < n; i++) {
				xx.add(points.get(i).x);
				yy.add(points.get(i).y);
			}
			for (int k = 1; k < n - 1; k++) {
				for (int i = 0; i <= n - 1 - k; i++) {
					int x = (int) (xx.get(i) * (1 - t) + xx.get(i + 1) * t + 0.5);
					int y = (int) (yy.get(i) * (1 - t) + yy.get(i + 1) * t + 0.5);
					xx.set(i, x);
					yy.set(i, y);

				}
			}
			g.drawLine(p0.x, p0.y, xx.get(0), yy.get(0));
			shapes.add(new Line2D.Double(p0.x, p0.y, xx.get(0), yy.get(0)));
			colors.add(Color.red);
			storks.add(stork);
			msal.add(0);
			jp.repaint();
			p0.move(xx.get(0), yy.get(0));
		}
	}
}
