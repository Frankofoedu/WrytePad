package com.WrytePad;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = true;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "com.WrytePad", "com.WrytePad.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "com.WrytePad", "com.WrytePad.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "com.WrytePad.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _timer = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelsplash = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelsplash2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelsidebar = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelhome = null;
public anywheresoftware.b4a.objects.PanelWrapper _panelshadow = null;
public flm.b4a.animationplus.AnimationPlusWrapper _animation = null;
public static boolean _boolslide = false;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1 = null;
public static int _conpanheight = 0;
public static int _conpanwidth = 0;
public static int _pantop = 0;
public com.WrytePad.starter _starter = null;
public com.WrytePad.add _add = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (add.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 41;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"1\")";
mostCurrent._activity.LoadLayout("1",mostCurrent.activityBA);
 //BA.debugLineNum = 46;BA.debugLine="ScrollView1.Panel.Width = ScrollView1.Width";
mostCurrent._scrollview1.getPanel().setWidth(mostCurrent._scrollview1.getWidth());
 //BA.debugLineNum = 50;BA.debugLine="timer.Initialize(\"timer\",3000)";
_timer.Initialize(processBA,"timer",(long) (3000));
 //BA.debugLineNum = 51;BA.debugLine="timer.Enabled = True";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 54;BA.debugLine="animation.InitializeRotateCenter(\"\", 0, 360, Labe";
mostCurrent._animation.InitializeRotateCenter(mostCurrent.activityBA,"",(float) (0),(float) (360),(android.view.View)(mostCurrent._label4.getObject()));
 //BA.debugLineNum = 55;BA.debugLine="animation.Duration = 1500";
mostCurrent._animation.setDuration((long) (1500));
 //BA.debugLineNum = 57;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 59;BA.debugLine="If File.Exists(File.DirInternal,\"notes.db\") = Tr";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"notes.db")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 60;BA.debugLine="Log(\"yes\")";
anywheresoftware.b4a.keywords.Common.Log("yes");
 //BA.debugLineNum = 61;BA.debugLine="File.Delete(File.DirInternal,\"notes.db\")";
anywheresoftware.b4a.keywords.Common.File.Delete(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"notes.db");
 };
 //BA.debugLineNum = 64;BA.debugLine="If File.Exists(File.DirInternal,\"notes.db\") = Fa";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"notes.db")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 65;BA.debugLine="Starter.db.Initialize(File.DirInternal,\"notes.d";
