package com.td.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.td.common.constant.CommonConstant;
import com.td.common.exception.CommonException;

/**
 * @description: 文件的解压、压缩
 * @author: cmh 2013-6-20
 * @version: 1.0
 * @modify:
 * @Copyright: 公司版权拥有
 */
public class ZipUtil {

    private static Logger logger = Logger.getLogger(ZipUtil.class);

    private static String encoding = "gbk";
    
    private static OutputStream out = null;
    private static BufferedOutputStream bos = null;
    private static ZipArchiveOutputStream zaos = null;

    private static String zipFile = null;
    private static String zipFileName = null;
        
    /**
     * 压缩目录下文件
     * @param dirpath 需要压缩的文件路径或单个文件，如：D:/webapps
     * @param zipPath 压缩文件路径 ，如：D:/webapps/zip
     * @param zipName 压缩文件名称 ，如：test.zip后缀可以定义其他的
     * @return true成功，false失败 ，
     * @throws CommonException 
     */
    public static boolean zip(String dirpath, String zipPath, String zipName) {
        zipFileName = zipName;
        zipFile = zipPath + File.separator+ zipName;
        
        boolean flag = true;
        try {
            createZipOut();
            packToolFiles(dirpath, "");
        }
        catch (FileNotFoundException e) {
            logger.error("文件压缩失败！", e);
//            throw new CommonException("zip.compress.fail", e, new Object[]{"压缩目录下文件"});
        }
        catch (IOException e) {
            logger.error("文件压缩失败！", e);
//            throw new CommonException("zip.compress.fail", e, new Object[]{"压缩目录下文件"});
        }catch (Exception e) {
            logger.error("文件压缩失败！", e);
//            throw new CommonException("zip.compress.fail", e, new Object[]{"压缩目录下文件"});
        } finally {
            try {
                closeZipOut();
            }
            catch (Exception e) {
                logger.error("文件压缩关闭流失败！", e);
//                throw new CommonException("zip.close.exception", e, new Object[]{"压缩目录下文件"});
                
            }
        }
        
        return flag;
        
    }
    
    /**
     * 压缩不同目录下的多个文件
     * @param zipFile 压缩后的zip文件路径 ,如"D:/test/aa.zip";
     * @param files 需要压缩的文件,如zipFiles("D:/ziptest.zip", "D:/ziptest/1.txt", "D:/2.jar","D:/test/3.doc");
     * @throws CommonException 
     */
    public static boolean zipFiles(String zipFile, String... files){
        boolean flag = true;
        InputStream in = null;
        File outFile = new File(zipFile);
        try {
            zaos = new ZipArchiveOutputStream(outFile);
            zaos.setEncoding(encoding);
            
            for (int i = 0; i < files.length; i++) {
                File file = new File(files[i]);
                if(file.exists() && file.isFile()) {
                        //zipFiles(f);
                    in = new FileInputStream(file);
                    zaos.putArchiveEntry(new ZipArchiveEntry(file.getName()));
                    IOUtils.copy(in, zaos);
                    zaos.closeArchiveEntry();
                    if(in != null) {
                        in.close();
                    }
                } else {
                    logger.warn("[" + files[i] + "]文件不存在");
                    //throw new IOException();
                }
            }
        } catch (IOException e) {
            logger.error("文件压缩失败！", e);
//            throw new CommonException("zip.compress.fail", e, new Object[]{"压缩不同目录下的多个文件"});
        } finally{
            try {
                if(zaos != null) {
                    zaos.close();
                }
                if(in != null) {
                    in.close();
                }
            }
            catch (IOException e) {
                logger.error("文件压缩关闭流失败！", e);
//                throw new CommonException("zip.close.exception", e, new Object[]{"压缩不同目录下的多个文件"});
            }
        }
        
        return flag;
        
    }
    
