;;; Sierra Script 1.0 - (do not remove this comment)

;	GAMEICONBAR.SC
;
;	The game's icon bar is initialized here. It could have been in the game's Main script, but
;	doing it this way makes everything better organized. In fact, this was done more often for SCI32 games.
;
;

(script# GAME_ICONBAR)
(include game.sh)
(use Main)
(use IconBar)
(use Invent)
(use System)

(public
	iconCode 0
)

(instance iconCode of Code
	(properties)
	
	(method (init)
		((= theIconBar IconBar)
			add: icon0 icon1 icon2 icon3 icon6 icon7 icon4 icon5 icon8 icon9
			eachElementDo: #init
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (EGAOrVGA myVGABackColor myEGABackColor)
			curIcon: icon0
			useIconItem: icon4
			helpIconItem: icon9
			disable:
		)
	)
)

(instance icon0 of IconItem
	(properties
		view 900
		loop 0
		cel 0
		cursor 6
		message verbWalk
		signal $0041
		helpStr {This icon is for walking.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
)

(instance icon1 of IconItem
	(properties
		view 900
		loop 1
		cel 0
		cursor 19
		message verbLook
		signal $0041
		helpStr {This icon is for looking.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
)

(instance icon2 of IconItem
	(properties
		view 900
		loop 2
		cel 0
		cursor 20
		message verbDo
		signal $0041
		helpStr {This icon is for doing.}
		maskView 900
		maskLoop 14
	)
)

(instance icon3 of IconItem
	(properties
		view 900
		loop 3
		cel 0
		cursor 7
		message verbTalk
		signal $0041
		helpStr {This icon is for talking.}
		maskView 900
		maskLoop 14
		maskCel 3
	)
)

(instance icon4 of IconItem
	(properties
		view 900
		loop 4
		cel 0
		cursor 999
		message verbUse
		signal $0041
		helpStr {This window displays the current inventory item.}
		maskView 900
		maskLoop 14
		maskCel 4
	)
)

(instance icon5 of IconItem
	(properties
		view 900
		loop 5
		cel 0
		cursor 999
		type $0000
		message 0
		signal $0043
		helpStr {This icon brings up the inventory window.}
		maskView 900
		maskLoop 14
		maskCel 2
	)
	
	(method (select)
		(if (super select:) (Inventory showSelf: ego))
	)
)

(instance icon6 of IconItem
	(properties
		view 900
		loop 10
		cel 0
		cursor 30
		message verbSmell
		signal $0041
		helpStr {This icon is for smelling.}
		maskView 900
		maskLoop 14
	)
)

(instance icon7 of IconItem
	(properties
		view 900
		loop 11
		cel 0
		cursor 31
		message verbTaste
		signal $0041
		helpStr {This icon is for tasting.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
)

(instance icon8 of IconItem
	(properties
		view 900
		loop 7
		cel 0
		cursor 999
		message 8
		signal $0043
		helpStr {This icon brings up the control panel.}
		maskView 900
		maskLoop 14
		maskCel 1
	)
	
	(method (select)
		(if (super select:)
			(theIconBar hide:)
			(gameControls show:)
		)
	)
)

(instance icon9 of IconItem
	(properties
		view 900
		loop 9
		cel 0
		cursor 29
		message verbHelp
		signal $0003
		helpStr {This icon tells you about other icons.}
		maskView 900
		maskLoop 14
	)
)
