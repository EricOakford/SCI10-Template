;;; Sierra Script 1.0 - (do not remove this comment)
(script# rTestRoom)
(include game.sh)
(use Main)
(use Procs)
(use Intrface)
(use Polygon)
(use Game)
(use Sound)
(use Actor)
(use System)

(public
	testRoom 0
)

(instance testRoom of Room
	(properties
		lookStr {You are in an empty room.}
		picture scriptNumber
		style FADEOUT
		horizon 50
		vanishingX 130
		vanishingY 50
	)
	
	(method (init)
		(super init:)
		(switch prevRoomNum
			; Add room numbers here to set up the ego when coming from different directions.
			(else 
				(ego posn: 150 130)
			)
		)
		;set up polygons
		;room poly
		(curRoom addObstacle:
			((Polygon new:)
				type: PContainedAccess
				init:
					319 189
					319 50
					0 50
					0 189
				yourself:
			)
		)
		(ego init: normalize:)
		; We just came from the title screen, so we need to call this to give control
		; to the player.
		(HandsOn)
	)
	
	(method (doVerb theVerb)
		(switch theVerb
			(else
				(super doVerb: theVerb)
			)
		)
	)
)