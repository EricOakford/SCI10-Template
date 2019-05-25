;;; Sierra Script 1.0 - (do not remove this comment)
(script# rTitle)
(include system.sh) (include sci2.sh) (include game.sh)
(use Main)
(use Game)
(use Sound)
(use PrintD)
(use Intrface)
(use System)

(public
	title 0
)

(enum 1
	skipIt
	watchIt
	restoreGame
)

(instance title of Room
	(properties
		picture pSpeedTest
		style FADEOUT
	)
	
	(method (init)
		(super init: &rest)
		(music number: sOpening play:)
		(keyDownHandler addToFront: self)
		(mouseDownHandler addToFront: self)
		(theGame setCursor: waitCursor TRUE)
		(Display
			"Intro/Opening screen"
			dsCOORD 90 80
			dsCOLOR clWHITE
			dsBACKGROUND clTRANSPARENT
		)
	)
	(method (handleEvent event)
		(if
			(and
				(event type?)
				(!= (event message?) KEY_F2)
				(== curRoomNum newRoomNum)
			)
			(event claimed: TRUE)
			(Sound pause: TRUE)
			(theIconBar enable:)
			(theGame setCursor: normalCursor TRUE 160 100)
			(switch
				(PrintD
					{Would you like to skip\nthe introduction or\nwatch the whole thing?} 67 100 60
					106
					#button {Skip it} skipIt
					106
					#button {Watch it} watchIt
					106
					#button {Restore a Game} restoreGame
				)
				(skipIt
					(theIconBar enable:)
					(self newRoom: rTestRoom)
					(music dispose:)
				)
				(watchIt (Sound pause: FALSE)
				)
				(restoreGame
					(Sound pause: FALSE)
					(theGame restore:)
				)
			)
			(Sound pause: FALSE)
			(theGame setCursor: waitCursor FALSE)
		)
	)
)