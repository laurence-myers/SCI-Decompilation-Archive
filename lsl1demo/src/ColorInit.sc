;;; Sierra Script 1.0 - (do not remove this comment)
(script# COLORINIT)
(include game.sh)
(use Main)

(public
	ColorInit 0
)

(procedure (ColorInit)
	(if (> (Graph GDetect) 16)
		(= colBlack (Palette PALMatch 31 31 31))
		(= colBlue (Palette PALMatch 103 103 231))
		(= colGreen (Palette PALMatch 103 231 103))
		(= colRed (Palette PALMatch 219 39 39))
		(= colMagenta (Palette PALMatch 187 35 187))
		(= colYellow (Palette PALMatch 231 231 103))
		(= colWhite (Palette PALMatch 255 255 255))
		(= colDGreen (Palette PALMatch 27 151 27))
		(= colLGreen (Palette PALMatch 103 231 103))
		(= colDRed (Palette PALMatch 119 23 23))
		(= colDBlue (Palette PALMatch 23 23 119))
		(= colGray1 (Palette PALMatch 63 63 63))
		(= colGray2 (Palette PALMatch 95 95 95))
		(= colGray3 (Palette PALMatch 127 127 127))
		(= colGray4 (Palette PALMatch 159 159 159))
		(= colGray5 (Palette PALMatch 191 191 191))
		(= colGray6 (Palette PALMatch 223 223 223))
	else
		(= colBlack vBLACK)
		(= colBlue vLBLUE)
		(= colGreen vLGREEN)
		(= colRed vLRED)
		(= colMagenta vLMAGENTA)
		(= colYellow vYELLOW)
		(= colWhite vWHITE)
	)
)
