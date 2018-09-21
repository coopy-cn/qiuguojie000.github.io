package com.core.teamwork.base.util.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class PictureUtil {
	/**
	 * 缩小图片(图片的长宽与设置的长宽不一致 按照区域缩略)
	 * @param inputStream 文件流
	 * @param fileName 文件名称 xx.png
	 * @param outputPath 输出路径
	 * @param height 高
	 * @param width 宽
	 * @param p  区域  Position.CENTER
	 * @return 输出路径
	 */
	@SuppressWarnings("rawtypes")
	public static String getSmallImg(InputStream inputStream, String fileName,
			String outputPath, int height, int width, Positions p) {
		try {
			String compressPath = outputPath + fileName;
			BufferedImage image = ImageIO.read(inputStream);
			Thumbnails.Builder builder = null;

			int imageWidth = image.getWidth();
			int imageHeitht = image.getHeight();
			String floatString = null;
			if (imageHeitht > imageWidth)
				floatString = Float.toString(imageHeitht / imageWidth);
			else {
				floatString = Float.toString(imageWidth / imageHeitht);
			}
			BigDecimal b1 = new BigDecimal(Float.toString(height / width));
			BigDecimal b2 = new BigDecimal(floatString);
			float ss = b1.subtract(b2).floatValue();
			if (ss != 0)
				builder = Thumbnails.of(image).sourceRegion(p, width, height)
						.size(width, height);
			else {
				builder = Thumbnails.of(image).size(width, height);
			}
			builder.toFile(compressPath);

			return compressPath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 按照比例缩小和压缩图片
	 * @param url 网络URL地址
	 * @param fileName 文件名称
	 * @param scale 比例 1为原大小  0.8 0.6 0.4 0.2等 
	 * @param quality 图片质量 1为原质量 
	 * @return 特定图片本地路径
	 */
	public static String getQualityImg(URL url, String fileName,double scale,float quality) {
		try {
			String outpath = System.getProperty("user.dir");
			String compressPath = outpath + fileName;
			Thumbnails.of(url.openStream()).scale(scale).outputQuality(quality).toFile(compressPath);

			return compressPath;
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 按照比例缩小和压缩图片
	 * @param url 网络URL地址
	 * @param fileName 文件名称
	 * @param outpath 输出路径
	 * @param scale 比例 1为原大小  0.8 0.6 0.4 0.2等 
	 * @param quality 图片质量 1为原质量 
	 * @return 输出路径
	 */
	public static String getQualityImg(URL url, String fileName,String outpath,double scale,float quality) {
		try {
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			String compressPath = outpath + fileName;
//			System.out.println(conn.getContentLength());
//			int filesize = conn.getContentLength();
			String compressPath = outpath + fileName;
			Thumbnails.of(url.openStream()).scale(scale).outputQuality(quality).toFile(compressPath);

			return compressPath;
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 按照比例缩小和压缩图片
	 * @param inputStream 文件流
	 * @param fileName 文件名称
	 * @param scale 比例 1为原大小  0.8 0.6 0.4 0.2等 
	 * @param quality 图片质量 1为原质量 
	 * @return 特定图片本地路径
	 */
	public static String getQualityImg(InputStream inputStream,String fileName,double scale,float quality) {
		try {
			String outpath = System.getProperty("user.dir");
			String compressPath = outpath + fileName;
			Thumbnails.of(inputStream).scale(scale).outputQuality(quality).toFile(compressPath);
			return compressPath;
		} catch (IOException e) {
		}
		return null;
	}
	

	/**
	 * @param inputStream 文件流
	 * @param fileName 文件名称
	 * @param outpath 输出路径
	 * @param scale 比例 1为原大小  0.8 0.6 0.4 0.2等 
	 * @param quality 图片质量 1为原质量 
	 * @return 输出路径
	 */
	public static String getQualityImg(InputStream inputStream,String fileName, String outpath,double scale,float quality) {
		try {
			String compressPath = outpath + fileName;
			Thumbnails.of(inputStream).scale(scale).outputQuality(quality).toFile(compressPath);
			return compressPath;
		} catch (IOException e) {
		}
		return null;
	}

	public static ByteArrayOutputStream getQualityImgToByteArray(InputStream inputStream,String prefix,double scale,float quality) {
		try {
			BufferedImage image = Thumbnails.of(inputStream).scale(scale).outputQuality(quality).asBufferedImage();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, prefix, out);
			return out;
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 裁剪图片
	 * @param url  网络URL地址
	 * @param fileName 文件名称
	 * @param x 开始区域X点 （左上角计算）
	 * @param y 开始区域Y点 （左上角计算）
	 * @param cutX 结束区域X点
	 * @param cutY 结束区域Y点
	 * @return 特定路径
	 */
	public static String getCutImg(URL url, String fileName, int x, int y,
			int cutX, int cutY) {
		try {
			String outpath = System.getProperty("user.dir");
			String compressPath = outpath + fileName;

			Thumbnails.of(url).sourceRegion(x, y, cutX, cutY).size(cutX, cutY)
					.toFile(compressPath);
			return compressPath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
