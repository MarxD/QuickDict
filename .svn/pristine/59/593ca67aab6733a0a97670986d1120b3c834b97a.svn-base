package com.gsd09.activity;

import com.gsd09.TApplication;
import com.gsd09.QuickDict.R;
import com.gsd09.services.ClipBroadService;
import com.gsd09.util.Const;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

/**
 * 设置页面，包含一个Switch与一个RadioGroup，分别负责服务开关与查询数据源选择
 * @author DJN
 *
 */
public class OptionFragment extends BaseFragment implements OnCheckedChangeListener, android.widget.RadioGroup.OnCheckedChangeListener 
{
	String name ="设置";
	RadioGroup rg_source;
	RadioButton rb_local,rb_baidu,rb_youdao;//三个数据源
	Switch switch_clip;
	View v;
	SharedPreferences setting;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setting = PreferenceManager.getDefaultSharedPreferences(TApplication.c);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment_option, container,false);
		setView();
		setListener();
		return v;
	}
	/**
	 * 设置单选按钮及切换开关的监听,单选按钮暂时未启用  2016.3.6 Marx
	 */
	private void setListener() {
		switch_clip.setOnCheckedChangeListener(this);
		rg_source.setOnCheckedChangeListener(this);
		
	}
	/**
	 * 初始化控件，读取SharedPreferences并设置状态
	 */
	private void setView() 
	{
		rg_source = (RadioGroup) v.findViewById(R.id.rg_source);
		switch_clip = (Switch)v.findViewById(R.id.sw_clip);
		rb_local = (RadioButton)v.findViewById(R.id.rb_local);
		rb_baidu = (RadioButton)v.findViewById(R.id.rb_baidu);
		rb_youdao = (RadioButton)v.findViewById(R.id.rb_youdao);
		switch_clip.setChecked(setting.getBoolean("running", false));
		
	}
	
	@Override
	public String getmyname() {
		// TODO Auto-generated method stub
		return name;
	}
	
	/**
	 * 重写监听器事件
	 * 数据源改变后发送相应广播，未启用   2016.3.6 Marx
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) 
	{
		switch (checkedId) {
		case R.id.rb_local:
			
			break;
		case R.id.rb_baidu:
			
			break;
		default:
			
			break;
		}
		
	}
	/**
	 * 重写监听器事件
	 * 监听开关状态，状态改变后启动服务或发送停止广播
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) 
		{
			//rg_source.setVisibility(View.VISIBLE);
			Intent i = new Intent(TApplication.c,ClipBroadService.class);
			getActivity().startService(i);
		}
		else
		{
			//rg_source.setVisibility(View.GONE);
			Intent stopIntent = new Intent();
			stopIntent.setAction(Const.ACTION_SERVICE_STOP);
			TApplication.c.sendBroadcast(stopIntent);
		}
		Editor editor = setting.edit();
		editor.putBoolean("running", switch_clip.isChecked());
		editor.commit();
		
	}
	
}