mostCurrent._starter._db.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"notes.db",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 66;BA.debugLine="CreateDatabase";
_createdatabase();
 }else {
 //BA.debugLineNum = 68;BA.debugLine="Starter.db.initialize(File.DirInternal,\"notes.d";
mostCurrent._starter._db.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"notes.db",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 73;BA.debugLine="conPanHeight =ScrollView1.Panel.Height";
_conpanheight = mostCurrent._scrollview1.getPanel().getHeight();
 //BA.debugLineNum = 74;BA.debugLine="conPanWidth = ScrollView1.Panel.Width";
_conpanwidth = mostCurrent._scrollview1.getPanel().getWidth();
 //BA.debugLineNum = 75;BA.debugLine="panTop  = PanelHome.Height * 0.03";
_pantop = (int) (mostCurrent._panelhome.getHeight()*0.03);
 //BA.debugLineNum = 77;BA.debugLine="getNotes(ScrollView1)";
_getnotes(mostCurrent._scrollview1);
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 85;BA.debugLine="If UserClosed Then";
if (_userclosed) { 
 //BA.debugLineNum = 86;BA.debugLine="Starter.db.Close  'if the user closes the progra";
mostCurrent._starter._db.Close();
 };
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 192;BA.debugLine="getNotes(ScrollView1)";
_getnotes(mostCurrent._scrollview1);
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _createdatabase() throws Exception{
String _query = "";
 //BA.debugLineNum = 111;BA.debugLine="Sub CreateDatabase";
 //BA.debugLineNum = 112;BA.debugLine="Dim query As String = \"CREATE TABLE notes(ID INTE";
_query = "CREATE TABLE notes(ID INTEGER PRIMARY KEY, Title TEXT, Body TEXT, Date TEXT)";
 //BA.debugLineNum = 113;BA.debugLine="Starter.db.ExecNonQuery(query)";
mostCurrent._starter._db.ExecNonQuery(_query);
 //BA.debugLineNum = 114;BA.debugLine="End Sub";
return "";
}
public static String  _getnotes(anywheresoftware.b4a.objects.ScrollViewWrapper _sv) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _panel0 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl2 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl3 = null;
anywheresoftware.b4a.objects.PanelWrapper _npanel = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 143;BA.debugLine="Sub getNotes(sv As ScrollView)";
 //BA.debugLineNum = 144;BA.debugLine="Dim panel0 As Panel = sv.Panel";
_panel0 = new anywheresoftware.b4a.objects.PanelWrapper();
_panel0 = _sv.getPanel();
 //BA.debugLineNum = 146;BA.debugLine="Dim lbl1 As Label";
_lbl1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 147;BA.debugLine="Dim lbl2 As Label";
_lbl2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 148;BA.debugLine="Dim lbl3 As Label";
_lbl3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 150;BA.debugLine="lbl1.Initialize(\"lbl1\")";
_lbl1.Initialize(mostCurrent.activityBA,"lbl1");
 //BA.debugLineNum = 151;BA.debugLine="lbl2.Initialize(\"lbl2\")";
_lbl2.Initialize(mostCurrent.activityBA,"lbl2");
 //BA.debugLineNum = 152;BA.debugLine="lbl3.Initialize(\"lbl3\")";
_lbl3.Initialize(mostCurrent.activityBA,"lbl3");
 //BA.debugLineNum = 158;BA.debugLine="lbl1.Text = \"uvytdrctvybulkjjjjjjjjjjjjjjjjjjjjjj";
_lbl1.setText(BA.ObjectToCharSequence("uvytdrctvybulkjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"));
 //BA.debugLineNum = 159;BA.debugLine="lbl2.Text = \"ojhu\"";
_lbl2.setText(BA.ObjectToCharSequence("ojhu"));
 //BA.debugLineNum = 160;BA.debugLine="lbl3.Text = \"ojhg7f66y\"";
_lbl3.setText(BA.ObjectToCharSequence("ojhg7f66y"));
 //BA.debugLineNum = 163;BA.debugLine="lbl1.TextSize = 25";
_lbl1.setTextSize((float) (25));
 //BA.debugLineNum = 164;BA.debugLine="lbl1.TextColor = Colors.Black";
_lbl1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 166;BA.debugLine="lbl2.Gravity = Gravity.CENTER";
_lbl2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 168;BA.debugLine="lbl3.TextSize = 20";
_lbl3.setTextSize((float) (20));
 //BA.debugLineNum = 170;BA.debugLine="Dim nPanel As Panel";
_npanel = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 171;BA.debugLine="nPanel.Initialize(\"npanel\")";
_npanel.Initialize(mostCurrent.activityBA,"npanel");
 //BA.debugLineNum = 173;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 174;BA.debugLine="cd.Initialize2(Colors.ARGB(255,255,235,205), 5dip";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (235),(int) (205)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 176;BA.debugLine="nPanel.Background = cd";
_npanel.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 179;BA.debugLine="panel0.AddView(nPanel,conPanWidth * 0.03,panTop,c";
_panel0.AddView((android.view.View)(_npanel.getObject()),(int) (_conpanwidth*0.03),_pantop,(int) (_conpanwidth*0.94),(int) (_conpanheight*0.18));
 //BA.debugLineNum = 180;BA.debugLine="nPanel.AddView(lbl1,5dip,0,0.78 * conPanWidth, 0.";
_npanel.AddView((android.view.View)(_lbl1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),(int) (0),(int) (0.78*_conpanwidth),(int) (0.43*_npanel.getHeight()));
 //BA.debugLineNum = 181;BA.debugLine="nPanel.AddView(lbl2,lbl1.Width,0, 0.16 * conPanWi";
_npanel.AddView((android.view.View)(_lbl2.getObject()),_lbl1.getWidth(),(int) (0),(int) (0.16*_conpanwidth),_npanel.getHeight());
 //BA.debugLineNum = 182;BA.debugLine="nPanel.AddView(lbl3,5dip,lbl1.Height,0.78 * conPa";
_npanel.AddView((android.view.View)(_lbl3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),_lbl1.getHeight(),(int) (0.78*_conpanwidth),(int) (0.57*_npanel.getHeight()));
 //BA.debugLineNum = 184;BA.debugLine="panTop = panTop + (0.2 * conPanHeight)";
_pantop = (int) (_pantop+(0.2*_conpanheight));
 //BA.debugLineNum = 186;BA.debugLine="If panTop > panel0.Height Then";
if (_pantop>_panel0.getHeight()) { 
 //BA.debugLineNum = 187;BA.debugLine="panel0.Height = panel0.Height + (0.2 * conPanHei";
_panel0.setHeight((int) (_panel0.getHeight()+(0.2*_conpanheight)));
 };
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 26;BA.debugLine="Private PanelSplash As Panel";
mostCurrent._panelsplash = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private PanelSplash2 As Panel";
mostCurrent._panelsplash2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private PanelSideBar As Panel";
mostCurrent._panelsidebar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private PanelHome As Panel";
mostCurrent._panelhome = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Panelshadow As Panel";
mostCurrent._panelshadow = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim animation As AnimationPlus";
mostCurrent._animation = new flm.b4a.animationplus.AnimationPlusWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim BoolSlide As Boolean = False";
_boolslide = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 33;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private ScrollView1 As ScrollView";
mostCurrent._scrollview1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim conPanHeight As Int";
_conpanheight = 0;
 //BA.debugLineNum = 37;BA.debugLine="Dim conPanWidth As Int";
_conpanwidth = 0;
 //BA.debugLineNum = 38;BA.debugLine="Dim panTop As Int";
_pantop = 0;
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static String  _label4_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub Label4_Click";
 //BA.debugLineNum = 117;BA.debugLine="If BoolSlide = False Then";
if (_boolslide==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 118;BA.debugLine="Label4.SetLayoutAnimated(1000,75%x,0,Label4.Widt";
mostCurrent._label4.SetLayoutAnimated((int) (1000),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),(int) (0),mostCurrent._label4.getWidth(),mostCurrent._label4.getHeight());
 //BA.debugLineNum = 120;BA.debugLine="PanelSideBar.SetLayoutAnimated(1000,0,0,PanelSid";
mostCurrent._panelsidebar.SetLayoutAnimated((int) (1000),(int) (0),(int) (0),mostCurrent._panelsidebar.getWidth(),mostCurrent._panelsidebar.getHeight());
 //BA.debugLineNum = 121;BA.debugLine="Panelshadow.SetVisibleAnimated(1000,True)";
mostCurrent._panelshadow.SetVisibleAnimated((int) (1000),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 122;BA.debugLine="BoolSlide = True";
_boolslide = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 123;BA.debugLine="Label5.Enabled = False";
mostCurrent._label5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 124;BA.debugLine="Label4.Text = Chr(0xE5C4)";
mostCurrent._label4.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe5c4))));
 }else {
 //BA.debugLineNum = 127;BA.debugLine="Label4.SetLayoutAnimated(1000,0,0,Label4.Width,L";
mostCurrent._label4.SetLayoutAnimated((int) (1000),(int) (0),(int) (0),mostCurrent._label4.getWidth(),mostCurrent._label4.getHeight());
 //BA.debugLineNum = 129;BA.debugLine="PanelSideBar.SetLayoutAnimated(1000,-PanelSideBa";
mostCurrent._panelsidebar.SetLayoutAnimated((int) (1000),(int) (-mostCurrent._panelsidebar.getWidth()),(int) (0),mostCurrent._panelsidebar.getWidth(),mostCurrent._panelsidebar.getHeight());
 //BA.debugLineNum = 130;BA.debugLine="Panelshadow.SetVisibleAnimated(1000,False)";
mostCurrent._panelshadow.SetVisibleAnimated((int) (1000),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 131;BA.debugLine="Label5.Enabled = True";
mostCurrent._label5.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 132;BA.debugLine="BoolSlide = False";
_boolslide = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 133;BA.debugLine="Label4.Text = Chr(0xE5D2)";
mostCurrent._label4.setText(BA.ObjectToCharSequence(anywheresoftware.b4a.keywords.Common.Chr((int) (0xe5d2))));
 };
 //BA.debugLineNum = 136;BA.debugLine="End Sub";
return "";
}
public static String  _label5_click() throws Exception{
 //BA.debugLineNum = 138;BA.debugLine="Sub Label5_Click";
 //BA.debugLineNum = 139;BA.debugLine="StartActivity(add)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._add.getObject()));
 //BA.debugLineNum = 140;BA.debugLine="SetAnimation(\"file3\", \"file4\")";
_setanimation("file3","file4");
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
starter._process_globals();
add._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim timer As Timer";
_timer = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public static String  _setanimation(String _inanimation,String _outanimation) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
String _package = "";
int _in = 0;
int _out = 0;
 //BA.debugLineNum = 92;BA.debugLine="Sub SetAnimation(InAnimation As String, OutAnimati";
 //BA.debugLineNum = 93;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 94;BA.debugLine="Dim package As String";
_package = "";
 //BA.debugLineNum = 95;BA.debugLine="Dim In, out As Int";
_in = 0;
_out = 0;
 //BA.debugLineNum = 96;BA.debugLine="package = r.GetStaticField(\"anywheresoftware.b4a.";
_package = BA.ObjectToString(_r.GetStaticField("anywheresoftware.b4a.BA","packageName"));
 //BA.debugLineNum = 97;BA.debugLine="In = r.GetStaticField(package & \".R$anim\", InAnim";
_in = (int)(BA.ObjectToNumber(_r.GetStaticField(_package+".R$anim",_inanimation)));
 //BA.debugLineNum = 98;BA.debugLine="out = r.GetStaticField(package & \".R$anim\", OutAn";
_out = (int)(BA.ObjectToNumber(_r.GetStaticField(_package+".R$anim",_outanimation)));
 //BA.debugLineNum = 99;BA.debugLine="r.Target = r.GetActivity";
_r.Target = (Object)(_r.GetActivity(processBA));
 //BA.debugLineNum = 100;BA.debugLine="r.RunMethod4(\"overridePendingTransition\", Array A";
_r.RunMethod4("overridePendingTransition",new Object[]{(Object)(_in),(Object)(_out)},new String[]{"java.lang.int","java.lang.int"});
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _timer_tick() throws Exception{
 //BA.debugLineNum = 103;BA.debugLine="Sub timer_Tick";
 //BA.debugLineNum = 104;BA.debugLine="timer.Enabled = False";
_timer.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 105;BA.debugLine="PanelSplash.SetLayoutAnimated(2000, -PanelSplash.";
mostCurrent._panelsplash.SetLayoutAnimated((int) (2000),(int) (-mostCurrent._panelsplash.getWidth()),(int) (0),mostCurrent._panelsplash.getWidth(),mostCurrent._panelsplash.getHeight());
 //BA.debugLineNum = 106;BA.debugLine="PanelSplash2.SetLayoutAnimated(2000, Activity.Wid";
mostCurrent._panelsplash2.SetLayoutAnimated((int) (2000),mostCurrent._activity.getWidth(),(int) (0),mostCurrent._panelsplash2.getWidth(),mostCurrent._panelsplash2.getHeight());
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
}
