;;; Sierra Script 1.0 - (do not remove this comment)
(script# NAMEFIND)
(include system.sh) (include sci2.sh)
(use System)

(public
	NameFind 0
)

(procedure (NameFind theNearName theList)
   (theList firstTrue: #perform NameComp theNearName)
)

(instance NameComp of Code
   (properties name "NC")
   (method (doit theObj theName)
      (== 0 (StrCmp (theObj name?) theName))
   )
)
