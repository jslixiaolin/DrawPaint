package com.lxl.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.Stack;

import javax.swing.JPanel;

public class SeedLineFill {
	Robot rt;
	Stack<Point> stk = new Stack<Point>();
	int dx;
	int dy;

	public void scanLineSeedFill(JPanel jp, List<Shape> shapes, int x, int y,
			Color fillColor, List<Color> colors, List<Float> storks,
			List<Integer> msal) {
		try {
			rt = new Robot();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		Point d = jp.getLocationOnScreen();
		dx = d.x;
		dy = d.y;
		Graphics2D g = (Graphics2D) jp.getGraphics();
		g.setColor(fillColor);
		g.setStroke(new BasicStroke(1.0f,
				BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		stk.push(new Point(x, y));
		while (!stk.empty()) {
			Point seed = stk.pop(); // 取当前种子点
			int seedx = seed.x;
			int seedy = seed.y;
			int xRight = fillLineRight(jp, seedx, seedy, fillColor);// 右填充
			if (xRight > 800)
				return;
			int xLeft = fillLineLeft(jp, seedx, seedy, fillColor);// 左填充
			if (xLeft < 0)
				return;
			if (xLeft == x - 1 && xRight == x)
				continue;
			// g.drawLine(xLeft, seedy, xRight, seedy);
			shapes.add(new Line2D.Double(xLeft, seedy, xRight, seedy));
			colors.add(fillColor);
			storks.add(1.0f);

			msal.add(0);
			jp.repaint();
			searchLineNewSeed(jp, xLeft, xRight, seedy - 1, fillColor);// 向下寻找种子
			searchLineNewSeed(jp, xLeft, xRight, seedy + 1, fillColor);// 向上寻找种子
		}
	}

	// 右填充，返回填充的像素数量
	int fillLineRight(JPanel jp, int x, int y, Color fillColor) {
		int px = x;
		Graphics g = jp.getGraphics();
		g.setColor(fillColor);
		while (!rt.getPixelColor(dx + px, dy + y).equals(fillColor)) {
			g.drawLine(px, y, px, y);
			px += 1;

			if (px > 800)
				return px;
		}
		return px;
	}

	// 左填充，返回填充的像素数量
	int fillLineLeft(JPanel jp, int x, int y, Color fillColor) {
		Graphics g = jp.getGraphics();
		g.setColor(fillColor);
		int px = x - 1;
		while ((!rt.getPixelColor(dx + px, dy + y).equals(fillColor)) && px > 0) {
			g.drawLine(px, y, px, y);
			px -= 1;

			if (px < 0)
				return px;
		}
		return px;
	}

	void searchLineNewSeed(JPanel jp, int xLeft, int xRight, int y,
			Color fillColor) {
		int xt = xLeft + 1;
		boolean findNewSeed = false;
		while (xt < xRight) {
			findNewSeed = false;
			while ((!rt.getPixelColor(dx + xt, dy + y).equals(fillColor))
					&& xt <= xRight) {
				findNewSeed = true;
				xt++;
			}
			if (findNewSeed) {
				if ((!rt.getPixelColor(dx + xt, dy + y).equals(fillColor))
						&& (xt == xRight)) {
					stk.push(new Point(xt - 1, y));
					return;
				} else {
					stk.push(new Point(xt - 1, y));
					return;
				}
			}
			if ((rt.getPixelColor(dx + xt, dy + y).equals(fillColor))
					&& (xt <= xRight)) {
				// 区间左为边界，缩小区间
				if (xt == (xLeft + 1)) {
					xt++;
					xLeft++;
				} else
					return;
			}
		}
	}
}
