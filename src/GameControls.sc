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
(use Window)
(use IconBar)
(use Procs)
(use SlideIcon)
(use System)

(public
	gcCode 0
)

(instance gcCode of Code
	(method (doit)
		(gcWin
			color: colBlack
			back: (FindColor colGray4 colGray2)
			topBordColor: colWhite
			lftBordColor: (FindColor colGray5 colWhite)
			rgtBordColor: (FindColor colGray3 colGray1)
			botBordColor: (FindColor colGray2 colGray1)
		)
		((= gameControls GameControls)
			window: gcWin
			add:
				iconOk
				(detailSlider
					theObj: theGame
					selector: #detailLevel
					yourself:
				)
				(volumeSlider
					theObj: theGame
					selector: #masterVolume
					yourself:
				)
				(speedSlider
					theObj: theGame
					selector: #setSpeed
					yourself:
				)
				(iconSave
					theObj: theGame
					selector: #save
					yourself:
				)
				(iconRestore
					theObj: theGame
					selector: #restore
					yourself:
				)
				(iconRestart
					theObj: theGame
					selector: #restart
					yourself:
				)
				(iconQuit
					theObj: theGame
					selector: #quitGame
					yourself:
				)
				(iconAbout
					theObj: theGame
					selector: #showAbout
					yourself:
				)
				iconControlHelp
			helpIconItem: iconControlHelp
			curIcon: iconRestore
			okButton: iconOk
			eachElementDo: #highlightColor 0
			eachElementDo: #lowlightColor (FindColor colGray4 colGray1)
			state: NOCLICKHELP
		)
	)
)

(define	SLIDER_TOP	37)
(define SLIDER_LEFT	67)
(define BUTTON_TOP 6)
(define BUTTON_LEFT 8)

(instance gcWin of BorderWindow
	(method (open &tmp
			theBevelWid t l r b theColor theMaps bottomColor topColor leftColor rightColor
			thePri i [str 15] [rectPt 4])
		(self
			top: (/ (- SCRNHIGH (+ (CelHigh vGameControls lControlFixtures 1) 6)) 2)
			left: (/ (- SCRNWIDE (+ 151 (CelWide vGameControls lSliderText 1))) 2)
			bottom:
				(+
					(CelHigh vGameControls lControlFixtures 1)
					6
					(/ (- SCRNHIGH (+ (CelHigh vGameControls lControlFixtures 1) 6)) 2)
				)
			right:
				(+
					151
					(CelWide vGameControls lSliderText 1)
					(/ (- SCRNWIDE (+ 151 (CelWide vGameControls lSliderText 1))) 2)
				)
			priority: 15
		)
		(super open:)
		
		; Game Paused text
		(DrawCel vGameControls lSliderText 5
			(+
				(/
					(-
						(- (+ 151 (CelWide vGameControls lSliderText 1)) (+ 4 (CelWide vGameControls lControlFixtures 1)))
						(CelWide vGameControls lSliderText 5)
					)
					2
				)
				4
				(CelWide vGameControls lControlFixtures 1)
			)
			3
			15
		)
		; Box for buttons
		(DrawCel vGameControls lControlFixtures 1 4 3 15)
		; 1st arrow between sliders
		(DrawCel vGameControls lControlFixtures 0 94 38 15)
		; 2nd arrow between sliders
		(DrawCel vGameControls lControlFixtures 0 135 38 15)
		; Detail text
		(DrawCel vGameControls lSliderText 4 63 (- SLIDER_TOP (+ (CelHigh vGameControls lSliderText 4) 3)) 15)
		; Volume text
		(DrawCel vGameControls lSliderText 3 101 (- SLIDER_TOP (+ (CelHigh vGameControls lSliderText 4) 3)) 15)
		; Speed text
		(DrawCel vGameControls lSliderText 2 146 (- SLIDER_TOP (+ (CelHigh vGameControls lSliderText 4) 3)) 15)
		(Graph GShowBits 12 1 15 (+ 151 (CelWide vGameControls lSliderText 1)) 1)
		(= b (+ (= t (+ 46 (CelHigh vGameControls lSliderText 1))) 13))
		(= r
			(+
				(= l (+ 10 (CelWide vGameControls lControlFixtures 1)))
				(-
					(+ 151 (CelWide vGameControls lSliderText 1))
					(+ 10 (CelWide vGameControls lControlFixtures 1) 6)
				)
			)
		)
		(= thePri 15)
		(= theColor 0)
		(= bottomColor (FindColor colGray2 colGray1))
		(= rightColor (FindColor colGray3 colGray1))
		(= leftColor (FindColor colGray5 colWhite))
		(= topColor colWhite)
		(= theBevelWid 3)
		(= theMaps 3)
		(Graph GFillRect t l (+ b 1) (+ r 1) theMaps theColor thePri)
		(-= t theBevelWid)
		(-= l theBevelWid)
		(+= r theBevelWid)
		(+= b theBevelWid)
		(Graph GFillRect t l (+ t theBevelWid) r theMaps bottomColor thePri)
		(Graph GFillRect (- b theBevelWid) l b r theMaps topColor thePri)
		(for ((= i 0)) (< i theBevelWid) ((++ i))
			(Graph GDrawLine (+ t i) (+ l i) (- b (+ i 1)) (+ l i) rightColor thePri -1)
			(Graph GDrawLine (+ t i)(- r (+ i 1)) (- b (+ i 1)) (- r (+ i 1)) leftColor thePri -1)
		)
		(Graph GShowBits t l (+ b 1) (+ r 1) 1)
		(Format @str "Score: %d of %d" score possibleScore)
		(TextSize @rectPt @str 999 0)
		(Display
			@str
			p_font 999
			p_color (FindColor colGray4 colWhite)
			p_at
			(+ 10 (CelWide vGameControls lControlFixtures 1)
				(/
					(-
						(-
							(+ 151 (CelWide vGameControls lSliderText 1))
							(+ 10 (CelWide vGameControls lControlFixtures 1) 6)
						)
						[rectPt 3]
					)
					2
				)
			)
			(+ 46 (CelHigh vGameControls lSliderText 1) 3)
		)
	)
)

