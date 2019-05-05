;;; Sierra Script 1.0 - (do not remove this comment)
(script# 801)
(include system.sh) (include sci2.sh)
(use Main)
(use LoadMany)
(use System)

(public
	disposeCode 0
)

(instance disposeCode of Code
	(properties)
	
	(method (doit param1)
		(LoadMany
			FALSE OSC NAMEFIND REVERSE SORT COUNT DPATH FORCOUNT
			SIGHT MOVEFWD RANDCYC STOPWALK MOVECYC POLYPATH POLYGON PRINTD
			812 808 708 810 807
		)
		(DisposeScript 800)
		(DisposeScript 801)
	)
)
