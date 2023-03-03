;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMECONTROLS.SC
;
;	This script contains the game's control panel and its controls.
;
;
(script# GAME_CONTROLS)
(include game.sh) (include language.sh)
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
			back: (FindColor colGray4 colGray1)
			topBordColor: colWhite
			lftBordColor: (FindColor colGray5 colWhite)
			rgtBordColor: (FindColor colGray3 colGray2)
			botBordColor: colGray2
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
(define SLIDDIST 40)
(define BUTTON_TOP 6)
(define BUTTON_LEFT 8)
(define BUTTON_DIST	20)
(define SCORE_TOP	13)


(instance gcWin of BorderWindow
	(method (open &tmp
			savePort theBevelWid t l r b theColor theMaps
			bottomColor topColor leftColor rightColor thePri i
			[str 40] [rectPt 4] theView
		)
		(self
			top: (/ (- SCRNHIGH (+ (CelHigh (FindGameControls) lControlFixtures 1) 6)) 2)
			left:
				(/
					(-
						SCRNWIDE
						(+
							151
							(CelWide (FindGameControls) lSliderText 1)
							(FindLanguage 10 10 10 10 0)
						)
					)
					2
				)
			bottom:
				(+
					(CelHigh (FindGameControls) lControlFixtures 1)
					6
					(/ (- SCRNHIGH (+ (CelHigh (FindGameControls) lControlFixtures 1) 6)) 2)
				)
			right:
				(+
					151
					(CelWide (FindGameControls) lSliderText 1)
					(FindLanguage 10 10 10 10 0)
					(/
						(-
							SCRNWIDE
							(+
								151
								(CelWide (FindGameControls) lSliderText 1)
								(FindLanguage 10 10 10 10 0)
							)
						)
						2
					)
				)
			priority: -1
		)
		(super open:)
		(= thePri -1)
		
		; Game Paused text
		(DrawCel
			(FindGameControls) 0 5
			(+
				(/
					(-
						(-
							(+
								151
								(CelWide (FindGameControls) lSliderText 1)
								(FindLanguage 10 10 10 10 0)
							)
							(+ 4 (CelWide (FindGameControls) lControlFixtures 1))
						)
						(CelWide (FindGameControls) lSliderText 5)
					)
					2
				)
				4
				(CelWide (FindGameControls) lControlFixtures 1)
			)
			(FindLanguage 2 6 6 6 6)
			thePri
		)
		; Box for buttons
		(DrawCel (FindGameControls) lControlFixtures 1 4 3 thePri)
		; 1st arrow between sliders
		(DrawCel (FindGameControls) lControlFixtures 0 94 38 thePri)
		; 2nd arrow between sliders
		(DrawCel (FindGameControls) lControlFixtures 0 135 38 thePri)
		; Detail text
		(DrawCel (FindGameControls) lSliderText 4
			(- SLIDER_LEFT (FindLanguage 0 0 2 4 4))
			(-
				(- SLIDER_TOP (+ (CelHigh (FindGameControls) lSliderText 4) 3))
				(FindLanguage -4 0 4 0 0)
			)
			thePri
		)
		; Volume text
		(DrawCel (FindGameControls) lSliderText 3
			(- (+ SLIDER_LEFT 40) (FindLanguage 3 6 6 6 6))
			(-
				(- SLIDER_TOP (+ (CelHigh (FindGameControls) lSliderText 4) 3))
				(FindLanguage -4 0 4 0 0)
			)
			thePri
		)
		; Speed text
		(DrawCel (FindGameControls) lSliderText 2
			(- (+ SLIDER_LEFT 80) (FindLanguage 14 5 5 1 1))
			(-
				(- SLIDER_TOP (+ (CelHigh (FindGameControls) lSliderText 4) 3))
				(FindLanguage -4 0 4 0 0)
			)
			thePri
		)
		
		;Now draw the window below the sliders for score
		(= b
			(+
				(= t (+ 46 (CelHigh (FindGameControls) lSliderText 1)))
				SCORE_TOP
			)
		)
		(= r
			(+
				(= l (+ 10 (CelWide (FindGameControls) lControlFixtures 1)))
				(-
					(+
						151
						(CelWide (FindGameControls) lSliderText 1)
						(FindLanguage 10 10 10 10 0)
					)
					(+ 10 (CelWide (FindGameControls) lControlFixtures 1) 6)
				)
			)
		)
		(= theColor 0)
		(= bottomColor colGray2)
		(= rightColor (FindColor colGray3 colGray2))
		(= leftColor (FindColor colGray5 colWhite))
		(= topColor colWhite)
		(= theBevelWid 3)
		(= theMaps VMAP)
		
		;draw the bevels
		(Graph GFillRect t l (+ b 1) (+ r 1) theMaps theColor thePri)
		(-= t theBevelWid)
		(-= l theBevelWid)
		(+= r theBevelWid)
		(+= b theBevelWid)
		(Graph GFillRect t l (+ t theBevelWid) r theMaps bottomColor thePri)
		(Graph GFillRect (- b theBevelWid) l b r theMaps topColor thePri)
		(for ((= i 0)) (< i theBevelWid) ((++ i))
			(Graph GDrawLine (+ t i) (+ l i) (- b (+ i 1)) (+ l i) rightColor thePri -1)
			(Graph GDrawLine (+ t i) (- r (+ i 1)) (- b (+ i 1)) (- r (+ i 1)) leftColor thePri -1)
		)
		(Graph GShowBits t l (+ b 1) (+ r 1) VMAP)
		
		;print the score centered in its window.
		;Here is also an example of strings split by language
		; done in the kernel.
		(Format @str
			"Score: %d of %d"			;English
			;#GPunkte: %d von %d"		;German
			;#SPuntuaci¢n: %d de %d"		;Spanish
			;#FScore: %d de %d"			;French
			score possibleScore score possibleScore)
		(TextSize @rectPt @str 999 0)
		(Display @str
			p_font 999
			p_color (FindColor colGray4 colWhite)
			p_at
			(+
				10
				(CelWide (FindGameControls) lControlFixtures 1)
				(/
					(-
						(-
							(+
								151
								(CelWide (FindGameControls) lSliderText 1)
								(FindLanguage 10 10 10 10 0)
							)
							(+ 10 (CelWide (FindGameControls) lControlFixtures 1) 6)
						)
						[rectPt 3]
					)
					2
				)
			)
			(+ 46 (CelHigh (FindGameControls) lSliderText 1) 3)
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
		nsLeft (+ SLIDER_LEFT SLIDDIST)
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
		nsLeft (+ SLIDER_LEFT (* 2 SLIDDIST))
		nsTop SLIDER_TOP
		signal (| FIXED_POSN RELSEND)
		helpStr "This adjusts your character's speed, within the limits of your computer's capabilities."
		sliderView vGameControls
		bottomValue 15
	)
	
	(method (show)
		(if (not (user controls?))
			(= sliderCel 6)
			(= signal (| FIXED_POSN DISABLED))
		else
			(= sliderCel 0)
			(= signal FIXED_POSN)
		)
		(super show: &rest)
	)
	
	(method (mask)
	)
	
	(method (move)
		(if (user controls?) (super move: &rest))
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
	
	(method (show)
		(= view (FindGameControls))
		(super show: &rest)
	)

)

(instance iconRestore of ControlIcon
	(properties
		view vGameControls
		loop lRestoreButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP BUTTON_DIST)
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "This restores a game you saved earlier."
	)
	
	(method (show)
		(= view (FindGameControls))
		(super show: &rest)
	)
)

(instance iconRestart of ControlIcon
	(properties
		view vGameControls
		loop lRestartButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP (* BUTTON_DIST 2))
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to restart the game from the very beginning."
	)
	
	(method (show)
		(= view (FindGameControls))
		(super show: &rest)
	)
)

(instance iconQuit of ControlIcon
	(properties
		view vGameControls
		loop lQuitButton
		cel 0
		nsLeft BUTTON_LEFT
		nsTop (+ BUTTON_TOP (* BUTTON_DIST 3))
		message verbNone
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to leave the game."
	)
	
	(method (show)
		(= view (FindGameControls))
		(super show: &rest)
	)
)

(instance iconAbout of ControlIcon
	(properties
		view vGameControls
		loop lAboutButton
		cel 0
		nsLeft (+ BUTTON_LEFT 26)
		nsTop (+ BUTTON_TOP (* BUTTON_DIST 5))
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
		nsTop (+ BUTTON_TOP (* BUTTON_DIST 5))
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
		nsTop (+ BUTTON_TOP (* BUTTON_DIST 4))
		message gameVerbs
		signal (| VICON FIXED_POSN HIDEBAR RELVERIFY IMMEDIATE)
		helpStr "Use this to exit this menu and resume game play."
	)
	
	(method (show)
		(= view (FindGameControls))
		(super show: &rest)
	)
)
