package com.system.baseapplibrary.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class FileUtils {
	private static final int BUFFER = 8192;

	public static final String TAG = "FileUtils";
	private String SDPATH;

	public FileUtils() {
		// 得到当前外部存储设备的目录
		if (IsExistsSDCard()) {
			SDPATH = Environment.getExternalStorageDirectory().getPath();
		} else {
			SDPATH = null;
		}

	}

	/**
	 * 获取SDCard路径
	 * 
	 * @return
	 */
	public static String getSdCardPath() {
		if (IsExistsSDCard()) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			return sdcardDir.getPath();

		} else {
			File root = Environment.getRootDirectory();
			return root.getPath();
		}
	}

	/**
	 * 是否存在SDCARD
	 * 
	 * @return
	 */
	public static boolean IsExistsSDCard() {
		String sdState = Environment.getExternalStorageState();// 获得sd卡的状态
		if (sdState.equals(Environment.MEDIA_MOUNTED)) { // 判断SD卡是否存在
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断文件或目录是否存在于外部存储器
	 * 
	 * @param file
	 *            文件或目录
	 * @return
	 */
	public static boolean isExists(File file) {
		boolean isExists = file.exists();
		return isExists;
	}

	/**
	 * 判断文件或目录是否存在于外部存储器
	 * 
	 * @param path
	 *            完整路径名
	 * @return
	 */
	public static boolean isExists(String path) {

		File file = new File(path);
		return isExists(file);
	}

    public static void delete(String path) {
        if(isExists(path))
        {
            File file = new File(path);
            file.delete();
        }

    }

	/**
	 * 获取剩余空间
	 * 
	 * @return
	 * @throws IOException
	 */
	public long getSDFreeSize() throws IOException {
		StatFs sf = new StatFs(getSdCardPath());
		long blockSize = sf.getBlockSize();
		long availableBlocks = sf.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	/**
	 * 获取所有空间
	 *
	 * @return
	 * @throws IOException
	 */
	public long getSDAllSize() throws IOException {
		StatFs sf = new StatFs(getSdCardPath());
		long blockSize = sf.getBlockSize();
		long blockCount = sf.getBlockCount();
		return blockSize * blockCount;
	}

	/**
	 * 创建文件到外部存储
	 *
	 * @param path
	 *            文件路径
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public File createFileToSD(String path, String fileName) throws IOException {

		File file = createDirToSD(path);
		file = new File(file, fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 创建文件到外部存储根目录
	 *
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public File creteFileToSD(String fileName) throws IOException {
		return createFileToSD(getSdCardPath(), fileName);
	}

	/**
	 * 创建目录到外部存储
	 *
	 * @param dirName
	 *            目录名
	 * @return
	 * @throws IOException
	 */
	public File createDirToSD(String dirName) throws IOException {

		File dir = new File(getSdCardPath() + dirName);
		if (isExists(dir)) {
			return dir;
		}
		boolean isCreated = dir.mkdirs();
		if (!isCreated) {
			throw new IOException("创建目录失败!");
		}
		return dir;
	}

	/**
	 * 删除外部存储中的文件或目录
	 *
	 * @param pathName
	 */
	public void deleteFileFromSD(String pathName) throws IOException {
		File file = new File(getSdCardPath() + pathName);
		boolean b = deleteFileFromSD(file);
	}

	/**
	 * 删除SdCard文件
	 *
	 * @param file
	 * @return
	 */
	public boolean deleteFileFromSD(File file) {

		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					this.deleteFileFromSD(files[i]);
				}
			}
			boolean isDeleted = file.delete();
			return isDeleted;
		} else {
			return true;
		}
	}

	/**
	 * 从SdCard读取文件
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = readTextInputStream(is);
			;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}

	/**
	 * 从SdCard读取文件
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readTextInputStream(InputStream is) throws IOException {
		StringBuffer strbuffer = new StringBuffer();
		String line;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			while ((line = reader.readLine()) != null) {
				strbuffer.append(line).append("\r\n");
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return strbuffer.toString();
	}

	public static void writeTextFile(File file, String str) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			out.write(str.getBytes());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[BUFFER];
			int length;
			while ((length = inBuff.read(buffer)) != -1) {
				outBuff.write(buffer, 0, length);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}

	/**
	 * Java文件操作 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * Java文件操作 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameForPath(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('/');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * Java文件操作 获取文件名
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileNameForUrl(String url) {
		if ((url != null) && (url.length() > 0)) {
			int dot = url.lastIndexOf('/');
			if ((dot > -1) && (dot < (url.length() - 1))) {
				return url.substring(dot + 1);
			}
		}
		return url;
	}

	/**
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getFileNameNoEx(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}

	public long getFileSizes(File f) throws Exception {// 取得文件大小
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
		} else {
			f.createNewFile();
			System.out.println("文件不存在");
		}
		return s;
	}

	/**
	 * //转换文件大小
	 * 
	 * @return
	 */
	public static String getFileSize(String path) {
		String fileSizeString = "0.0K";
		long fileLength = 0;
		File f = new File(path);
		if (f.exists()) {
			try {
				FileInputStream fis = new FileInputStream(f);
				fileLength = fis.available();
				DecimalFormat df = new DecimalFormat("#.00");
				if (fileLength < 1024) {
					fileSizeString = df.format((double) fileLength) + "B";
				} else if (fileLength < 1048576) {
					fileSizeString = df.format((double) fileLength / 1024)
							+ "K";
				} else if (fileLength < 1073741824) {
					fileSizeString = df.format((double) fileLength / 1048576)
							+ "M";
				} else {
					fileSizeString = df
							.format((double) fileLength / 1073741824) + "G";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return fileSizeString;
	}

	/**
	 * 读取json数据文件
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getJson(Context context, String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			AssetManager assetManager = context.getAssets();
			BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
			String line;
			while ((line = bf.readLine()) != null) {
				stringBuilder.append(line);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