    /**
     * 压缩不同目录下的多个文件，同时更改文件名
     * @param zipFile 压缩后的zip文件路径 ,如"D:/test/aa.zip";
     * @param files 需要压缩的文件同时需要更改的文件名,如zipFiles[{"D:/ziptest.zip", "1.rar"}, {"D:/ziptest/1.txt","1.ux"}, {"D:/2.jar", "2.txt"}];
     * @throws CommonException 
     */
    public static boolean zip2Rename(String zipFile, Map<String, String> files) {
        boolean flag = true;
        File outFile = new File(zipFile);
        InputStream in = null;
        try {
            zaos = new ZipArchiveOutputStream(outFile);
            zaos.setEncoding(encoding);
            
            for(Entry<String, String> entry : files.entrySet()) {
                String orgFile = entry.getKey();
                File file = new File(orgFile);
                
                String renameFile = entry.getValue() == null ? file.getName() : entry.getValue();
                
                if(file.exists() && file.isFile()) {
                    zaos.putArchiveEntry(new ZipArchiveEntry(file, renameFile));
                    in = new FileInputStream(file);
                    IOUtils.copy(in, zaos);
                    zaos.closeArchiveEntry();
                        
                } else {
                    logger.warn("[" + orgFile + "]文件不存在");
                    //throw new IOException();
                }
                
            }
        } catch (IOException e) {
            logger.error("文件压缩失败！", e);
//            throw new CommonException("zip.compress.fail", e, new Object[]{"压缩不同目录下的多个文件"});
        } finally{
            try {
                if(zaos != null) {
                    zaos.close();
                }
                if(in != null) {
                	in.close();
                }
            }
            catch (IOException e) {
                logger.error("文件压缩关闭流失败！", e);
//                throw new CommonException("zip.close.exception", e, new Object[]{"压缩不同目录下的多个文件"});
            }
        }
        
        return flag;
        
    }
    /**
     * 把一个ZIP文件解压到一个指定的目录中
     * @param zipfilename  ZIP文件地址， 如：D:/webapps/test.zip
     * @param outputdir  目录绝对地址，如：D:/webapps/unzip
     * @throws CommonException 
     */
    public static boolean unZip(String zipfilename, String outputdir) {
        boolean flag = true;
        File zipfile = new File(zipfilename);
        ZipFile zf = null;
        FileOutputStream out = null;
        InputStream in = null;
        
        try {
            if (zipfile.exists()) {
                outputdir = outputdir + File.separator;
                FileUtils.forceMkdir(new File(outputdir));
    
                zf = new ZipFile(zipfile, encoding);
                Enumeration<ZipArchiveEntry> zipArchiveEntrys = zf.getEntries();
                while (zipArchiveEntrys.hasMoreElements()) {
                    ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                    if (zipArchiveEntry.isDirectory()) {
                        FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
                    }
                    else {
                        out = FileUtils.openOutputStream(new File(outputdir + zipArchiveEntry.getName()));
                        in = zf.getInputStream(zipArchiveEntry);
                        IOUtils.copy(in, out);
                        if(out != null) {
                            out.close();
                        }
                        if(in != null) {
                            in.close();
                        }
                    }
                }
            }
            else {
                logger.warn("指定的解压文件不存在：\t" + zipfilename);
                return flag = false;
            }
        }
        catch (IOException e) {
            logger.error("解压缩文件出错！", e);
//            return flag = false;
//            throw new CommonException("zip.uncompress.fail", e, new Object[]{});
        } finally {
            try {
                if(zf != null) {
                    zf.close();
                }
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            }
            catch (Exception e) {
                logger.error("文件解压缩关闭流失败！", e);
//                throw new CommonException("zip.uncompress.close.exception", e, new Object[]{});
            }
        }
        return flag;
    }
    
