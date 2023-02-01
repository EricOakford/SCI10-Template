;;; Sierra Script 1.0 - (do not remove this comment)
;
;	DEATH.SC
;
;	This script handles death scenes, and is triggered by calling
;	the EgoDead procedure with a parameter corresponding to a death message..
;
;

(script# DEATH)
(include game.sh)
(use Main)
(use LoadMany)
(use Intrface)
(use DCIcon)
(use Sound)
(use Window)
(use Motion)
(use Procs)
(use Game)
(use System)

(public
	deathRoom 0
)

(enum
	waitABit
	setItUp
	showMessage
)

(local
	[deathTitle 20]
	[deathMsg	80]
)

(instance deathRoom of Room
	(properties
		picture vSpeedTest
		style FADEOUT
	)
	
	(method (init)
		(theMusic fade:)
		(theMusic2 fade:)
		(theIconBar disable:)
		(theGame setCursor: normalCursor TRUE)
		(super init:)
		(curRoom setScript: deathScript)
	)
)

(instance deathScript of Script
	(method (changeState newState)
		(switch (= state newState)
			(waitABit
				(switch deathReason
					(deathGENERIC
						(deathIcon view: vDeathSkull)
						(Format @deathTitle "You're dead.")
						(Format @deathMsg "It's all over for now. Please try again.")
					)
					(else	;if teleporting directly here
						(deathIcon view: vDeathSkull)
						(Format @deathTitle "You're dead.")
						(Format @deathMsg "It's all over for now. Please try again.")
					)
				)
				(ego hide:)
				(= cycles 2)
			)
			(setItUp
				;set the message and title based on the reason for death
				(deathMusic number: sDeath play:)
				(deathWindow back: (FindColor colGray4 colGray1))
				(= cycles 2)
			)
			(showMessage
				(repeat
					(switch
						(Print @deathMsg
							#title @deathTitle
							#mode teJustCenter
							#button {Restore} 1
							#button {Restart} 2
							#button {Quit} 3
							#icon deathIcon
							#window deathWindow
						)
						(1
							(theGame restore:)
						)
						(2
							(theGame restart: 1)
						)
						(3
							(= quit TRUE)
							(break)
						)
					)
				)
			)
		)
	)
)

(instance deathMusic of Sound
	(properties
		flags mNOPAUSE
	)
)

(instance deathWindow of Window)

(instance deathIcon of DCIcon
	(properties
		view vDeathSkull
	)
)