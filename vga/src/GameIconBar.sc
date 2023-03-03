;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEICONBAR.SC
;
;	The game's icon bar is initialized here. It could have been in the game's Main script, but
;	doing it this way makes everything better organized. In fact, this was done more often for SCI32 games.
;
;

(script# GAME_ICONBAR)
(include game.sh)
(use Main)
(use Intrface)
(use Procs)
(use IconBar)
(use System)

(public
	iconCode 0
)

(instance iconCode of Code
	(method (doit)
		((= theIconBar IconBar)
			add:
			;These correspond to ICON_*** in game.sh
			iconWalk iconLook iconDo iconTalk
			iconUseIt iconInventory iconControlPanel iconHelp iconScore
			eachElementDo: #init
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (FindColor colGray4 colGray1)
			curIcon: iconWalk
			useIconItem: iconUseIt
			helpIconItem: iconHelp
			state: (| OPENIFONME NOCLICKHELP)
		)
		(iconInventory message: (if (HaveMouse) TAB else SHIFTTAB))
	)
)

(instance iconWalk of IconItem
	(properties
		view vIcons
		loop lIconWalk
		cel 0
		cursor WALK_CURSOR
		message verbWalk
		signal (| HIDEBAR RELVERIFY)
		helpStr "Use this icon to move your character."
		maskView vIcons
		maskLoop lIconDisabled
	)
)

(instance iconLook of IconItem
	(properties
		view vIcons
		loop lIconLook
		cel 0
		cursor LOOK_CURSOR
		message verbLook
		signal (| HIDEBAR RELVERIFY)
		helpStr "Use this icon to look at things."
		maskView vIcons
		maskLoop lIconDisabled
	)
)

(instance iconDo of IconItem
	(properties
		view vIcons
		loop lIconHand
		cel 0
		cursor DO_CURSOR
		message verbDo
		signal (| HIDEBAR RELVERIFY)
		helpStr "Use this icon to do things."
		maskView vIcons
		maskLoop lIconDisabled
		maskCel 1
	)
)

(instance iconTalk of IconItem
	(properties
		view vIcons
		loop lIconTalk
		cel 0
		cursor TALK_CURSOR
		message verbTalk
		signal (| HIDEBAR RELVERIFY)
		helpStr "Use this icon to talk to other characters."
		maskView vIcons
		maskLoop lIconDisabled
		maskCel 2
	)
)

(instance iconUseIt of IconItem
	(properties
		view vIcons
		loop lIconInvItem
		cel 0
		cursor ARROW_CURSOR
		message verbUse
		signal (| HIDEBAR RELVERIFY)
		helpStr "Select this icon to use your current inventory object."
		maskView vIcons
		maskLoop lIconDisabled
		maskCel 4
	)
)

(instance iconInventory of IconItem
	(properties
		view vIcons
		loop lIconInventory
		cel 0
		cursor ARROW_CURSOR
		type NULL
		message NULL
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this icon to bring up your inventory window."
		maskView vIcons
		maskLoop lIconDisabled
		maskCel 3
	)
	
	(method (select)
		(if (super select: &rest)
			(ego showInv:)
		)
	)
)

(instance iconScore of IconItem
	(properties
		view vIcons
		loop lIconScore
		cel 0
		cursor ARROW_CURSOR
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "This icon displays your current score."
		maskView vIcons
		maskLoop lIconScore
		maskCel 1
	)
	
	(method (show &tmp [str 7] [rectPt 4] theFont)
		(super show: &rest)
		(= theFont 999)
		(Format @str "%d" score)
		(TextSize @rectPt @str theFont 0)
		(Display @str
			p_color colLRed
			p_font theFont
			p_at
				(+ nsLeft 5 (/ (- 25 [rectPt 3]) 2))
				(+ nsTop 14)
		)
	)
)

(instance iconControlPanel of IconItem
	(properties
		view vIcons
		loop lIconControls
		cel 0
		cursor ARROW_CURSOR
		message verbNone
		signal (| HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "This icon brings up the control panel."
		maskView vIcons
		maskLoop lIconDisabled
	)
	
	(method (select)
		(if (super select: &rest)
			(theIconBar hide:)
			(gameControls show:)
		)
	)
)

(instance iconHelp of IconItem
	(properties
		view vIcons
		loop lIconHelp
		cel 0
		cursor HELP_CURSOR
		message verbHelp
		signal (| RELVERIFY IMMEDIATE)
		helpStr "To learn about the other icons, first click here,
				then pass the question mark over the other icons."
		maskView vIcons
		maskLoop lIconDisabled
		maskCel 1
	)
)