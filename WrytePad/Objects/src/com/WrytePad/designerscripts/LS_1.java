package com.WrytePad.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_1{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[1/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 6;BA.debugLine="PanelSplash.Width = 50%x"[1/General script]
views.get("panelsplash").vw.setWidth((int)((50d / 100 * width)));
//BA.debugLineNum = 7;BA.debugLine="PanelSplash.Height = 100%y"[1/General script]
views.get("panelsplash").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 8;BA.debugLine="PanelSplash2.Width = 50%x"[1/General script]
views.get("panelsplash2").vw.setWidth((int)((50d / 100 * width)));
//BA.debugLineNum = 9;BA.debugLine="PanelSplash2.Height = 100%y"[1/General script]
views.get("panelsplash2").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 10;BA.debugLine="PanelSplash2.Left = 50%x"[1/General script]
views.get("panelsplash2").vw.setLeft((int)((50d / 100 * width)));
//BA.debugLineNum = 13;BA.debugLine="Label1.VerticalCenter = PanelSplash.VerticalCenter"[1/General script]
views.get("label1").vw.setTop((int)((views.get("panelsplash").vw.getTop() + views.get("panelsplash").vw.getHeight()/2) - (views.get("label1").vw.getHeight() / 2)));
//BA.debugLineNum = 14;BA.debugLine="Label2.VerticalCenter = PanelSplash2.VerticalCenter"[1/General script]
views.get("label2").vw.setTop((int)((views.get("panelsplash2").vw.getTop() + views.get("panelsplash2").vw.getHeight()/2) - (views.get("label2").vw.getHeight() / 2)));
//BA.debugLineNum = 15;BA.debugLine="Label1.Right = 50%x"[1/General script]
views.get("label1").vw.setLeft((int)((50d / 100 * width) - (views.get("label1").vw.getWidth())));
//BA.debugLineNum = 16;BA.debugLine="Label2.Left = 0"[1/General script]
views.get("label2").vw.setLeft((int)(0d));
//BA.debugLineNum = 19;BA.debugLine="PanelSideBar.Width = 70%x"[1/General script]
views.get("panelsidebar").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 20;BA.debugLine="PanelSideBar.Height = 100%y"[1/General script]
views.get("panelsidebar").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 21;BA.debugLine="PanelSideBar.Left = -PanelSideBar.Width"[1/General script]
views.get("panelsidebar").vw.setLeft((int)(0-(views.get("panelsidebar").vw.getWidth())));
//BA.debugLineNum = 22;BA.debugLine="Panel6.Width = PanelSideBar.Width  * 0.9"[1/General script]
views.get("panel6").vw.setWidth((int)((views.get("panelsidebar").vw.getWidth())*0.9d));
//BA.debugLineNum = 23;BA.debugLine="Panel8.Width = PanelSideBar.Width * 0.9"[1/General script]
views.get("panel8").vw.setWidth((int)((views.get("panelsidebar").vw.getWidth())*0.9d));
//BA.debugLineNum = 24;BA.debugLine="Panel7.Width = PanelSideBar.Width * 0.9"[1/General script]
views.get("panel7").vw.setWidth((int)((views.get("panelsidebar").vw.getWidth())*0.9d));
//BA.debugLineNum = 25;BA.debugLine="Panel2.Width = PanelSideBar.Width"[1/General script]
views.get("panel2").vw.setWidth((int)((views.get("panelsidebar").vw.getWidth())));
//BA.debugLineNum = 28;BA.debugLine="PanelHome.Width = 100%x"[1/General script]
views.get("panelhome").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 29;BA.debugLine="PanelHome.Height = 100%y"[1/General script]
views.get("panelhome").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 30;BA.debugLine="PanelHome.Left = 0"[1/General script]
views.get("panelhome").vw.setLeft((int)(0d));
//BA.debugLineNum = 31;BA.debugLine="Panel1.Width = 100%x"[1/General script]
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 32;BA.debugLine="Label3.Left = 2 * Label4.Width"[1/General script]
views.get("label3").vw.setLeft((int)(2d*(views.get("label4").vw.getWidth())));
//BA.debugLineNum = 34;BA.debugLine="Panelshadow.Height = 100%y"[1/General script]
views.get("panelshadow").vw.setHeight((int)((100d / 100 * height)));
//BA.debugLineNum = 35;BA.debugLine="Panelshadow.Width = 100%x"[1/General script]
views.get("panelshadow").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 36;BA.debugLine="Label5.Right = 100%x"[1/General script]
views.get("label5").vw.setLeft((int)((100d / 100 * width) - (views.get("label5").vw.getWidth())));
//BA.debugLineNum = 37;BA.debugLine="ScrollView1.Width = 100%x"[1/General script]
views.get("scrollview1").vw.setWidth((int)((100d / 100 * width)));
//BA.debugLineNum = 38;BA.debugLine="ScrollView1.Height = PanelHome.Height * 0.79"[1/General script]
views.get("scrollview1").vw.setHeight((int)((views.get("panelhome").vw.getHeight())*0.79d));
//BA.debugLineNum = 39;BA.debugLine="ScrollView1.SetTopAndBottom(PanelHome.Height * 0.21,PanelHome.Height)"[1/General script]
views.get("scrollview1").vw.setTop((int)((views.get("panelhome").vw.getHeight())*0.21d));
views.get("scrollview1").vw.setHeight((int)((views.get("panelhome").vw.getHeight()) - ((views.get("panelhome").vw.getHeight())*0.21d)));

}
}