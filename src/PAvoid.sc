;;; Sierra Script 1.0 - (do not remove this comment)
(script# 927)
(include sci.sh)
(use PolyPath)
(use Polygon)
(use Sight)
(use System)


(class PAvoider of Code
	(properties
		blocker 0
		client 0
	)
	
	(method (init theClient)
		(if (>= argc 1) (= client theClient))
	)
	
	(method (doit &tmp temp0 temp1 theBlocker temp3 temp4 clientMover)
		(if (and blocker (blocker respondsTo: #mover))
			(if
				(and
					(blocker mover?)
					(>
						(AngleDiff
							(GetAngle (blocker nsLeft?))
							(client heading?)
						)
						45
					)
					(>
						(AngleDiff
							(GetAngle (blocker nsTop?))
							(client heading?)
						)
						45
					)
					(>
						(AngleDiff
							(GetAngle (blocker nsRight?))
							(client heading?)
						)
						45
					)
					(>
						(AngleDiff
							(GetAngle (blocker nsBottom?))
							(client heading?)
						)
						45
					)
				)
				(blocker signal: (| (blocker signal?) $0100))
			else
				(blocker signal: (& (blocker signal?) $feff))
				(= blocker 0)
			)
		)
		(if
			(and
				(= clientMover (client mover?))
				(IsObject (= theBlocker (clientMover doit:)))
				(not (clientMover completed?))
				(clientMover isKindOf: PolyPath)
			)
			(= blocker theBlocker)
			(= temp0
				(+ (client xStep?) (/ (CelWide (client view?) 2 0) 2))
			)
			(= temp1 (* (client yStep?) 2))
			(if (IsObject (clientMover obstacles?))
				((clientMover obstacles?)
					add:
						(= temp3
							((Polygon new:)
								init:
									(- (theBlocker brLeft?) temp0)
									(- (CoordPri 1 (CoordPri (theBlocker y?))) temp1)
									(+ (theBlocker brRight?) temp0)
									(- (CoordPri 1 (CoordPri (theBlocker y?))) temp1)
									(+ (theBlocker brRight?) temp0)
									(+ (theBlocker y?) temp1)
									(- (theBlocker brLeft?) temp0)
									(+ (theBlocker y?) temp1)
								name: {isBlockedPoly?}
								yourself:
							)
						)
				)
			)
			(clientMover
				value: 2
				init: client (clientMover finalX?) (clientMover finalY?)
			)
			((clientMover obstacles?) delete: temp3)
			(temp3 dispose:)
		)
	)
)
