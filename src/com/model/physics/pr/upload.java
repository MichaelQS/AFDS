package com.model.physics.pr;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.h2.store.fs.FileUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.uploader.DownloadFile;
import com.bstek.dorado.uploader.UploadFile;
import com.bstek.dorado.uploader.annotation.FileProvider;
import com.bstek.dorado.uploader.annotation.FileResolver;
import com.bstek.dorado.uploader.util.ParameterUtils;
import com.bstek.dorado.web.DoradoContext;

@Component
public class upload {
	private String getDestPath() {
		return "F:\\workplace\\AFDS\\web\\images";
	}

	@SuppressWarnings("unused")
	@FileResolver
	public Map<String, String> process1(UploadFile file,
			Map<String, Object> parameter) throws Exception {
		/*
		 * String uploadFolder = (String) parameter.get("uploadFolder");
		 * 获取UploadAction的parameter对应的参数值 
		 * String param2 = (String)parameter.get("param2"); 
		 * 获取UploadAction的parameter对应的参数值 
		 * 此处假设上传路径由客户端传过来，也可以适用其他情况，比如本地一个绝对路径。
		 * if (uploadFolder == null) {uploadFolder = "./target/upload"; } 
		 * File destFile =getDestFile(file.getFileName(), new File(uploadFolder));
		 */
		String param1 = (String) parameter.get("param1");// 获取UploadAction的parameter对应的参数值
		String param2 = (String) parameter.get("param2");// 获取UploadAction的parameter对应的参数值

		File destFile = getDestFile(
				new String(file.getFileName().getBytes("ISO8859_1"), "UTF-8"),
				new File(getDestPath()));
		try {
			file.transferTo(destFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> data = new HashMap<String, String>();
		data.put("fileName", destFile.getName());
		data.put("absolutePath", destFile.getAbsolutePath());
		return data;
	}

	@SuppressWarnings("unused")
	@FileResolver
	public void process2(UploadFile file, Map<String, Object> parameter)
			throws Exception {
		String name = (String) parameter.get("name");// 获取UploadAction的parameter对应的参数值

		File destFile = getDestFile(file.getFileName(), new File(getDestPath()));
		try {
			file.transferTo(destFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getDestFile(String fileName, File folder) {

		File destFile;

		// 若文件不存在直接返回
		if (!new File(folder, fileName).exists()) {
			destFile = new File(folder, fileName);
		} else {
			int i = 1, lastDotPos = fileName.lastIndexOf("."), len = fileName
					.length();

			String prefix = null, suffix = null, destFileName;

			// 文件名中不存在"."或者最后一位是"."的时候
			if (lastDotPos == len - 1 || lastDotPos == -1) {
				prefix = fileName;
				suffix = "";
			} else if (lastDotPos != -1) {
				prefix = fileName.substring(0, lastDotPos);
				suffix = fileName.substring(lastDotPos + 1);
			}
			do {
				if (suffix.length() == 0) {
					destFileName = String.format("%s_%d", prefix, i++);
				} else {
					destFileName = String.format("%s_%d.%s", prefix, i++,
							suffix);
				}
				destFile = new File(folder, destFileName);
			} while (destFile.exists());

		}

		FileUtils.createDirectory(destFile.getParent());

		return destFile;
	}

	private DownloadFile getDownloadFile(String fileName) throws IOException {
		String name = fileName;
		// String path = System.getProperty("java.io.tmpdir") + File.separator +
		// "upload";
		ParameterUtils.validateParameterCharacters(name);
		URL url = this.getClass().getResource(name);
		// URL url = new URL(System.getProperty("java.io.tmpdir") +
		// File.separator + "upload");
		InputStream stream = url.openConnection().getInputStream();
		DownloadFile file = new DownloadFile(name, stream);
		if ("UploadAction.pdf".equals(name)) {
			file.setName("UpdateAction用户指南.pdf");
			// 如果文件下载时,中文名称变乱码,可以参考下列代码自定义转码方式,并设置DownloadFile的fileName属性
			// file.setFileName(new
			// String("UpdateAction用户指南.pdf".getBytes("UTF-8"),"iso8859-1"));
		}
		return file;

	}

	@FileProvider
	public DownloadFile downloadInline(Map<String, String> parameter)
			throws IOException {
		String fileName = parameter.get("file");
		DownloadFile file = getDownloadFile(fileName);
		return file;
	}

	@FileProvider
	public DownloadFile download(Map<String, String> parameter)
			throws IOException {
		String fileName = parameter.get("fileName");
		String path = System.getProperty("java.io.tmpdir") + File.separator
				+ "upload" + File.separator + fileName;
		File file = new File(path);
		InputStream is = new FileInputStream(file);
		DownloadFile dwfile = new DownloadFile(fileName, is);
		// DownloadFile file = getDownloadFile(fileName);
		return dwfile;
	}

	@FileProvider
	public DownloadFile showImage(Map<String, String> parameter)
			throws IOException {
		String fileName = parameter.get("file");
		ParameterUtils.validateParameterCharacters(fileName);
		String realPath = DoradoContext.getCurrent().getRequest().getSession()
				.getServletContext().getRealPath("\\");
		if (realPath.endsWith("\\"))
			realPath = realPath.substring(0, realPath.length() - 1);
		realPath += "images" + File.separator + fileName;
		return new DownloadFile(new File(realPath));
	}

}
