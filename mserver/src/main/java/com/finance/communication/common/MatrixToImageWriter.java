package com.finance.communication.common;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.google.zxing.common.BitMatrix;

public final  class MatrixToImageWriter {
	private static final int BLACK = 0xFF000000;
	   private static final int WHITE = 0xFFFFFFFF;
	 
	   private MatrixToImageWriter() {}
	 
	   /**
	    * 转成透明背景
	 * @param image
	 * @return
	 */
	private static BufferedImage convert(BufferedImage image) {  
		  ImageIcon imageIcon = new ImageIcon(image);  
          BufferedImage bufferedImage = new BufferedImage(  
                  imageIcon.getIconWidth(), imageIcon.getIconHeight(),  
                  BufferedImage.TYPE_4BYTE_ABGR);  
          Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();  
          g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());  
          int alpha = 0;  
          for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage  
                  .getHeight(); j1++) {  
              for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage  
                      .getWidth(); j2++) {  
                  int rgb = bufferedImage.getRGB(j2, j1);  
                  if (colorInRange(rgb))  
                      alpha = 0;  
                  else  
                      alpha = 255;  
                  rgb = (alpha << 24) | (rgb & 0x00ffffff);  
                  bufferedImage.setRGB(j2, j1, rgb);  
              }  
          }  
          g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());  
          g2D.dispose();
          return bufferedImage;
	  }
	   
	  private static boolean colorInRange(int color) {  
	        int red = (color & 0xff0000) >> 16;  
	        int green = (color & 0x00ff00) >> 8;  
	        int blue = (color & 0x0000ff);  
	        if (red >= 210 && green >= 210 && blue >= 210)  
	            return true;  
	        return false;  
	    }  
	  
	  
	   public static BufferedImage toBufferedImage(BitMatrix matrix) {
	     int width = matrix.getWidth();
	     int height = matrix.getHeight();
	     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	     for (int x = 0; x < width; x++) {
	       for (int y = 0; y < height; y++) {
	         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
	       }
	     }
	     return convert(image);
	   }
	 
	   
	   
	   
	   public static void writeToFile(BitMatrix matrix, String format, File file)
	       throws IOException {
	     BufferedImage image = toBufferedImage(matrix);
	     if (!ImageIO.write(image, format, file)) {
	       throw new IOException("Could not write an image of format " + format + " to " + file);
	     }
	   }
	 
	   
	   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
	       throws IOException {
	     BufferedImage image = toBufferedImage(matrix);
	     if (!ImageIO.write(image, format, stream)) {
	       throw new IOException("Could not write an image of format " + format);
	     }
	   }
	 
	 }
