;;; Sierra Script 1.0 - (do not remove this comment)
;	WHERETO.SC
;
;	If debugging is enabled, the game brings up this prompt.
;	You can go immediately to any room in the game.
;
;

(script# WHERE_TO)
(include game.sh)
(use Main)
(use Intrface)
(use Game)
(use System)

(public
	whereTo 0
)

(instance whereTo of Room
	(properties
		picture vSpeedTest
	)
	
	(method (init &tmp [str 10] nextRoom)
		(super init:)
		(= str 0)
		(= nextRoom 0)
		(= nextRoom
			(Print "Where to, boss?"
				#edit @str 5 115 0
			)
		)
		(= nextRoom rTitle)
		(if str
			(= nextRoom (ReadNumber @str))
		)
		(self newRoom: nextRoom)
	)
)


