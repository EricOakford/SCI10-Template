;;; Sierra Script 1.0 - (do not remove this comment)
;
;	MAIN.SC
;
;	This is the main game script. It contains the main game instance and all the global variables.
;
;

(script# MAIN)
(include game.sh) (include language.sh)
(use GameEgo)
(use Procs)
(use Intrface)
(use PMouse)
(use Polygon)
(use PolyPath)
(use IconBar)
(use Feature)
(use Window)
(use Sound)
(use Game)
(use User)
(use System)

(public
	SCI10 0 ;Replace "SCI10" with the game's internal name (up to 6 characters)
	HandsOn 1
	HandsOff 2
)

(local
	ego										;pointer to ego
	theGame									;ID of the Game instance
	curRoom									;ID of current room
	speed				=	6				;number of ticks between animations
	quit									;when TRUE, quit game
	cast									;collection of actors
	regions									;set of current regions
	timers									;list of timers in the game
	sounds									;set of sounds being played
	inventory								;set of inventory items in game
	addToPics								;list of views added to the picture
	curRoomNum								;current room number
	prevRoomNum								;previous room number
	newRoomNum								;number of room to change to
	debugOn									;generic debug flag -- set from debug menu
	score									;the player's current score
	possibleScore							;highest possible score
	showStyle			=	IRISOUT			;style of picture showing
	aniInterval								;# of ticks it took to do the last animation cycle
	theCursor								;the number of the current cursor
	normalCursor		=	ARROW_CURSOR	;number of normal cursor form
	waitCursor			=	HAND_CURSOR		;cursor number of "wait" cursor
	userFont			=	USERFONT		;font to use for Print
	smallFont			=	4		;small font for save/restore, etc.
	lastEvent								;the last event (used by save/restore game)
	modelessDialog							;the modeless Dialog known to User and Intrface
	bigFont				=	USERFONT		;large font
	version				=	0				;pointer to 'incver' version string
											;***WARNING***  Must be set in room 0
											; (usually to {x.yyy    } or {x.yyy.zzz})
	locales									;set of current locales
	curSaveDir								;address of current save drive/directory string
	aniThreshold		=	10
	perspective								;player's viewing angle:
											;	 degrees away from vertical along y axis
	features								;locations that may respond to events
	sortedFeatures							;above+cast sorted by "visibility" to ego
	useSortedFeatures	=	FALSE			;enable cast & feature sorting?
	egoBlindSpot		=	0				;used by sortCopy to exclude 
											;actors behind ego within angle 
											;from straight behind. 
											;Default zero is no blind spot
	overlays			=	-1
	doMotionCue								;a motion cue has occurred - process it
	systemWindow							;ID of standard system window
	demoDialogTime		=	3				;how long Prints stay up in demo mode
	currentPalette							;
	modelessPort		
	[sysLogPath 20]								;used for system standard logfile path	
	endSysLogPath					;uses 20 globals
	gameControls		
	ftrInitializer		
	doVerbCode			
	firstSaidHandler				;will be the first to handle said events
	useObstacles		=	TRUE	;will Ego use PolyPath or not?
	theMenuBar						;points to TheMenuBar or Null	
	theIconBar						;points to TheIconBar or Null	
	mouseX				
	mouseY				
	keyDownHandler					;our EventHandlers, get called by the game
	mouseDownHandler	
	directionHandler	
	gameCursor			
	lastVolume			
	pMouse				=	NULL	;pointer to a Pseudo-Mouse, or NULL
	theDoits			=	NULL	;list of objects to get doits done every cycle
	eatMice				=	60		;how many ticks minimum that a window stays up	
	user				=	NULL	;pointer to specific applications User
	syncBias						;; globals used by sync.sc (will be removed shortly)
	theSync				
	cDAudio				
	fastCast			
	inputFont			=	SYSFONT	;font used for user type-in

	tickOffset			

	howFast				 ;; measurment of how fast a machine is
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
	;globals 100 and above are for game use	
	theMusic	;music object, current playing music
	gameCode = 1234	;EO: Don't mess with this, or you'll get Error 11 shortly after startup!
	theMusic2	;second sound object, can be used for sound effects
	;standard globals for colors
	colBlack
	colGray1
	colGray2
	colGray3
	colGray4
	colGray5
	colWhite
	colDRed
	colLRed
	colVLRed
	colDYellow
	colYellow
	colLYellow
	colVDGreen
	colDGreen
	colLGreen
	colVLGreen
	colDBlue
	colBlue
	colLBlue
	colVLBlue
	colMagenta
	colLMagenta
	colCyan
	colLCyan
	;end standard color globals
	myTextColor				;color of text in message boxes
	myBackColor				;color of message boxes
	
	[gameFlags FLAG_ARRAY]	;array of all event flags. It can be increased in GAME.SH.
	saveCursorX				; position of cursor when HandsOff is used
	saveCursorY				;
	numColors				;Number of colors supported by graphics driver
	numVoices				;Number of voices supported by sound driver
	debugging				;debug mode enabled
	isHandsOff				;ego can't be controlled
	egoLooper				;pointer for ego's stopGroop
	deathReason
	theCurIcon
	iconSettings
)

;
; Global sound objects
(instance longSong of Sound
	(properties
		flags mNOPAUSE
	)
)

(instance longSong2 of Sound
	(properties
		flags mNOPAUSE
	)
)

;
;  Sound used only by theGame:solvePuzzle
(instance pointsSound of Sound
	(properties
		number sScore
		flags mNOPAUSE
	)
)
;
; Event handlers
(instance keyH of EventHandler)
(instance mouseH of EventHandler)
(instance dirH of EventHandler)

;
; The main game instance. It adds game-specific functionality.	
; Replace "SCI10" with the game's internal name (up to 6 characters)
(instance SCI10 of Game
	(properties
		printLang ENGLISH	;set your game's language here. Supported languages can be found in LANGUAGE.SH.
	)

	(method (init)
		;load up the standard game system
		(= systemWindow SysWindow)
		(= version {x.yyy})
		(super init: &rest)
		
		;initialize the colors first
		((ScriptID COLOR_INIT 0) doit:)
		
		;set up the global sounds
		((= theMusic longSong)
			owner: self
			init:
		)
		((= theMusic2 longSong2)
			owner: self
			init:
		)
		
		(pointsSound
			owner: self
			init:
			setPri: 15
			setLoop: 1
		)
		
		;set up doVerb and feature initializer code
		(= doVerbCode gameDoVerbCode)
		(= ftrInitializer gameFtrInit)
		
		;set up the event handlers
		((= keyDownHandler keyH) add:)
		((= mouseDownHandler mouseH) add:)
		((= directionHandler dirH) add:)
		(= pMouse PseudoMouse)
		
		;set up the ego
		(= ego GameEgo)
		(= egoLooper (ScriptID GAME_EGO 1))
		(User alterEgo:  ego)
		
		;initialize icon bar, control panel, and inventory
		((ScriptID GAME_ICONBAR 0) doit:)
		((ScriptID GAME_CONTROLS 0) doit:)
		((ScriptID GAME_INV 0) init:)
		
		;initialize everything else
		((ScriptID GAME_INIT 0) doit:)
		
		(theDefaultFeature init:)
		
		;now go to the speed tester
		(self newRoom: SPEED_TEST)
	)

	(method (startRoom n)
		(cls)
		((ScriptID DISPOSE 0) doit: n)
		; Check for frags
		(if (and	(!= (- (MemoryInfo FreeHeap) 2)
					(MemoryInfo LargestPtr))
				(Print {Memory fragmented.})
				(theGame showMem:)
			)
		)
		(if debugging
			((ScriptID DEBUG 0) init:)
		)
		(super startRoom: n)
	)

	(method (pragmaFail &tmp theVerb theItem)
		;nobody responds to user input
		(if (User canInput?)
			(= theVerb ((User curEvent?) message?))
			(= theItem (inventory indexOf: (theIconBar curInvIcon?)))
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
					(switch theItem
						(iMoney
							(Print "Your money's no good there.")
						)
						(else
							(Print "It doesn't work.")
						)
					)
				)
			)
		)
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
								(ego showInv:)
							)
						)
						(SHIFTTAB
							(if (not (& ((theIconBar at: ICON_INVENTORY) signal?) DISABLED))
								(ego showInv:)
							)
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
						(`#5
							(if (not (& ((theIconBar at: ICON_CONTROL) signal?) DISABLED))
								(if fastCast (return))
								(theGame save:)
								(event claimed: TRUE)
							)
						)
						(`#7
							(if (not (& ((theIconBar at: ICON_CONTROL) signal?) DISABLED))
								(if fastCast (return))
								(theGame restore:)
								(event claimed: TRUE)
							)
						)
						(`#9
							(theGame restart:)
							(event claimed: TRUE)
						)
						(`^q
							(theGame quitGame:)
							(event claimed: TRUE)
						)
					)
				)
			)
		)
	)
	
	(method (setSpeed what)
		(if argc
			(ego
			  	cycleSpeed:	what,
			  	moveSpeed: 	what
			)
			(self egoMoveSpeed: what)
		)
		(return (ego moveSpeed?))
	)

	(method (solvePuzzle pFlag pValue)
		;Adds an amount to the player's current score.
		;It checks if a certain flag is set so that the points are awarded only once.
		(if (and (> argc 1) (Btst pFlag))
			(return)
		)
		(if pValue
			(theGame changeScore: pValue)
			(if (and (> argc 1) pFlag)
				(Bset pFlag)
				(pointsSound play:)
			)
		)
	)

	(method (showAbout)
		((ScriptID GAME_ABOUT 0) doit:)
		(DisposeScript GAME_ABOUT)
	)
	
	(method (restart)
		;if a parameter is given, skip the dialog and restart immediately
		(if argc
			(super restart:)
		else
			;the game's restart dialog
			(if (YesNoDialog "Are you sure you want to restart?")
				(super restart:)
			)
		)
	)

	(method (quitGame)
		(super quitGame:
			(YesNoDialog "Do you really want to quit?")
		)
	)
)

