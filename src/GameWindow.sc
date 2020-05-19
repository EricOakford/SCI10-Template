;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEWINDOW.SC
;
;	This is a custom window that Print points to.
;	Customize it any way you like.
;
;	If you decide not to use it, it consumes no heap.
;

(script#	GAME_WINDOW)
(include game.sh)
(use Main)
(use BordWind)
(use Window)

(public
	GameWindow 0
)

(class GameWindow kindof BorderWindow
	
	(method (init)
		(self
			color: myTextColor
			back: (EGAOrVGA myBackColor myBotBordColor)
			topBordColor: myTopBordColor
			lftBordColor: (EGAOrVGA myLftBordColor myTopBordColor)
			rgtBordColor: (EGAOrVGA myRgtBordColor myInsideColor)
			botBordColor: (EGAOrVGA myBotBordColor myInsideColor)
		)
	)
	
	(method (open)
		;Customize the window here
		(super open:	&rest)
	)
)
