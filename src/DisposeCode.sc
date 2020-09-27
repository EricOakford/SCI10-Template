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
	
	(method (doit roomNum)
		(LoadMany FALSE
			OSC NAMEFIND REVERSE SORT COUNT DPATH FORCOUNT TALKER
			SIGHT MOVEFWD RANDCYC MOVECYC POLYPATH POLYGON PRINTD
		)
		(DisposeScript DISPOSE_CODE)
	)
)
