;;; Sierra Script 1.0 - (do not remove this comment)
(script# rTestroom)
(include game.sh)
(use Main)
(use Game)
(use Sound)
(use Intrface)
(use Talker)
(use Actor)
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
		(super init:)
	)
	
	(method (doit)
		(super doit:)
	)
	
	(method (doVerb theVerb)
		(switch theVerb
			(verbTalk
				(egoTalk
					init: egoBust egoMouth egoEyes
					"Hello anyone? I'm testing the SCI10 talker system. Everything seems to be in order!"
					0 0 1
				)
			)
			(else
				(super doVerb: theVerb &rest)
			)
		)
	)
)


(instance egoTalk of Talker
	(properties
		x -1
		y 87
		nsTop 10
		nsLeft 3
		view vEgoTalk
	)
)

(instance egoBust of View
	(properties
		view vEgoTalk
	)
)

(instance egoEyes of Prop
	(properties
		view vEgoTalk
		loop 2
		nsLeft 44
		nsTop 25
		cycleSpeed 18
	)
)

(instance egoMouth of Prop
	(properties
		view vEgoTalk
		loop 1
		nsLeft 42
		nsTop 35
		cycleSpeed 6
	)
)