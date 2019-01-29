;;; Sierra Script 1.0 - (do not remove this comment)
(script# 979)
(include sci.sh)
(use Main)
(use Intrface)
(use Motion)
(use System)


(instance COn of Code
	(properties)
	
	(method (doit param1)
		(MousedOn param1 &rest)
	)
)

(instance MTM of Motion
	(properties)
	
	(method (doit)
		(super doit: &rest)
		(if (client isStopped:) (self moveDone:))
	)
)

(class MDH of EventHandler
	(properties
		elements 0
		size 0
		x 0
		y 0
		modifiers $0000
		targetObj 0
		shiftParser 0
	)
	
	(method (handleEvent pEvent &tmp userAlterEgo temp1 temp2)
		(= temp1 ((= userAlterEgo (user alterEgo?)) mover?))
		(= x (pEvent x?))
		(= y (pEvent y?))
		(= modifiers (pEvent modifiers?))
		(if (& modifiers $000c)
			(super handleEvent: pEvent)
		else
			(= temp2 (FirstNode elements))
			(while (and temp2 (= targetObj (NodeValue temp2)))
				(if
					(= targetObj
						(cond 
							((targetObj isKindOf: Collection) (targetObj firstTrue: #perform COn pEvent))
							((MousedOn targetObj pEvent) targetObj)
						)
					)
					(if (& modifiers $0003)
						(pEvent type: 128)
						(shiftParser doit: targetObj pEvent)
						(targetObj handleEvent: pEvent)
						(pEvent type: 1)
						(pEvent claimed?)
						(return)
						(break)
					)
					(if
						(and
							(user controls?)
							(IsObject userAlterEgo)
							(cast contains: userAlterEgo)
						)
						(userAlterEgo
							setMotion: MTM (targetObj x?) (targetObj y?) self
						)
						(user prevDir: 0)
						(pEvent claimed: 1)
						(break)
					)
					(super handleEvent: pEvent)
					(break)
				)
				(= temp2 (NextNode temp2))
			)
			(if (== targetObj 0) (super handleEvent: pEvent))
		)
	)
	
	(method (cue &tmp newEvent)
		((= newEvent (Event new:))
			type: 1
			x: x
			y: y
			modifiers: modifiers
		)
		(targetObj handleEvent: newEvent)
		(= targetObj 0)
		(newEvent dispose:)
	)
)