(instance gameDoVerbCode of Code
	;if there is no corresponding message for an object and verb, bring up a default message.
	(method (doit theVerb theObj &tmp objDesc invItem)
		(if (theObj description?)
			(= objDesc (theObj description?))
		else
			(= objDesc (theDefaultFeature description?))
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
				(verbTalk
					(Printf "You get no response from %s." objDesc)
				)
				(verbDo
					(Printf "You can't do much with %s." objDesc)
				)
				(verbUse
					(= invItem (inventory indexOf: (theIconBar curInvIcon?)))
					(switch invItem
						(iMoney
							(Print "There's no coin slot.")
						)
						(else
							(Printf "It seems that just doesn't work with %s."
								objDesc
							)
						)
					)
				)
 			)
		)
	)
)

(instance gameFtrInit of Code		; sets up defaults
	(method (doit theObj)
		; angle used by facingMe
		(if (== (theObj sightAngle?) ftrDefault)
			(theObj sightAngle: 90)
		)
		; maximum distance to get an object (for example.)
		; instance of Action or EventHandler with Actions
		(if (== (theObj actions?) ftrDefault)
			(theObj actions: 0)
		)
	)
)

(instance theDefaultFeature of Feature
	(properties
		description "that thing (whatever it is)"
	)
)

(procedure (HandsOff)
	;Disable ego control
	(if (not theCurIcon)	; don't want to save it twice!
		(= theCurIcon (theIconBar curIcon?))
	)
	
	(= isHandsOff TRUE)
	(User
		canControl: FALSE
		canInput: FALSE
	)
	(ego setMotion: 0)
	
	; save the state of each icon so we can put the icon bar back the way it was
	(= iconSettings 0)
	(theIconBar eachElementDo: #perform checkIcon)

	; disable some icons so user doesn't screw us up
	(theIconBar disable:
		ICON_WALK
		ICON_LOOK
		ICON_DO
		ICON_TALK
		ICON_ITEM
		ICON_INVENTORY
	)
	
	; if no mouse, move the cursor out of the way, but save the initial
	; posn so HandsOn can restore it	
	(if (not (HaveMouse))
		(= saveCursorX ((User curEvent?) x?))
		(= saveCursorY ((User curEvent?) y?))
		(theGame setCursor: waitCursor TRUE 310 185)
	else
		(theGame setCursor: waitCursor TRUE)
	)
)

(procedure (HandsOn)
	;Enable ego control
	(= isHandsOff FALSE)
	(User
		canControl: TRUE
		canInput: TRUE
	)
	
	; re-enable iconbar
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

	(if theCurIcon
		(theIconBar curIcon: theCurIcon)
		(theGame setCursor: ((theIconBar curIcon?) cursor?))
		(= theCurIcon 0)
		(if (and	(== (theIconBar curIcon?) (theIconBar at: ICON_ITEM))
					(not (theIconBar curInvIcon?))
				)
			(theIconBar advanceCurIcon:)
		)
	)	
	
	; restore cursor xy posn if no mouse
	(if (not (HaveMouse))
		(theGame setCursor:
			((theIconBar curIcon?) cursor?) TRUE saveCursorX saveCursorY
		)
	else
		(theGame setCursor:
			((theIconBar curIcon?) cursor?) TRUE
		)
	)
)

(instance checkIcon of Code
	(method (doit theIcon)
		(if (theIcon isKindOf: IconItem)		; It's an icon
			(if (& (theIcon signal?) DISABLED)
				(|= iconSettings (>> $8000 (theIconBar indexOf: theIcon)))
			)
		)
	)
)
