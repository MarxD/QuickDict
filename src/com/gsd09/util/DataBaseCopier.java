package com.gsd09.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

/**
 *  DataBaseCopier��
 *  ʵ�ֽ�assets�µ��ļ���Ŀ¼�ṹ������sdcard��
 *    
 *  @author ticktick
 *  @Email lujun.hust@gmail.com
 */
public class DataBaseCopier {

  private static final String ASSET_LIST_FILENAME = "filelist.lst";

  private final Context mContext;
  private final AssetManager mAssetManager;
  private File mAppDirectory;	
  
  public DataBaseCopier( Context context ) {
  mContext = context;
  mAssetManager = context.getAssets();		
  }
    
  /**
   *  ��assetsĿ¼��ָ�����ļ�������sdcard��
   *  @return �Ƿ񿽱��ɹ�,true �ɹ���false ʧ��
   *  @throws IOException
   */
  public boolean copy() throws IOException {

      List<String> srcFiles = new ArrayList<String>();
    
      //��ȡϵͳ��SDCard��Ϊapp�����Ŀ¼��eg:/sdcard/Android/data/$(app's package)
      //��Ŀ¼���app��صĸ����ļ�(��cache�������ļ���)��unstall app���Ŀ¼Ҳ����֮ɾ��	
      mAppDirectory = mContext.getExternalFilesDir(null);
  if (null == mAppDirectory) {
      return false;
  }
    
  Builder builder = new AlertDialog.Builder(mContext).setTitle("���Ե�").setMessage("�������룬�ʿ��ļ�������").setCancelable(false);
  //��ȡassets/$(subDirectory)Ŀ¼�µ�filelist.lst�ļ����õ���Ҫcopy���ļ��б�
  List<String> assets = getAssetsList(); 
  for( String asset : assets ) {        	
        	//��������ڣ�����ӵ�copy�б�
        	if( ! new File(mAppDirectory,asset).exists() ) {
        		srcFiles.add(asset);
        	}
  }        	  
        
  //���ο�����App�İ�װĿ¼��
  AlertDialog dialog = builder.show();
  for( String file : srcFiles ) {
      copy(file);
  }
    
  dialog.dismiss();
      return true;
  }
  
  /**
   *  ��ȡ��Ҫ�������ļ��б���¼��assets/filelist.lst�ļ��У�
   *  @return �ļ��б�
   *  @throws IOException
   */
  protected List<String> getAssetsList() throws IOException {

      List<String> files = new ArrayList<String>();
    
      InputStream listFile = mAssetManager.open(new File(ASSET_LIST_FILENAME).getPath());
      BufferedReader br = new BufferedReader(new InputStreamReader(listFile));
      String path;
  while (null != (path = br.readLine())) {
        	files.add(path);
    	    }
        
  return files;
  }
    
  /**
   *  ִ�п�������
   *  @param asset ��Ҫ������assets�ļ�·��
   *  @return �����ɹ����Ŀ���ļ����
   *  @throws IOException
   */
  protected File copy( String asset ) throws IOException {
    
        InputStream source = mAssetManager.open(new File(asset).getPath());
        File destinationFile = new File(mAppDirectory, asset);
        destinationFile.getParentFile().mkdirs();
        OutputStream destination = new FileOutputStream(destinationFile);
        byte[] buffer = new byte[1024];
        int nread;

        while ((nread = source.read(buffer)) != -1) {
  if (nread == 0) {
      nread = source.read();
      if (nread < 0)
          break;
      destination.write(nread);
      continue;
  }
  destination.write(buffer, 0, nread);
        }
        destination.close();
        
        return destinationFile;
    }
}