// stdafx.cpp : source file that includes just the standard includes
// Forms.pch will be the pre-compiled header
// stdafx.obj will contain the pre-compiled type information

#include "stdafx.h"


Private Sub Form_Load()
     Call DrawText
 End Sub
 Private Sub DrawText()
     Dim strText As String
     Me.Cls '// clear form
     'Never allow the percentage to be 0 or 100 unless it is exactly that value. This
     'prevents, for instance, the status bar from reaching 100% until we are entirely done.
     strText = "Contents" '// set the text
     SetCenterPos (strText) '// set the center pos
     Me.Print strText '// print the text
     strText = ""
     SetCenterPos (strText)
     Me.Print strText
     strText = "Chapter 1: A walk in the woods"
     SetCenterPos (strText)
     Me.Print strText
     strText = "Chapter 2: The suprise"
     SetCenterPos (strText)
     Me.Print strText
 End Sub
 Private Sub SetCenterPos(strText As String)
     Dim intX As Integer
     Dim intWidth As Integer
     intWidth = Me.TextWidth(strText)
     '// Set intX to the starting location for printing the percentage
     intX = Me.ScaleWidth / 2 - intWidth / 2
     '// Go to the center print position
     Me.CurrentX = intX
 End Sub 