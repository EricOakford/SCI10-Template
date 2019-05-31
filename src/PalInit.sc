;;; Sierra Script 1.0 - (do not remove this comment)
(script# 802)
(include system.sh) (include sci2.sh)
(use Main)

(public
	PalInit 0
)

(procedure (PalInit)
	(if (> (Graph GDetect) 16)
		(= myTextColor (Palette PALMatch 31 31 31))
		(= myEGABordColor (Palette PALMatch 63 63 63))
		(= myEGABackColor (Palette PALMatch 95 95 95))
		(= myVGABordColor (Palette PALMatch 127 127 127))
		(= myVGABackColor (Palette PALMatch 159 159 159))
		(= myVGABordColor2 (Palette PALMatch 191 191 191))
		(= myEGABordColor2 (Palette PALMatch 223 223 223))
		(= myHighlightColor (Palette PALMatch 187 35 35))
	else
		(= myTextColor vBLACK)
		(= myEGABordColor vLGREY)
		(= myEGABackColor vGREY)
		(= myEGABordColor2 vWHITE)
	)
)
