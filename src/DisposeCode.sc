;;; Sierra Script 1.0 - (do not remove this comment)
(script# DISPOSE_CODE)
(include game.sh)
(use Main)
(use LoadMany)
(use System)

(public
	disposeCode 0
)

(instance disposeCode of Code
	(properties)
	
	(method (doit roomNum)
		;Cramming in as many system scripts as possible to ensure no "Memory Fragmented" errors.
		(LoadMany FALSE
			OSC NAMEFIND REVERSE SORT COUNT DPATH FORCOUNT SIGHT MOVEFWD RANDCYC STOPWALK MOVECYC
			POLYPATH POLYGON PRINTD
		)
;		(DisposeScript DEBUG)
		(DisposeScript DISPOSE_CODE)
	)
)
