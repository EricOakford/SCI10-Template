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
	HaveMem 4
	IsObjectOnControl 5
	Btst 6
	Bset 7
	Bclr 8
	SolvePuzzle 10
	Face 11
	Speak 12
	EGAOrVGA 13
	EgoDead 14
	cls 15
	DoDisplay 16
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
	waitCursor =  HAND_CURSOR
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
	numColors
	numVoices
	startingRoom
	pMouseX
	pMouseY
	theEgoHead
	egoLooper
	egoStopWalk
	egoAvoider
	[gameFlags 10]
	myTextColor
	myInsideColor
	myBotBordColor
	myRgtBordColor
	myBackColor
	myLftBordColor
	myTopBordColor
	myHighlightColor
	debugging
	egoTalker
)
(procedure (NormalEgo)
	(ego
		normal: TRUE
		setLoop: -1
		looper: egoLooper
		setPri: -1
		setMotion: 0
		setCycle: egoStopWalk
		setStep: 3 2
		setAvoider: egoAvoider
		ignoreActors: FALSE
		moveSpeed: (theGame egoMoveSpeed?)
		cycleSpeed: (theGame egoMoveSpeed?)
	)
)

(procedure (HandsOff &tmp oldIcon)
	(User canControl: FALSE canInput: FALSE)
	(ego setMotion: 0)
	(= oldIcon (theIconBar curIcon?))
	(theIconBar disable:
		ICON_WALK
		ICON_LOOK
		ICON_DO
		ICON_TALK
		ICON_ITEM
		ICON_INVENTORY
		ICON_SMELL
		ICON_TASTE
	)
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
	(theIconBar enable:
		ICON_WALK
		ICON_LOOK
		ICON_DO
		ICON_TALK
		ICON_ITEM
		ICON_INVENTORY
		ICON_SMELL
		ICON_TASTE
	)
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

(procedure (HaveMem howMuch)
	(return (u> (MemoryInfo LargestPtr) howMuch))
)

(procedure (IsObjectOnControl theObj theControl)
	(return
		(if (& (theObj onControl: origin) theControl)
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

(procedure (Face actor1 actor2 both whoToCue &tmp ang1to2 theX theY obj)
	(= obj 0)
	(if (IsObject actor2)
		(= theX (actor2 x?))
		(= theY (actor2 y?))
		(if (== argc 3) (= obj both))
	else
		(= theX actor2)
		(= theY both)
		(if (== argc 4) (= obj whoToCue))
	)
	(= ang1to2
		(GetAngle (actor1 x?) (actor1 y?) theX theY)
	)
	(actor1
		setHeading: ang1to2 (if (IsObject obj) obj else 0)
	)
)

(procedure (Speak theView theString moreStuff &tmp [str 500])
	(if (u< theString 1000)
		(GetFarText theString moreStuff @str)
	else
		(StrCpy @str theString)
	)
	(babbleIcon
		view: theView
		cycleSpeed: (* (+ howFast 1) 4)
	)
	(if (u< theString 1000)
		(Print @str &rest #icon babbleIcon 0 0)
	else
		(Print @str moreStuff &rest #icon babbleIcon 0 0)
	)
)

(procedure (EGAOrVGA vga ega)
	(if (< vga 0) (= vga 0))			;if color is less than zero, make it equal zero
	(if (> vga 255) (= vga 255))		;if color is more than 255, make it equal 255 
	(if (< ega 0) (= ega 0))			;if color is less than zero, make it equal zero
	(if (> ega 15) (= ega 15))			;if color is more than 15, make it equal 15
	(return
		(if (Btst fIsVGA) vga else ega)
	)
)

(procedure (EgoDead theView theLoop theCel &tmp deadView deadLoop deadCel [str 300])
	(sounds eachElementDo: #stop)
	(if argc
		(= deadView theView)
		(= deadLoop theLoop)
		(= deadCel theCel)
		(Format @str &rest)
	else
		(= deadView 944)
		(= deadLoop 0)
		(= deadCel 0)
		(Format @str "It's all over for now. Please try again.")
	)
	(music number: 900 vol: 127 loop: 1 flags: mNOPAUSE play:)
	(theGame setCursor: normalCursor TRUE)
	(repeat
		(switch
			(Print @str
				#mode teJustCenter
				#button {Restore} 1
				#button {Restart} 2
				#button {____Quit____} 3
				#icon deadView deadLoop deadCel
			)
			(1
				(theGame restore:)
			)
			(2
				(theGame restart:)
			)
			(3
				(= quit TRUE)
				(break)
			)
		)
	)
)


(procedure (cls)
	(if modelessDialog
		(modelessDialog dispose:)
	)
)

(procedure (DoDisplay theString &tmp
		theMode theForeFont theBackFont theWidth theX theY theForeColor theBackColor ret)
	(return
		(if (== argc 1)
			(Display "" p_restore [theString 0])
		else
			(= theX
				(= theY -1)
			)
			(= theMode teJustLeft)
			(= theForeFont 68)
			(= theBackFont 69)
			(= theWidth -1)
			(= theForeColor myTopBordColor)
			(= theBackColor 0)
			(= ret 1)
			(while (< ret argc)
				(switch [theString ret]
					(#mode
						(= theMode
							[theString (++ ret)]
						)
					)
					(#font
						(= theBackFont
							(+
								(= theForeFont
									[theString (++ ret)]
								)
								1
							)
						)
					)
					(#width
						(= theWidth
							[theString (++ ret)]
						)
					)
					(#at
						(= theX
							[theString (++ ret)]
						)
						(= theY
							[theString (++ ret)]
						)
					)
					(#color
						(= theForeColor [theString (++ ret)])
					)
					(#back
						(= theBackColor
							[theString (++ ret)]
						)
					)
				)
				(++ ret)
			)
			(= ret
				(Display
					[theString 0]
					p_at theX theY
					p_color theBackColor
					p_width theWidth
					p_mode theMode
					p_font theBackFont
					p_save
				)
			)
			(Display
				[theString 0]
				p_at theX theY
				p_color theForeColor
				p_width theWidth
				p_mode theMode
				p_font theForeFont
			)
			(return ret)
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
	
	(method (doit roomNum &tmp [str 50])
		(if
			;add rooms where the status line is not shown
			(not (OneOf roomNum 
					rTitle SPEED
				 )
			)	
		(Format @str "___Template Game__________________Score: %d of %d" score possibleScore)
		(DrawStatus @str 23 0)
		)
	)
)

(instance stopGroop of GradualLooper
	
	(method (doit)
		(if (== (ego loop?) (- (NumLoops ego) 1))
			(ego loop: (ego cel?))
		)
		(super doit: &rest)
	)
)

(instance egoSW of StopWalk)

(instance egoAvoid of PAvoider)

(instance babbleIcon of DCIcon
	
	(method (init)
		((= cycler (RandCycle new:)) init: self 20)
	)
)

(instance keyH of EventHandler)		;get keyDown events

(instance dirH of EventHandler)		;get direction events

(instance mouseH of EventHandler)	;get mouseDown events

(instance SCI10 of Game
	(properties
		;Set your game's language here.
		;Supported langauges can be found in SYSTEM.SH.
		printLang ENGLISH
	)
	
	(method (init)
		;load some important modules
		(= systemWindow BorderWindow)
		(super init: &rest)
		
		(StrCpy @sysLogPath {})
		(= egoLooper stopGroop)
		(= egoStopWalk egoSW)
		(= egoAvoider egoAvoid)
		(= doVerbCode DoVerbCode)
		(= ftrInitializer FtrInit)
		((= keyDownHandler keyH) add:)
		((= mouseDownHandler mouseH) add:)
		((= directionHandler dirH) add:)
		(= pMouse PseudoMouse)
		(self
			egoMoveSpeed: 5
			setSpeed: 0
			setCursor: theCursor TRUE 304 172
		)
		((= theMusic music)
			owner: self
			flags: mNOPAUSE
			init:
		)
		((= soundFx SFX)
			owner: self
			flags: mNOPAUSE
			init:
		)
		;Moved the icon bar, inventory, and control panel into their own scripts.
		(= ego (ScriptID GAME_EGO 0))
		(User alterEgo: ego canControl: FALSE canInput: FALSE)		
		((ScriptID GAME_ICONBAR 0) init:)
		((ScriptID GAME_INV 0) init:)
		((ScriptID GAME_CONTROLS 0) init:)	
		((theIconBar at: ICON_INVENTORY)
			message: (if (HaveMouse) SHIFTTAB else TAB)
		)
		((ScriptID GAME_INIT 0) init:)
	)
	
	(method (startRoom roomNum)
		((ScriptID DISPOSE_CODE) doit: roomNum)
		(if
			(and
				(u> (MemoryInfo FreeHeap) (+ 20 (MemoryInfo LargestPtr)))
				(Print "Memory fragmented." #button {Debug} 1)
			)
			(theGame showMem:)
		)		
		(if debugging
			((ScriptID DEBUG 0) init:)
		)
		(if pMouse
			(pMouse stop:)
		)
		(NormalEgo)
		(statusCode doit: roomNum)
		(super startRoom: roomNum)
	)

	
	(method (handleEvent event)
		(super handleEvent: event)
		(if (event claimed?)
			(return TRUE)
		)
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
						(`^q
							(theGame quitGame:)
							(event claimed: TRUE)
						)
						(`#2
							(cond 
								((theGame masterVolume:)
									(theGame masterVolume: 0)
								)
								((> numVoices 1)
									(theGame masterVolume: 15)
								)
								(else
									(theGame masterVolume: 1)
								)
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
			(if (< (= egoMoveSpeed newSpeed) 0)
				(= egoMoveSpeed 0)
			)
			(if (> egoMoveSpeed 15) (= egoMoveSpeed 15))
			(ego cycleSpeed: newSpeed moveSpeed: newSpeed)
		)
		(return egoMoveSpeed)
	)
		
	(method (quitGame)
		(super quitGame:
			(Print "Do you really want to stop playing?"
				#button {Quit} TRUE
				#button {Don't Quit} FALSE
			)
		)
	)
	
	(method (showAbout)
		((ScriptID GAME_ABOUT 0) doit:)
	)
	
	(method (pragmaFail &tmp theVerb theObj)
		(if (User canInput:)
			(= theVerb ((User curEvent?) message?))
			(if (theIconBar curInvIcon?)
				(= theObj
					((theIconBar curInvIcon?) description?)
				)
			else
				(= theObj
					(theDefaultFeature description?)
				)
			)
			(cls)
			(switch theVerb
				(verbLook
					(Print "It's not much to look at.")
				)
				(verbTalk
					(Print "To whom were you trying to speak?")
				)
				(verbDo
					(Print "There's nothing to do there.")
				)
				(verbUse
					(switch theObj
						(((inventory at: iCoin) description?)
							(Print "Don't go throwing money like you're made of it!")
						)
						(else
							(Print "Nothing happens.")
						)
					)
				)
				(verbSmell
					(Print "Funny... no smell.")
				)
				(verbTaste
					(Print "Funny... no taste.")
				)
			)
		)
	)
)

(instance DoVerbCode of Code
	
	(method (doit theVerb theObj theItem &tmp objDesc itemDesc)
		(= objDesc
			(theObj description?)
		)
		(if (inventory at: theItem)
			(= itemDesc
				((inventory at: theItem) description?)
			)
		else
			(= itemDesc
				(theDefaultFeature description?)
			)
		)
		(cls)
		(if (theObj facingMe: ego)
			(switch theVerb
				(verbLook
					(if (theObj lookStr?)
						(Print (theObj lookStr?))
					else
						(Printf "Why, look! It's %s." objDesc)
					)
				)
				(verbDo
					(Printf "That feels exactly like %s." objDesc)
				)
				(verbTalk
					(Printf "Don't bother trying to talk to %s." objDesc)
				)
				(verbUse
					(switch theItem 
						(iCoin
							(Print "Your money's no good there.")
						)
						(else
							(Printf "It seems %s just doesn't work with %s." itemDesc objDesc)
						)
					)
				)
				(verbSmell
					(Printf "To you, %s has no distinct smell." objDesc)
				)
				(verbTaste
					(Printf "The taste of %s is exactly as you would expect." objDesc)
				)
			)
		)
	)
)


(instance FtrInit of Code
	
	(method (doit obj)
		(if (== (obj sightAngle?) ftrDefault)
			(obj sightAngle: 90)
		)
		(if (== (obj actions?) ftrDefault)
			(obj actions: 0)
		)
	)
)

(instance theDefaultFeature of Feature
	(properties
		description {that thing (whatever it is)}
	)
)
