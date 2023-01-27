;;; Sierra Script 1.0 - (do not remove this comment)
;	PROCS.SC
;
;	 A place to put a variety of useful procedures.
;

(script# PROCS)
(include game.sh)
(use Main)
(use Intrface)
(use StopWalk)
(use User)

(public
	RedrawCast 0
	HandsOn 1
	HandsOff 2
	cls 3
	Btst 4
	Bset 5
	Bclr 6
	EgoDead 7
	FindColor 8
	Face 9
	InRoom 10
	PutInRoom 11
	YesNoDialog 12
)

(procedure (RedrawCast)
	;Used to re-animate the cast without cycling
	(Animate (cast elements?) FALSE)
)

(procedure (HandsOn)
	;Enable ego control
	(= isHandsOff FALSE)
	(User canControl: TRUE canInput: TRUE)
	(theIconBar enable:
		ICON_WALK
		ICON_LOOK
		ICON_DO
		ICON_TALK
		ICON_ITEM
		ICON_INVENTORY
		ICON_CONTROL
		ICON_HELP
	)
	(if (not (theIconBar curInvIcon?))
		(theIconBar disable: ICON_ITEM)
	)
	(if (not (HaveMouse))
		(theGame setCursor:
			((theIconBar curIcon?) cursor?) TRUE saveCursorX saveCursorY
		)
	else
		(theGame setCursor: ((theIconBar curIcon?) cursor?))
	)
)

(procedure (HandsOff &tmp saveIcon)
	;Disable ego control
	(= isHandsOff TRUE)
	(User canControl: FALSE canInput: FALSE)
	(ego setMotion: 0)
	(= saveIcon (theIconBar curIcon?))
	(theIconBar disable:
		ICON_WALK
		ICON_LOOK
		ICON_DO
		ICON_TALK
		ICON_ITEM
		ICON_INVENTORY
	)
	(theIconBar curIcon: saveIcon)
	(if (not (HaveMouse))
		(= saveCursorX ((User curEvent?) x?))
		(= saveCursorY ((User curEvent?) y?))
		(theGame setCursor: waitCursor TRUE 310 180)
	else
		(theGame setCursor: waitCursor TRUE)
	)
	(if pMouse
		(pMouse stop:)
	)
)

(procedure (cls)
	;Clear modeless dialog from the screen
	(if modelessDialog (modelessDialog dispose:))
)

(procedure (Btst flagEnum)
	;Test a boolean game flag
	(return
		(&
			[gameFlags (/ flagEnum 16)]
			(>> $8000 (mod flagEnum 16))
		)
	)
)

(procedure (Bset flagEnum &tmp oldState)
	;Set a boolean game flag
	(= oldState (Btst flagEnum))
	(|= [gameFlags (/ flagEnum 16)] (>> $8000 (mod flagEnum 16)))
	(return oldState)
)

(procedure (Bclr flagEnum &tmp oldState)
	;Clear a boolean game flag
	(= oldState (Btst flagEnum))
	(&= [gameFlags (/ flagEnum 16)] (~ (>> $8000 (mod flagEnum 16))))
	(return oldState)
)


(procedure (EgoDead theReason)
	;This procedure handles when ego dies. It closely matches that of SQ4, SQ5 and KQ6.
	;If a specific message is not given, the game will use a default message.
	(if (not argc)
		(= deathReason deathGENERIC)
	else
		(= deathReason theReason)
	)
	(curRoom newRoom: DEATH)
)

(procedure (FindColor col256 col16)
	(if (< col256 0)
		(= col256 0)
	)
	(if (> col256 255)
		(= col256 255)
	)
	(if (< col16 0)
		(= col16 0)
	)
	(if (> col16 15)
		(= col16 15)
	)
	(return (if (Btst fIsVGA) col256 else col16))
)

(procedure (Face who theObjOrX theY whoCares &tmp theHeading lookX looKY whoToCue)
	;make one actor face another
	(= whoToCue 0)
	(if (IsObject theObjOrX)
		(= lookX (theObjOrX x?))
		(= looKY (theObjOrX y?))
		(if (== argc 3)
			(= whoToCue theY)
		)
	else
		(= lookX theObjOrX)
		(= looKY theY)
		(if (== argc 4)
			(= whoToCue whoCares)
		)
	)
	(= theHeading (GetAngle (who x?) (who y?) lookX looKY))
	(who setHeading: theHeading (if (IsObject whoToCue) whoToCue else 0))
)

(procedure (InRoom what where)
	;check whether an inventory object is in a room.
	; If no room is specified, it assumes the current room.
	(return
		(==
			((inventory at: what) owner?)
			(if (< argc 2) curRoomNum else where)
		)
	)
)

(procedure (PutInRoom what where)
	;put an inventory object in a room.
	; If no room is specified, it assumes the current room.	
	((inventory at: what)
		owner: (if (< argc 2) curRoomNum else where)
	)
)
(procedure (YesNoDialog &tmp oldCur)
	;this brings up a "yes or no" dialog choice.
	(= oldCur ((theIconBar curIcon?) cursor?))
	(theGame setCursor: normalCursor)
	(cls)
	(return
		(Print &rest
			#button {Yes} 1
			#button {No} 0
		)
	)
	(theGame setCursor: oldCur)
)