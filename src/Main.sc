;;; Sierra Script 1.0 - (do not remove this comment)
(script# 0)
(include game.sh)
(use Intrface)
(use Window)
(use PalInit)
(use PMouse)
(use SlideIcon)
(use BordWind)
(use Feature)
(use IconBar)
(use GameEgo)
(use RandCyc)
(use StopWalk)
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
	EgoHeadMove 9
	SolvePuzzle 10
	WindowlessPrint 11
	AimToward 12
	VerbFail 13
	proc0_17 14
	EGAOrVGA 15
	EgoDead 16
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
	dongle = TRUE	;EO: Don't mess with this, or you'll get Error 11 shortly after startup!
	globalSound
	soundFx
	deathMusic = sDeath
	colorCount
	musicChannels
	startingRoom
	pMouseX
	pMouseY
	gEgoHead
	gStopGroop
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
)
(procedure (NormalEgo param1 param2)
	(if (> argc 0)
		(if (!= param1 -1) (ego view: param1))
		(if (and (> argc 1) (!= param2 -1))
			(ego loop: param2)
		)
	)
	(ego
		normal: 1
		moveHead: 1
		setLoop: -1
		setLoop: stopGroop
		setPri: -1
		setMotion: 0
		setCycle: Walk
		setStep: 3 2
		illegalBits: 0
		ignoreActors: 0
		moveSpeed: (theGame egoMoveSpeed?)
		cycleSpeed: (theGame egoMoveSpeed?)
	)
)


