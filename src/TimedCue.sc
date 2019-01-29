;;; Sierra Script 1.0 - (do not remove this comment)
(script# 960)
(include sci.sh)
(use System)


(class TimedCue of Script
	(properties
		client 0
		state $ffff
		start 0
		timer 0
		cycles 0
		seconds 0
		lastSeconds 0
		register 0
		script 0
		caller 0
		next 0
	)
	
	(method (init param1 theSeconds theCycles)
		(super init: param1 param1)
		(if (>= argc 2)
			(= seconds theSeconds)
			(if (>= argc 3) (= cycles theCycles))
		)
	)
	
	(method (cue)
		(self dispose:)
	)
)
