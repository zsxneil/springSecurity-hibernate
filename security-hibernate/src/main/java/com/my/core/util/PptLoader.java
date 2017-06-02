package com.my.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 * 
 * <p>
 * Title: PptLoader
 * </p>
 * <p>
 * Description:读资源文件
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Kingdee International Software Group
 * </p>
 * 
 * @author wuweiwen
 * @version 1.0
 */
public class PptLoader {

	@SuppressWarnings("rawtypes")
	private static Hashtable pptContainer = new Hashtable();

	private static String directory = PptLoader.class.getResource("/").getPath();
	static {
		try {
			directory = URLDecoder.decode(directory, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println("Decode error!");
		}
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
			directory = directory.substring(1);
		}
	}

	private static class PptFilter implements FilenameFilter {
		private String extention;

		public PptFilter(String name) {
			extention = "." + name;
		}

		/**
		 * Tests if a specified file should be included in a file list
		 * 
		 * @param dir
		 *            File
		 * @param name
		 *            String
		 * @return boolean
		 */
		public boolean accept(File dir, String name) {
			return name.endsWith(extention);
		}
	}

	/**
	 *  
	 */
	public PptLoader() {
		super();
	}

	/**
	 * Load the ppt files by exention ".properties" in the directory denoted by
	 * pathname
	 * 
	 * @param pathname
	 *            String
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean load(String pathname) {
		if (pathname.matches("\\S*[$][{]\\S+[}]\\S*"))
			pathname = pathname.replace(pathname.substring(pathname.indexOf('{') - 1, pathname.indexOf('}') + 1),
					System.getProperty(pathname.substring(pathname.indexOf('{') + 1, pathname.indexOf('}'))));
		try {
			File dir = new File(pathname);
			if (!dir.isDirectory()) {
				System.out.println("PptLoader: (load)Not directory");
				return false;
			}
			String[] names = dir.list(new PptFilter("properties"));
			if (names == null) {
				System.out.println("PptLoader: (load)Haven't properties files");
				return false;
			}
			directory = dir.getAbsolutePath();
			for (int i = 0; i < names.length; i++) {
				String pptname = names[i];
				int idx = pptname.indexOf(".properties");
				pptname = pptname.substring(0, idx);
				FileInputStream fis;
				try {
					fis = new FileInputStream(directory + File.separatorChar + names[i]);
				} catch (FileNotFoundException e) {
					System.out.println("PptLoader: (load)" + e.getMessage());
					return false;
				}
				Properties ppt = new Properties();
				try {
					ppt.load(fis);
					fis.close();
				} catch (IOException e) {
					try {
						fis.close();
					} catch (IOException e1) {
					}
					System.out.println("PptLoader: (load)" + e.getMessage());
					return false;
				}
				pptContainer.put(pptname, ppt);
			}
			return true;
		} catch (NullPointerException e) {
			System.out.println("PptLoader: (load)" + e.getMessage());
			return false;
		} catch (SecurityException e) {
			System.out.println("PptLoader: (load)" + e.getMessage());
			return false;
		}
	}

	/**
	 * Load the ppt files by exention ".properties" in the directory denoted by
	 * pathname
	 * 
	 * @param pathname
	 *            String
	 * @param filename
	 *            String
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean load(String pathname, String filename) {
		if (pathname.matches("\\S*[$][{]\\S+[}]\\S*"))
			pathname = pathname.replace(pathname.substring(pathname.indexOf('{') - 1, pathname.indexOf('}') + 1),
					System.getProperty(pathname.substring(pathname.indexOf('{') + 1, pathname.indexOf('}'))));
		try {
			File dir = new File(pathname);
			if (!dir.isDirectory()) {
				System.out.println("PptLoader: (load)Not directory");
				return false;
			}
			directory = dir.getAbsolutePath();
			FileInputStream fis;
			try {
				fis = new FileInputStream(directory + File.separatorChar + filename + ".properties");
			} catch (FileNotFoundException e) {
				System.out.println("PptLoader: (load)" + e.getMessage());
				return false;
			}
			Properties ppt = new Properties();
			try {
				ppt.load(fis);
				fis.close();
			} catch (IOException e) {
				try {
					fis.close();
				} catch (IOException e1) {
				}
				System.out.println("PptLoader: (load)" + e.getMessage());
				return false;
			}
			pptContainer.put(filename, ppt);
			return true;
		} catch (NullPointerException e) {
			System.out.println("PptLoader: (load)" + e.getMessage());
			return false;
		} catch (SecurityException e) {
			System.out.println("PptLoader: (load)" + e.getMessage());
			return false;
		}
	}

	/**
	 * Return the property value
	 * 
	 * @param pptname
	 *            String the ppt file name
	 * @param key
	 *            String the key of request value
	 * @return String
	 */
	public static String getPptValue(String pptname, String key) {
		Properties ppt = (Properties) pptContainer.get(pptname);
		if (ppt == null) {
			PptLoader.load(directory);
			System.out.println("dir=" + directory);
			ppt = (Properties) pptContainer.get(pptname);
		}
		return (String) ppt.get(key);
	}

	/**
	 * Set the ppt value
	 * 
	 * @param pptname
	 *            String
	 * @param key
	 *            String
	 * @param value
	 *            String
	 * @return boolean
	 */
	public static boolean setPptValue(String pptname, String key, String value) {
		Properties ppt = (Properties) pptContainer.get(pptname);
		if (ppt == null) {
			return false;
		}
		ppt.put(key, value);
		return true;
	}

	/**
	 * Remove the ppt value
	 * 
	 * @param pptname
	 *            String
	 * @param key
	 *            String
	 * @return String
	 */
	public static String removePptValue(String pptname, String key) {
		Properties ppt = (Properties) pptContainer.get(pptname);
		if (ppt == null) {
			return null;
		}
		return (String) ppt.remove(key);
	}

	/**
	 * save all the ppt files in denoted directory
	 * 
	 * @param path
	 *            String
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean save(String path) {
		try {
			File dir = new File(path);
			if (!dir.isDirectory()) {
				System.out.println("PptLoader: (save)Not directory");
				return false;
			}
			directory = dir.getAbsolutePath();
		} catch (NullPointerException e) {
			System.out.println("PptLoader: (save)" + e.getMessage());
			return false;
		} catch (SecurityException e) {
			System.out.println("PptLoader: (save)" + e.getMessage());
			return false;
		}
		Enumeration keys = pptContainer.keys();
		while (keys.hasMoreElements()) {
			String name = (String) keys.nextElement();
			Properties ppt = (Properties) pptContainer.get(name);
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(directory + File.separatorChar + name + ".properties");
				if (ppt != null) {
					ppt.store(fos, "The properties of " + name);
				}
				fos.close();
				return true;
			} catch (IOException e) {
				System.out.println("PptLoader: (save)" + e.getMessage());
				return false;
			}
		}
		return true;
	}
}