    /**
     * 把一个ZIP文件解压到一个指定的目录中
     * @param zipfile  File对象
     * @param outputdir  目录绝对地址，如：D:/webapps/unzip
     * @param isUseUUID 是否使用UUID作为新的文件名
     * @param enableUploadFileSizeInZip zip文件中允许上传的文件数，-1为不限制
     * @throws CommonException 
     */
    public static Map<String, String> unZip(File zipfile, String outputdir, boolean isUseUUID, int enableUploadFileSizeInZip) throws CommonException {
        //List<String> fileNames = new ArrayList<String>();
    	logger.debug("看看压缩文件的路径====" + zipfile.getName());
    	
        Map<String, String> fileNameMap = new HashMap<String, String>();
        
        File outputFile = null;
        ZipFile zf = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            outputdir = outputdir + File.separator;
            FileUtils.forceMkdir(new File(outputdir));

            zf = new ZipFile(zipfile, encoding);
            Enumeration<ZipArchiveEntry> zipArchiveEntrys = zf.getEntries();
            
            if(enableUploadFileSizeInZip != -1) {
            	Enumeration<ZipArchiveEntry> zipArchiveEntrysList = zf.getEntries();
            	int files = 0;
            	while (zipArchiveEntrysList.hasMoreElements()) {
            		zipArchiveEntrysList.nextElement();
            		files++;
            	}
            	
            	if(files > enableUploadFileSizeInZip) {
            		fileNameMap.put(CommonConstant.WARN_INFO, "zip文件中有" + files + "个文件，超过系统设定的允许上传zip文件中只能有" + enableUploadFileSizeInZip + "个文件，请重新调整要上传的zip文件！");
            		return fileNameMap;
            	}
            }

            while (zipArchiveEntrys.hasMoreElements()) {
                ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
                if (zipArchiveEntry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputdir + zipArchiveEntry.getName() + File.separator));
                }
                else {
                    String zipArchiveEnteryName = zipArchiveEntry.getName();
                    String suffix = "";
                    if(isUseUUID) {
                        
                        suffix = zipArchiveEnteryName.substring(zipArchiveEnteryName.lastIndexOf("."));
                        outputFile = new File(outputdir + CommonUtil.getUUID() + suffix);
                        fileNameMap.put(zipArchiveEnteryName, outputFile.getName());
                    } else {
                        outputFile = new File(outputdir + zipArchiveEnteryName);
                        fileNameMap.put(zipArchiveEnteryName, zipArchiveEnteryName);
                    }
                    
                    logger.debug(outputFile.getName());
                    in = zf.getInputStream(zipArchiveEntry);
                    out = FileUtils.openOutputStream(outputFile);
                    IOUtils.copy(in, out);
                    if(out != null) {
                        out.close();
                    }
                    if(in != null) {
                        in.close();
                    }
//                    if(enableUploadFileSizeInZip != -1) {
//                    	uploadedFiles += 1;
//                    	if(uploadedFiles == enableUploadFileSizeInZip && zipArchiveEntrys.hasMoreElements()) {
//                    		fileNameMap.put(Constant.WARN_INFO, "zip文件中有多个文件，系统设定允许上传zip文件中的" + enableUploadFileSizeInZip + "个文件，当前只上传成功zip文件中的" + enableUploadFileSizeInZip + "个文件！");
//                    		break;
//                    	}
//                    }
                    
                }
            }
        }catch (ZipException e) {
            logger.error("解压缩文件出错！", e);
//            fileNameMap.put(CommonConstant.RESPONSE_SEND_PAGE_ERROR_MSG, "解压缩文件出错!当前文件[" + zipfile.getName() + "]不是zip文件格式，请压缩时使用zip文件格式");
//            return fileNameMap;
        	throw new CommonException("zip.not.zip.archive", e, new Object[]{zipfile.getName()}, zipfile.getName());
		}
        catch (IOException e) {
            logger.error("解压缩文件出错！", e);
            //fileNameMap.put(CommonConstant.RESPONSE_SEND_PAGE_ERROR_MSG, zipfile.getName() + "解压缩文件出错!");
            //return fileNameMap;
//            return flag = false;
            throw new CommonException("zip.uncompress.fail", e, new Object[]{zipfile.getName()}, zipfile.getName());
        } finally {
            try {
                if(zf != null) {
                    zf.close();
                }
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            }
            catch (Exception e) {
                logger.error("文件解压缩关闭流失败！", e);
                throw new CommonException("zip.uncompress.close.exception", e, new Object[]{zipfile.getName()}, zipfile.getName());
            }
        }
        return fileNameMap;
    }
    
    
    /**
     * 创建压缩输入流
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void createZipOut() throws FileNotFoundException, IOException {
        File f = new File(zipFile);
        out = new FileOutputStream(f);
        bos = new BufferedOutputStream(out);
        zaos = new ZipArchiveOutputStream(bos);
        zaos.setEncoding(encoding);
    }
    
    /**
     * 把一个目录打包到ZIP文件中的某目录
     * @param dirpath 目录绝对地址
     * @param pathName ZIP中目录
     * @throws CommonException 
     */
    private static void packToolFiles(String dirpath, String pathName) throws FileNotFoundException, IOException {
        if (StringUtils.isNotEmpty(pathName)) {
            pathName = pathName + File.separator;
        }
        packToolFiles(zaos, dirpath, pathName);
    }

    /**
     * 把一个目录打包到一个指定的ZIP文件中，如果是单个文件那就只压缩一个文件
     * @param dirpath 目录绝对地址
     * @param pathName ZIP文件抽象地址
     * @throws CommonException 
     */
    private static void packToolFiles(ZipArchiveOutputStream zaos, String dirpath, String pathName) throws FileNotFoundException, IOException {
        File dir = new File(dirpath);
        
        if(dir.isFile()) {//如果只是一个文件
            writeZip(zaos, pathName + dir.getName(), dir.getAbsolutePath());
            return;
        }
        // 返回此绝对路径下的文件
        File[] files = dir.listFiles();
        
        if (files == null || files.length < 1) {
            return;
        }
        
        logger.debug(files.length);
        
        for (int i = 0; i < files.length; i++) {
            String file = pathName + files[i].getName();
            String absolutePath = files[i].getAbsolutePath();
            
            // 判断此文件是否是一个文件夹
            if (files[i].isDirectory()) {
                packToolFiles(zaos, absolutePath, file + File.separator);
            }
            else {
                logger.debug(pathName + files[i].getName());
                logger.debug(" ==" + zipFileName);
                if(!zipFileName.equals(files[i].getName())) {
                    writeZip(zaos, file, absolutePath);
                }
            }
        }
    }

    /**
     * 写入zip文件
     * @param zaos 
     * @param file 文件包括路径
     * @param absolutePath 绝对路径
     * @throws CommonException 
     * @throws IOException
     * @throws FileNotFoundException
     */
    private static void writeZip(ZipArchiveOutputStream zaos, String file, String absolutePath) {
        InputStream in = null;
        try {
            zaos.putArchiveEntry(new ZipArchiveEntry(file));
            in = new FileInputStream(absolutePath);
            IOUtils.copy(in, zaos);
            zaos.closeArchiveEntry();
        }
        catch (IOException e) {
            logger.error("写入zip文件失败！", e);
//            throw new CommonException("zip.write.file.fail", e, new Object[]{});
        } finally {
            if(in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                    logger.error("写入zip文件失败！", e);
//                    throw new CommonException("zip.write.file.fail", e, new Object[]{});
                }
            }
        }
        
    }

    private static void closeZipOut() throws Exception {
        zaos.flush();
        zaos.close();

        bos.flush();
        bos.close();

        out.flush();
        out.close();
    }
    
    public static void main(String[] args) {
        logger.debug("start...");
        
//        String zipPath = "D:/webapps";
//        String zipName = "test.a";
//        String file = "D:/webapps";
//        try {
//            logger.debug(zip(file, zipPath, zipName));
//        }
//        catch (CommonException e1) {
//            logger.error(ErrorMessageHelper.getErrorMessage(e1));
//        }
        
//        try {
//            File zipFile = new File("D:/webapps/test.a");
//            logger.debug(unZip(zipFile, "D:/webapps/unzip"));
//        }
//        catch (CommonException e) {
//            logger.error(ErrorMessageHelper.getErrorMessage(e));
//        }
       
//        try {
//            logger.debug(zip("D:/webapps/zipFilesTest.a", "D:/webapps/新建 文本文档.txt", "D:/treenode.sql1", "D:/TEST/ReportBat_Bx(2012-12-18).xls", "D:/TEST/ReportBat_Bx(2012-12-18).xls", "D:/liop_log/ec_debug.log.1"));
//        }
//        catch (CommonException e) {
//           logger.error(ErrorMessageHelper.getErrorMessage(e));
//           
//        }
        Map<String, String> reMap = new HashMap<String, String>();
        reMap.put("D:/webapps/test1.txt", "1.txt");
        reMap.put("D:/webapps/test2-download.txt", "2.txt");
        zip2Rename("D:/webapps/rename.zip", reMap);
        
           
        
        logger.debug("finish...");
    }
}