(procedure (HandsOff &tmp theIconBarCurIcon)
	(User canControl: FALSE canInput: FALSE)
	(ego setMotion: 0)
	(= theIconBarCurIcon (theIconBar curIcon?))
	(theIconBar disable: ICON_WALK ICON_LOOK ICON_DO ICON_TALK ICON_ITEM ICON_INVENTORY ICON_SMELL ICON_TASTE)
	(theIconBar curIcon: theIconBarCurIcon)
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

(procedure (IsObjectOnControl param1 param2)
	(return (if (& (param1 onControl: 1) param2) (return 1) else 0))
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

(procedure (EgoHeadMove)
	(egoHead init: ego view: (ego view?) cycleSpeed: 24)
)

(procedure (SolvePuzzle flag points)
	(if (not (Btst flag))
		(theGame changeScore: points)
		(Bset flag)
		(pointsSound play:)
	)
)

(procedure (WindowlessPrint param1 &tmp temp0 temp1 temp2 temp3 temp4 temp5 temp6 temp7 temp8)
	(return
		(if (== argc 1)
			(Display 0 26 108 [param1 0])
		else
			(= temp4 (= temp5 -1))
			(= temp0 0)
			(= temp1 68)
			(= temp2 69)
			(= temp3 -1)
			(= temp6 myVGABordColor)
			(= temp7 0)
			(= temp8 1)
			(while (< temp8 argc)
				(switch [param1 temp8]
					(30
						(= temp0 [param1 (++ temp8)])
					)
					(33
						(= temp2 (+ (= temp1 [param1 (++ temp8)]) 1))
					)
					(70
						(= temp3 [param1 (++ temp8)])
					)
					(67
						(= temp4 [param1 (++ temp8)])
						(= temp5 [param1 (++ temp8)])
					)
					(28
						(= temp6 [param1 (++ temp8)])
					)
					(29
						(= temp7 [param1 (++ temp8)])
					)
				)
				(++ temp8)
			)
			(= temp8
				(Display
					[param1 0]
					dsCOORD
					temp4
					temp5
					dsCOLOR
					temp7
					dsWIDTH
					temp3
					dsALIGN
					temp0
					dsFONT
					temp2
					dsSAVEPIXELS
				)
			)
			(Display
				[param1 0]
				dsCOORD
				temp4
				temp5
				dsCOLOR
				temp6
				dsWIDTH
				temp3
				dsALIGN
				temp0
				dsFONT
				temp1
			)
			(return temp8)
		)
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
	(Animate (cast elements?) 0)
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

(procedure (proc0_17 param1 param2 param3 &tmp [temp0 500])
	(if (u< param2 1000)
		(GetFarText param2 param3 @temp0)
	else
		(StrCpy @temp0 param2)
	)
	(babbleIcon
		view: param1
		cycleSpeed: (* (+ howFast 1) 4)
	)
	(if (u< param2 1000)
		(Print @temp0 &rest 82 babbleIcon 0 0)
	else
		(Print @temp0 param3 &rest 82 babbleIcon 0 0)
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

	(sounds eachElementDo: #stop)	; Stop any other theMusic
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


(instance egoBody of Body
	(properties
		name "ego"
		description {you}
		sightAngle 180
		lookStr {It's you!}
		view vEgo
	)
	
	(method (doVerb theVerb theItem)
		(switch theVerb
			(verbTalk
				(Print "You talk to yourself but are stumped for a reply.")
			)
			(verbDo
				(Print "Hey! Keep your hands off yourself! This is a family game.")
			)
			(verbTaste
				(Print "I'll bet you wish you could!")
			)
			(verbSmell
				(Print "Ahhh!  The aroma of several adventure games emanates from your person.")
			)
			(verbUse
				(switch theItem
					(iCoin (Print "You pay yourself some money."))
					(iBomb (EgoDead "Maybe messing with the unstable ordinance wasn't such a hot idea..."))
				)
			)
			(else  (super doVerb: theVerb))
		)
	)
)

(instance egoHead of Head
	(properties
		description {your head.}
		lookStr {There's nothing going on in your stupid little head.}
	)
	
	(method (doVerb theVerb theItem)
		(ego doVerb: theVerb theItem)
	)
)

(instance music of Sound
	(properties
		number 1
	)
)

(instance longSong2 of Sound
	(properties
		number 1
	)
)

(instance pointsSound of Sound
	(properties
		flags $0001
		number sScore
		priority 15
	)
)

(instance statusCode of Code
	(properties)
	
	(method (doit strg)
		(Format strg "___Template Game__________________Score: %d of %d" score possibleScore)
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

(instance gameKeyDownHandler of EventHandler
	(properties)
)

(instance gameMouseDownHandler of EventHandler
	(properties)
)

(instance gameDirectionHandler of EventHandler
	(properties)
)

(instance SCI10 of Game
	(properties
		;Set your game's language here.
		;Supported langauges can be found in SYSTEM.SH.
		printLang ENGLISH
	)
	
	(method (init &tmp temp0)
		(= debugging TRUE)
		(= systemWindow gameWindow)
		(PalInit)
		(= version {x.yyy.zzz})
		(= gStopGroop stopGroop)
		(= deathMusic sDeath)
;		(= useSortedFeatures TRUE)
		(super init:)
		(StrCpy @sysLogPath {})
		(= doVerbCode gameDoVerbCode)
		(= ftrInitializer gameFtrInit)
		((= keyDownHandler gameKeyDownHandler) add:)
		((= mouseDownHandler gameMouseDownHandler) add:)
		((= directionHandler gameDirectionHandler) add:)
		(= pMouse PseudoMouse)
		(self egoMoveSpeed: 2 setCursor: theCursor TRUE 304 172)
		(= ego egoBody)
		(ego
			head: egoHead
			moveSpeed: (self egoMoveSpeed?)
			cycleSpeed: (self egoMoveSpeed?)
		)
		(User alterEgo: ego canControl: FALSE canInput: FALSE)
		((= theMusic music) owner: self priority: 15 init:)
		((= soundFx longSong2) owner: self init:)
		(= waitCursor HAND_CURSOR)
		(= possibleScore 0)
		(= userFont 1)
		(StatusLine code: statusCode disable:) ;hide the status code at startup
		(= musicChannels (DoSound NumVoices))
		(if
			(and
				(>= (= colorCount (Graph GDetect)) 2)
				(<= colorCount 16)
			)
			(Bclr fIsVGA)
		else
			(Bset fIsVGA)
		)
		(gameWindow
			color: myTextColor
			back: (EGAOrVGA myVGABackColor myEGABackColor)
			topBordColor: myEGABordColor2
			lftBordColor: (EGAOrVGA myVGABordColor2 myEGABordColor2)
			rgtBordColor: (EGAOrVGA myVGABordColor myEGABordColor)
			botBordColor: (EGAOrVGA myEGABackColor myEGABordColor)
		)
		(gcWin
			color: myTextColor
			back: (EGAOrVGA myVGABackColor myEGABackColor)
			topBordColor: myEGABordColor2
			lftBordColor: (EGAOrVGA myVGABordColor2 myEGABordColor2)
			rgtBordColor: (EGAOrVGA myVGABordColor myEGABordColor)
			botBordColor: (EGAOrVGA myEGABackColor myEGABordColor)
		)
		(invWin
			color: myTextColor
			back: (EGAOrVGA myEGABackColor myEGABordColor)
			topBordColor: (EGAOrVGA myVGABackColor myEGABordColor2)
			lftBordColor: (EGAOrVGA myVGABordColor myEGABordColor2)
			rgtBordColor: (EGAOrVGA myEGABordColor myEGABackColor)
			botBordColor: (EGAOrVGA myTextColor myEGABackColor)
			insideColor: (EGAOrVGA myEGABordColor myEGABackColor)
			topBordColor2: myTextColor
			lftBordColor2: myTextColor
			botBordColor2: (EGAOrVGA myVGABackColor myEGABordColor2)
			rgtBordColor2: (EGAOrVGA myVGABordColor2 myEGABordColor2)
			botBordHgt: 25
		)
		((= theIconBar IconBar)
			add: icon0 icon1 icon2 icon3 icon6 icon7 icon4 icon5 icon8 icon9
			eachElementDo: #init
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (EGAOrVGA myVGABackColor myEGABackColor)
			curIcon: icon0
			useIconItem: icon4
			helpIconItem: icon9
			disable:
		)
		(icon5 message: (if (HaveMouse) 3840 else 9))
		(Inventory
			init:
			add:
				Coin
				Bomb
				invLook
				invHand
				invSelect
				invHelp
				ok
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (EGAOrVGA myEGABordColor myEGABackColor)
			eachElementDo: #init
			window: invWin
			helpIconItem: invHelp
			selectIcon: invSelect
			okButton: ok
		)
		(GameControls
			window: gcWin
			add:
				iconOk
				(detailSlider theObj: self selector: #detailLevel yourself:)
				(volumeSlider theObj: self selector: #masterVolume yourself:)
				speedSlider
				(iconSave theObj: self selector: #save yourself:)
				(iconRestore theObj: self selector: #restore yourself:)
				(iconRestart theObj: self selector: #restart yourself:)
				(iconQuit theObj: self selector: #quitGame yourself:)
				(iconAbout theObj: self selector: #doit yourself:)
				iconHelp
			helpIconItem: iconHelp
			curIcon: iconRestore
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (EGAOrVGA myVGABordColor myEGABordColor)
		)
		(Coin owner: ego)
		(Bomb owner: ego)
		(= startingRoom rTitle)
		(theIconBar disable: hide:)
		(self newRoom: rSpeedTest)
	)
	
	(method (doit)
		(super doit:)
	)
	
	(method (replay)
;		(ShowStatus -1)
		(Palette PALIntensity 0 255 100)
		(super replay:)
	)
	
	(method (newRoom)
		(super newRoom: &rest)
	)
	
	(method (startRoom roomNum)
		(if pMouse (pMouse stop:))
		((ScriptID 801) doit: roomNum)
		;EO: Commenting this out until I can figure out how to not trigger this when
		;changing rooms.
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
		(if (cast contains: ego)
			(if (not (ego looper?)) (ego setLoop: stopGroop))
			(EgoHeadMove)
		)
		(redX init: hide: setPri: 15 posn: 1000 -1000)
	)

	
	(method (handleEvent event)
		(if debugging
			(if
				(and
					(== (event type?) mouseDown)
					(& (event modifiers?) shiftDown)
				)
				(if (not (User canInput:))
					(event claimed: TRUE)
				else
					(cast eachElementDo: #handleEvent event)
					(if (event claimed?) (return))
				)
			)
			(super handleEvent: event)
			(if (event claimed?) (return))
			(switch (event type?)
				(keyDown
					((ScriptID 800) handleEvent: event)
				)
				(mouseDown
					((ScriptID 800) handleEvent: event)
				)
			)
		else
			(super handleEvent: event)
		)
		(super handleEvent: event)
		(if (event claimed?) (return TRUE))
		(return
			(switch (event type?)
				(keyDown
					(switch (event message?)
						(TAB
							(if (not (& (icon5 signal?) $0004))
								(Inventory showSelf: ego)
							)
						)
						(SHIFTTAB
							(if (not (& (icon5 signal?) $0004))
								(Inventory showSelf: ego)
							)
						)
						(KEY_CONTROL
							(theGame quitGame:)
							(event claimed: TRUE)
						)
						(KEY_F2
							(cond 
								((theGame masterVolume:) (theGame masterVolume: 0))
								((> musicChannels 1) (theGame masterVolume: 15))
								(else (theGame masterVolume: 1))
							)
							(event claimed: TRUE)
						)
						(KEY_F5
							(theGame save:)
							(event claimed: TRUE)
						)
						(KEY_F7
							(theGame restore:)
							(event claimed: TRUE)
						)
						(KEY_F9
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
	
	(method (pragmaFail)
		(if (User canInput:) (VerbFail)
		)
	)
)

(instance ok of IconItem
	(properties
		view 901
		loop 3
		cel 0
		nsLeft 40
		cursor ARROW_CURSOR
		signal $0043
		helpStr {Select this Icon to close this window.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myVGABackColor myEGABackColor)
		)
		(super init:)
	)
)

(instance invLook of IconItem
	(properties
		view 901
		loop 2
		cel 0
		cursor 19
		message verbLook
		helpStr {Select this Icon then select an inventory item you'd like a description of.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myVGABackColor myEGABackColor)
		)
		(super init:)
	)
)

(instance invHand of IconItem
	(properties
		view 901
		loop 0
		cel 0
		cursor 20
		message verbDo
		helpStr {This allows you to do something to an item.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myVGABackColor myEGABackColor)
		)
		(super init:)
	)
)

(instance invHelp of IconItem
	(properties
		view 901
		loop 1
		cel 0
		cursor 29
		message verbHelp
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myVGABackColor myEGABackColor)
		)
		(super init:)
	)
)

(instance invSelect of IconItem
	(properties
		view 901
		loop 4
		cel 0
		cursor ARROW_CURSOR
		helpStr {This allows you to select an item.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myVGABackColor myEGABackColor)
		)
		(super init:)
	)
)

(instance Coin of InvItem
	(properties
		view 700
		cel 0
		cursor 1
		signal $0002
		description {the buckazoid coin}
		owner 40
	)
	(method (doVerb theVerb param2)
		(switch theVerb
			(verbLook
				(Print "It's a buckazoid coin.")
			)
		)
	)
)

(instance Bomb of InvItem
	(properties
		view 700
		cel 1
		cursor 22
		signal $0002
		description {the unstable ordinance}
		owner 40
	)
	(method (doVerb theVerb param2)
		(switch theVerb
			(verbLook
				(Print "It's a piece of unstable ordinance.")
			)
		)
	)
)

(instance redX of View
	(properties
		view 942
	)
)

(instance icon0 of IconItem
	(properties
		view 900
		loop 0
		cel 0
		cursor 6
		message verbWalk
		signal $0041
		helpStr {This icon is for walking.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
)

(instance icon1 of IconItem
	(properties
		view 900
		loop 1
		cel 0
		cursor 19
		message verbLook
		signal $0041
		helpStr {This icon is for looking.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
)

(instance icon2 of IconItem
	(properties
		view 900
		loop 2
		cel 0
		cursor 20
		message verbDo
		signal $0041
		helpStr {This icon is for doing.}
		maskView 900
		maskLoop 14
	)
)

(instance icon3 of IconItem
	(properties
		view 900
		loop 3
		cel 0
		cursor 7
		message verbTalk
		signal $0041
		helpStr {This icon is for talking.}
		maskView 900
		maskLoop 14
		maskCel 3
	)
)

(instance icon4 of IconItem
	(properties
		view 900
		loop 4
		cel 0
		cursor 999
		message verbUse
		signal $0041
		helpStr {This window displays the current inventory item.}
		maskView 900
		maskLoop 14
		maskCel 4
	)
)

(instance icon5 of IconItem
	(properties
		view 900
		loop 5
		cel 0
		cursor 999
		type $0000
		message 0
		signal $0043
		helpStr {This icon brings up the inventory window.}
		maskView 900
		maskLoop 14
		maskCel 2
	)
	
	(method (select)
		(if (super select:) (Inventory showSelf: ego))
	)
)

(instance icon6 of IconItem
	(properties
		view 900
		loop 10
		cel 0
		cursor 30
		message verbSmell
		signal $0041
		helpStr {This icon is for smelling.}
		maskView 900
		maskLoop 14
	)
)

(instance icon7 of IconItem
	(properties
		view 900
		loop 11
		cel 0
		cursor 31
		message verbTaste
		signal $0041
		helpStr {This icon is for tasting.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
)

(instance icon8 of IconItem
	(properties
		view 900
		loop 7
		cel 0
		cursor 999
		message 8
		signal $0043
		helpStr {This icon brings up the control panel.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
	
	(method (select)
		(if (super select:)
			(theIconBar hide:)
			(GameControls show:)
		)
	)
)

(instance icon9 of IconItem
	(properties
		view 900
		loop 9
		cel 0
		cursor 29
		message verbHelp
		signal $0003
		helpStr {This icon tells you about other icons.}
		maskView 900
		maskLoop 14
	)
)

(instance gameDoVerbCode of Code
	(properties)
	
	(method (doit param1 param2 &tmp temp0)
		(= temp0 (param2 description?))
		(switch param1
			(verbLook
				(if (param2 facingMe: ego)
					(if (param2 lookStr?)
						(Print (param2 lookStr?))
					)
				)
			)
		)
	)
)

(instance gameFtrInit of Code
	(properties)
	
	(method (doit param1)
		(if (== (param1 sightAngle?) 26505)
			(param1 sightAngle: 90)
		)
		(if (== (param1 actions?) 26505) (param1 actions: 0))
	)
)

(instance gameWindow of BorderWindow
	(properties)
)

(instance invWin of InsetWindow
	(properties)
)

(instance gcWin of BorderWindow
	(properties)
	
	(method (open &tmp temp0 temp1 temp2 temp3 temp4 temp5
			temp6 temp7 temp8 temp9 temp10 temp11 temp12
			[temp13 15] [temp28 4]
			)
			
		(self
			top: (/ (- 200 (+ (CelHigh 947 1 1) 6)) 2)
			left: (/ (- 320 (+ 151 (CelWide 947 0 1))) 2)
			bottom:
				(+
					(CelHigh 947 1 1)
					6
					(/ (- 200 (+ (CelHigh 947 1 1) 6)) 2)
				)
			right:
				(+
					151
					(CelWide 947 0 1)
					(/ (- 320 (+ 151 (CelWide 947 0 1))) 2)
				)
			priority: 15
		)
		(super open:)
		(DrawCel 947 0 5
			(+
				(/
					(-
						(- (+ 151 (CelWide 947 0 1)) (+ 4 (CelWide 947 1 1)))
						(CelWide 947 0 5)
					)
					2
				)
				4
				(CelWide 947 1 1)
			)
			3 15
		)
		(DrawCel 947 1 1 4 3 15)
		(DrawCel 947 1 0 94 38 15)
		(DrawCel 947 1 0 135 38 15)
		(DrawCel 947 0 4 63 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(DrawCel 947 0 3 101 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(DrawCel 947 0 2 146 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(Graph GShowBits 12 1 15 (+ 151 (CelWide 947 0 1)) 1)
		(= temp4 (+ (= temp1 (+ 46 (CelHigh 947 0 1))) 13))
		(= temp3
			(+
				(= temp2 (+ 10 (CelWide 947 1 1)))
				(-
					(+ 151 (CelWide 947 0 1))
					(+ 10 (CelWide 947 1 1) 6)
				)
			)
		)
		(= temp11 15)
		(= temp5 0)
		(= temp7 (EGAOrVGA myEGABackColor myEGABackColor))
		(= temp10 (EGAOrVGA myVGABordColor myEGABackColor))
		(= temp9 (EGAOrVGA myVGABordColor2 myEGABordColor2))
		(= temp8 myEGABordColor2)
		(= temp0 3)
		(= temp6 3)
		(Graph
			GFillRect
			temp1
			temp2
			(+ temp4 1)
			(+ temp3 1)
			temp6
			temp5
			temp11
		)
		(= temp1 (- temp1 temp0))
		(= temp2 (- temp2 temp0))
		(= temp3 (+ temp3 temp0))
		(= temp4 (+ temp4 temp0))
		(Graph
			GFillRect
			temp1
			temp2
			(+ temp1 temp0)
			temp3
			temp6
			temp7
			temp11
		)
		(Graph
			GFillRect
			(- temp4 temp0)
			temp2
			temp4
			temp3
			temp6
			temp8
			temp11
		)
		(= temp12 0)
		(while (< temp12 temp0)
			(Graph
				GDrawLine
				(+ temp1 temp12)
				(+ temp2 temp12)
				(- temp4 (+ temp12 1))
				(+ temp2 temp12)
				temp10
				temp11
				-1
			)
			(Graph
				GDrawLine
				(+ temp1 temp12)
				(- temp3 (+ temp12 1))
				(- temp4 (+ temp12 1))
				(- temp3 (+ temp12 1))
				temp9
				temp11
				-1
			)
			(++ temp12)
		)
		(Graph
			GShowBits
			temp1
			temp2
			(+ temp4 1)
			(+ temp3 1)
			1
		)
		(Format @temp13 "Score: %d of %d" score possibleScore)
		(TextSize @temp28 @temp13 999 0)
		(Display @temp13
			p_font 999
			p_color (EGAOrVGA myVGABackColor myEGABordColor2)
			p_at
			(+ 10
				(CelWide 947 1 1)
				(/
					(-
						(-
							(+ 151 (CelWide 947 0 1))
							(+ 10 (CelWide 947 1 1) 6)
						)
						[temp28 3]
					)
					2
				)
			)
			(+ 46 (CelHigh 947 0 1) 3)
		)
	)
)

(instance detailSlider of Slider
	(properties
		view 947
		loop 0
		cel 1
		nsLeft 67
		nsTop 37
		signal $0280
		helpStr {The graphics detail level.}
		sliderView 947
		yStep 2
		topValue 3
	)
)

(instance volumeSlider of Slider
	(properties
		view 947
		loop 0
		cel 1
		nsLeft 107
		nsTop 37
		signal $0080
		helpStr {Overall sound volume.}
		sliderView 947
		yStep 2
		topValue 15
	)
)

(instance speedSlider of Slider
	(properties
		view 947
		loop 0
		cel 1
		nsLeft 147
		nsTop 37
		signal $0280
		helpStr {The speed at which the player character moves.}
		sliderView 947
		yStep 2
		bottomValue 15
	)
	
	(method (doit param1)
		(if argc
			(theGame egoMoveSpeed: param1)
			(ego
				moveSpeed: (theGame egoMoveSpeed?)
				cycleSpeed: (theGame egoMoveSpeed?)
			)
		)
		(theGame egoMoveSpeed?)
	)
)

(instance iconSave of ControlIcon
	(properties
		view 947
		loop 2
		cel 0
		nsLeft 8
		nsTop 6
		message 7
		signal $01c3
		helpStr {Allows you to save your game.}
	)
)

(instance iconRestore of ControlIcon
	(properties
		view 947
		loop 3
		cel 0
		nsLeft 8
		nsTop 26
		message 7
		signal $01c3
		helpStr {Allows you to restore a previously saved game.}
	)
)

(instance iconRestart of ControlIcon
	(properties
		view 947
		loop 4
		cel 0
		nsLeft 8
		nsTop 46
		message 7
		signal $01c3
		helpStr {Allows you to restart the game.}
	)
)

(instance iconQuit of ControlIcon
	(properties
		view 947
		loop 5
		cel 0
		nsLeft 8
		nsTop 66
		message 7
		signal $01c3
		helpStr {Allows you to quit the game.}
	)
)

(instance iconAbout of ControlIcon
	(properties
		view 947
		loop 6
		cel 0
		nsLeft 34
		nsTop 106
		message 7
		signal $01c3
		helpStr {Information about the game.}
	)
)

(instance iconHelp of IconItem
	(properties
		view 947
		loop 7
		cel 0
		nsLeft 8
		nsTop 106
		cursor 29
		message 6
		signal $0183
	)
)

(instance iconOk of IconItem
	(properties
		view 947
		loop 8
		cel 0
		nsLeft 8
		nsTop 86
		message 7
		signal $01c3
		helpStr {Returns you to the game.}
	)
)
