;;; Sierra Script 1.0 - (do not remove this comment)
(script# 811)
(include sci.sh)
(use Main)
(use Intrface)
(use System)

(public
	aboutCode 0
)

(instance aboutCode of Code
	(properties)
	
	(method (doit)
		(Printf {SCI10 Template Game\nBy Eric Oakford} version)
	)
)
