;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMECONTROLS.SC
;
;	This script contains the game's control panel and its controls.
;
;

(script# GAME_CONTROLS)
(include game.sh)
(use Main)
(use BordWind)
(use GameWindow)
(use GControl)
(use IconBar)
(use Window)
(use System)

(public
	gcCode 0
	gcWin 1
)

(instance gcCode of Code
	(properties)
	
	(method (init)
		(= gameControls GameControls)
		(gameControls
			window: gcWin
			add:
				iconOk
				(detailSlider theObj: theGame selector: #detailLevel yourself:)
				(volumeSlider theObj: theGame selector: #masterVolume yourself:)
				(speedSlider theObj: theGame selector: #setSpeed yourself:)
				(iconSave theObj: theGame selector: #save yourself:)
				(iconRestore theObj: theGame selector: #restore yourself:)
				(iconRestart theObj: theGame selector: #restart yourself:)
				(iconQuit theObj: theGame selector: #quitGame yourself:)
				(iconAbout theObj: theGame selector: #showAbout yourself:)
				iconHelp
			okButton: iconOk
			helpIconItem: iconHelp
			curIcon: iconRestore
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (EGAOrVGA myVGABordColor myEGABordColor)
		)
	)
)

(instance gcWin of BorderWindow
	(properties)
	
	(method (init)
		(gcWin
			color: myTextColor
			back: (EGAOrVGA myVGABackColor myEGABackColor)
			topBordColor: myEGABordColor2
			lftBordColor: (EGAOrVGA myVGABordColor2 myEGABordColor2)
			rgtBordColor: (EGAOrVGA myVGABordColor myEGABordColor)
			botBordColor: (EGAOrVGA myEGABackColor myEGABordColor)
		)
	)
	
	(method (open &tmp temp0 temp1 temp2 temp3 temp4 temp5
			temp6 temp7 temp8 temp9 temp10 temp11 temp12
			[temp13 15] [temp28 4]
			)
			
		(self
			top: (/ (- 200 (+ (CelHigh 947 1 1) 6)) 2)
			left: (/ (- 320 (+ 151 (CelWide 947 0 1))) 2)
			bottom:
				(+
					(CelHigh 947 1 1)
					6
					(/ (- 200 (+ (CelHigh 947 1 1) 6)) 2)
				)
			right:
				(+
					151
					(CelWide 947 0 1)
					(/ (- 320 (+ 151 (CelWide 947 0 1))) 2)
				)
			priority: 15
		)
		(super open:)
		(DrawCel 947 0 5
			(+
				(/
					(-
						(- (+ 151 (CelWide 947 0 1)) (+ 4 (CelWide 947 1 1)))
						(CelWide 947 0 5)
					)
					2
				)
				4
				(CelWide 947 1 1)
			)
			3 15
		)
		(DrawCel 947 1 1 4 3 15)
		(DrawCel 947 1 0 94 38 15)
		(DrawCel 947 1 0 135 38 15)
		(DrawCel 947 0 4 63 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(DrawCel 947 0 3 101 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(DrawCel 947 0 2 146 (- 37 (+ (CelHigh 947 0 4) 3)) 15)
		(Graph GShowBits 12 1 15 (+ 151 (CelWide 947 0 1)) 1)
		(= temp4 (+ (= temp1 (+ 46 (CelHigh 947 0 1))) 13))
		(= temp3
			(+
				(= temp2 (+ 10 (CelWide 947 1 1)))
				(-
					(+ 151 (CelWide 947 0 1))
					(+ 10 (CelWide 947 1 1) 6)
				)
			)
		)
		(= temp11 15)
		(= temp5 0)
		(= temp7 (EGAOrVGA myEGABackColor myEGABackColor))
		(= temp10 (EGAOrVGA myVGABordColor myEGABackColor))
		(= temp9 (EGAOrVGA myVGABordColor2 myEGABordColor2))
		(= temp8 myEGABordColor2)
		(= temp0 3)
		(= temp6 3)
		(Graph
			GFillRect
			temp1
			temp2
			(+ temp4 1)
			(+ temp3 1)
			temp6
			temp5
			temp11
		)
		(= temp1 (- temp1 temp0))
		(= temp2 (- temp2 temp0))
		(= temp3 (+ temp3 temp0))
		(= temp4 (+ temp4 temp0))
		(Graph
			GFillRect
			temp1
			temp2
			(+ temp1 temp0)
			temp3
			temp6
			temp7
			temp11
		)
		(Graph
			GFillRect
			(- temp4 temp0)
			temp2
			temp4
			temp3
			temp6
			temp8
			temp11
		)
		(= temp12 0)
		(while (< temp12 temp0)
			(Graph
				GDrawLine
				(+ temp1 temp12)
				(+ temp2 temp12)
				(- temp4 (+ temp12 1))
				(+ temp2 temp12)
				temp10
				temp11
				-1
			)
			(Graph
				GDrawLine
				(+ temp1 temp12)
				(- temp3 (+ temp12 1))
				(- temp4 (+ temp12 1))
				(- temp3 (+ temp12 1))
				temp9
				temp11
				-1
			)
			(++ temp12)
		)
		(Graph
			GShowBits
			temp1
			temp2
			(+ temp4 1)
			(+ temp3 1)
			1
		)
		(Format @temp13 "Score: %d of %d" score possibleScore)
		(TextSize @temp28 @temp13 999 0)
		(Display @temp13
			p_font 999
			p_color (EGAOrVGA myVGABackColor myEGABordColor2)
			p_at
			(+ 10
				(CelWide 947 1 1)
				(/
					(-
						(-
							(+ 151 (CelWide 947 0 1))
							(+ 10 (CelWide 947 1 1) 6)
						)
						[temp28 3]
					)
					2
				)
			)
			(+ 46 (CelHigh 947 0 1) 3)
		)
	)
)

(instance detailSlider of Slider
	(properties
		view 947
		loop 0
		cel 1
		nsLeft 67
		nsTop 37
		signal $0280
		helpStr {The graphics detail level.}
		sliderView 947
		yStep 2
		topValue 3
	)
)

(instance volumeSlider of Slider
	(properties
		view 947
		loop 0
		cel 1
		nsLeft 107
		nsTop 37
		signal $0080
		helpStr {Overall sound volume.}
		sliderView 947
		yStep 2
		topValue 15
	)
)

(instance speedSlider of Slider
	(properties
		view 947
		loop 0
		cel 1
		nsLeft 147
		nsTop 37
		signal $0280
		helpStr {The speed at which the player character moves.}
		sliderView 947
		yStep 2
		bottomValue 15
	)
)

(instance iconSave of ControlIcon
	(properties
		view 947
		loop 2
		cel 0
		nsLeft 8
		nsTop 6
		message 7
		signal $01c3
		helpStr {Allows you to save your game.}
	)
)

(instance iconRestore of ControlIcon
	(properties
		view 947
		loop 3
		cel 0
		nsLeft 8
		nsTop 26
		message 7
		signal $01c3
		helpStr {Allows you to restore a previously saved game.}
	)
)

(instance iconRestart of ControlIcon
	(properties
		view 947
		loop 4
		cel 0
		nsLeft 8
		nsTop 46
		message 7
		signal $01c3
		helpStr {Allows you to restart the game.}
	)
)

(instance iconQuit of ControlIcon
	(properties
		view 947
		loop 5
		cel 0
		nsLeft 8
		nsTop 66
		message 7
		signal $01c3
		helpStr {Allows you to quit the game.}
	)
)

(instance iconAbout of ControlIcon
	(properties
		view 947
		loop 6
		cel 0
		nsLeft 34
		nsTop 106
		message 7
		signal $01c3
		helpStr {Information about the game.}
	)
)

(instance iconHelp of IconItem
	(properties
		view 947
		loop 7
		cel 0
		nsLeft 8
		nsTop 106
		cursor 29
		message 6
		signal $0183
	)
)

(instance iconOk of IconItem
	(properties
		view 947
		loop 8
		cel 0
		nsLeft 8
		nsTop 86
		message 7
		signal $01c3
		helpStr {Returns you to the game.}
	)
)
