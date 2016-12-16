package com.finance.communication.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SuppressWarnings("deprecation")
public class MultiFileUpload {
	
	// 保存普通 form 表单域
	private Map<String, String> parameters;
	// 保存上传文件
	private List<FileItem> files;
	// 文件路径
	private List<String> listFilePath = new ArrayList<String>();
	// 文件新名字
	private List<String> listFileName = new ArrayList<String>();
	// 文件新名字【数据库中使用：不包含中文的名字】
	private List<String> listFileNameCN = new ArrayList<String>();
	// 文件旧名字
	private List<String> listFileNameOld = new ArrayList<String>();
    
	// 设置编码格式，推荐编码格式和jsp的编码格式一样
	private String encoding = "UTF-8";
	// 最多上传10兆
	private long sizeMax = 1000 * 1024 * 1024;
	// 超过一兆采用临时文件缓存
	private int sizeThreshold = 1024 * 1024;
	// 文件超出缓冲区大小时的临时存放目录
	private String repositoryPath = "temp";
	//上传图片存放路径
	private String file_path = "upload"+File.separator+"qd_images"+File.separator;
	//是否重命名
	private int isRename = 1;       // 1：重命名  其他:不不重名
	//是否复制两份文件
	private int isCopyDouble = 0;   // 1:复制两份 其他:一份
	
	
	public MultiFileUpload() {
	}

	public MultiFileUpload(String path) {
		this.file_path = path;
	}
	
