;;; Sierra Script 1.0 - (do not remove this comment)
(script# 801)
(include game.sh)
(use Main)
(use LoadMany)
(use System)

(public
	disposeCode 0
)

(instance disposeCode of Code
	(properties)
	
	(method (doit param1)
		;Cramming in as many system scripts as possible to ensure no "Memory Fragmented" errors.
		(LoadMany
			FALSE OSC NAMEFIND REVERSE SORT COUNT DPATH FORCOUNT PCHASE
			SIGHT MOVEFWD RANDCYC STOPWALK MOVECYC POLYPATH POLYGON PRINTD
			TIMER PAVOID EXTRA JUMP CHASE FOLLOW WANDER TALKER 812
		)
		(DisposeScript 800)
		(DisposeScript 801)
	)
)
