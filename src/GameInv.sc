;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEINV.SC
;
;	Here, you can add inventory item instances, and can create custom properties.
;	
;	 An example might be::
;	
;	 	(instance Hammer of InvItem
;	 		(properties
;	 			view 900
;	 			loop 1
;	 			cursor 900			; Required cursor resource when using this item.
;	 			signal IMMEDIATE
;	 			description			; the item's displayed name
;	 		)
;	 	)
;	
;	 Then in the invCode init, add the inventory item to the add: call.
;
;

(script# GAME_INV)
(include game.sh)
(use Main)
(use BordWind)
(use Intrface)
(use IconBar)
(use Invent)
(use System)

(public
	invCode 0
	invWin 1
)

(instance invCode of Code
	
	(method (init)
		(inventory
			init:
			add:
				Coin
				Bomb
				invLook
				invHand
				invSelect
				invHelp
				ok
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (EGAOrVGA myInsideColor myBotBordColor)
			eachElementDo: #init
			window: invWin
			helpIconItem: invHelp
			selectIcon: invSelect
			okButton: ok
		)
		(Coin owner: ego)
	)
)

(instance invWin of InsetWindow
	
	(method (init)
		(invWin
			color: myTextColor
			back: (EGAOrVGA myBotBordColor myInsideColor)
			topBordColor: (EGAOrVGA myBackColor myTopBordColor)
			lftBordColor: (EGAOrVGA myRgtBordColor myTopBordColor)
			rgtBordColor: (EGAOrVGA myInsideColor myBotBordColor)
			botBordColor: (EGAOrVGA myTextColor myBotBordColor)
			insideColor: (EGAOrVGA myInsideColor myBotBordColor)
			topBordColor2: myTextColor
			lftBordColor2: myTextColor
			botBordColor2: (EGAOrVGA myBackColor myTopBordColor)
			rgtBordColor2: (EGAOrVGA myLftBordColor myTopBordColor)
			botBordHgt: 25
		)
	)
)

(instance ok of IconItem
	(properties
		view 901
		loop 3
		cel 0
		nsLeft 40
		cursor ARROW_CURSOR
		signal (| forceUpdOn updOn)
		helpStr {Select this Icon to close this window.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myBackColor myBotBordColor)
		)
		(super init:)
	)
)

(instance invLook of IconItem
	(properties
		view 901
		loop 2
		cel 0
		cursor 19
		message verbLook
		helpStr {Select this Icon then select an inventory item you'd like a description of.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myBackColor myBotBordColor)
		)
		(super init:)
	)
)

(instance invHand of IconItem
	(properties
		view 901
		loop 0
		cel 0
		cursor 20
		message verbDo
		helpStr {This allows you to do something to an item.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myBackColor myBotBordColor)
		)
		(super init:)
	)
)

(instance invHelp of IconItem
	(properties
		view 901
		loop 1
		cel 0
		cursor 29
		message verbHelp
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myBackColor myBotBordColor)
		)
		(super init:)
	)
)

(instance invSelect of IconItem
	(properties
		view 901
		loop 4
		cel 0
		cursor ARROW_CURSOR
		helpStr {This allows you to select an item.}
	)
	
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (EGAOrVGA myBackColor myBotBordColor)
		)
		(super init:)
	)
)

(instance Coin of InvItem
	(properties
		view 700
		cel 0
		cursor 1
		signal IMMEDIATE
		description {the buckazoid coin}
		owner 40
	)
	(method (doVerb theVerb param2)
		(switch theVerb
			(verbLook
				(Print "It's a buckazoid coin.")
			)
		)
	)
)

(instance Bomb of InvItem
	(properties
		view 700
		cel 1
		cursor 22
		signal IMMEDIATE
		description {the unstable ordinance}
		owner 40
	)
	(method (doVerb theVerb param2)
		(switch theVerb
			(verbLook
				(Print "It's a piece of unstable ordinance.")
			)
		)
	)
)
