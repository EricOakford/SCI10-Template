;;; Sierra Script 1.0 - (do not remove this comment)
(script# 802)
(include system.sh) (include sci2.sh)
(use Main)

(public
	PalInit 0
)

(procedure (PalInit)
	(if (> (Graph GDetect) 16)
		(= global129 (Palette 5 31 31 31))
		(= global155 (Palette 5 63 63 63))
		(= global156 (Palette 5 95 95 95))
		(= global157 (Palette 5 127 127 127))
		(= global158 (Palette 5 159 159 159))
		(= global161 (Palette 5 191 191 191))
		(= global130 (Palette 5 223 223 223))
		(= global131 (Palette 5 151 27 27))
		(= global132 (Palette 5 231 103 103))
		(= global133 (Palette 5 235 135 135))
		(= global134 (Palette 5 187 187 35))
		(= global135 (Palette 5 219 219 39))
		(= global136 (Palette 5 223 223 71))
		(= global150 (Palette 5 27 151 27))
		(= global137 (Palette 5 71 223 71))
		(= global138 (Palette 5 135 235 135))
		(= global139 (Palette 5 23 23 119))
		(= global149 (Palette 5 35 35 187))
		(= global152 (Palette 5 71 71 223))
		(= global153 (Palette 5 135 135 235))
		(= global140 (Palette 5 219 39 219))
		(= global141 (Palette 5 27 151 151))
		(= global142 (Palette 5 187 35 35))
		(= global143 (Palette 5 255 100 100))
		(= global144 (Palette 5 151 27 27))
		(= global145 (Palette 5 219 127 39))
		(= global146 (Palette 5 231 231 103))
		(= global147 (Palette 5 15 87 87))
		(= global148 (Palette 5 27 27 151))
	else
		(= global129 0)
		(= global149 1)
		(= global150 2)
		(= global141 3)
		(= global131 4)
		(= global140 5)
		(= global135 6)
		(= global155 7)
		(= global156 8)
		(= global152 9)
		(= global137 10)
		(= global189 11)
		(= global132 12)
		(= global190 13)
		(= global136 14)
		(= global130 15)
		(= global142 global131)
		(= global143 global132)
		(= global144 global135)
		(= global145 global135)
		(= global146 global135)
		(= global147 global135)
		(= global148 global135)
	)
)
