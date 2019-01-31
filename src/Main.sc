;;; Sierra Script 1.0 - (do not remove this comment)
(script# 0)
(include system.sh) (include sci2.sh) (include game.sh)
(use Intrface)
(use PalInit)
(use PMouse)
(use SlideIcon)
(use BordWind)
(use IconBar)
(use SQEgo)
(use RandCyc)
(use StopWalk)
(use DCIcon)
(use Grooper)
(use Sound)
(use Game)
(use Invent)
(use PrintD)
(use User)
(use Actor)
(use System)

(public
	SCI10 0
	SetUpEgo 1
	HandsOff 2
	HandsOn 3
	IsHeapFree 4
	IsObjectOnControl 5
	Btst 6
	Bset 7
	Bclr 8
	EgoHeadMove 9
	proc0_10 10
	AddToScore 11
	WindowlessPrint 12
	AimToward 13
	ShowStatus 14
	proc0_15 15
	VerbFail 16
	proc0_17 17
	proc0_18 18
	EgoDead 19
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
	showStyle =  7
	overRun
	theCursor
	normalCursor =  999
	waitCursor =  20
	userFont =  1
	smallFont =  4
	lastEvent
	modelessDialog
	bigFont =  1
	version
	locales
	curSaveDir
	animationDelay =  10
	perspective
	features
	saidFeatures
	useSortedFeatures
	egoBlindSpot
	overlays =  -1
	doMotionCue
	systemWindow
	global39 =  3
	defaultPalette
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
	useObstacles =  1
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
	global83
	fastCast
	inputFont
	tickOffset
	howFast
	gameTime
	narrator
	global90
	global91
	global92
	global93
	global94
	global95
	global96
	global97
	global98
	global99
	music
	deathMusic = sDeath
	global102
	global103 =  1
	global104
	colorCount
	musicChannels
	startingRoom
	global108
	global109
	global110
	global111
	gEgoHead
	gStopGroop
	[gameFlags 10]
	global124
	global125
	global126
	global127
	global128
	global129
	global130
	global131
	global132
	global133
	global134
	global135
	global136
	global137
	global138
	global139
	global140
	global141
	global142
	global143
	global144
	global145
	global146
	global147
	global148
	global149
	global150
	global151
	global152
	global153
	SFX
	global155
	global156
	global157
	global158
	numBuckazoids =  59
	global160
	global161
	global162
	global163
	global164
	global165
	global166
	global167
	global168
	global169 =  2001
	global170
	global171
	global172
	global173 =  10
	global174
	global175
	global176
	global177
	global178
	global179
	global180
	global181
	global182
	global183
	global184
	global185
	global186
	global187
	global188
	global189
	global190
	global191
	global192
	global193
	global194
)
(procedure (SetUpEgo param1 param2 param3 &tmp temp0)
	(= temp0 0)
	(if (> argc 0)
		(ego loop: param1)
		(if (> argc 1)
			(ego view: param2)
			(if (> argc 2) (= temp0 param3))
		)
	)
	(if (not temp0) (= temp0 4))
	(ego
		normal: 1
		moveHead: 1
		setLoop: -1
		setLoop: stopGroop
		setPri: -1
		setMotion: 0
		setCycle: StopWalk temp0
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
		(= global192 ((User curEvent?) x?))
		(= global193 ((User curEvent?) y?))
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
			setCursor: ((theIconBar curIcon?) cursor?) 1 global192 global193
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

(procedure (EgoHeadMove param1 &tmp temp0)
	(= temp0 0)
	(if argc (= temp0 param1) else (= temp0 4))
	((= gEgoHead egoHead)
		init: ego
		view: temp0
		cycleSpeed: 24
	)
)


(procedure (proc0_10 param1 param2)
	(if (> argc 0)
		(= global186 param1)
		(if (OneOf (ego view?) 373 374 993)
			(if (== global186 0) (= global186 7))
			(if (== global186 8) (= global186 9))
		)
	else
		(= global186 0)
	)
	(if (> argc 1)
		(= global187 param2)
	else
		(= global187 0)
	)
	(curRoom newRoom: 900)
)

(procedure (AddToScore flag points)
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
			(= temp6 global130)
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

(procedure (ShowStatus str)
	(StrCpy @str {__Template Game})
	(DrawStatus @str 0 (proc0_18 global158 global155))
)

(procedure (proc0_15 param1 param2)
	(return (if (== (param1 onControl:) param2) (return 1) else 0))
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

(procedure (proc0_18 param1 param2)
	(if (< param1 0) (= param1 0))
	(if (> param1 255) (= param1 255))
	(if (< param2 0) (= param2 0))
	(if (> param2 15) (= param2 15))
	(return (if (Btst 21) param1 else param2))
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
	(music number: deathMusic play:)
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


(instance egoBody of SQEgo
	(properties
		description {you}
		sightAngle 180
		lookStr {It's you!}
	)
	
	(method (doVerb theVerb theItem)
		(switch theVerb
			(V_TALK (Print {You talk to yourself but are stumped for a reply.}))
			(V_DO (Print {Hey! Keep your hands off yourself! This is a family game.}))
			(V_TASTE (Print {I'll bet you wish you could!}))
			(V_SMELL (Print {Ahhh!  The aroma of several adventure games emanates from your person.}))
			(V_ITEM
				(switch theItem
					(iCoin (Print {There isn't much you can do to it what inflation hasn't already.}))
					(iBomb (EgoDead {Maybe messing with the unstable ordinance wasn't such a hot idea...}))
					(else  (VerbFail))
				)
			)
			(else  (super doVerb: theVerb))
		)
	)
)

(instance egoHead of Head
	(properties
		description {your head.}
		lookStr {It's your head.}
		view 4
	)
	
	(method (doVerb theVerb theItem)
		(ego doVerb: theVerb theItem)
	)
)

(instance longSong of Sound
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

(instance stopGroop of GradualLooper
	(properties)
	
	(method (doit)
		(if
			(and
				(IsObject (ego cycler?))
				((ego cycler?) isKindOf: StopWalk)
			)
			(ego view: ((ego cycler?) vWalking?))
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

(instance sq4KeyDownHandler of EventHandler
	(properties)
)

(instance sq4MouseDownHandler of EventHandler
	(properties)
)

(instance sq4DirectionHandler of EventHandler
	(properties)
)

(instance SCI10 of Game
	(properties)
	
	(method (init &tmp temp0)
		(= systemWindow sq4Win)
		(PalInit)
		(= gStopGroop stopGroop)
		(= deathMusic sDeath)
		(= useSortedFeatures TRUE)
		(super init:)
		(StrCpy @sysLogPath {})
		(= doVerbCode sq4DoVerbCode)
		(= ftrInitializer sq4FtrInit)
		((= keyDownHandler sq4KeyDownHandler) add:)
		((= mouseDownHandler sq4MouseDownHandler) add:)
		((= directionHandler sq4DirectionHandler) add:)
		(= pMouse PseudoMouse)
		(self egoMoveSpeed: 0 setCursor: theCursor TRUE 304 172)
		((= ego egoBody)
			_head: (= gEgoHead egoHead)
			moveSpeed: (self egoMoveSpeed?)
			cycleSpeed: (self egoMoveSpeed?)
		)
		((ego _head?) client: ego)
		(User
			alterEgo: ego
			verbMessager: 0
			canControl: FALSE
			canInput: FALSE
		)
		((= music longSong) owner: self init: flags: 1)
		((= SFX longSong2) owner: self init:)
		(= waitCursor HAND_CURSOR)
		(= possibleScore 0)
		(= userFont 1)
		(= version {x.yyy})
		(= musicChannels (DoSound sndDISPOSE))
		(if
			(and
				(>= (= colorCount (Graph GDetect)) 2)
				(<= colorCount 16)
			)
			(Bclr 21)
		else
			(Bset 21)
		)
		(sq4Win
			color: 0
			back: (proc0_18 global158 global155)
			topBordColor: global130
			lftBordColor: (proc0_18 global161 global130)
			rgtBordColor: (proc0_18 global157 global156)
			botBordColor: global156
		)
		(gcWin
			color: 0
			back: (proc0_18 global158 global155)
			topBordColor: global130
			lftBordColor: (proc0_18 global161 global130)
			rgtBordColor: (proc0_18 global157 global156)
			botBordColor: global156
		)
		(invWin
			color: 0
			back: (proc0_18 global156 global155)
			topBordColor: (proc0_18 global158 global130)
			lftBordColor: (proc0_18 global157 global130)
			rgtBordColor: (proc0_18 global155 global156)
			botBordColor: (proc0_18 global129 global156)
			insideColor: (proc0_18 global155 global156)
			topBordColor2: global129
			lftBordColor2: global129
			botBordColor2: (proc0_18 global158 global130)
			rgtBordColor2: (proc0_18 global161 global130)
		)
		((= theIconBar IconBar)
			add: icon0 icon1 icon2 icon3 icon6 icon7 icon4 icon5 icon8 icon9
			eachElementDo: #init
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (proc0_18 global158 global155)
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
			eachElementDo: #lowlightColor (proc0_18 global155 global156)
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
				(detailSlider
					theObj: self
					selector: 291
					topValue: 3
					bottomValue: 0
					yourself:
				)
				(volumeSlider
					theObj: self
					selector: 381
					topValue: (if (> musicChannels 1) 15 else 1)
					bottomValue: 0
					yourself:
				)
				(speedSlider
					theObj: self
					selector: 378
					topValue: 1
					bottomValue: 15
					yourself:
				)
				(iconSave theObj: self selector: 78 yourself:)
				(iconRestore theObj: self selector: 79 yourself:)
				(iconRestart theObj: self selector: 104 yourself:)
				(iconQuit theObj: self selector: 103 yourself:)
				(iconAbout
					theObj: (ScriptID 811 0)
					selector: 60
					yourself:
				)
				iconHelp
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (proc0_18 global157 global156)
			helpIconItem: iconHelp
			curIcon: iconRestore
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
		(ShowStatus -1)
		(Palette 4 0 255 100)
		(super replay:)
	)
	
	(method (newRoom)
		(super newRoom: &rest)
	)
	
	(method (startRoom roomNum)
		(if pMouse (pMouse stop:))
		((ScriptID 801) doit: roomNum)
		(if debugOn (SetDebug))
		(ScriptID SIGHT)
		(super startRoom: roomNum)
		(if (cast contains: ego)
			(if
				(and
					(ego normal?)
					(not (OneOf roomNum 405 406 410 411))
					(not ((ego cycler?) isKindOf: StopWalk))
				)
				(ego setCycle: StopWalk 4)
			)
			(if (not (ego looper?)) (ego setLoop: stopGroop))
			(EgoHeadMove (egoHead view?))
		)
		(redX init: hide: setPri: 15 posn: 1000 -1000)
	)
	
	(method (handleEvent event)
		(super handleEvent: event)
		(if (event claimed?) (return TRUE))
		(return
			(switch (event type?)
				(keyDown
					(switch (event message?)
						(KEY_TAB
							(if (not (& (icon5 signal?) $0004))
								(Inventory showSelf: ego)
							)
						)
						(KEY_SHIFTTAB
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
	(method (quitGame)
		(super
			quitGame:
				(Print {Do you really want to stop playing?}
					#button {Quit} 1
					#button {Don't Quit} 0
				)
		)
	)
	
	(method (pragmaFail)
		(if (User canInput:) (VerbFail))
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
			lowlightColor: (proc0_18 global158 global155)
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
		message V_LOOK
		helpStr {Select this Icon then select an inventory item you'd like a description of.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (proc0_18 global158 global155)
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
		message V_DO
		helpStr {This allows you to do something to an item.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (proc0_18 global158 global155)
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
		message V_HELP
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (proc0_18 global158 global155)
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
			lowlightColor: (proc0_18 global158 global155)
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
		description {It's a buckazoid coin.}
		owner 40
	)
)

(instance Bomb of InvItem
	(properties
		view 700
		cel 1
		cursor 22
		signal $0002
		description {It's a piece of unstable ordinance.}
		owner 40
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
		message V_WALK
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
		message V_LOOK
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
		message V_DO
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
		message V_TALK
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
		message V_ITEM
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
		message V_SMELL
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
		message V_TASTE
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
		message V_HELP
		signal $0003
		helpStr {This icon tells you about other icons.}
		maskView 900
		maskLoop 14
	)
)

(instance sq4DoVerbCode of Code
	(properties)
	
	(method (doit param1 param2 &tmp temp0)
		(= temp0 (param2 description?))
		(switch param1
			(V_LOOK
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

(instance sq4FtrInit of Code
	(properties)
	
	(method (doit param1)
		(if (== (param1 sightAngle?) 26505)
			(param1 sightAngle: 90)
		)
		(if (== (param1 actions?) 26505) (param1 actions: 0))
	)
)

(instance sq4Win of BorderWindow
	(properties)
)

(instance invWin of InsetWindow
	(properties)
)

(instance gcWin of BorderWindow
	(properties)
	
	(method (open &tmp temp0 temp1 temp2 temp3 temp4 temp5 temp6 temp7 temp8 temp9 temp10 temp11 temp12 temp13 [temp14 15] [temp29 4])
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
		(DrawCel
			947
			0
			5
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
			6
			15
		)
		(DrawCel 947 1 1 4 3 15)
		(DrawCel 947 1 0 94 38 15)
		(DrawCel 947 1 0 135 38 15)
		(DrawCel 947 0 4 63 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(DrawCel 947 0 3 101 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(DrawCel 947 0 2 146 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(= temp5 (+ (= temp2 (+ 46 (CelHigh 947 0 1))) 13))
		(= temp4
			(+
				(= temp3 (+ 10 (CelWide 947 1 1)))
				(-
					(+ 151 (CelWide 947 0 1))
					(+ 10 (CelWide 947 1 1) 6)
				)
			)
		)
		(= temp12 15)
		(= temp6 0)
		(= temp8 global156)
		(= temp11 (proc0_18 global157 global156))
		(= temp10 (proc0_18 global161 global130))
		(= temp9 global130)
		(= temp1 3)
		(= temp7 3)
		(Graph
			grFILL_BOX
			temp2
			temp3
			(+ temp5 1)
			(+ temp4 1)
			temp7
			temp6
			temp12
		)
		(= temp2 (- temp2 temp1))
		(= temp3 (- temp3 temp1))
		(= temp4 (+ temp4 temp1))
		(= temp5 (+ temp5 temp1))
		(Graph
			grFILL_BOX
			temp2
			temp3
			(+ temp2 temp1)
			temp4
			temp7
			temp8
			temp12
		)
		(Graph
			grFILL_BOX
			(- temp5 temp1)
			temp3
			temp5
			temp4
			temp7
			temp9
			temp12
		)
		(= temp13 0)
		(while (< temp13 temp1)
			(Graph
				grDRAW_LINE
				(+ temp2 temp13)
				(+ temp3 temp13)
				(- temp5 (+ temp13 1))
				(+ temp3 temp13)
				temp11
				temp12
				-1
			)
			(Graph
				grDRAW_LINE
				(+ temp2 temp13)
				(- temp4 (+ temp13 1))
				(- temp5 (+ temp13 1))
				(- temp4 (+ temp13 1))
				temp10
				temp12
				-1
			)
			(++ temp13)
		)
		(Graph
			grUPDATE_BOX
			temp2
			temp3
			(+ temp5 1)
			(+ temp4 1)
			1
		)
		(Format @temp14 {Score: %d of %d} score possibleScore)
		(TextSize @temp29 @temp14 999 0)
		(Display
			@temp14
			dsFONT
			999
			dsCOLOR
			(proc0_18 global161 global130)
			dsCOORD
			(+
				10
				(CelWide 947 1 1)
				(/
					(-
						(-
							(+ 151 (CelWide 947 0 1))
							(+ 10 (CelWide 947 1 1) 6)
						)
						[temp29 3]
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
		signal $0080
		helpStr {Raises and lowers the level of graphics detail.}
		sliderView 947
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
		helpStr {Adjusts sound volume.}
		sliderView 947
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
		signal $0080
		helpStr {Adjusts the speed of the game's animation (within the limits of your computer's capability).}
		sliderView 947
		bottomValue 15
	)
)

(instance iconSave of ControlIcon
	(properties
		view 947
		loop 2
		cel 0
		nsLeft 8
		nsTop 6
		message 9
		signal $01c3
		helpStr {Saves your current game.}
	)
)

(instance iconRestore of ControlIcon
	(properties
		view 947
		loop 3
		cel 0
		nsLeft 8
		nsTop 26
		message 9
		signal $01c3
		helpStr {Restores a previously saved game.}
	)
)

(instance iconRestart of ControlIcon
	(properties
		view 947
		loop 4
		cel 0
		nsLeft 8
		nsTop 46
		message 9
		signal $01c3
		helpStr {Restarts the Game.}
	)
)

(instance iconQuit of ControlIcon
	(properties
		view 947
		loop 5
		cel 0
		nsLeft 8
		nsTop 66
		message 9
		signal $01c3
		helpStr {Exits the game.}
	)
)

(instance iconAbout of ControlIcon
	(properties
		view 947
		loop 6
		cel 0
		nsLeft 8
		nsTop 86
		message 9
		signal $01c3
		helpStr {Information about the game.}
	)
)

(instance iconHelp of IconItem
	(properties
		view 947
		loop 7
		cel 0
		nsLeft 34
		nsTop 86
		cursor 70
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
		nsTop 106
		cursor 70
		message 9
		signal $01c3
		helpStr {Exits this menu.}
	)
)
