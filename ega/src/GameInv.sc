;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEINV.SC
;
;	Here, you can add inventory item instances.
;
;
(script# GAME_INV)
(include game.sh)
(use Main)
(use Procs)
(use Intrface)
(use IconBar)
(use BordWind)
(use Invent)
(use System)

(public
	invCode 0
)

(define INVENTORY_CURSOR_START 200)

(instance invCode of Code
	(method (init)
		((= inventory Inventory)
			init:
			add:
				;Add your inventory items here.
				;Make sure they are in the same order as the item list in GAME.SH.
				Money
			;add the interface buttons afterwards
			add: invLook invHand invSelect invHelp ok
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (FindColor colGray1 colGray2)
			eachElementDo: #init
			state: NOCLICKHELP
			window:	invWin
			helpIconItem:	invHelp
			selectIcon:	invSelect
			okButton:	ok
		)
		;set up the inventory window
		(invWin
			color: 0
			back: (FindColor colGray2 colGray1)
			topBordColor: (FindColor colGray4 colWhite)
			lftBordColor: (FindColor colGray3 colWhite)
			rgtBordColor: (FindColor colGray1 colGray2)
			botBordColor: (FindColor colBlack colGray2)
			insideColor: (FindColor colGray1 colGray2)
			topBordColor2: colBlack
			lftBordColor2: colBlack
			botBordColor2: (FindColor colGray4 colWhite)
			rgtBordColor2: (FindColor colGray5 colWhite)
		)
	)
)

(instance invWin of InsetWindow)

(class GameIconItem of IconItem
	;this is to ensure that showInv: doesn't mistake the icons
	;for inventory items
	(method (ownedBy)
		(return FALSE)
	)
)

(instance Money of InvItem
	(properties
		view vInvItems
		cel iMoney
		cursor 200
		description {Money! Cash! Assets!}
	)
	
	(method (doVerb theVerb)
		(switch theVerb
			(verbDo
				(Print "There isn't much you can do to it what inflation hasn't already.")
			)
			(else
				(super doVerb: theVerb)
			)
		)
	)
)

(instance invLook of GameIconItem
	(properties
		view vInvIcons
		loop lInvLook
		cel 0
		cursor LOOK_CURSOR
		message verbLook
		helpStr {Select this Icon then select an inventory item you'd like a description of.}
	)
	;these need different colors than the inventory items
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (FindColor colGray4 colGray1)
		)
		(super init:)
	)
)

(instance invHand of GameIconItem
	(properties
		view vInvIcons
		loop lInvHand
		cel 0
		cursor DO_CURSOR
		message verbDo
		helpStr {This allows you to do something to an item.}
	)
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (FindColor colGray4 colGray1)
		)
		(super init:)
	)
)

(instance invHelp of GameIconItem
	(properties
		view vInvIcons
		loop lInvHelp
		cel 0
		cursor HELP_CURSOR
		message verbHelp
		helpStr {This icon tells you about the other icons.}
	)
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (FindColor colGray4 colGray1)
		)
		(super init:)
	)
)

(instance invSelect of GameIconItem
	(properties
		view vInvIcons
		loop lInvSelect
		cel 0
		cursor ARROW_CURSOR
		helpStr {This allows you to select an item.}
	)
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (FindColor colGray4 colGray1)
		)
		(super init:)
	)
)

(instance ok of GameIconItem
	(properties
		view vInvIcons
		loop lInvOK
		cel 0
		nsLeft 40
		cursor ARROW_CURSOR
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		helpStr {Select this Icon to close this window.}
	)
	(method (init)
		(self
			highlightColor: 0
			lowlightColor: (FindColor colGray4 colGray1)
		)
		(super init:)
	)
)