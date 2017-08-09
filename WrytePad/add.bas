Type=Activity
Version=7.01
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: true
		#IncludeTitle: false
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private LblHintBody As Label
	Private LblHintTitle As Label
	Private EditText1 As EditText
	Private EditText2 As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("2")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub SetAnimation(InAnimation As String, OutAnimation As String)
	Dim r As Reflector
	Dim package As String
	Dim In, out As Int
	package = r.GetStaticField("anywheresoftware.b4a.BA", "packageName")
	In = r.GetStaticField(package & ".R$anim", InAnimation)
	out = r.GetStaticField(package & ".R$anim", OutAnimation)
	r.Target = r.GetActivity
	r.RunMethod4("overridePendingTransition", Array As Object(In, out), Array As String("java.lang.int", "java.lang.int"))
End Sub

Sub Activity_KeyPress (KeyCode As Int) As Boolean 'Return True to consume the event
	If KeyCode = KeyCodes.KEYCODE_BACK Then
		Activity.Finish
		SetAnimation("file2", "file1") 'move the current Activity to the right, and the new (or known as the old Activity) will come from the left and will scroll to the right
		Return True
	End If
End Sub

Sub SaveToDb(title As String, body As String , date As String)
	Dim arr() As String
	arr = Array As String(title,body,date)
	Starter.db.ExecNonQuery2("IMSERT INTO notes VALUES (?,?,?)",arr)
End Sub

Sub EditText2_FocusChanged (HasFocus As Boolean)
	
	If HasFocus Or EditText2.Text.Trim.Length > 0 Then
		LblHintBody.Visible = False
	Else
		LblHintBody.Visible = True
	End If
End Sub

Sub EditText1_FocusChanged (HasFocus As Boolean)
	If HasFocus Or EditText1.Text.Trim.Length > 0 Then
		LblHintTitle.Visible = False
	Else
		LblHintTitle.Visible = True
	End If
End Sub

Sub Label2_Click
	
	If EditText1.Text.Trim.Length < 1 And  EditText2.Text.Trim.Length < 1 Then
		Msgbox("Please create a note to save", "Error")
		
	else If EditText1.Text.Trim.Length > 0 And EditText2.Text.Trim.length < 1 Then
		Dim value As Int
		value = Msgbox2("Save only notes title?","Alert","Yes","","Cancel",Null)
		If value = DialogResponse.POSITIVE Then
			Dim date As Long 
			date = DateTime.Date(DateTime.Now)
			SaveToDb(EditText1.Text,"",date)
			ToastMessageShow("Saved",True)
		End If
		
	Else If EditText1.Text.Trim.Length < 1 And EditText2.Text.Trim.length > 0 Then
		Dim value As Int
		value = Msgbox2("Save only notes body?","Alert","Yes","","Cancel",Null)
		If value = DialogResponse.POSITIVE Then
			Dim date As Long
			date = DateTime.Date(DateTime.Now)
			SaveToDb("",EditText2.Text,date)
			ToastMessageShow("Saved",True)
		End If
			
    Else If EditText1.Text.Trim.Length > 0 And EditText2.Text.Trim.length > 0 Then
			Dim date As Long
			date = DateTime.Date(DateTime.Now)
			SaveToDb(EditText1.Text,EditText2.Text,date)
			ToastMessageShow("Saved",True)				
	End If
End Sub

Sub Label1_Click
	Activity.Finish
	SetAnimation("file2", "file1")
End Sub

