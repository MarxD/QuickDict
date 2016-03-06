package com.gsd09.services;

import com.gsd09.TApplication;
import com.gsd09.QuickDict.R;
import com.gsd09.entity.Word;
import com.gsd09.util.Const;
import com.gsd09.util.LocalDictUtil;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.util.Log;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/**
 * 自定义服务，继承IntentService，监听剪贴板数据变化并弹出翻译结果
 * @author DJN
 */
public class ClipBroadService  extends IntentService implements OnPrimaryClipChangedListener {
	private static int sourcefrom=3;//未用
	private static int notificationId=1;
	private BroadcastReceiver receiver;
	private IntentFilter inf;
	private static ClipboardManager cm;
	public  Context context = TApplication.c;
	
	public ClipBroadService()
	{
		super("ClipBoardService");
	}
	
	@Override
	public void onCreate() {
		regReceiver();
		Log.i("servicestate", "oncreate");
		super.onCreate();
	}

	/**
	 * 注册广播接收器
	 */
	private void regReceiver() 
	{
		receiver = new ClipBroadReceiver();
		inf = new IntentFilter();
		inf.addAction(Const.ACTION_SERVICE_STOP);
		inf.addAction(Const.ACTION_CHANGED_SOURCE);
		this.registerReceiver(receiver, inf);
		
	}
	
  public void onStart(Intent intent, int startId) {};
	
	@Override
	public void onDestroy() {
		Log.i("servicestate", "销毁");
		super.onDestroy();
	}

	/**
	 * 服务被系统kill之后重启服务并重新发送最后一次发送的Intent
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{	
		Log.i("servicestate", "onStartCommand");
		setClipBroadListener();
		return Service.START_REDELIVER_INTENT;
	}
	
	

	/**
	 * 设置剪贴板监听，在收到停止广播后移除监听
	 */
	private void setClipBroadListener() 
	{
		 cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		cm.addPrimaryClipChangedListener(this); 
;
	}
	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 判断是否为英文字符串
	 * @param text String型数据 待判断字符串
	 * @return 判断结果
	 */
	public boolean checkIsEnglish(String text)
	{
		for(int i=0;i<text.length();i++)
		{ 
			int asc =(int)text.charAt(i);
			if((asc<65)||(asc>122)||asc>90&asc<97)return false;
		}
		Log.i("servicestate", "还在监听");
		return true;
	}
	
	/**
	 * 重写接口类的方法，剪贴板状态改变后获取当前板内字符串，作出相应处理
	 */
	@Override
	public void onPrimaryClipChanged() {
		ClipData cd = cm.getPrimaryClip();
		String source = cd.getItemAt(0).getText().toString();
		if(checkIsEnglish(source))
		{
			showTranslation(source);
		}
		
	}
	/**
	 * 查询翻译并显示于通知栏
	 * @param source String型数据  待查单词
	 */
	private void showTranslation(String source) {
		Word clip = new Word();
		new LocalDictUtil(context).query(source,clip);
		NotificationManager nfm= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note;
		Notification.Builder build = new Notification.Builder(context).setTicker("查询结果").setSmallIcon(R.drawable.app_icon).setContentText(clip.gettranslation()).setContentTitle(clip.getsourceword());
		nfm.cancel(notificationId);
		note = build.build();
		nfm.notify(notificationId,note);
		
	}

	/**
	 * 自定义接收器，用于接收关闭广播 
	 * @author DJN
	 *
	 */
	class ClipBroadReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) 
		{
			String action = intent.getAction();
			if(action.equals(Const.ACTION_CHANGED_SOURCE))//未使用
			{
				sourcefrom=intent.getIntExtra("from", 3);
			}
			if(action.equals(Const.ACTION_SERVICE_STOP))
			{
				Log.i("servicestate", "关闭");
				cm.removePrimaryClipChangedListener(ClipBroadService.this);//移除监听
				stopSelf();//停止服务
			}
			
		}
		
	}




}
