;;; Sierra Script 1.0 - (do not remove this comment)
(script# rTitle)
(include game.sh)
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
		(theMusic number: sOpening play:)
		(theGame setCursor: waitCursor TRUE)
		(Display
			"Intro/Opening screen"
			p_at 90 80
			p_color 15
			p_back -1
		)
	)
	(method (handleEvent event)
		(if
			(and
				(event type?)
				(!= (event message?) `#2)
				(== curRoomNum newRoomNum)
			)
			(event claimed: TRUE)
			(Sound pause: TRUE)
			(theGame setCursor: normalCursor TRUE 160 100)
			(switch
				(PrintD
					{Would you like to skip\nthe introduction or\nwatch the whole thing?} 67 100 60
					#new #button {Skip it} skipIt
					#new #button {Watch it} watchIt
					#new #button {Restore a Game} restoreGame
				)
				(skipIt
					(curRoom newRoom: rTestRoom)
					(theMusic dispose:)
				)
				(watchIt
					(Sound pause: FALSE)
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