;;; Sierra Script 1.0 - (do not remove this comment)
;
;	COLORINIT.SC
;
;	This script initializes the global variables for colors.
;	It gathers all the color assignments based on their proportions of
;	red, green, and blue, and thus will display the correct color regardless
;	of the palette or graphics driver.
;
;	SCI1.0 requires separate interpreters for EGA and VGA.
;

(script# COLOR_INIT)
(include game.sh)
(use Main)
(use Procs)
(use System)

(public
	colorInit 0
)

(instance colorInit of Code
	(method (doit)
		;initialize the colors
		
		;VGA colors
		(if (> (Graph GDetect) 16)
			(Bset fIsVGA)
			(= colBlack 	(Palette PALMatch 31 31 31))
			(= colGray1 	(Palette PALMatch 63 63 63))
			(= colGray2 	(Palette PALMatch 95 95 95))
			(= colGray3 	(Palette PALMatch 127 127 127))
			(= colGray4 	(Palette PALMatch 159 159 159))
			(= colGray5 	(Palette PALMatch 191 191 191))
			(= colWhite		(Palette PALMatch 223 223 223))
			(= colDRed		(Palette PALMatch 151  27  27))
			(= colLRed		(Palette PALMatch 231 103 103))
			(= colVLRed		(Palette PALMatch 235 135 135))
			(= colDYellow	(Palette PALMatch 187 187 35))
			(= colYellow	(Palette PALMatch 219 219 39))
			(= colLYellow	(Palette PALMatch 223 223 71))
			(= colDGreen	(Palette PALMatch 27 151 27))
			(= colLGreen	(Palette PALMatch 71 223 71))
			(= colVLGreen	(Palette PALMatch 135 235 135))
			(= colDBlue		(Palette PALMatch 23 23 119))
			(= colBlue		(Palette PALMatch 35 35 187))
			(= colLBlue		(Palette PALMatch 71 71 223))
			(= colVLBlue	(Palette PALMatch 135 135 235))
			(= colMagenta	(Palette PALMatch 219 39 219))
			(= colLMagenta	(Palette PALMatch 223 71 223))
			(= colCyan		(Palette PALMatch 71 223 223))
			(= colLCyan		(Palette PALMatch 135 235 235))
		else
		
		;EGA colors
			(Bclr fIsVGA)
			(= colBlack vBLACK)
			(= colBlue vBLUE)
			(= colDGreen vGREEN)
			(= colLMagenta vCYAN)
			(= colDRed vRED)
			(= colMagenta vMAGENTA)
			(= colYellow vBROWN)
			(= colGray1 vLGREY)
			(= colGray2 vGREY)
			(= colLBlue vLBLUE)
			(= colLGreen vLGREEN)
			(= colLCyan vLCYAN)
			(= colLRed vLRED)
			(= colLMagenta vLMAGENTA)
			(= colLYellow vYELLOW)
			(= colWhite vWHITE)
		)
	)
)