package com.iteye.weimingtom.triangletest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.bric.geom.TransformUtils;
import com.iteye.weimingtom.firetree.MainFrame;

public class Test001 extends MainFrame {
	private static final long serialVersionUID = 1L;
	private BufferedImage srcImage = null, dstImage = null;
	private Point src1 = new Point(512 / 4, 0);
	private Point src2 = new Point(512, 0);
	private Point src3 = new Point(0, 512);
	private Point dst1 = new Point(512 / 4, 0);
	private Point dst2 = new Point(512 , 0);
	private Point dst3 = new Point(0, 800);
	
	public Test001() {
		super("Test001", 800, 600, 24);
	}

	@Override
	protected void onInit() {
		AffineTransform transform = TransformUtils.createAffineTransform(src1, src2, src3, dst1, dst2, dst3);
		System.out.println("transform : " + transform);
		
		srcImage = load("lena.jpg");
		dstImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		AffineTransformOp op = new AffineTransformOp(transform,  
                AffineTransformOp.TYPE_BILINEAR);  
        dstImage = op.filter(srcImage, dstImage);  
	}

	@Override
	protected void onDraw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, canvasWidth, canvasHeight);
		if (dstImage != null) {
			GeneralPath path = new GeneralPath();
			path.moveTo(dst1.x, dst1.y);
			path.lineTo(dst2.x, dst2.y);
			path.lineTo(dst3.x, dst3.y);
			path.closePath();
//			g.setClip(null);
//			g.drawImage(srcImage, 0,  0, null);
			g.setClip(path);
			g.drawImage(dstImage, 0,  0, null);
//			g.setClip(null);
//			g.drawImage(srcImage, 0,  0, null);
		}
	}
	
	@Override
	protected void onMouseClick(int x, int y) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
		}
	}
	
	public static void main(String[] args) {		
		new Test001().start();
	}

	public static BufferedImage load(String filename) {
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(filename));
			BufferedImage img = ImageIO.read(is);
			return img;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
