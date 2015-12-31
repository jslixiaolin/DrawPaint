package com.lxl.paint;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import javax.swing.border.*; 
class fontset extends JFrame implements ActionListener {
	Choice font, size, bolder;
	JButton bb;
	JButton qd;
	JLabel nrl;
	int out = 0;
	Color fcolor = Color.BLACK;
	String ffont = "Kartika";
	int fbolder = 1;
	int fsize = 12;
	TextField nr;

	fontset(String s) {
		setTitle(s);
		font = new Choice();
		bolder = new Choice();
		size = new Choice();
		nr = new TextField(20);
		nrl = new JLabel("文本内容");
		bolder.addItemListener(new bolderlistener());
		font.addItemListener(new fontlistener());
		size.addItemListener(new sizelistener());
		Panel p1 = new Panel();
		Panel p2 = new Panel();
		Panel p3 = new Panel();
		qd = new JButton("确定");
		qd.addActionListener(new qdlistener());
		p1.setLayout(new GridLayout(4, 1));
		p2.setLayout(new GridLayout(4, 1));
		p3.setLayout(new GridLayout(2, 1));

		GraphicsEnvironment gg = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String ss[] = gg.getAvailableFontFamilyNames();
		String bold[] = { "Font.BOLD", "Font.ITALIC", "Font.PLAIN" };
		for (int i = 126; i < ss.length; i++)
			font.add(ss[i]);
		for (int i = 12; i <= 64; i += 2) {
			String w = String.valueOf(i);
			size.add(w);
		}
		for (int i = 0; i < bold.length; i++) {
			bolder.add(bold[i]);
		}
		p1.add(new JLabel("字体"));
		p1.add(font);
		p1.add(new JLabel("大小"));
		p1.add(size);
		p2.add(new JLabel("字型"));
		p2.add(bolder);
		p3.add(nrl);
		p3.add(nr);
		add(p2, BorderLayout.WEST);
		add(p1, BorderLayout.EAST);
		add(p3, BorderLayout.NORTH);
		add(qd, BorderLayout.SOUTH);
		setSize(250, 150);
		setLocation(180, 100);
		setVisible(true);
		pack();
		/*
		 * addWindowListener(new WindowAdapter() { public void
		 * windowClosing(WindowEvent ee) { } });
		 */
	}

	public void actionPerformed(ActionEvent e) {
	}

	class fontlistener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			// System.out.println(font.getSelectedItem());
			ffont = font.getSelectedItem();
		}
	}

	class bolderlistener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			// System.out.println(bolder.getSelectedItem());
			if (bolder.getSelectedItem() == "Font.BOLD")
				fbolder = 1;
			else if (bolder.getSelectedItem() == "Font.ITALIC")
				fbolder = 2;
			else if (bolder.getSelectedItem() == "Font.PLAIN")
				fbolder = 0;
		}
	}

	class sizelistener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			// System.out.println(size.getSelectedItem());
			fsize = Integer.parseInt(size.getSelectedItem());
		}
	}

	class qdlistener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	}
}