	public MultiFileUpload(String path, int isRename) {
		this.file_path = path;
		this.isRename = isRename;
	}
	public MultiFileUpload(String path, int isRename, int isCopyDouble) {
		this.file_path = path;
		this.isRename = isRename;
		this.isCopyDouble = isCopyDouble;
	}	
	public void handeRequestSpringMVC(HttpServletRequest request){
		    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        //判断 request 是否有文件上传,即多部分请求  
	        if(multipartResolver.isMultipart(request)){  
	            //转换成多部分request    
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	            //取得request中的所有文件名  
	            Iterator<String> iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //取得上传文件  
	                MultipartFile file = multiRequest.getFile(iter.next());  
	                if(file != null){  
	                    //取得当前上传文件的文件名称  
	                    String myFileName = file.getOriginalFilename();  
	                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
	                    if(myFileName.trim() !=""){  
	                    	String oldName  = file.getOriginalFilename();
	                        //重命名上传后的文件名  
	                        String fileName = 1 == isRename ? this.getUUID() + oldName : oldName;  
	                	    String path = request.getSession(false).getServletContext().getRealPath(file_path);
	                	    File f2 = new File(path);
	                	    if(!f2.exists()){
	                	    	f2.mkdirs();
	                	    }
	                        try {
	                        	//定义上传路径  
	                        	File localFile = new File(path + File.separator + fileName);
	                        	if( localFile.isFile() && localFile.exists()){
	                        		localFile.delete();
	                        	}
								file.transferTo(localFile);
								/*if(1 == isCopyDouble){
									String temp_fileName = this.getUUID() + fileName.substring(fileName.lastIndexOf("."));
									File localFile2 = new File(path + File.separator + temp_fileName);
									this.copyFile2(localFile, localFile2);
									listFileNameCN.add(temp_fileName);
								}*/
							} catch (Exception e) {
								e.printStackTrace();
							}  
	                        listFileName.add(fileName);
	                        listFilePath.add(path+File.separator+fileName);
	                        listFileNameOld.add(oldName);
	                    }  
	                }  
	            }  
	              
	        }  
	}
	
	/**
	 * 方法2
	 * 
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void handleRequest(HttpServletRequest request) throws Exception {
		parameters = new HashMap<String, String>();
		files = new ArrayList<FileItem>();
		Date date = null;
		String ymd  = "";
		String suffix ="";
        InputStream io  =  null;
        OutputStream os =  null;
		File f = new File(repositoryPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		ServletContext application = request.getSession(false).getServletContext();
	    
	    String path = application.getRealPath(file_path);
	    //System.out.println("file_path:"+file_path+"\n"+"path:"+path);
	    File f2 = new File(path);
	    //System.out.println("d:"+file.exists());
	    if(!f2.exists()){
	    	f2.mkdirs();
	    }
		
		
		
		DiskFileUpload fu = new DiskFileUpload();
		fu.setSizeMax(sizeMax);
		fu.setSizeThreshold(sizeThreshold);
		fu.setHeaderEncoding(encoding);
		fu.setRepositoryPath(repositoryPath);
		// 存放表单中的所有字段对象的集合
		List<FileItem> fileItems = fu.parseRequest(request);
		Iterator<FileItem> i = fileItems.iterator();
		while (i.hasNext()) {
			// 处理每一个表单字段
			FileItem fi = i.next();
			if (fi.isFormField()) {
				String content = fi.getString(encoding);
				String fieldName = fi.getFieldName();
				parameters.put(fieldName, content);
			} else {
				try {
					String pathSrc = fi.getName();
					// 如果用户没有选择任何文件就忽略对该字段的处理
					if (pathSrc.trim().equals("")) {
						continue;
					}
					io = fi.getInputStream();
					suffix = pathSrc.split("\\.")[pathSrc.split("\\.").length-1];
					ymd  = "qd_"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+"."+suffix;
	    		    os = new FileOutputStream(f2+File.separator+ymd);
	    		    byte[] bs = new byte[1024];
	    			while(io.read(bs) > 0){
	    				os.write(bs);
	    			}
	    			if(io != null){
	    				io.close();
	    			}
	    			if(os!=null){
	    				os.close();
	    			}
	    			listFilePath.add(file_path.replace("\\", "/")+ymd);
	    			listFileName.add(ymd);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}// end of while
        
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	public List<FileItem> getFiles() {
		return files;
	}

	public void setFiles(List<FileItem> files) {
		this.files = files;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public int getSizeThreshold() {
		return sizeThreshold;
	}

	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) {
		this.repositoryPath = repositoryPath;
	}


	public String getQdyh_path() {
		return file_path;
	}

	public void setQdyh_path(String file_path) {
		this.file_path = file_path;
	}

	public long getSizeMax() {
		return sizeMax;
	}




	public List<String> getListFilePath() {
		return listFilePath;
	}

	public void setListFilePath(List<String> listFilePath) {
		this.listFilePath = listFilePath;
	}

	public List<String> getListFileName() {
		return listFileName;
	}

	public void setListFileName(List<String> listFileName) {
		this.listFileName = listFileName;
	}

	public List<String> getListFileNameOld() {
		return listFileNameOld;
	}

	public void setListFileNameOld(List<String> listFileNameOld) {
		this.listFileNameOld = listFileNameOld;
	}

    public List<String> getListFileNameCN() {
		return listFileNameCN;
	}

	public void setListFileNameCN(List<String> listFileNameCN) {
		this.listFileNameCN = listFileNameCN;
	}
    
	/**
	 * 文件复制 【旧方法】
	 * @param sourceFile
	 * @param targetFile
	 * @throws Exception
	 */
	private void copyFile(File sourceFile , File targetFile) throws Exception{
    	BufferedInputStream biStream = null;
    	BufferedOutputStream boStream = null;
    	try {
    		biStream = new BufferedInputStream(new FileInputStream(sourceFile));
    		boStream = new BufferedOutputStream(new FileOutputStream(targetFile));
    		byte[] b = new byte[1024 * 5];
    		int i ;
    		while( (i = biStream.read(b)) != -1 ){
    			boStream.write(b, 0, i);
    		}
		 } catch (Exception e) {
		}finally{
			if(null != biStream){
				biStream.close();
			}
			if(null != boStream){
				boStream.close();
			}
		}
    	
    }
	
	/**
	 * 文件复制 【新方法  优点：速度更快】
	 * @param sourceFile
	 * @param targetFile
	 * @throws Exception
	 */
	private void copyFile2(File sourceFile , File targetFile) throws Exception{
		    FileInputStream fi = null;
	        FileOutputStream fo = null;
	        FileChannel in = null;
	        FileChannel out = null;
	        try {
	            fi = new FileInputStream(sourceFile);
	            fo = new FileOutputStream(targetFile);
	            in = fi.getChannel();//得到对应的文件通道
	            out = fo.getChannel();//得到对应的文件通道
	            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                fi.close();
	                in.close();
	                fo.close();
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
     }
	/**
	 * 获取32位UUID码
	 * 
	 * @param decDeshs
	 *            true：去除横杠，false：不去除，默认为true
	 * @return
	 */
	private static String getUUID(boolean... decDeshs) {
		boolean decDesh = (null == decDeshs || 1 > decDeshs.length) ? true
				: decDeshs[0];
		String uuid = UUID.randomUUID().toString();
		if (decDesh)
			uuid = uuid.replaceAll("-", "");
		return uuid;
	}
}
