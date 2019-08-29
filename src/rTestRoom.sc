;;; Sierra Script 1.0 - (do not remove this comment)
(script# rTestroom)
(include system.sh) (include sci2.sh) (include game.sh)
(use Main)
(use Game)
(use Sound)
(use Intrface)
(use System)

(public
	testRoom 0
)

(instance testRoom of Room
	(properties
		lookStr {This is an empty room.}
		picture pTestRoom
		style PIXELDISSOLVE
	)
	
	(method (init)
		(HandsOn)
		(ego posn: 146 153 loop: 0 init:)
		(NormalEgo)
		(super init:)
	)
	
	(method (doit)
		(super doit:)
	)
)
