package com.WrytePad.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_2{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("panel1").vw.setWidth((int)((100d / 100 * width)));
views.get("panel1").vw.setHeight((int)((100d / 100 * height)));
views.get("panel2").vw.setWidth((int)((100d / 100 * width)));
views.get("label3").vw.setLeft((int)(1.5d*(views.get("label1").vw.getWidth())));
views.get("label2").vw.setLeft((int)((100d / 100 * width) - (views.get("label2").vw.getWidth())));
views.get("panel3").vw.setLeft((int)((3.125d / 100 * width)));
views.get("panel3").vw.setWidth((int)((96.88d / 100 * width) - ((3.125d / 100 * width))));
views.get("panel4").vw.setLeft((int)((3.125d / 100 * width)));
views.get("panel4").vw.setWidth((int)((96.88d / 100 * width) - ((3.125d / 100 * width))));
views.get("panel3").vw.setTop((int)((13.95d / 100 * height)));
views.get("panel3").vw.setHeight((int)((27.91d / 100 * height) - ((13.95d / 100 * height))));
views.get("panel4").vw.setTop((int)((32.56d / 100 * height)));
views.get("panel4").vw.setHeight((int)((95.35d / 100 * height) - ((32.56d / 100 * height))));
views.get("edittext1").vw.setHeight((int)((views.get("panel3").vw.getHeight())*0.9d));
views.get("edittext1").vw.setTop((int)((views.get("panel3").vw.getHeight())*0.05d));
views.get("edittext1").vw.setWidth((int)((views.get("panel3").vw.getWidth())*0.9d));
views.get("edittext1").vw.setLeft((int)((views.get("panel3").vw.getWidth())*0.05d));
views.get("edittext2").vw.setHeight((int)((views.get("panel4").vw.getHeight())*0.9d));
views.get("edittext2").vw.setTop((int)((views.get("panel4").vw.getHeight())*0.05d));
views.get("edittext2").vw.setWidth((int)((views.get("panel4").vw.getWidth())*0.9d));
views.get("edittext2").vw.setLeft((int)((views.get("panel4").vw.getWidth())*0.05d));
views.get("lblhintbody").vw.setHeight((int)((views.get("panel4").vw.getHeight())*0.15d));
views.get("lblhintbody").vw.setTop((int)((views.get("panel4").vw.getHeight())*0.44d));
views.get("lblhintbody").vw.setWidth((int)((views.get("panel4").vw.getWidth())*0.33d));
views.get("lblhintbody").vw.setLeft((int)(((views.get("panel4").vw.getWidth())-(views.get("lblhintbody").vw.getWidth()))/2d));
views.get("lblhinttitle").vw.setHeight((int)((views.get("panel3").vw.getHeight())*0.5d));
views.get("lblhinttitle").vw.setTop((int)((views.get("panel3").vw.getHeight())*0.22d));
views.get("lblhinttitle").vw.setWidth((int)((views.get("panel3").vw.getWidth())*0.33d));
views.get("lblhinttitle").vw.setLeft((int)(((views.get("panel3").vw.getWidth())-(views.get("lblhinttitle").vw.getWidth()))/2d));

}
}