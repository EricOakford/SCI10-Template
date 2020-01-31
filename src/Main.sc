;;; Sierra Script 1.0 - (do not remove this comment)
(script# 0)
(include game.sh)
(use Intrface)
(use Window)
(use PMouse)
(use GControl)
(use BordWind)
(use Feature)
(use LoadMany)
(use IconBar)
(use BordWind)
(use RandCyc)
(use PAvoid)
(use StopWalk)
(use PolyPath)
(use Polygon)
(use Osc)
(use DCIcon)
(use Motion)
(use Grooper)
(use Sound)
(use Game)
(use Invent)
(use User)
(use Actor)
(use System)

(public
	SCI10 0
	NormalEgo 1
	HandsOff 2
	HandsOn 3
	IsHeapFree 4
	IsObjectOnControl 5
	Btst 6
	Bset 7
	Bclr 8
	SolvePuzzle 10
	AimToward 11
	VerbFail 12
	Say 13
	EGAOrVGA 14
	EgoDead 15
)

(local
	ego
	theGame
	curRoom
	speed =  6
	quit
	cast
	regions
	timers
	sounds
	inventory
	addToPics
	curRoomNum
	prevRoomNum
	newRoomNum
	debugOn
	score
	possibleScore
	showStyle =  IRISOUT
	aniInterval
	theCursor
	normalCursor =  ARROW_CURSOR
	waitCursor =  20
	userFont =  USERFONT
	smallFont =  4
	lastEvent
	modelessDialog
	bigFont =  USERFONT
	version
	locales
	curSaveDir
	aniThreshold =  10
	perspective
	features
	sortedFeatures
	useSortedFeatures
	egoBlindSpot
	overlays =  -1
	doMotionCue
	systemWindow
	demoDialogTime =  3
	currentPalette
	modelessPort
	sysLogPath
		global43
		global44
		global45
		global46
		global47
		global48
		global49
		global50
		global51
		global52
		global53
		global54
		global55
		global56
		global57
		global58
		global59
		global60
		global61
	endSysLogPath
	gameControls
	ftrInitializer
	doVerbCode
	approachCode
	useObstacles =  TRUE
	theMenuBar
	theIconBar
	mouseX
	mouseY
	keyDownHandler
	mouseDownHandler
	directionHandler
	speechHandler
	lastVolume
	pMouse
	theDoits
	eatMice =  60
	user
	syncBias
	theSync
	cDAudio
	fastCast
	inputFont
	tickOffset
	howFast
	gameTime
	;globals 89-99 are unused
	global89
	global90
	global91
	global92
	global93
	global94
	global95
	global96
	global97
	global98
	lastSysGlobal
	theMusic
	dongle = 1234	;EO: Don't mess with this, or you'll get Error 11 shortly after startup!
	globalSound
	soundFx
	deathMusic = sDeath
	colorCount
	musicChannels
	startingRoom
	pMouseX
	pMouseY
	gEgoHead
	theStopGroop
	[gameFlags 10]
	myTextColor
	myEGABordColor
	myEGABackColor
	myVGABordColor
	myVGABackColor
	myVGABordColor2
	myEGABordColor2
	myHighlightColor
	debugging
	theEgoHead
)
(procedure (NormalEgo)
	(ego
		setLoop: -1
		setPri: -1
		setMotion: 0
		setCycle: StopWalk
		setStep: 3 2
		setAvoider: PAvoider
		ignoreActors: 0
		moveSpeed: (theGame egoMoveSpeed?)
		cycleSpeed: (theGame egoMoveSpeed?)
	)
)

(procedure (HandsOff &tmp oldIcon)
	(User canControl: FALSE canInput: FALSE)
	(ego setMotion: 0)
	(= oldIcon (theIconBar curIcon?))
	(theIconBar disable: ICON_WALK ICON_LOOK ICON_DO ICON_TALK ICON_ITEM ICON_INVENTORY ICON_SMELL ICON_TASTE)
	(theIconBar curIcon: oldIcon)
	(if (not (HaveMouse))
		(= pMouseX ((User curEvent?) x?))
		(= pMouseY ((User curEvent?) y?))
		(theGame setCursor: waitCursor TRUE 304 172)
	else
		(theGame setCursor: waitCursor TRUE)
	)
	(if pMouse (pMouse stop:))
)

(procedure (HandsOn)
	(User canControl: TRUE canInput: TRUE)
	(theIconBar enable:ICON_WALK ICON_LOOK ICON_DO ICON_TALK ICON_ITEM ICON_INVENTORY ICON_SMELL ICON_TASTE)
	(if (not (theIconBar curInvIcon?))
		(theIconBar disable: (theIconBar useIconItem?))
	)
	(if (not (HaveMouse))
		(theGame
			setCursor: ((theIconBar curIcon?) cursor?) TRUE pMouseX pMouseY
		)
	else
		(theGame setCursor: ((theIconBar curIcon?) cursor?))
	)
)

(procedure (IsHeapFree memSize)
	(return (u> (MemoryInfo LargestPtr) memSize))
)

(procedure (IsObjectOnControl obj param2)
	(return
		(if (& (obj onControl: TRUE) param2)
			(return TRUE)
			else FALSE
		)
	)
)

(procedure (Btst flagEnum)
	(return
		(&
			[gameFlags (/ flagEnum 16)]
			(>> $8000 (mod flagEnum 16))
		)
	)
)

(procedure (Bset flagEnum &tmp oldState)
	(= oldState (Btst flagEnum))
	(= [gameFlags (/ flagEnum 16)]
		(|
			[gameFlags (/ flagEnum 16)]
			(>> $8000 (mod flagEnum 16))
		)
	)
	(return oldState)
)

(procedure (Bclr flagEnum &tmp oldState)
	(= oldState (Btst flagEnum))
	(= [gameFlags (/ flagEnum 16)]
		(&
			[gameFlags (/ flagEnum 16)]
			(~ (>> $8000 (mod flagEnum 16)))
		)
	)
	(return oldState)
)

(procedure (SolvePuzzle flag points)
	(if (not (Btst flag))
		(theGame changeScore: points)
		(Bset flag)
		(pointsSound play:)
	)
)

(procedure (AimToward param1 param2 param3 param4 &tmp temp0 temp1 temp2 temp3)
	(= temp3 0)
	(if (IsObject param2)
		(= temp1 (param2 x?))
		(= temp2 (param2 y?))
		(if (== argc 3) (= temp3 param3))
	else
		(= temp1 param2)
		(= temp2 param3)
		(if (== argc 4) (= temp3 param4))
	)
	(= temp0
		(GetAngle (param1 x?) (param1 y?) temp1 temp2)
	)
	(param1
		setHeading: temp0 (if (IsObject temp3) temp3 else 0)
	)
)

(procedure (VerbFail &tmp newList [temp1 2] temp3 userCurEvent temp5 [temp6 5])
	(= temp3 (theGame setCursor: 69 1))
	(= userCurEvent (User curEvent?))
	(redX
		x: (userCurEvent x?)
		y: (+ 300 (userCurEvent y?))
		z: 300
		show:
	)
	((= newList (List new:)) add: redX)
	(Animate (newList elements?) 1)
	(Animate (cast elements?) FALSE)
	(= temp5 (GetTime))
	(while (< (Abs (- temp5 (GetTime))) 40)
		(breakif
			(OneOf ((= userCurEvent (Event new:)) type?) 4 1)
		)
		(userCurEvent dispose:)
	)
	(if (IsObject userCurEvent) (userCurEvent dispose:))
	(redX hide: posn: 1000 -1000)
	(Animate (newList elements?) 1)
	(newList delete: redX dispose:)
	(theGame setCursor: temp3)
)

(procedure (Say param1 param2 param3 &tmp [str 500])
	(if (u< param2 1000)
		(GetFarText param2 param3 @str)
	else
		(StrCpy @str param2)
	)
	(babbleIcon
		view: param1
		cycleSpeed: (* (+ howFast 1) 4)
	)
	(if (u< param2 1000)
		(Print @str &rest #icon babbleIcon 0 0)
	else
		(Print @str param3 &rest #icon babbleIcon 0 0)
	)
)

(procedure (EGAOrVGA vga ega)
	(if (< vga 0) (= vga 0))			;if color is less than zero, make it equal zero
	(if (> vga 255) (= vga 255))		;if color is more than 255, make it equal 255 
	(if (< ega 0) (= ega 0))			;if color is less than zero, make it equal zero
	(if (> ega 15) (= ega 15))			;if color is more than 15, make it equal 15
	(return (if (Btst fIsVGA) vga else ega))
)

(procedure (EgoDead &tmp printRet)
	;This procedure handles when Ego dies. It closely matches that of SQ1VGA.
	;To use it: "(EgoDead {death message})".
	;You can add an icon in the same way as a normal Print message.
	(HandsOff)
	(Wait 100)
	(= normalCursor ARROW_CURSOR)
	(theGame setCursor: normalCursor TRUE)

	(sounds eachElementDo: #stop)	; Stop any other music
	(theMusic number: deathMusic play:)
	(repeat
		(= printRet
			(Print
				&rest
				#width 250
				#button	{Restore} 1
				#button {Restart} 2
				#button {__Quit__} 3
			)
		)
		(switch printRet
			(1
				(theGame restore:)
			)
			(2
				(theGame restart:)
			)
			(3
				(= quit TRUE) (break)
			)
		)
	)
)

(instance music of Sound
	(properties
		number 1
	)
)

(instance SFX of Sound
	(properties
		number 1
	)
)

(instance pointsSound of Sound
	(properties
		flags mNOPAUSE
		number sScore
		priority 15
	)
)

(instance statusCode of Code
	(properties)
	
	(method (doit roomNum &tmp [strg 50])
		(if
			;add rooms where the status line is not shown
			(not (OneOf roomNum 
					rTitle SPEED_TEST
				 )
			)	
		(Format @strg "___Template Game__________________Score: %d of %d" score possibleScore)
		(DrawStatus @strg 23 0)
		)
	)
)

(instance stopGroop of GradualLooper
	(properties)
	
	(method (doit)
		(if (== (ego loop?) (- (NumLoops ego) 1))
			(ego loop: (ego cel?))
		)
		(super doit: &rest)
	)
)

(instance babbleIcon of DCIcon
	(properties)
	
	(method (init)
		((= cycler (RandCycle new:)) init: self 20)
	)
)

(instance kDHandler of EventHandler
	(properties)
)

(instance mDHandler of EventHandler
	(properties)
)

(instance dirHandler of EventHandler
	(properties)
)

(instance SCI10 of Game
	(properties
		;Set your game's language here.
		;Supported langauges can be found in SYSTEM.SH.
		printLang ENGLISH
	)
	
	(method (init &tmp temp0)
		;load some important modules
		BorderWindow
		DText
		DButton
		StopWalk
		Polygon
		PolyPath
		(ScriptID GAME_EGO)
		IconBar
		Inventory
		(ScriptID SIGHT)
		RandCycle
		Oscillate
		(super init: &rest)
		
		(StrCpy @sysLogPath {})
		(= theStopGroop stopGroop)
		(= doVerbCode DoVerbCode)
		(= ftrInitializer FtrInit)
		((= keyDownHandler kDHandler) add:)
		((= mouseDownHandler mDHandler) add:)
		((= directionHandler dirHandler) add:)
		(= pMouse PseudoMouse)
		(= ego (ScriptID GAME_EGO 0))
		(self egoMoveSpeed: 5 setSpeed: 0 setCursor: theCursor TRUE 304 172)
		((= theMusic music) owner: self flags: mNOPAUSE init:)
		((= soundFx SFX) owner: self flags: mNOPAUSE init:)
		((ScriptID GAME_INIT 0) init:)
	)
	
	(method (startRoom roomNum)
		(if debugging
			((ScriptID DEBUG 0) init:)
		)		
		(if pMouse (pMouse stop:))
		(statusCode doit: roomNum)
		((ScriptID DISPOSE_CODE) doit: roomNum)
		;EO: despite what the message will say, the memory is NOT fragmented.
		;Disabling the message until I can find a way to prevent it from
		;appearing when changing rooms.
;;;		(if
;;;			(and
;;;				(!= (- (MemoryInfo FreeHeap) 2) (MemoryInfo LargestPtr))
;;;				(Print "Memory fragmented." 
;;;					#button {Who cares} FALSE
;;;					#button {Debug} TRUE
;;;				)
;;;			)
;;;			(SetDebug)
;;;		)
		(super startRoom: roomNum)
		(redX init: hide: setPri: 15 posn: 1000 -1000)
	)

	
	(method (handleEvent event)
		(super handleEvent: event)
		(if (event claimed?) (return TRUE))
		(return
			(switch (event type?)
				(keyDown
					(switch (event message?)
						(TAB
							(if (not (& ((theIconBar at: ICON_INVENTORY) signal?) DISABLED))
								(inventory showSelf:)
							)
						)
						(SHIFTTAB
							(if (not (& ((theIconBar at: ICON_INVENTORY) signal?) DISABLED))
								(inventory showSelf:)
							)
						)
						(`^q	;KEY_CONTROL
							(theGame quitGame:)
							(event claimed: TRUE)
						)
						(`#2	;KEY_F2
							(cond 
								((theGame masterVolume:) (theGame masterVolume: 0))
								((> musicChannels 1) (theGame masterVolume: 15))
								(else (theGame masterVolume: 1))
							)
							(event claimed: TRUE)
						)
						(`#5	;KEY_F5
							(theGame save:)
							(event claimed: TRUE)
						)
						(`#7	;KEY_F7
							(theGame restore:)
							(event claimed: TRUE)
						)
						(`#9	;KEY_F9
							(theGame restart:)
							(event claimed: TRUE)
						)
					)
				)
			)
		)
	)
	(method (setSpeed newSpeed)
		(if (not (User canControl:))
			(return (not (User canControl:)))
		)
		(if argc
			(if (< (= egoMoveSpeed newSpeed) 0) (= egoMoveSpeed 0))
			(if (> egoMoveSpeed 15) (= egoMoveSpeed 15))
			(ego cycleSpeed: newSpeed moveSpeed: newSpeed)
		)
		(return egoMoveSpeed)
	)
		
	(method (quitGame)
		(super
			quitGame:
				(Print "Do you really want to stop playing?"
					#button {Quit} TRUE
					#button {Don't Quit} FALSE
				)
		)
	)
	
	(method (showAbout)
		((ScriptID GAME_ABOUT 0) doit:)
	)
	
	(method (pragmaFail)
		(if
			(User canInput:)
			(VerbFail)
		)
	)
)

(instance redX of View
	(properties
		view vRedX
	)
)


(instance DoVerbCode of Code
	(properties)
	
	(method (doit theVerb param2 &tmp temp0)
		(= temp0 (param2 description?))
		(switch theVerb
			(verbLook
				(if (param2 facingMe: ego)
					(if (param2 lookStr?)
						(Print (param2 lookStr?))
					else
						(VerbFail)
					)
				)
			)
			(else  (VerbFail))
		)
	)
)


(instance FtrInit of Code
	(properties)
	
	(method (doit param1)
		(if (== (param1 sightAngle?) ftrDefault)
			(param1 sightAngle: 90)
		)
		(if (== (param1 actions?) ftrDefault) (param1 actions: 0))
	)
)