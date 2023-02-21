;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEEGO.SC
;
;	 GameEgo is a game-specific subclass of Ego. Here, you can put default answers for
;	 looking, talking and performing actions on yourself.
;
;
(script# GAME_EGO)
(include game.sh)
(use Main)
(use Intrface)
(use StopWalk)
(use Grooper)
(use User)
(use Invent)
(use Procs)

(public
	GameEgo 0
	stopGroop 1
)

(class GameEgo of Ego
	(properties
		name {ego}
		description {you}
		sightAngle 180
		lookStr {It's you!}
		view vEgo
	)
	
	(method (doVerb theVerb &tmp invItem)
		;add interactivity with the player character here
		(switch theVerb
			(verbDo
				(Print "You tug at your clothing to achieve a slightly more comfortable fit.")
			)
			(verbTalk
				(Print "Talking to yourself isn't helpful.")
			)
			(verbUse
				(= invItem (inventory indexOf: (theIconBar curInvIcon?)))
				(switch invItem
					(iMoney
						(Print "You check to see if your money is still there. Yep, it is.")
					)
					(else
						(Print "That doesn't do anything for you.")
					)
				)
			)
			(else
				(super doVerb: theVerb)
			)
		)
	)
	
	(method (normalize theLoop theView stopView &tmp sView)
		;normalizes ego's animation
		(= stopView 0)
		(if (> argc 0)
			(ego loop: theLoop)
			(if (> argc 1)
				(ego view: theView)
				(if (> argc 2)
					(= stopView sView)
				)
			)
		)
		(if (not stopView)
			(= stopView vEgoStand)
		)
		(ego
			setLoop: -1
			setLoop: egoLooper
			setPri: -1
			setMotion: FALSE
			setCycle: StopWalk stopView
			setStep: 4 2
			illegalBits: 0
			ignoreActors: FALSE
			ignoreHorizon: TRUE
			moveSpeed: (theGame egoMoveSpeed?)
			cycleSpeed: (theGame egoMoveSpeed?)
		)
	)
	
	(method (showInv)
		;bring up the inventory
		(theIconBar hide:)
		(if (inventory firstTrue: #ownedBy ego)
			(inventory showSelf: ego)
			(if (not (theIconBar curInvIcon?))
				(theIconBar
					curIcon: (theIconBar at: ICON_WALK),
					disable: ICON_ITEM
				)
				(if (& ((theIconBar curIcon?) signal?) DISABLED)
					(theIconBar advanceCurIcon?)
				)
				(theGame setCursor: ((theIconBar curIcon?) cursor?))
			)
		else
			;if ego has no items, bring up the message saying so.
			(Print "You're not carrying anything at this time.")
		)
	)
)

(instance stopGroop of GradualLooper)