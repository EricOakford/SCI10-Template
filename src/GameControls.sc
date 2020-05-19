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
			eachElementDo: #lowlightColor (EGAOrVGA myRgtBordColor myInsideColor)
		)
	)
)

(instance gcWin of BorderWindow
	
	(method (init)
		(gcWin
			color: myTextColor
			back: (EGAOrVGA myBackColor myBotBordColor)
			topBordColor: myTopBordColor
			lftBordColor: (EGAOrVGA myLftBordColor myTopBordColor)
			rgtBordColor: (EGAOrVGA myRgtBordColor myInsideColor)
			botBordColor: (EGAOrVGA myBotBordColor myInsideColor)
		)
	)
	
	(method (open &tmp theBevelWid t l b r theColor
			theMaps bottomColor topColor leftColor rightColor thePri i
			[scoreBuf 15] [scoreRect 4]
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
		(= r (+ (= t (+ 46 (CelHigh 947 0 1))) 13))
		(= b
			(+
				(= l (+ 10 (CelWide 947 1 1)))
				(-
					(+ 151 (CelWide 947 0 1))
					(+ 10 (CelWide 947 1 1) 6)
				)
			)
		)
		(= thePri 15)
		(= theColor 0)
		(= bottomColor (EGAOrVGA myBotBordColor myBotBordColor))
		(= rightColor (EGAOrVGA myRgtBordColor myBotBordColor))
		(= leftColor (EGAOrVGA myLftBordColor myTopBordColor))
		(= topColor myTopBordColor)
		(= theBevelWid 3)
		(= theMaps 3)
		(Graph GFillRect t l (+ r 1) (+ b 1) theMaps theColor thePri)
		(= t (- t theBevelWid))
		(= l (- l theBevelWid))
		(= b (+ b theBevelWid))
		(= r (+ r theBevelWid))
		(Graph GFillRect t l (+ t theBevelWid) b theMaps bottomColor thePri)
		(Graph GFillRect (- r theBevelWid) l r b theMaps topColor thePri)
		(= i 0)
		(while (< i theBevelWid)
			(Graph GDrawLine (+ t i) (+ l i) (- r (+ i 1)) (+ l i) rightColor thePri -1)
			(Graph GDrawLine (+ t i) (- b (+ i 1)) (- r (+ i 1)) (- b (+ i 1)) leftColor thePri -1)
			(++ i)
		)
		(Graph GShowBits t l (+ r 1) (+ b 1) 1)
		(Format @scoreBuf "Score: %d of %d" score possibleScore)
		(TextSize @scoreRect @scoreBuf 999 0)
		(Display @scoreBuf
			p_font 999
			p_color (EGAOrVGA myBackColor myTopBordColor)
			p_at
			(+ 10
				(CelWide 947 1 1)
				(/
					(-
						(-
							(+ 151 (CelWide 947 0 1))
							(+ 10 (CelWide 947 1 1) 6)
						)
						[scoreRect 3]
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
		signal (| FIXED_POSN RELSEND)
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
		signal FIXED_POSN
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
		signal (| FIXED_POSN RELSEND)
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
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
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
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
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
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
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
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
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
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
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
		message verbHelp
		signal (| VICON FIXED_POSN RELVERIFY IMMEDIATE)
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
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr {Returns you to the game.}
	)
)