(instance detailSlider of Slider
	(properties
		view vGameControls
		loop lSliderText
		cel 1
		nsLeft SLIDER_LEFT
		nsTop SLIDER_TOP
		signal (| FIXED_POSN RELSEND)
		helpStr "Raise this to increase the amount of background animation.
				Lower it if game play seems sluggish."
		sliderView vGameControls
		topValue 3
	)
)

(instance volumeSlider of Slider
	(properties
		view vGameControls
		loop lSliderText
		cel 1
		nsLeft (+ SLIDER_LEFT 40)
		nsTop SLIDER_TOP
		signal FIXED_POSN
		helpStr "This adjusts the volume on some sound boards and synthesizers."
		sliderView vGameControls
		topValue 15
	)
)

(instance speedSlider of Slider
	(properties
		view vGameControls
		loop lSliderText
		cel 1
		nsLeft (+ SLIDER_LEFT (* 2 40))
		nsTop SLIDER_TOP
		signal (| FIXED_POSN RELSEND)
		helpStr "This adjusts your character's speed, within the limits of your computer's capabilities."
		sliderView vGameControls
		bottomValue 15
	)
)

(instance iconSave of ControlIcon
	(properties
		view vGameControls
		loop lSaveButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop BUTTON_TOP
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to save the current state of your game.
				When you later select Restore, everything will be exactly as it is now."
	)
)

(instance iconRestore of ControlIcon
	(properties
		view vGameControls
		loop lRestoreButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP 20)
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "This restores a game you saved earlier."
	)
)

(instance iconRestart of ControlIcon
	(properties
		view vGameControls
		loop lRestartButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP (* 20 2))
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to restart the game from the very beginning."
	)
)

(instance iconQuit of ControlIcon
	(properties
		view vGameControls
		loop lQuitButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP (* 20 3))
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to leave the game."
	)
)

(instance iconAbout of ControlIcon
	(properties
		view vGameControls
		loop lAboutButton
		cel 0
		nsLeft (+ BUTTON_LEFT 26)
		nsTop (+ BUTTON_TOP (* 20 5))
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Here's where you learn more than you care to know about the creators of this game."
	)
)

(instance iconControlHelp of IconItem
	(properties
		view vGameControls
		loop lHelpButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP (* 20 5))
		cursor HELP_CURSOR
		message verbHelp
		signal (| VICON FIXED_POSN RELVERIFY IMMEDIATE)
		helpStr "To learn about the other items in this window,
				first click here, then pass the question mark
				over the other items."
	)
)

(instance iconOk of IconItem
	(properties
		view vGameControls
		loop lOKButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP (* 20 4))
		message gameVerbs
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to exit this menu and resume game play."
	)
)
