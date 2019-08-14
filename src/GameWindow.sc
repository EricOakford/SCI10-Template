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
	(properties)
	
	(method (init)
		(self
			color: myTextColor
			back: (EGAOrVGA myVGABackColor myEGABackColor)
			topBordColor: myEGABordColor2
			lftBordColor: (EGAOrVGA myVGABordColor2 myEGABordColor2)
			rgtBordColor: (EGAOrVGA myVGABordColor myEGABordColor)
			botBordColor: (EGAOrVGA myEGABackColor myEGABordColor)
		)
	)
	
	(method (open)
		;Customize the window here
		(super open:	&rest)
	)
)
