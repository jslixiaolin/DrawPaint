package com.lxl.paint;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.UIManager;

// 设置菜单
public class MyPainter extends JFrame implements ActionListener {

	// 画布
	DrawPanel myDrawPanel;
	JLabel statusBar; // 显示鼠标状态的提示条
	JToolBar buttonPanel; // 定义按钮面板
	fontset wz;
	JButton[] buttons;
	JMenuItem[] jmi = new JMenuItem[20];
	int width = 1400, height = 800; // 定义画图区域初始大小
	String tipText[] = { "", "直线", "铅笔", "椭圆", "矩形", "圆", "文字", "橡皮擦", "曲线",
			"种子填充", "种子扫描线填充" };
	FileDialog storefile;
	FileDialog openfile;
	FileInputStream filein;
	FileOutputStream fileout;
	ObjectInputStream objectin;
	ObjectOutputStream objectout;

	// 生成菜单
	MyPainter() {
		super("Drawing Pad  | 无标题");
		buttons = new JButton[tipText.length];
		myDrawPanel = new DrawPanel();
		// 初始化顶部菜单
		JMenuBar bar = new JMenuBar();
		initMenuBar(bar);
		/*
		 * wz = new fontset("字体设置"); wz.setVisible(false);
		 */

		buttonPanel = new JToolBar(JToolBar.HORIZONTAL);
		statusBar = new JLabel();
		statusBar.setText("     Welcome To The Little Drawing Pad!!!  :)");
		for (int i = 1; i < buttons.length; i++) {
			buttons[i] = new JButton(tipText[i]);
			buttons[i].setToolTipText(tipText[i]);
			buttons[i].addActionListener(this);
			buttonPanel.add(buttons[i]);
		}

		super.setJMenuBar(bar);
		this.getContentPane().add(buttonPanel, BorderLayout.NORTH);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.getContentPane().add(myDrawPanel, BorderLayout.CENTER);

		storefile = new FileDialog(this, "存储文件", FileDialog.SAVE);
		storefile.setVisible(false);
		openfile = new FileDialog(this, "打开文件", FileDialog.LOAD);
		openfile.setVisible(false);

		setSize(width, height);
		setLocationRelativeTo(null);
		// 将默认图标替换掉
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				"../lab6/picture/logo.png"));
		show();
	}

	// 初始化顶部菜单栏
	public void initMenuBar(JMenuBar bar) {
		JMenu menu1 = new JMenu("文件");
		jmi[0] = new JMenuItem("打开");
		jmi[1] = new JMenuItem("保存");
		jmi[9] = new JMenuItem("新建");
		jmi[10] = new JMenuItem("退出");
		menu1.add(jmi[0]);
		menu1.add(jmi[1]);
		menu1.add(jmi[9]);
		menu1.add(jmi[10]);
		bar.add(menu1);

		JMenu menu2 = new JMenu("颜色");
		jmi[2] = new JMenuItem("左颜色");
		jmi[3] = new JMenuItem("背景色");
		jmi[11]=new JMenuItem("右颜色");
		menu2.add(jmi[2]);
		menu2.add(jmi[11]);
		menu2.add(jmi[3]);
		bar.add(menu2);


		JMenu menu4 = new JMenu("粗细");
		jmi[6] = new JMenuItem("粗细设置");
		menu4.add(jmi[6]);
		bar.add(menu4);

		JMenu menu5 = new JMenu("模式");
		jmi[7] = new JMenuItem("填充");
		jmi[8] = new JMenuItem("普通");
		menu5.add(jmi[7]);
		menu5.add(jmi[8]);
		bar.add(menu5);

		jmi[0].addActionListener(this);
		jmi[1].addActionListener(this);
		jmi[2].addActionListener(this);
		jmi[3].addActionListener(this);
		jmi[6].addActionListener(this);
		jmi[7].addActionListener(this);
		jmi[8].addActionListener(this);
		jmi[9].addActionListener(this);
		jmi[9].addActionListener(myDrawPanel);
		jmi[10].addActionListener(this);
		jmi[11].addActionListener(this);
	}

	// 开始画板
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 将界面设置为当前windows风格
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		MyPainter draw = new MyPainter();
		draw.myDrawPanel.MyfColor = Color.black;
		draw.myDrawPanel.setBackground(Color.white);
		draw.myDrawPanel.drawtype=2;
		// draw.myDrawPanel.stork=1.0f;
		draw.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// draw.setSize(500, 500);
		// draw.pack();
		draw.setVisible(true);
	}

	public void setStroke() {
		String input;
		input = JOptionPane
				.showInputDialog("Please input a float stroke value! ( >0 )");
		if(input!=null){
			myDrawPanel.stork = Float.parseFloat(input);
		}
		// itemList[index].stroke = stroke;
	}

	// 设置鼠标触击事件
	@Override
	public void actionPerformed(ActionEvent e1) {
		// TODO Auto-generated method stub
		// 设置线条粗细
		if (e1.getSource() == jmi[6]) {
			setStroke();
		}

		// 直线
		if (e1.getSource() == buttons[1]) {
			myDrawPanel.drawtype = 1;
		}
		// 铅笔
		if (e1.getSource() == buttons[2]) {
			myDrawPanel.drawtype = 2;
		}
		// 圆
		if (e1.getSource() == buttons[5]) {
			myDrawPanel.drawtype = 5;
		}
		// 椭圆
		if (e1.getSource() == buttons[3]) {
			myDrawPanel.drawtype = 3;
		}
		// 矩形
		if (e1.getSource() == buttons[4]) {
			myDrawPanel.drawtype = 4;
		}
		// 左颜色
		if (e1.getSource() == jmi[2]) {
			myDrawPanel.MyfColor = JColorChooser.showDialog(this, "选择左键颜色",
					myDrawPanel.MyfColor);
		}
		
		// 右颜色
		if (e1.getSource() == jmi[11]) {
			myDrawPanel.rColor = JColorChooser.showDialog(this, "选择右键颜色",
					myDrawPanel.MyfColor);
		}
		
		// 背景色
		if (e1.getSource() == jmi[3]) {
			myDrawPanel.MybColor = JColorChooser.showDialog(this, "选择背景色",
					myDrawPanel.MybColor);
			myDrawPanel.setBackground(myDrawPanel.MybColor);
		}
		//清空
		if (e1.getSource() == jmi[9]) {
			//myDrawPanel.tf = 0;
			myDrawPanel.drawtype = 7;
			this.setTitle("Drawing Pad  | 无标题");
		}
		// 打开文件
		if (e1.getSource() == jmi[0])
		{
			Vector<shapeclass> scvector2 = new Vector<shapeclass>();
			Vector<wzclass> wcvector2 = new Vector<wzclass>();
			openfile.setVisible(true);
			if (openfile.getFile() != null) {
				try {
					// points.removeAllElements();
					myDrawPanel.shapes = null;
					myDrawPanel.colors = null;
					myDrawPanel.msal = null;
					myDrawPanel.storks=null;
					File file = new File(openfile.getDirectory(), openfile
							.getFile());
					filein = new FileInputStream(file);
					objectin = new ObjectInputStream(filein);
					scvector2 = (Vector<shapeclass>) objectin.readObject();
					wcvector2 = (Vector<wzclass>) objectin.readObject();
					for (int i = 0; i < scvector2.size(); i++) {
						if (myDrawPanel.shapes == null)
							myDrawPanel.shapes = new ArrayList<Shape>();
						if (myDrawPanel.colors == null)
							myDrawPanel.colors = new ArrayList<Color>();
						if (myDrawPanel.msal == null)
							myDrawPanel.msal = new ArrayList<Integer>();
						if (myDrawPanel.storks == null)
							myDrawPanel.storks = new ArrayList<Float>();
						myDrawPanel.shapes.add(scvector2.elementAt(i).scshape);
						myDrawPanel.colors.add(scvector2.elementAt(i).sccolor);
						myDrawPanel.msal.add(scvector2.elementAt(i).scms);
						myDrawPanel.storks.add(scvector2.elementAt(i).stork);
					}
					for (int i = 0; i < wcvector2.size(); i++) {
						if (myDrawPanel.colal == null)
							myDrawPanel.colal = new ArrayList<Color>();
						if (myDrawPanel.cotal == null)
							myDrawPanel.cotal = new ArrayList<String>();
						if (myDrawPanel.xal == null)
							myDrawPanel.xal = new ArrayList<Integer>();
						if (myDrawPanel.yal == null)
							myDrawPanel.yal = new ArrayList<Integer>();
						if (myDrawPanel.fal == null)
							myDrawPanel.fal = new ArrayList<Font>();
						myDrawPanel.cotal.add(wcvector2.elementAt(i).wcstring);
						myDrawPanel.colal.add(wcvector2.elementAt(i).wccolor);
						myDrawPanel.fal.add(wcvector2.elementAt(i).wcfont);
						myDrawPanel.xal.add(wcvector2.elementAt(i).wcx);
						myDrawPanel.yal.add(wcvector2.elementAt(i).wcy);
					}
					objectin.close();
					filein.close();
					myDrawPanel.repaint();
					this.setTitle("Drawing Pad  | " + file.getName());
				} catch (Exception ee) {
					System.out.println(ee.toString());
				}
			}

		}
		//保存
		if (e1.getSource() == jmi[1]) {
		//	myDrawPanel.tf = 0;
			if (myDrawPanel.shapes == null)
				myDrawPanel.shapes = new ArrayList<Shape>();
			if (myDrawPanel.colors == null)
				myDrawPanel.colors = new ArrayList<Color>();
			if (myDrawPanel.msal == null)
				myDrawPanel.msal = new ArrayList<Integer>();

			if (myDrawPanel.colal == null)
				myDrawPanel.colal = new ArrayList<Color>();
			if (myDrawPanel.cotal == null)
				myDrawPanel.cotal = new ArrayList<String>();
			if (myDrawPanel.xal == null)
				myDrawPanel.xal = new ArrayList<Integer>();
			if (myDrawPanel.yal == null)
				myDrawPanel.yal = new ArrayList<Integer>();
			if (myDrawPanel.fal == null)
				myDrawPanel.fal = new ArrayList<Font>();
			if(myDrawPanel.storks==null)
				myDrawPanel.storks=new ArrayList<Float>();
			
			Vector<shapeclass> scvector = new Vector<shapeclass>();
			Vector<wzclass> wcvector = new Vector<wzclass>();
			for (int i = 0; i < myDrawPanel.shapes.size(); i++)
				scvector.addElement(new shapeclass(myDrawPanel.shapes.get(i),
						myDrawPanel.colors.get(i), myDrawPanel.msal.get(i),myDrawPanel.storks.get(i)));
			for (int i = 0; i < myDrawPanel.cotal.size(); i++)
				wcvector.addElement(new wzclass(myDrawPanel.cotal.get(i),
						myDrawPanel.fal.get(i), myDrawPanel.colal.get(i),
						myDrawPanel.xal.get(i), myDrawPanel.yal.get(i)));
			storefile.setVisible(true);
			if (storefile.getFile() != null) {
				try {
					File file = new File(storefile.getDirectory(), storefile
							.getFile());
					fileout = new FileOutputStream(file);
					objectout = new ObjectOutputStream(fileout);
					objectout.writeObject(scvector);
					objectout.writeObject(wcvector);
					objectout.close();
					fileout.close();
					this.setTitle("Drawing Pad  | " + file.getName());
					// myDrawPanel.repaint();
				} catch (FileNotFoundException e2) {
					System.out.println(e1.toString());
					e2.printStackTrace();
				} catch (IOException ee) {
					System.out.println(ee.toString());
					ee.printStackTrace();
				}
			}
		}
		//退出
		if (e1.getSource() == jmi[10]) {
			System.exit(0);
		}
		//填充模式
		if (e1.getSource() == jmi[7]) {
			myDrawPanel.tcflag = 1;
		}
		//普通模式
		if (e1.getSource() == jmi[8])
			myDrawPanel.tcflag = 0;

		//橡皮擦
		if (e1.getSource() == buttons[7]) {
			myDrawPanel.drawtype = 6;
		}
		//曲线
		if (e1.getSource() == buttons[8]) {
			myDrawPanel.drawtype = 8;
		}
		//种子填充
		if (e1.getSource() == buttons[9]) {
			myDrawPanel.tf = 3;
		}
		//扫描线填充
		if (e1.getSource() == buttons[10]) {
			myDrawPanel.tf = 2;
		}
	}

	// 设置画板
	class DrawPanel extends JPanel implements MouseListener,
			MouseMotionListener, ActionListener {
		List<Shape> shapes;
		Color MyfColor;
		Color MybColor;
		Color rColor; //右键选择颜色
		boolean rflag=false;
		float stork = 1.0f;
		List<Float> storks;
		List<Color> colors;
		List<Integer> msal; // 模式
		List<Point> points; // 型值点
		List<Color> colal;
		List<String> cotal;
		List<Integer> xal; // x坐标
		List<Integer> yal; // y坐标
		List<Font> fal; // 字体
		int drawtype = 100;
		Point seed;
		Point prePoint;
		Point prePoint2;
		Point prePoint3;
		Shape tempShape;

		String wzfont;
		String wzcontent;
		int wzbolder;
		int wzsize;
		Color wzcolor;
		Font f;
		int tf;
		int tcflag;
		int flag;

		DrawPanel() {
			//设置十字游标
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			setBackground(Color.white);
			shapes = new ArrayList<Shape>();
			storks=new ArrayList<Float>();
			colors=new ArrayList<Color>();
			msal=new ArrayList<Integer>(); 
			points=new ArrayList<Point>();
			colal=new ArrayList<Color>();
			cotal=new ArrayList<String>();
			xal=new ArrayList<Integer>(); // x坐标
			yal=new ArrayList<Integer>(); // y坐标
			fal=new ArrayList<Font>(); // 字体
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}

		// 使用画板
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;
				for (int i = 0; i < shapes.size(); i++) {
					g2.setStroke(new BasicStroke(storks.get(i),
							BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
					if (msal.get(i) == 1) {
						//填充
						g2.setColor(colors.get(i));
						g2.fill(shapes.get(i));
					} else if (msal.get(i) == 2) {
						//橡皮擦
						g2.setColor(this.getBackground());
						g2.draw(shapes.get(i));
					} else {
						g2.setColor(colors.get(i));
						g2.draw(shapes.get(i));
					}
				}
			//绘制临时图像	
			if (tempShape != null) {
				g2.setStroke(new BasicStroke(this.stork,
						BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
				
				g.setColor(rflag?rColor:MyfColor);
				if(tcflag==1){
					g2.fill(tempShape);
				}
				else{
					g2.draw(tempShape);
				}
			}
			//写字
			if (cotal != null && colal != null && fal != null && xal != null
					&& yal != null) {
				for (int j = 0; j < cotal.size(); j++) {
					g.setColor(colal.get(j));
					g2.setFont(fal.get(j));
					g2.drawString(cotal.get(j), xal.get(j), yal.get(j));
				}
			}
		}

		// 预生成图像
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			statusBar.setText("     Mouse Dragged @:[" + e.getX() + ", "
					+ e.getY() + "]");
			switch (drawtype) {
			// 直线
			case 1:
				tempShape = new Line2D.Double(prePoint.x, prePoint.y, e.getX(),
						e.getY());
				break;
			// 铅笔
			case 2:
				tempShape = new Line2D.Double(prePoint2.x, prePoint2.y, e
						.getX(), e.getY());
				shapes.add(tempShape);
				storks.add(stork);
				colors.add(rflag?rColor:MyfColor);
				msal.add(0);
				break;
			// 椭圆
			case 3:
				// tempShape=new
				// Ellipse2D.Double(prePoint.x,prePoint.y,e.getX()-prePoint.x,e.getY()-prePoint.y);
				if (e.getX() >= prePoint.x && e.getY() >= prePoint.y)
					tempShape = new Ellipse2D.Double(prePoint.x, prePoint.y, e
							.getX()
							- prePoint.x, e.getY() - prePoint.y);
				else if (e.getX() > prePoint.x && e.getY() < prePoint.y)
					tempShape = new Ellipse2D.Double(prePoint.x, e.getY(), e
							.getX()
							- prePoint.x, prePoint.y - e.getY());
				else if (e.getX() < prePoint.x && e.getY() > prePoint.y)
					tempShape = new Ellipse2D.Double(e.getX(), prePoint.y,
							prePoint.x - e.getX(), e.getY() - prePoint.y);
				else if (e.getX() < prePoint.x && e.getY() < prePoint.y)
					tempShape = new Ellipse2D.Double(e.getX(), e.getY(),
							prePoint.x - e.getX(), prePoint.y - e.getY());
				break;
			// 矩形
			case 4:
				if (e.getX() >= prePoint.x && e.getY() >= prePoint.y)
					tempShape = new Rectangle(prePoint.x, prePoint.y, e.getX()
							- prePoint.x, e.getY() - prePoint.y);
				else if (e.getX() > prePoint.x && e.getY() < prePoint.y)
					tempShape = new Rectangle(prePoint.x, e.getY(), e.getX()
							- prePoint.x, prePoint.y - e.getY());
				else if (e.getX() < prePoint.x && e.getY() > prePoint.y)
					tempShape = new Rectangle(e.getX(), prePoint.y, prePoint.x
							- e.getX(), e.getY() - prePoint.y);
				else if (e.getX() < prePoint.x && e.getY() < prePoint.y)
					tempShape = new Rectangle(e.getX(), e.getY(), prePoint.x
							- e.getX(), prePoint.y - e.getY());
				break;
			// 圆
			case 5:
				if (e.getX() >= prePoint.x && e.getY() >= prePoint.y)
					tempShape = new Ellipse2D.Double(prePoint.x, prePoint.y,
							Math.abs(e.getX() - prePoint.x), Math.abs(e.getX()
									- prePoint.x));
				else if (e.getX() >= prePoint.x && e.getY() < prePoint.y)
					tempShape = new Ellipse2D.Double(prePoint.x, e.getY(), Math
							.abs(e.getX() - prePoint.x), Math.abs(e.getX()
							- prePoint.x));
				else if (e.getX() < prePoint.x && e.getY() > prePoint.y)
					tempShape = new Ellipse2D.Double(e.getX(), prePoint.y, Math
							.abs(prePoint.x - e.getX()), Math.abs(prePoint.x
							- e.getX()));
				else if (e.getX() < prePoint.x && e.getY() < prePoint.y)
					tempShape = new Ellipse2D.Double(e.getX(), e.getY(), Math
							.abs(prePoint.x - e.getX()), Math.abs(prePoint.x
							- e.getX()));
				break;
			case 6:// 橡皮擦

     			shapes.add(new Line2D.Double(prePoint2.x, prePoint2.y,	e.getX(), e.getY()));
				colors.add(this.getBackground());
				storks.add(this.stork);
				msal.add(2);
				break;
			}
			prePoint2 = e.getPoint();
			repaint();
		}

		// 读取鼠标坐标
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			statusBar.setText("     Mouse Pressed @:[" + e.getX() + ", "
					+ e.getY() + "]");// 设置状态提示
			prePoint = e.getPoint();
			prePoint2 = e.getPoint();
			prePoint3 = e.getPoint();
			rflag=false;
			if (e.getButton() == MouseEvent.BUTTON3){
				rflag=true;
			}

		}

		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			statusBar.setText("     Mouse Released @:[" + e.getX() + ", "
					+ e.getY() + "]");
			tempShape = null;
		
			//曲线
			if (drawtype == 8) {
				//右键被单击
				if (e.getButton() == MouseEvent.BUTTON3){
					drawtype = 9;
				}
				else
					points.add(e.getPoint());
			}
			switch (drawtype) {
			// 文本
			case 0:
				colal.add(MyfColor);
				f = new Font(wzfont, wzbolder, wzsize);
				fal.add(f);
				cotal.add(wzcontent);
				xal.add(prePoint3.x);
				yal.add(prePoint3.y);
				repaint();
				break;
			// 直线
			case 1:
				shapes.add(new Line2D.Double(prePoint.x, prePoint.y, e.getX(),
						e.getY()));
				colors.add(rflag?rColor:MyfColor);
				storks.add(this.stork);
				msal.add(0);
				repaint();
				break;
			// 铅笔
			case 2:
				shapes.add(new Line2D.Double(prePoint2.x, prePoint2.y,
						e.getX(), e.getY()));
				colors.add(rflag?rColor:MyfColor);
				storks.add(this.stork);
				msal.add(0);
				repaint();
				break;
			// 椭圆
			case 3:
				if (e.getX() >= prePoint.x && e.getY() >= prePoint.y)
					shapes.add(new Ellipse2D.Double(prePoint.x, prePoint.y, e
							.getX()
							- prePoint.x, e.getY() - prePoint.y));
				else if (e.getX() > prePoint.x && e.getY() < prePoint.y)
					shapes.add(new Ellipse2D.Double(prePoint.x, e.getY(), e
							.getX()
							- prePoint.x, prePoint.y - e.getY()));
				else if (e.getX() < prePoint.x && e.getY() > prePoint.y)
					shapes.add(new Ellipse2D.Double(e.getX(), prePoint.y,
							prePoint.x - e.getX(), e.getY() - prePoint.y));
				else if (e.getX() < prePoint.x && e.getY() < prePoint.y)
					shapes.add(new Ellipse2D.Double(e.getX(), e.getY(),
							prePoint.x - e.getX(), prePoint.y - e.getY()));
				colors.add(rflag?rColor:MyfColor);
				storks.add(this.stork);
				msal.add(tcflag);
				repaint();
				break;
			// 矩形
			case 4:
				if (e.getX() >= prePoint.x && e.getY() >= prePoint.y)
					shapes.add(new Rectangle(prePoint.x, prePoint.y, e.getX()
							- prePoint.x, e.getY() - prePoint.y));
				else if (e.getX() > prePoint.x && e.getY() < prePoint.y)
					shapes.add(new Rectangle(prePoint.x, e.getY(), e.getX()
							- prePoint.x, prePoint.y - e.getY()));
				else if (e.getX() < prePoint.x && e.getY() > prePoint.y)
					shapes.add(new Rectangle(e.getX(), prePoint.y, prePoint.x
							- e.getX(), e.getY() - prePoint.y));
				else if (e.getX() < prePoint.x && e.getY() < prePoint.y)
					shapes.add(new Rectangle(e.getX(), e.getY(), prePoint.x
							- e.getX(), prePoint.y - e.getY()));
				colors.add(rflag?rColor:MyfColor);
				storks.add(this.stork);
				msal.add(tcflag);
				repaint();
				break;
			// 圆
			case 5:
				if (e.getX() >= prePoint.x && e.getY() >= prePoint.y)
					shapes.add(new Ellipse2D.Double(prePoint.x, prePoint.y,
							Math.abs(e.getX() - prePoint.x), Math.abs(e.getX()
									- prePoint.x)));
				else if (e.getX() > prePoint.x && e.getY() < prePoint.y)
					shapes.add(new Ellipse2D.Double(prePoint.x, e.getY(), Math
							.abs(e.getX() - prePoint.x), Math.abs(e.getX()
							- prePoint.x)));
				else if (e.getX() < prePoint.x && e.getY() > prePoint.y)
					shapes.add(new Ellipse2D.Double(e.getX(), prePoint.y, Math
							.abs(prePoint.x - e.getX()), Math.abs(prePoint.x
							- e.getX())));
				else if (e.getX() < prePoint.x && e.getY() < prePoint.y)
					shapes.add(new Ellipse2D.Double(e.getX(), e.getY(), Math
							.abs(prePoint.x - e.getX()), Math.abs(prePoint.x
							- e.getX())));
				colors.add(rflag?rColor:MyfColor);
				storks.add(this.stork);
				msal.add(tcflag);
				repaint();
				break;
				
			case 9:
				if (!points.isEmpty()) {
					(new Bezier()).deCasteljau(this, shapes, points, MyfColor,
							colors,stork,storks, msal);
					points.clear();
				}
				drawtype = 8;
				repaint();
				break;
			}
			//扫描线种子填充
			if (tf == 2) {
				seed = new Point(e.getX(), e.getY());
				(new SeedLineFill()).scanLineSeedFill(this, shapes, seed.x,
						seed.y, MyfColor, colors,storks, msal);
				repaint();
				tf = 0;
			}
			//种子填充
			if (tf == 3) {
				seed = new Point(e.getX(), e.getY());
				(new SeedFill()).scanSeedFill(this, shapes, seed.x, seed.y,
						MyfColor, colors, storks,msal);
				repaint();
				tf = 0;
			}
		}

		//
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			shapes.clear();
			colors.clear();
			msal.clear();
			fal.clear();
			cotal.clear();
			colal.clear();
			xal.clear();
			yal.clear();
			points.clear();
			storks.clear();
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			statusBar.setText("     Mouse Entered @:[" + e.getX() + ", "
					+ e.getY() + "]");

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			statusBar.setText("     Mouse Exited @:[" + e.getX() + ", "
					+ e.getY() + "]");

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			statusBar.setText("     Mouse Moved @:[" + e.getX() + ", "
					+ e.getY() + "]");

		}

	}

}



