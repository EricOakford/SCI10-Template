;;; Sierra Script 1.0 - (do not remove this comment)
;
;	TITLE.SC
;
;	Show the title screen, then the startup menu.
;
;

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

(enum
	buttonStart
	buttonRestore
	buttonQuit
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
		(self setScript:	sTitle)
	)
)

(instance sTitle of Script
	(method (changeState ns &tmp str nextRoom)
		(switchto (= state ns)
			(
				(= cycles 2)
			)
			(			
				(= seconds 3)
				(SetCursor normalCursor TRUE)
				(repeat
					(switch
						(Print ""
							#at 20 165
							#font SYSFONT
							#button {Start Game} buttonStart
							#button {Restore Game} buttonRestore
							#button {Quit Game} buttonQuit
						)
						(buttonStart
							(curRoom newRoom: rTestRoom)
							(break)
						)
						(buttonRestore
							(theGame restore:)
						)
						(buttonQuit
							(= quit TRUE)
							(break)
						)
					)
				)
			)
		)
	)
)